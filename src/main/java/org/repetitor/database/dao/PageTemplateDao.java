package org.repetitor.database.dao;

import org.hibernate.criterion.Restrictions;
import org.repetitor.database.model.PageTemplate;
import org.springframework.stereotype.Service;

@Service
public class  PageTemplateDao extends AbstractDao<Integer, PageTemplate> {
    public PageTemplate findByName(String name) {
        return (PageTemplate) createCriteria().add(Restrictions.eq("name", name)).uniqueResult();
    }
}
