package org.gagauz.shop.services.data;

public interface DataRow {

    int getFirstCellNum();

    DataCell getCell(int i);

    int getRowNum();

}
