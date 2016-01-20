package org.gagauz.shop.web.services;

import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.gagauz.shop.database.dao.AdminDao;
import org.gagauz.shop.database.dao.BuyerDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.model.AbstractUser;
import org.gagauz.shop.database.model.Admin;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.web.services.security.AccessAttributeImpl;
import org.gagauz.shop.web.services.security.AdminCredentials;
import org.gagauz.shop.web.services.security.BuyerCredentials;
import org.gagauz.shop.web.services.security.CredentialsImpl;
import org.gagauz.shop.web.services.security.Secured;
import org.gagauz.shop.web.services.security.SellerCredentials;
import org.gagauz.tapestry.security.AccessDeniedException;
import org.gagauz.tapestry.security.UserSet;
import org.gagauz.tapestry.security.api.AccessAttributeExtractorChecker;
import org.gagauz.tapestry.security.api.UserProvider;
import org.gagauz.tapestry.web.services.CoreWebappModule;

@ImportModule({CoreWebappModule.class})
public class AppModule {
    public static AccessAttributeExtractorChecker buildAccessAttributeExtractorChecker(final ApplicationStateManager applicationStateManager) {
        return new AccessAttributeExtractorChecker<AccessAttributeImpl>() {

            @Override
            public void check(AccessAttributeImpl attribute) throws AccessDeniedException {
                if (null != attribute) {
                    UserSet<AbstractUser> userSet = applicationStateManager.getIfExists(UserSet.class);
                    if (null != userSet) {
                        for (AbstractUser user : userSet) {
                            if (user.hasRole(attribute.getRoles())) {
                                return;
                            }
                        }
                    }
                }
                throw new AccessDeniedException(attribute);
            }

            @Override
            public AccessAttributeImpl extract(PlasticClass plasticClass) {
                Secured annotation = plasticClass.getAnnotation(Secured.class);
                if (null != annotation) {
                    return new AccessAttributeImpl(annotation.value());
                }
                return null;
            }

            @Override
            public AccessAttributeImpl extract(PlasticClass plasticClass, PlasticMethod plasticMethod) {
                Secured annotation = plasticMethod.getAnnotation(Secured.class);
                if (null != annotation) {
                    return new AccessAttributeImpl(annotation.value());
                }
                return null;
            }

        };
    }

    public static UserProvider buildUserProvider(final SellerDao sellerDao, final BuyerDao buyerDao, final AdminDao adminDao) {
        return new UserProvider<AbstractUser, CredentialsImpl>() {
            @Override
            public AbstractUser findByCredentials(CredentialsImpl arg0) {
                if (arg0 instanceof SellerCredentials) {
                    Seller seller = sellerDao.findByEmail(arg0.getUsername());
                    if (null != seller && seller.checkPassword(arg0.getPassword())) {
                        return seller;
                    }
                    return null;
                } else if (arg0 instanceof AdminCredentials) {
                    Admin seller = adminDao.findByEmail(arg0.getUsername());
                    if (null != seller && seller.checkPassword(arg0.getPassword())) {
                        return seller;
                    }
                    return null;
                } else if (arg0 instanceof BuyerCredentials) {
                    Buyer seller = buyerDao.findByEmail(arg0.getUsername());
                    if (null != seller && seller.checkPassword(arg0.getPassword())) {
                        return seller;
                    }
                    return null;
                }
                return null;
            }
        };
    }
}
