package org.gagauz.shop.web.components;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.gagauz.tapestry.security.UserSet;

public class IfUser extends AbstractConditional {

    @SessionState(create = false)
    private UserSet userSet;

    @Override
    protected boolean test() {
        return null != userSet && !userSet.isEmpty();
    }

}
