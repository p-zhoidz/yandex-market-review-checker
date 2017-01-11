package by.pzh.yandex.market.review.checker.service.mail;

/**
 * Contract for email sender service.
 *
 * @author p.zhoidz.
 */
public interface EmailSender {

    /**
     * Send email.
     *
     * @param mail Wrapper object.
     */
    void send(Email mail);
}
