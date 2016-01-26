package org.gagauz.shop.web.services;

import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.gagauz.shop.database.dao.AdminDao;
import org.gagauz.shop.database.dao.BuyerDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.model.AbstractUser;
import org.gagauz.shop.database.model.Admin;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.utils.CryptoUtils;
import org.gagauz.shop.web.services.security.*;
import org.gagauz.tapestry.security.UserSet;
import org.gagauz.tapestry.security.api.AccessAttributeExtractorChecker;
import org.gagauz.tapestry.security.api.Credentials;
import org.gagauz.tapestry.security.api.User;
import org.gagauz.tapestry.security.api.UserProvider;
import org.gagauz.tapestry.security.impl.CookieCredentials;
import org.gagauz.tapestry.web.components.BigDecimalField;
import org.gagauz.tapestry.web.services.CoreWebappModule;

@ImportModule({CoreWebappModule.class, ValueEncoderModule.class})
public class AppModule {

    @Decorate(serviceInterface = JavaScriptStackSource.class)
    public JavaScriptStackSource decorateJavaScriptStackSource(JavaScriptStackSource original) {
        return new JavaScriptStackSourceWrapper(original);
    }

    @ApplicationDefaults
    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru");
        configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
        configuration.add(SymbolConstants.ENABLE_HTML5_SUPPORT, true);
        configuration.add(SymbolConstants.COMBINE_SCRIPTS, true);
        configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, false);
        configuration.add(SymbolConstants.SECURE_ENABLED, true);
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        configuration.add(SymbolConstants.FORM_FIELD_CSS_CLASS, "form-control");
        configuration.add(SymbolConstants.ENCODE_LOCALE_INTO_PATH, false);
        configuration.add(SymbolConstants.CHARSET, "utf-8");
        configuration.add(SymbolConstants.EXCEPTION_REPORT_PAGE, "Error500");
    }

    public static AccessAttributeExtractorChecker buildAccessAttributeExtractorChecker() {
        return new AccessAttributeExtractorChecker<AccessAttributeImpl>() {

            @Override
            public boolean check(UserSet userSet, AccessAttributeImpl attribute) {
                if (null != userSet) {
                    for (User user : userSet) {
                        AbstractUser aUser = (AbstractUser) user;
                        if (aUser.hasRole(attribute.getRoles())) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public AccessAttributeImpl extract(PlasticClass plasticClass, PlasticMethod plasticMethod) {

                if (null == plasticMethod) {
                    Secured annotation = plasticClass.getAnnotation(Secured.class);
                    if (null != annotation) {
                        return new AccessAttributeImpl(annotation.value());
                    }
                    return null;
                }

                Secured annotation = plasticMethod.getAnnotation(Secured.class);
                if (null != annotation) {
                    return new AccessAttributeImpl(annotation.value());
                }
                return null;
            }

        };
    }

    public static UserProvider buildUserProvider(final SellerDao sellerDao, final BuyerDao buyerDao, final AdminDao adminDao) {
        return new UserProvider() {
            @Override
            public <U extends User, C extends Credentials> U fromCredentials(C arg0) {
                if (arg0 instanceof SellerCredentials) {
                    Seller seller = sellerDao.findByEmail(((SellerCredentials) arg0).getUsername());
                    if (null != seller && seller.checkPassword(((SellerCredentials) arg0).getPassword())) {
                        return (U) seller;
                    }
                    return null;
                } else if (arg0 instanceof AdminCredentials) {
                    Admin seller = adminDao.findByEmail(((AdminCredentials) arg0).getUsername());
                    if (null != seller && seller.checkPassword(((AdminCredentials) arg0).getPassword())) {
                        return (U) seller;
                    }
                    return null;
                } else if (arg0 instanceof BuyerCredentials) {
                    Buyer seller = buyerDao.findByEmail(((BuyerCredentials) arg0).getUsername());
                    if (null != seller && seller.checkPassword(((BuyerCredentials) arg0).getPassword())) {
                        return (U) seller;
                    }
                    return null;
                } else if (arg0 instanceof CookieCredentials) {
                    try {
                        String[] tokens = CryptoUtils.decryptArrayAES(((CookieCredentials) arg0).getValue());
                        CredentialsImpl cred = null;
                        if (tokens[2].equals(Seller.class.getSimpleName())) {
                            cred = new SellerCredentials();
                        } else if (tokens[2].equals(Admin.class.getSimpleName())) {
                            cred = new AdminCredentials();
                        } else if (tokens[2].equals(Buyer.class.getSimpleName())) {
                            cred = new BuyerCredentials();
                        }
                        if (null != cred) {
                            cred.setUsername(tokens[0]);
                            cred.setPassword(tokens[1]);
                            return fromCredentials(cred);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }

            @Override
            public <U extends User, C extends Credentials> C toCredentials(U arg0, Class<C> arg1) {
                if (arg1.equals(CookieCredentials.class)) {
                    AbstractUser user = (AbstractUser) arg0;
                    String value = CryptoUtils.encryptArrayAES(user.getEmail(), user.getPassword(), user.getClass().getSimpleName());
                    return (C) new CookieCredentials(value);
                }
                throw new IllegalStateException();
            }

        };
    }

    @Contribute(MarkupRenderer.class)
    public void contributeMarkupRenderer(final OrderedConfiguration<MarkupRendererFilter> configuration, final Environment environment) {

        MarkupRendererFilter validationDecorator = new MarkupRendererFilter() {
            @Override
            public void renderMarkup(final MarkupWriter markupWriter, MarkupRenderer renderer) {
                ValidationDecorator decorator = new BaseValidationDecorator() {
                    @Override
                    public void insideField(Field field) {
                        String c = field.getClass().getName();
                        if (BigDecimalField.class.getName().equals(c)) {
                            markupWriter.getElement().forceAttributes("type", "text");
                        }
                    }
                };
                environment.push(ValidationDecorator.class, decorator);
                renderer.renderMarkup(markupWriter);
                environment.pop(ValidationDecorator.class);
            }
        };

        configuration.override("ValidationDecorator", validationDecorator);
    }
}
