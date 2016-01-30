package org.gagauz.shop.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractCsvImporter {

    abstract void init();

    abstract void process(String[] ids);

    abstract void commit();

    protected <T> void importFile(InputStream stream, String separator) {

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

}
