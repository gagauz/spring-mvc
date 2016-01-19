package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Sms;
import org.gagauz.shop.database.model.enums.SmsStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SmsDao extends AbstractDao<Integer, Sms> {

    public List<Sms> findUnsent() {
        return createQuery("select s from Sms s where status not in :status").setParameterList("status",
                Arrays.asList(SmsStatus.SENT_OK, SmsStatus.NO_RECIPIENTS)).list();
    }

}
