package org.repetitor.services;

import org.gagauz.utils.C;
import org.repetitor.database.dao.ConfigDao;
import org.repetitor.database.model.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigService.class);

    @Autowired
    private ConfigDao configDao;

    private Map<ConfigConstant, String> config;
    private Date lastUpdated;

    public String get(String string) {
        try {
            return config.get(ConfigConstant.valueOf(string));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String get(ConfigConstant name) {
        check();
        return config.get(name);
    }

    private void check() {
        Date now = new Date();
        if (null == config
                || null == lastUpdated
                || (now.getTime() - lastUpdated.getTime() > 60000)) {

            LOG.info("Update configurations");
            synchronized (this) {
                Map<ConfigConstant, String> map = C.newHashMap();
                for (Config config : configDao.findAll()) {
                    map.put(config.getName(), config.getValue());
                }
                config = map;
                lastUpdated = now;
            }
        }
    }

}
