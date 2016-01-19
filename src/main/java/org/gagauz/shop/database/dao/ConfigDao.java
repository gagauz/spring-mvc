package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Config;
import org.gagauz.shop.database.model.enums.ConfigType;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigDao extends AbstractDao<Integer, Config> {
    @SuppressWarnings("unchecked")
    public List<Config> findByType(ConfigType type) {
        return createCriteria().add(Restrictions.eq("type", type)).list();
    }
}
