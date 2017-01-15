package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReportEntry.
 */
@RestController
@RequestMapping("/api")
public class ReportEntryController {

    @Inject
    private ReportEntryService reportEntryService;

    /**
     * GET  /report-entries : get all the reportEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reportEntries in body
     */
    @GetMapping("/report-entries")
    public List<ReportEntryDTO> getAllReportEntries() {
        return reportEntryService.findAll();
    }

    /**
     * GET  /report-entries/:id : get the "id" reportEntry.
     *
     * @param id the id of the reportEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/report-entries/{id}")
    public ResponseEntity<ReportEntryDTO> getReportEntry(@PathVariable Long id) {
        ReportEntryDTO reportEntryDTO = reportEntryService.findOne(id);
        return Optional.ofNullable(reportEntryDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /report-entries/:id : delete the "id" reportEntry.
     *
     * @param id the id of the reportEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/report-entries/{id}")
    public ResponseEntity<Void> deleteReportEntry(@PathVariable Long id) {
        reportEntryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
