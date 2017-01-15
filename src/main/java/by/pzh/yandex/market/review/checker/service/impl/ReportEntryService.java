package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.repository.ReportEntryRepository;
import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ReportEntryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author p.zhoidz.
 */
@Service
@Transactional
public class ReportEntryService {
    private ReportEntryMapper reportEntryMapper;
    private ReportEntryRepository reportEntryRepository;

    /**
     * Parametrized constructor.
     *
     * @param reportEntryRepository report entry repository.
     * @param reportEntryMapper     report entry mapper.
     */
    @Inject
    public ReportEntryService(ReportEntryRepository reportEntryRepository, ReportEntryMapper reportEntryMapper) {
        this.reportEntryMapper = reportEntryMapper;
        this.reportEntryRepository = reportEntryRepository;
    }

    /**
     * Get all the report entries.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReportEntryDTO> findAll() {
        return reportEntryRepository.findAll().stream()
                .map(reportEntryMapper::reportEntryToReportEntryDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one report entry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ReportEntryDTO findOne(Long id) {
        ReportEntry reportEntry = reportEntryRepository.findOne(id);
        return reportEntryMapper.reportEntryToReportEntryDTO(reportEntry);
    }

    /**
     * Delete the report entry by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        reportEntryRepository.delete(id);
    }


}
