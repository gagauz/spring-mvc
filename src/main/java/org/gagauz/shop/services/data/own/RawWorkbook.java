package org.gagauz.shop.services.data.own;

import java.util.Iterator;
import java.util.Map;

import org.gagauz.shop.services.data.DataSheet;
import org.gagauz.shop.services.data.DataWorkbook;

public class RawWorkbook implements DataWorkbook {

    private final Map<String, String[][]> data;

    public RawWorkbook(Map<String, String[][]> data) {
        this.data = data;
    }

    @Override
    public DataSheet getSheetAt(int i) {
        Iterator<String[][]> it = data.values().iterator();
        for (int c = 0; c < data.size(); c++) {
            if (i == c) {
                return new RawSheet(it.next());
            }
            it.next();
        }
        return null;
    }
}
