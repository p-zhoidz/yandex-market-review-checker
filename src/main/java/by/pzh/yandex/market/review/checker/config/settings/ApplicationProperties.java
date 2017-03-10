package by.pzh.yandex.market.review.checker.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;


//CHECKSTYLE:OFF

/**
 * Properties specific to the Tenant Referencing application.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "ym", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Async async = new Async();
    private final Mail mail = new Mail();
    private final Constants constants = new Constants();

    /**
     * @return Returns the value of mail.
     */
    public Mail getMail() {
        return mail;
    }

    /**
     * @return Returns the value of constants.
     */
    public Constants getConstants() {
        return constants;
    }

    /**
     * @return Returns the value of async.
     */
    public Async getAsync() {
        return async;
    }

    public static class Mail {

        private String from = "YMRC@localhost";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public static class Async {

        private static final int DEFAULT_CORE_POOL_SIZE = 2;
        private static final int DEFAULT_MAX_POOL_SIZE = 50;
        private static final int DEFAULT_QUEUE_CAPACITY = 10000;

        private int corePoolSize = DEFAULT_CORE_POOL_SIZE;

        private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;

        private int queueCapacity = DEFAULT_QUEUE_CAPACITY;

        /**
         * @return Returns the value of corePoolSize.
         */
        public int getCorePoolSize() {
            return corePoolSize;
        }

        /**
         * Sets the corePoolSize.
         *
         * @param corePoolSize the value to be set
         */
        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        /**
         * @return Returns the value of maxPoolSize.
         */
        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        /**
         * Sets the maxPoolSize.
         *
         * @param maxPoolSize the value to be set
         */
        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        /**
         * @return Returns the value of queueCapacity.
         */
        public int getQueueCapacity() {
            return queueCapacity;
        }

        /**
         * Sets the queueCapacity.
         *
         * @param queueCapacity the value to be set
         */
        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    /**
     * Application constants class.
     */
    public static class Constants {
        private String baseUrl;

        /**
         * @return Returns the value of baseUrl.
         */
        public String getBaseUrl() {
            return baseUrl;
        }

        /**
         * Sets the baseUrl.
         *
         * @param baseUrl the value to be set
         */
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }


}
//CHECKSTYLE:ON