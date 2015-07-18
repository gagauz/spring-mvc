package org.repetitor.services.scheduled;

import org.repetitor.database.dao.SmsDao;
import org.repetitor.database.model.Sms;
import org.repetitor.services.ImobisSmsServeice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmsSendScheduler {

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private ImobisSmsServeice smsServeice;

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void sendSms() {
        List<Sms> unsent = smsDao.findUnsent();
        smsServeice.sendBatch(unsent);

    }
}
