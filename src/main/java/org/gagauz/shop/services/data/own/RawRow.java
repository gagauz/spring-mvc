package org.gagauz.shop.services.data.own;

import org.gagauz.shop.services.data.DataCell;
import org.gagauz.shop.services.data.DataRow;

public class RawRow implements DataRow {

    private final String[] strings;
    private final int num;

    public RawRow(String[] strings, int num) {
        this.strings = strings;
        this.num = num;
    }

    @Override
    public int getFirstCellNum() {
        return 0;
    }

    @Override
    public DataCell getCell(int i) {
        return i < strings.length ? new RawCell(strings[i]) : RawCell.EMPTY_CELL;
    }

    @Override
    public int getRowNum() {
        return num;
    }

}
