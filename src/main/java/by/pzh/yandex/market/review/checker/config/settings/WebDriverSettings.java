package by.pzh.yandex.market.review.checker.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author p.zhoidz.
 */
@ConfigurationProperties(prefix = "wd", ignoreUnknownFields = false)
public class WebDriverSettings {
    private final Driver driver = new Driver();
    private final Review review = new Review();

    /**
     * @return Returns the value of driver.
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * @return Returns the value of review.
     */
    public Review getReview() {
        return review;
    }

    public static class Driver {
        private String driverPath;
        private String driverPropertyName;

        /**
         * @return Returns the value of driverPath.
         */
        public String getDriverPath() {
            return driverPath;
        }

        /**
         * Sets the driverPath.
         *
         * @param driverPath the value to be set
         */
        public void setDriverPath(String driverPath) {
            this.driverPath = driverPath;
        }

        /**
         * @return Returns the value of driverPropertyName.
         */
        public String getDriverPropertyName() {
            return driverPropertyName;
        }

        /**
         * Sets the driverPropertyName.
         *
         * @param driverPropertyName the value to be set
         */
        public void setDriverPropertyName(String driverPropertyName) {
            this.driverPropertyName = driverPropertyName;
        }
    }

    public static class Review {
        private String reviewPostfix;
        private String reviewXPath;

        /**
         * @return Returns the value of reviewPostfix.
         */
        public String getReviewPostfix() {
            return reviewPostfix;
        }

        /**
         * Sets the reviewPostfix.
         *
         * @param reviewPostfix the value to be set
         */
        public void setReviewPostfix(String reviewPostfix) {
            this.reviewPostfix = reviewPostfix;
        }

        /**
         * @return Returns the value of reviewXPath.
         */
        public String getReviewXPath() {
            return reviewXPath;
        }

        /**
         * Sets the reviewXPath.
         *
         * @param reviewXPath the value to be set
         */
        public void setReviewXPath(String reviewXPath) {
            this.reviewXPath = reviewXPath;
        }
    }

}
