package by.pzh.yandex.market.review.checker.service.mail;

/**
 * Storage of the email template constants.
 *
 * @author p.zhoidz.
 */
public enum EmailTemplate {
    ;

    private String templateName;

    /**
     * Parametrized constructor.
     *
     * @param templateName name of the template.
     */
    EmailTemplate(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return Returns the value of templateName.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Each template can have dynamic subject, so have to implement it explicitly.
     *
     * @param messageSource message source.
     * @param locale        locale to retrieve messages based on.
     * @param params        template parameters.
     * @return Subject of the email.
     */
    //public abstract String getSubject(MessageSource messageSource, Locale locale, Object[] params);
}
