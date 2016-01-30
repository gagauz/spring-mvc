package org.gagauz.shop.web.components;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

public class Price {

    private static final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();

    static {
        symbols.setGroupingSeparator(' ');
    }

    private static final DecimalFormat formatter = new DecimalFormat("###,###", symbols);

    @Parameter
    private BigDecimal value;

    void beginRender(MarkupWriter writer) {
        writer.writeRaw(formatter.format(value.floatValue()));
    }
}
