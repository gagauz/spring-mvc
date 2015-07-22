package org.webservice.database.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.webservice.database.model.PageTemplate;

@Service
public class  PageTemplateDao extends AbstractDao<Integer, PageTemplate> {
    public PageTemplate findByName(String name) {
        return (PageTemplate) createCriteria().add(Restrictions.eq("name", name)).uniqueResult();
    }
}
