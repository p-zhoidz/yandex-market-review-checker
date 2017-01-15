package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity ReportEntry and its DTO ReportEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportEntryMapper {

    /**
     * Map {@link ReportEntry} to {@link ReportEntryDTO}.
     *
     * @param reportEntry entity to be mapped.
     * @return instance of the {@link ReportEntryDTO}.
     */
    @Mapping(source = "report.id", target = "reportId")
    ReportEntryDTO reportEntryToReportEntryDTO(ReportEntry reportEntry);

    /**
     * Map list of {@link ReportEntry} to list of {@link ReportEntryDTO}.
     *
     * @param reportEntries list of entities to be mapped.
     * @return list of {@link ReportEntryDTO}.
     */
    List<ReportEntryDTO> reportEntriesToReportEntryDTOs(List<ReportEntry> reportEntries);

    /**
     * Map {@link ReportEntryDTO} to {@link ReportEntry}.
     *
     * @param reportEntryDTO entity to be mapped.
     * @return instance of the {@link ReportEntry}.
     */
    @Named("reportEntryDTOToReportEntry")
    @Mapping(source = "reportId", target = "report")
    ReportEntry reportEntryDTOToReportEntry(ReportEntryDTO reportEntryDTO);

    /**
     * Map {@link ReportEntryDTO} to {@link ReportEntry}.
     * Skipping id.
     *
     * @param reportEntryDTO entity to be mapped.
     * @return instance of the {@link ReportEntry}.
     */
    @Mapping(source = "reportId", target = "report")
    @Mapping(target = "id", ignore = true)
    ReportEntry reportEntryDTOToNewReportEntry(ReportEntryDTO reportEntryDTO);

    /**
     * Map list of {@link ReportEntryDTO} to list of {@link ReportEntry}.
     *
     * @param reportEntryDTOs list of entities to be mapped.
     * @return list of {@link ReportEntry}.
     */
    @IterableMapping(qualifiedByName = "reportEntryDTOToReportEntry")
    List<ReportEntry> reportEntryDTOsToReportEntries(List<ReportEntryDTO> reportEntryDTOs);

    /**
     * Generate {@link Report} based on id.
     *
     * @param id Identifier.
     * @return {@link Report}.
     */
    default Report reportFromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
