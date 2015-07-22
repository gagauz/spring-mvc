package org.webservice.services.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webservice.database.dao.ConfigDao;
import org.webservice.database.dao.MailDao;
import org.webservice.database.model.Config;
import org.webservice.database.model.Mail;
import org.webservice.database.model.enums.ConfigType;
import org.webservice.database.model.enums.MailStatus;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import java.util.List;
import java.util.Properties;

@Service
public class MailSendSheduler {

    private static final Logger LOG = LoggerFactory.getLogger(MailSendSheduler.class);

    @Autowired
    private MailDao mailDao;

    @Autowired
    private ConfigDao configDao;

    @Scheduled(cron = "59 */1 * * * *")
    @Transactional
    public void send() {
        final List<Mail> batchList = mailDao.findByStatus(MailStatus.CREATED, 50);
        for (Mail mail : batchList) {
            try {
                LOG.info("Trying to send email mail : {}", mail);
                sendMessage(mail);
                mail.setStatus(MailStatus.SENT);
            } catch (Exception e) {
                mail.setStatus(MailStatus.ERROR);
                LOG.error("Failed to create or send mail!", e);
            }
        }
        mailDao.save(batchList);
    }

    private void sendMessage(Mail mail) throws AddressException, MessagingException {
        final Properties props = getMailProps();
        Session session = Session.getDefaultInstance(getMailProps(),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.get("username").toString(), props
                                .get("password").toString());
                    }
                });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mail.getFrom()));
        if (mail.getFrom().startsWith("no-reply") || mail.getFrom().startsWith("noreply")) {
            message.setReplyTo(new Address[] {new InternetAddress(mail.getFrom())});
        }
        message.setRecipient(RecipientType.TO, new InternetAddress(mail.getTo()));
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody(), "utf-8", "html");
        Transport.send(message);
    }

    private Properties getMailProps() {
        Properties props = new Properties();
        for (Config config : configDao.findByType(ConfigType.MAIL)) {
            props.put(config.getName().toPropKey(), config.getValue());
        }

        return props;
    }
}
