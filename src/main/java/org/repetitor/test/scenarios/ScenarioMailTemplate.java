package org.repetitor.test.scenarios;

import org.repetitor.database.dao.MessageTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScenarioMailTemplate extends DataBaseScenario {
    @Autowired
    private MessageTemplateDao mailTemplateDao;

    @Override
    @Transactional
    protected void execute() {
        //        MessageTemplate mailTemplate = new MessageTemplate();
        //        mailTemplate.setType(MessageType.CONTACT_SENT);
        //        mailTemplate.setSubjectTemplate("Вам отправлены контактные данные ученика");
        //        mailTemplate.setBodyTemplate("<html>" +
        //                "<header></header>" +
        //                "<body>" +
        //                "Here is mail content!!!!" +
        //                "</body>" +
        //                "</html>");
        //        mailTemplateDao.save(mailTemplate);
        //
        //        mailTemplate = new MessageTemplate();
        //        mailTemplate.setType(MessageType.CONTACT_SENT);
        //        mailTemplate.setSubjectTemplate("TutorFinder");
        //        mailTemplate.setSms(true);
        //        mailTemplate
        //                .setBodyTemplate("Контактные данные по заявке #${order.id}: ${order.pupil.name}<#if order.pupil.email??>, ${order.pupil.email}</#if><#if order.pupil.phone??>, ${order.pupil.phone}</#if>.");
        //        mailTemplateDao.save(mailTemplate);
    }
}
