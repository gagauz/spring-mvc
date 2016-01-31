package org.gagauz.shop.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.gagauz.shop.services.data.xls.XlsxParser;

public abstract class AbstractCsvImporter {

    abstract void init();

    abstract void process(String[] ids);

    abstract void commit();

    protected <T> void importCsvFile(InputStream stream, String separator) {

        init();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String l;
        try {
            while ((l = reader.readLine()) != null) {
                String[] ids = l.split(separator);
                process(ids);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        commit();
    }

    protected <T> void importXlsFile(File file) {

        init();

        try {
            Map<String, String[][]> map = XlsxParser.parse(file);
            for (Entry<String, String[][]> e : map.entrySet()) {
                for (String[] row : e.getValue()) {
                    process(row);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        commit();
    }

}
