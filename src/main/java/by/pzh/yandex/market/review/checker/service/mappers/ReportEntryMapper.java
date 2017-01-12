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

    @Mapping(source = "report.id", target = "reportId")
    ReportEntryDTO reportEntryToReportEntryDTO(ReportEntry reportEntry);

    List<ReportEntryDTO> reportEntriesToReportEntryDTOs(List<ReportEntry> reportEntries);

    @Named("reportEntryDTOToReportEntry")
    @Mapping(source = "reportId", target = "report")
    ReportEntry reportEntryDTOToReportEntry(ReportEntryDTO reportEntryDTO);

    @Mapping(source = "reportId", target = "report")
    @Mapping(target = "id", ignore = true)
    ReportEntry reportEntryDTOToNewReportEntry(ReportEntryDTO reportEntryDTO);

    @IterableMapping(qualifiedByName = "reportEntryDTOToReportEntry")
    List<ReportEntry> reportEntryDTOsToReportEntries(List<ReportEntryDTO> reportEntryDTOs);

    default Report reportFromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
