package org.webservice.scenarios;

import static org.webservice.services.ConfigConstant.CHRONOPAY_CLIENT_ID;
import static org.webservice.services.ConfigConstant.CHRONOPAY_SECRET;
import static org.webservice.services.ConfigConstant.MAIL_SMTP_HOST;
import static org.webservice.services.ConfigConstant.MAIL_SMTP_PORT;
import static org.webservice.services.ConfigConstant.SMS_PASS;
import static org.webservice.services.ConfigConstant.SMS_SUBJECT;
import static org.webservice.services.ConfigConstant.SMS_URL;
import static org.webservice.services.ConfigConstant.SMS_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webservice.database.dao.ConfigDao;
import org.webservice.database.model.Config;
import org.webservice.services.ConfigConstant;

@Service
public class ScenarioConfig extends DataBaseScenario {

    @Autowired
    private ConfigDao configDao;

    @Override
    @Transactional
    protected void execute() {

        create(SMS_USER, "ns454858API");
        create(SMS_PASS, "HUMeHx");
        create(SMS_URL, "http://gate.sms-manager.ru/_getsmsd.php");
        create(SMS_SUBJECT, "sms-manager");

        create(MAIL_SMTP_HOST, "localhost");
        create(MAIL_SMTP_PORT, "25");

        create(CHRONOPAY_CLIENT_ID, "007488-0001-0001");
        create(CHRONOPAY_SECRET, "73rHdumaqLExcEtwMUb6");

    }

    @Override
    protected DataBaseScenario[] getDependsOn() {
        return new DataBaseScenario[] {};
    }

    private void create(ConfigConstant key, String value) {
        Config config = new Config();
        config.setName(key);
        config.setValue(value);
        configDao.save(config);
    }

}
