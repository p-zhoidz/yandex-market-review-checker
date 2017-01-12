package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.service.dto.ReportDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Report and its DTO ReportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportMapper {

    @Mapping(source = "poster.id", target = "posterId")
    ReportDTO reportToReportDTO(Report report);

    List<ReportDTO> reportsToReportDTOs(List<Report> reports);

    @Named("reportDTOToReport")
    @Mapping(source = "posterId", target = "poster")
    Report reportDTOToReport(ReportDTO reportDTO);

    @IterableMapping(qualifiedByName = "reportDTOToReport")
    @Mapping(source = "posterId", target = "poster")
    @Mapping(target = "id", ignore = true)
    Report reportDTOToNewReport(ReportDTO reportDTO);

    List<Report> reportDTOsToReports(List<ReportDTO> reportDTOs);

    default Poster posterFromId(Long id) {
        if (id == null) {
            return null;
        }
        Poster poster = new Poster();
        poster.setId(id);
        return poster;
    }
}