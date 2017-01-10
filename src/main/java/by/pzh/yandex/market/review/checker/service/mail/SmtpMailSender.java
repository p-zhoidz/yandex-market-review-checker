package by.pzh.yandex.market.review.checker.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

/**
 * SMTP implementation of the email sender.
 *
 * @author p.zhoidz.
 */
@Service("smtMailSender")
public class SmtpMailSender implements EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void send(Email mail) {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, mail.isMultipart(), "UTF-8");
            message.setTo(mail.getTo().toArray(new String[mail.getTo().size()]));
            message.setFrom(mail.getFrom());
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody(), mail.isHtml());
            javaMailSender.send(mimeMessage);
            LOGGER.debug("Sent e-mail to User '{}'", mail.getTo());
        } catch (Exception e) {
            LOGGER.warn("E-mail could not be sent to user '{}', exception is: {}", mail.getTo(), e.getMessage());
        }

    }
}
