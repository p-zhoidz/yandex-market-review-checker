package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.TaskGenerationDTO;
import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST controller for managing task distribution process.
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DistributionController {

    private TaskDistributionService distributionService;

    /**
     * Parametrized constructor.
     *
     * @param distributionService task distribution service.
     */
    @Inject
    public DistributionController(TaskDistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/tasks/distribute")
    public ResponseEntity<TaskGenerationDTO> distribute() {
        TaskGenerationDTO result = distributionService.distribute();
        return ResponseEntity.ok(result);
    }
}
