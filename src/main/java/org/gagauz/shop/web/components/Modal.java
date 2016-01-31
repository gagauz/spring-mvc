package org.gagauz.shop.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Modal {
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String id;

}
