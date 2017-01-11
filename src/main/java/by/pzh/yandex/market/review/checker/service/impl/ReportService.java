package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.repository.ReportRepository;
import by.pzh.yandex.market.review.checker.service.dto.ReportDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Report.
 */
@Service
@Transactional
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportMapper reportMapper;

    /**
     * Save a report.
     *
     * @param reportDTO the entity to save
     * @return the persisted entity
     */
    public ReportDTO save(ReportDTO reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.reportDTOToReport(reportDTO);
        report = reportRepository.save(report);
        ReportDTO result = reportMapper.reportToReportDTO(report);
        return result;
    }

    /**
     * Get all the reports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reports");
        Page<Report> result = reportRepository.findAll(pageable);
        return result.map(report -> reportMapper.reportToReportDTO(report));
    }

    /**
     * Get one report by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ReportDTO findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        Report report = reportRepository.findOne(id);
        ReportDTO reportDTO = reportMapper.reportToReportDTO(report);
        return reportDTO;
    }

    /**
     * Delete the  report by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.delete(id);
    }
}
