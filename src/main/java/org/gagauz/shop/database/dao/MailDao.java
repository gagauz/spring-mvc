package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Mail;
import org.gagauz.shop.database.model.enums.MailStatus;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailDao extends AbstractDao<Integer, Mail> {
    @SuppressWarnings("unchecked")
    public List<Mail> findByStatus(MailStatus status, int limit) {
        return createCriteria().add(Restrictions.eq("status", status))
                .setFetchSize(limit).list();
    }
}
