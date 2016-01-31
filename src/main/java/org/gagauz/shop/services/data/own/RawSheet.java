package org.gagauz.shop.services.data.own;

import org.gagauz.shop.services.data.DataRow;
import org.gagauz.shop.services.data.DataSheet;

public class RawSheet implements DataSheet {

    private final String[][] rowData;

    public RawSheet(String[][] sheet) {
        this.rowData = sheet;
    }

    @Override
    public int getFirstRowNum() {
        return 0;
    }

    @Override
    public int getLastRowNum() {
        return rowData.length - 1;
    }

    @Override
    public int getPhysicalNumberOfRows() {
        return rowData.length;
    }

    @Override
    public DataRow getRow(int r) {
        return new RawRow(rowData[r], r);
    }

}
