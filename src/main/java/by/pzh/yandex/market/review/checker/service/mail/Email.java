package by.pzh.yandex.market.review.checker.service.mail;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper object to store email related parameters.
 *
 * @author p.zhoidz.
 */
public final class Email {
    private String from;
    private Set<String> to;
    private Set<String> cc;
    private Set<String> bcc;
    private String subject;
    private String body;
    private Boolean isHtml;
    private Map<String, String> headers;
    private Boolean isMultipart;

    /**
     * Private constructor.
     * Instance can be created with {@link MailBuilder} class.
     */
    private Email() {
    }

    /**
     * @return Returns the value of from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return Returns the value of to.
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * @return Returns the value of cc.
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * @return Returns the value of bcc.
     */
    public Set<String> getBcc() {
        return bcc;
    }

    /**
     * @return Returns the value of subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return Returns the value of body.
     */
    public String getBody() {
        return body;
    }

    /**
     * @return Returns the value of isHtml.
     */
    public Boolean isHtml() {
        return isHtml;
    }

    /**
     * @return Returns the value of headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return Returns the value of isMultipart.
     */
    public Boolean isMultipart() {
        return isMultipart;
    }

    /**
     * Get new instance of the MailBuilder.
     *
     * @return new instance of the MailBuilder.
     */
    public static MailBuilder newMailBuilder() {
        return new Email().new MailBuilder();
    }

    /**
     * Builder for the {@link Email} class.
     */
    public final class MailBuilder {

        /**
         * Private constructor.
         */
        private MailBuilder() {
        }

        /**
         * Builder method. Sets from parameter.
         *
         * @param from email from attribute.
         * @return builder instance.
         */
        public MailBuilder setFrom(@NotNull String from) {
            Email.this.from = from;
            return this;
        }

        /**
         * Builder method. Set email subject.
         *
         * @param subject Subject of the email.
         * @return builder instance.
         */
        public MailBuilder setSubject(@NotNull String subject) {
            Email.this.subject = subject;
            return this;
        }

        /**
         * Builder method. Set email body.
         *
         * @param body Body of the email.
         * @return builder instance.
         */
        public MailBuilder setBody(@NotNull String body) {
            Email.this.body = body;
            return this;
        }

        /**
         * Builder method. Set whether content is html.
         *
         * @param isHtml Flag whether content is html.
         * @return builder instance.
         */
        public MailBuilder setIsHtml(@NotNull Boolean isHtml) {
            Email.this.isHtml = isHtml;
            return this;
        }

        /**
         * Builder method. Set whether message is multipart.
         *
         * @param isMultipart Flag whether content is multipart.
         * @return builder instance.
         */
        public MailBuilder setIsMultipart(@NotNull Boolean isMultipart) {
            Email.this.isMultipart = isMultipart;
            return this;
        }

        /**
         * Builder method. Set "to" email attribute.
         *
         * @param to Recipient to add.
         * @return builder instance.
         */
        public MailBuilder addTo(@NotNull String to) {
            if (Email.this.to == null) {
                Email.this.to = new HashSet<>();
            }
            Email.this.to.add(to);

            return this;
        }

        /**
         * Builder method. Add "cc".
         *
         * @param cc CC to add.
         * @return builder instance.
         */
        public MailBuilder addCc(@NotNull String cc) {
            if (Email.this.cc == null) {
                Email.this.cc = new HashSet<>();
            }
            Email.this.cc.add(cc);

            return this;
        }

        /**
         * Builder method. Add "bcc".
         *
         * @param bcc BCC to add.
         * @return builder instance.
         */
        public MailBuilder addBcc(@NotNull String bcc) {
            if (Email.this.bcc == null) {
                Email.this.bcc = new HashSet<>();
            }
            Email.this.bcc.add(bcc);

            return this;
        }

        /**
         * Builder method. Add header.
         *
         * @param key   Header key.
         * @param value Header value.
         * @return builder instance.
         */
        public MailBuilder addHeader(@NotNull String key, @NotNull String value) {
            if (Email.this.headers == null) {
                Email.this.headers = new HashMap<>();
            }
            Email.this.headers.put(key, value);

            return this;
        }

        /**
         * Built email wrapper.
         *
         * @return email wrapper instance based on applied parameters.
         */
        public Email build() {
            return Email.this;
        }

    }


}
