package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.impl.ValidationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author p.zhoidz.
 */
@RestController
@RequestMapping(value = "/api/validation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ValidationController {


    @Inject
    private ValidationService validationService;

    @PostMapping(value = "/run")
    public ResponseEntity<?> runValidation() {
        validationService.runValidation();
        return ResponseEntity.noContent().build();

    }
}
