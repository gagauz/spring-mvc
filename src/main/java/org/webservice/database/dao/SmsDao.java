package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Sms;
import org.webservice.database.model.enums.SmsStatus;

import java.util.Arrays;
import java.util.List;

@Service
public class SmsDao extends AbstractDao<Integer, Sms> {

    public List<Sms> findUnsent() {
        return createQuery("select s from Sms s where status not in :status").setParameterList("status",
                Arrays.asList(SmsStatus.SENT_OK, SmsStatus.NO_RECIPIENTS)).list();
    }

}
