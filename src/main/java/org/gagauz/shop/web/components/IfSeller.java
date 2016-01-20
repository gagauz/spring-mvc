package org.gagauz.shop.web.components;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.tapestry.security.UserSet;

public class IfSeller extends AbstractConditional {

    @SessionState(create = false)
    private UserSet userSet;

    @Override
    protected boolean test() {
        return null != userSet && userSet.stream().anyMatch(user -> user.getClass().equals(Seller.class));
    }

}
