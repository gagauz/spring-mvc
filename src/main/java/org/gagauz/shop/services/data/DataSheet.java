package org.gagauz.shop.services.data;

public interface DataSheet {

    int getFirstRowNum();

    int getLastRowNum();

    int getPhysicalNumberOfRows();

    DataRow getRow(int r);

}
