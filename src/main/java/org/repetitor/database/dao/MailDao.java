package org.repetitor.database.dao;

import org.hibernate.criterion.Restrictions;
import org.repetitor.database.model.Mail;
import org.repetitor.database.model.enums.MailStatus;
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
