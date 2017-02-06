package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.repository.ReportEntryRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author p.zhoidz.
 */
@Service
public class ReviewConfirmationService {
    private ReportEntryRepository reportEntryRepository;


    public void confirm() {
        List<ReportEntry> all = reportEntryRepository.findAll();

        all.stream().

        all.forEach(reportEntry -> {
            Store store = reportEntry.getStore();


            WebDriver driver = new ChromeDriver();
            driver.get(store.getUrl());
            List<WebElement> elements = driver.findElements(By.className("product-review-item__text"));

            //product-review-item__stat
        });

        //get all report entries //order by store

        //get all tasks

    }

    public static void main(String... args) throws MalformedURLException {
        //  WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
        String path = ReviewConfirmationService.class.getClassLoader().getResource("drivers/chromedriver").getPath();

        System.setProperty("webdriver.chrome.driver", path);
        System.out.println("");

        WebDriver driver = new ChromeDriver();
        driver.get("https://market.yandex.by/shop/320273/reviews");
        List<WebElement> elements = driver.findElements(By.className("product-review-item__text"));
//element.get(0).getTest();
        System.out.println("");
    }
}
