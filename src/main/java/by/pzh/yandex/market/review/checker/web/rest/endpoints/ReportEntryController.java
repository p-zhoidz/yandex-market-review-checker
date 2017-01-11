package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.repository.ReportEntryRepository;
import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ReportEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ReportEntry.
 */
@RestController
@RequestMapping("/api")
public class ReportEntryController {

    private final Logger log = LoggerFactory.getLogger(ReportEntryController.class);

    @Inject
    private ReportEntryRepository reportEntryRepository;

    @Inject
    private ReportEntryMapper reportEntryMapper;

    /**
     * POST  /report-entries : Create a new reportEntry.
     *
     * @param reportEntryDTO the reportEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportEntryDTO, or with status 400 (Bad Request) if the reportEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report-entries")
    public ResponseEntity<ReportEntryDTO> createReportEntry(@Valid @RequestBody ReportEntryDTO reportEntryDTO) throws URISyntaxException {
        log.debug("REST request to save ReportEntry : {}", reportEntryDTO);

        ReportEntry reportEntry = reportEntryMapper.reportEntryDTOToReportEntry(reportEntryDTO);
        reportEntry = reportEntryRepository.save(reportEntry);
        ReportEntryDTO result = reportEntryMapper.reportEntryToReportEntryDTO(reportEntry);
        return ResponseEntity.created(new URI("/api/report-entries/" + result.getId()))

                .body(result);
    }

    /**
     * PUT  /report-entries : Updates an existing reportEntry.
     *
     * @param reportEntryDTO the reportEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportEntryDTO,
     * or with status 400 (Bad Request) if the reportEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportEntryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/report-entries")
    public ResponseEntity<ReportEntryDTO> updateReportEntry(@Valid @RequestBody ReportEntryDTO reportEntryDTO) throws URISyntaxException {
        log.debug("REST request to update ReportEntry : {}", reportEntryDTO);
        if (reportEntryDTO.getId() == null) {
            return createReportEntry(reportEntryDTO);
        }
        ReportEntry reportEntry = reportEntryMapper.reportEntryDTOToReportEntry(reportEntryDTO);
        reportEntry = reportEntryRepository.save(reportEntry);
        ReportEntryDTO result = reportEntryMapper.reportEntryToReportEntryDTO(reportEntry);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET  /report-entries : get all the reportEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reportEntries in body
     */
    @GetMapping("/report-entries")
    public List<ReportEntryDTO> getAllReportEntries() {
        log.debug("REST request to get all ReportEntries");
        List<ReportEntry> reportEntries = reportEntryRepository.findAll();
        return reportEntryMapper.reportEntriesToReportEntryDTOs(reportEntries);
    }

    /**
     * GET  /report-entries/:id : get the "id" reportEntry.
     *
     * @param id the id of the reportEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/report-entries/{id}")
    public ResponseEntity<ReportEntryDTO> getReportEntry(@PathVariable Long id) {
        log.debug("REST request to get ReportEntry : {}", id);
        ReportEntry reportEntry = reportEntryRepository.findOne(id);
        ReportEntryDTO reportEntryDTO = reportEntryMapper.reportEntryToReportEntryDTO(reportEntry);
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
        log.debug("REST request to delete ReportEntry : {}", id);
        reportEntryRepository.delete(id);
        return ResponseEntity.ok().build();
    }

}
