package org.gagauz.shop.web.services;

import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
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
import org.gagauz.tapestry.web.services.CoreWebappModule;
import org.gagauz.tapestry.web.services.security.CookieEncryptorDecryptor;

@ImportModule({CoreWebappModule.class})
public class AppModule {
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

    public static UserProvider buildUserProvider(final SellerDao sellerDao, final BuyerDao buyerDao, final AdminDao adminDao, final CookieEncryptorDecryptor cookieEncryptorDecryptor) {
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
                        if ()
                    } catch (Exception e) {

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
}
