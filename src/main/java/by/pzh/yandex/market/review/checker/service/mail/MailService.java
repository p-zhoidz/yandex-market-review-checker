package by.pzh.yandex.market.review.checker.service.mail;

import by.pzh.yandex.market.review.checker.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    @Qualifier("smtMailSender")
    private EmailSender mailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    /**
     * Send email.
     *
     * @param to          Email Recipient.
     * @param subject     Email subject.
     * @param content     Content of the email.
     * @param isMultipart Flag, whether email is multipart.
     * @param isHtml      Flag, whether email is HTML content.
     */
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        Email.MailBuilder mailBuilder = Email.newMailBuilder();

        Email mail = mailBuilder.setIsMultipart(isMultipart)
                .addTo(to)
                .setFrom(applicationProperties.getMail().getFrom())
                .setSubject(subject)
                .setBody(content)
                .setIsHtml(isHtml)
                .build();

        mailSender.send(mail);
    }

}
