package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.CSVTaskDTO;
import by.pzh.yandex.market.review.checker.service.impl.CSVService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;

/**
 * REST controller for managing reports.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private CSVService csvService;

    @Inject
    public ReportingController(CSVService csvService) {
        this.csvService = csvService;
    }

    @RequestMapping(value = "/tasks/{id}/task", method = RequestMethod.GET, produces = "text/csv")
    public ResponseEntity generateCSVTask(@PathVariable Long id) throws DocumentException, IOException {
        try {
            CSVTaskDTO csvTaskDTO = csvService.generateCSVTask(id);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            String.format("attachment; filename=\"%s\"", csvTaskDTO.getFileName()))
                    .body(csvTaskDTO.getContent());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
