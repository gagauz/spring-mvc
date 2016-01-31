package org.gagauz.shop.services.data.own;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.gagauz.shop.services.data.DataCell;

public class RawCell implements DataCell {

    public static final DataCell EMPTY_CELL = new RawCell("");
    private final String data;

    public RawCell(String string) {
        this.data = null == string ? "" : string.trim();
    }

    @Override
    public String getString() {
        return data;
    }

    @Override
    public int getInt() {
        return NumberUtils.toInt(data);
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal(data);
    }

    @Override
    public String toString() {
        return data;
    }
}
