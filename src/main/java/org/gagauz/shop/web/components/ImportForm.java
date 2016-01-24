package org.gagauz.shop.web.components;

import javax.persistence.criteria.From;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;

public class ImportForm {
    @Component
    private From form;

    @Parameter
    private int cols;

}
