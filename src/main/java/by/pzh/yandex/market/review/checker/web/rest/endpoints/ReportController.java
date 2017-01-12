package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.ReportDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportController {

    @Inject
    private ReportService reportService;

    /**
     * POST  /reports : Create a new report.
     *
     * @param reportDTO the reportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportDTO, or with status 400 (Bad Request) if the report has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reports")
    public ResponseEntity<ReportDTO> createReport(@Valid @RequestBody ReportDTO reportDTO) throws URISyntaxException {
        ReportDTO result = reportService.create(reportDTO);
        return ResponseEntity.created(new URI("/api/reports/" + result.getId()))
                .body(result);
    }

    /**
     * PUT  /reports : Updates an existing report.
     *
     * @param reportDTO the reportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportDTO,
     * or with status 400 (Bad Request) if the reportDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reports")
    public ResponseEntity<ReportDTO> updateReport(@Valid @RequestBody ReportDTO reportDTO) throws URISyntaxException {
        if (reportDTO.getId() == null) {
            return createReport(reportDTO);
        }
        ReportDTO result = reportService.update(reportDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET  /reports : get all the reports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports(/*@ApiParam*/ Pageable pageable)
            throws URISyntaxException {
        Page<ReportDTO> page = reportService.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    /**
     * GET  /reports/:id : get the "id" report.
     *
     * @param id the id of the reportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reports/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {
        ReportDTO reportDTO = reportService.findOne(id);
        return Optional.ofNullable(reportDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reports/:id : delete the "id" report.
     *
     * @param id the id of the reportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.delete(id);
        return ResponseEntity.ok().build();
    }

}
