package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.MessageTemplate;
import org.gagauz.shop.database.model.enums.MessageType;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Service
public class MessageTemplateDao extends AbstractDao<Integer, MessageTemplate> {
    @SuppressWarnings("unchecked")
    public List<MessageTemplate> findByType(MessageType type) {
        return createCriteria().add(Restrictions.eq("type", type)).list();
    }

    public MessageTemplate findByType(MessageType type, boolean isSms) {
        return (MessageTemplate) createCriteria().add(eq("type", type)).add(eq("sms", isSms)).uniqueResult();
    }
}
