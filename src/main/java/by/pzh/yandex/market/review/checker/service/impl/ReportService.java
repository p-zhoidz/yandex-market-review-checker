package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.repository.ReportRepository;
import by.pzh.yandex.market.review.checker.service.dto.ReportDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ReportMapper;
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
    private ReportRepository reportRepository;
    private ReportMapper reportMapper;

    /**
     * Parametrized constructor.
     *
     * @param reportRepository report repository instance.
     * @param reportMapper     report mapper instance.
     */
    @Inject
    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    /**
     * Create a report.
     *
     * @param reportDTO the entity to save
     * @return the persisted entity
     */
    public ReportDTO create(ReportDTO reportDTO) {
        Report report = reportMapper.reportDTOToNewReport(reportDTO);
        return save(report);
    }

    /**
     * Update a report.
     *
     * @param reportDTO the entity to save
     * @return the persisted entity
     */
    public ReportDTO update(ReportDTO reportDTO) {
        Report report = reportMapper.reportDTOToReport(reportDTO);
        return save(report);
    }

    /**
     * Get all the reports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable) {
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
        reportRepository.delete(id);
    }

    /**
     * Save a report.
     *
     * @param report the entity to save
     * @return the persisted entity
     */
    private ReportDTO save(Report report) {
        report = reportRepository.save(report);
        return reportMapper.reportToReportDTO(report);
    }
}
