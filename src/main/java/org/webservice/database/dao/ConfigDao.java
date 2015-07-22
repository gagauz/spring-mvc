package org.webservice.database.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.webservice.database.model.Config;
import org.webservice.database.model.enums.ConfigType;

import java.util.List;

@Service
public class ConfigDao extends AbstractDao<Integer, Config> {
    @SuppressWarnings("unchecked")
    public List<Config> findByType(ConfigType type) {
        return createCriteria().add(Restrictions.eq("type", type)).list();
    }
}
