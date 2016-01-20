package org.gagauz.shop.web.pages.seller;

import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class Settings {

}
