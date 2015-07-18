package org.repetitor.database.dao;

import org.repetitor.database.model.RepetitorView;
import org.springframework.stereotype.Service;

@Service
public class RepetitorViewDao extends AbstractDao<Integer, RepetitorView> {

    public void insertView(int ip, int repetitorId) {
        createSQLQuery("REPLACE INTO `REPETITOR_VIEW` set ip=:ip, created=now(), repetitorId=:repetitorId").setInteger("ip", ip)
                .setInteger("repetitorId", repetitorId).executeUpdate();

    }

}
