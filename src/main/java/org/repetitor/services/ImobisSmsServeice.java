package org.repetitor.services;

import org.apache.tapestry5.SymbolConstants;
import org.repetitor.database.dao.SmsDao;
import org.repetitor.database.model.Sms;
import org.repetitor.database.model.enums.SmsStatus;
import org.repetitor.utils.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

import static org.repetitor.services.ConfigConstant.*;

@Service
public class ImobisSmsServeice {
    private static final Logger LOG = LoggerFactory.getLogger(ImobisSmsServeice.class);

    @Autowired
    private ConfigService configuration;

    @Autowired
    private SmsDao smsDao;

    public void sendBatch(Collection<Sms> smses) {

        for (Sms sms : smses) {

            if (sms.getStatus() == SmsStatus.SENT_OK) {
                LOG.error("Sms has already status SENT_OK. SMS.id [" + sms.getId() + "] phones [" + sms.getPhone() + "] subject [" + sms.getSubject()
                        + "] message [" + sms.getBody() + "]");
                continue;
            }

            try {

                if ("true".equals(System.getProperty(SymbolConstants.PRODUCTION_MODE, "false"))) {
                    RequestSender request = RequestSender.get(configuration.get(SMS_URL));
                    request.setParam("user", configuration.get(SMS_USER));
                    request.setParam("password", configuration.get(SMS_PASS));
                    request.setParam("sender", configuration.get(SMS_SUBJECT));
                    request.setParam("SMSText", sms.getBody());
                    request.setParam("GSM", sms.getPhoneString());

                    StringBuilder response = new StringBuilder();
                    request.execute(response);
                    updateStatus(sms, parseResult(response.toString(), sms));
                }

            } catch (Exception e) {
                LOG.error("Send error:", e);
            }
        }

        smsDao.save(smses);
    }

    private static SmsStatus parseResult(String response, Sms sms) {

        try {
            int status = Integer.parseInt(response);

            if (status < 0) {
                LOG.error("SMS.id [" + sms.getId() + "] response [" + response + "] phones [" + sms.getPhone() + "] subject [" + sms.getSubject() + "]");
            } else {
                return SmsStatus.SENT_OK;
            }

            switch (status) {
            case -11:
            case -10:
            case -5:
                return SmsStatus.AUTH_FAILED;

            case -2:
                return SmsStatus.NOT_ENOUGH_CREDITS;

            case -6:
            case -8:
                return SmsStatus.NO_RECIPIENTS;
            default:
                return SmsStatus.UNKNOWN;
            }

        } catch (Exception e) {
            if (sms != null) {
                LOG.error("Exception sending sms: " + sms.getId(), e);
            }

            LOG.error("SMSStatus parsing error: " + response);
            return SmsStatus.EXCEPTION;
        }
    }

    private void updateStatus(Sms sms, SmsStatus status) {
        sms.setStatus(status);
        sms.setUpdated(new Date());
    }
}
