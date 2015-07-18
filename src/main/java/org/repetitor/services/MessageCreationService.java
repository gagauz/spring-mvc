package org.repetitor.services;

import org.gagauz.utils.C;
import org.repetitor.database.dao.MailDao;
import org.repetitor.database.dao.SmsDao;
import org.repetitor.database.model.*;
import org.repetitor.database.model.enums.MessageType;
import org.repetitor.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Map;

@Service
public class MessageCreationService {

    @Autowired
    private FreemarkerService freemarkerService;

    @Autowired
    private MailDao mailDao;

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private ConfigService configService;

    public void createEmail(MessageType type, Order order, Repetitor repetitor, Pupil pupil) {
        createMessage(type, order, repetitor, pupil, false, true);
    }

    public void createSms(MessageType type, Order order, Repetitor repetitor, Pupil pupil) {
        createMessage(type, order, repetitor, pupil, true, false);
    }

    public void createSmsAndEmail(MessageType type, Order order, Repetitor repetitor, Pupil pupil) {
        createMessage(type, order, repetitor, pupil, true, true);
    }

    protected void createMessage(MessageType type, Order order, Repetitor repetitor, Pupil pupil,
                                 boolean isSms, boolean isMail) {
        Map<String, Object> map = C.newHashMap();
        if (null != order)
            map.put("order", order);
        if (null != repetitor)
            map.put("repetitor", repetitor);
        if (null != pupil)
            map.put("pupil", pupil);
        if (type == MessageType.LEAVE_COMMENT) {
            String hash = URLEncoder.encode(CryptoUtils.encryptArrayAES(order.getOwner().getEmail(), "" + order.getOwner().getId()));
            map.put("commentLink", "http://tutorfinder.ru/leavecomment/" + hash);
        }
        if (isSms) {
            Sms sms = freemarkerService.createSmsContent(type, map);
            if (null != repetitor && repetitor.getPhone1() != null) {
                sms.setPhoneString(repetitor.getPhone1());
            } else if (null != pupil && pupil.getPhone() != null) {
                sms.setPhoneString(pupil.getPhone());
            }

            if (null != sms.getPhone()) {
                sms.setRepetitor(repetitor);
                sms.setOrder(order);
                smsDao.save(sms);
            }
        }
        if (isMail) {

            String from = configService.get("MAIL_FROM_" + type.name());
            if (null == from) {
                from = configService.get(ConfigConstant.MAIL_FROM);
            }

            Mail mail = freemarkerService.createEmailContent(type, map);
            mail.setFrom(from);
            if (null != repetitor) {
                mail.setTo(repetitor.getEmail());
            } else if (null != pupil) {
                mail.setTo(pupil.getEmail());
            }
            mail.setRepetitor(repetitor);
            mail.setOrder(order);
            mailDao.save(mail);
        }
    }
}
