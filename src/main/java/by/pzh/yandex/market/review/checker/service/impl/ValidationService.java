package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.config.settings.WebDriverSettings;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author p.zhoidz.
 */
@Service
@Transactional
public class ValidationService {

    @Inject
    private TaskEntryRepository taskEntryRepository;

    @Inject
    private WebDriverSettings webDriverSettings;

    @PostConstruct
    public void postConstruct() {
        String path = ValidationService.class
                .getClassLoader()
                .getResource(webDriverSettings.getDriver().getDriverPath())
                .getPath();

        System.setProperty(webDriverSettings.getDriver().getDriverPropertyName(), path);
    }


    public void runValidation() {
        Specification<TaskEntry> taskEntrySpecification = TaskEntrySpecifications.statusEquals(TaskEntryStatus.OPEN);

        taskEntryRepository.findAll(taskEntrySpecification)
                .stream()
                .collect(groupingBy(o -> o.getStore().getUrl()))
                .entrySet()
                .forEach(es -> check(es.getValue(), es.getKey()));


    }

    private void check(List<TaskEntry> notValidated, String url) {
        WebDriver driver = new ChromeDriver();
        String reviewPostfix = webDriverSettings.getReview().getReviewPostfix();
        String reviewXPath = webDriverSettings.getReview().getReviewXPath();
        int page = 0;
        while (!notValidated.isEmpty()) {

            driver.get(url + reviewPostfix + ++page);
            List<WebElement> elements = driver.findElements(By.className(reviewXPath));
            List<String> collect = elements.stream().map(WebElement::getText).collect(toList());

            if (collect.isEmpty()) {
                break;
            }

            List<TaskEntry> collect1 = notValidated
                    .stream()
                    .filter(taskEntry -> TaskEntryStatus.OPEN.equals(taskEntry.getStatus()))
                    .filter(taskEntry -> collect.contains(taskEntry.getText()))
                    .map(taskEntry -> {
                        taskEntry.setStatus(TaskEntryStatus.CONFIRMED);
                        return taskEntry;
                    }).collect(toList());

            System.out.println("");
            notValidated.removeAll(collect1);

        }
        driver.close();
    }

}
