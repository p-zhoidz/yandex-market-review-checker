package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ReportEntry and its DTO ReportEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportEntryMapper {

    @Mapping(source = "report.id", target = "reportId")
    ReportEntryDTO reportEntryToReportEntryDTO(ReportEntry reportEntry);

    List<ReportEntryDTO> reportEntriesToReportEntryDTOs(List<ReportEntry> reportEntries);

    @Mapping(source = "reportId", target = "report")
    ReportEntry reportEntryDTOToReportEntry(ReportEntryDTO reportEntryDTO);

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
