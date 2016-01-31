package org.gagauz.shop.web.components;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SessionState;
import org.gagauz.shop.database.model.enums.Currency;

public class Price {

    private static final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
    private static final Map<Currency, Map<Currency, BigDecimal>> rates = new HashMap<>();

    static {
        symbols.setGroupingSeparator(' ');
        for (Currency c1 : Currency.values()) {
            Map<Currency, BigDecimal> map = new HashMap<>();
            rates.put(c1, map);
            for (Currency c2 : Currency.values()) {
                map.put(c2, BigDecimal.ONE);
            }
        }
    }

    private static final DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

    @Parameter
    private BigDecimal value;

    @Parameter(name = "currency")
    private Currency fromCurrency;

    @SessionState
    private Currency toCurrency;

    void beginRender(MarkupWriter writer) {
        writer.writeRaw(formatter.format(convert()));
    }

    private float convert() {
        if (fromCurrency.equals(toCurrency)) {
            return value.floatValue();
        }
        return rates.get(fromCurrency).get(toCurrency).multiply(value).floatValue();
    }
}
