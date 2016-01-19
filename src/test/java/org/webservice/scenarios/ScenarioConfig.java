package org.webservice.scenarios;

import org.gagauz.shop.database.dao.ConfigDao;
import org.gagauz.shop.database.model.Config;
import org.gagauz.shop.services.ConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gagauz.shop.services.ConfigConstant.*;

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
