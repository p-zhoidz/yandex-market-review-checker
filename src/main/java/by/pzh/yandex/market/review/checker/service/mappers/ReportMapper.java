package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.Task;
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

    /**
     * Map {@link Report} to {@link ReportDTO}.
     *
     * @param report entity to be mapped.
     * @return instance of the {@link ReportDTO}.
     */
    @Mapping(source = "task.id", target = "taskId")
    ReportDTO reportToReportDTO(Report report);

    /**
     * Map list of {@link Report} to list of {@link ReportDTO}.
     *
     * @param reports list of entities to be mapped.
     * @return list of {@link ReportDTO}.
     */
    List<ReportDTO> reportsToReportDTOs(List<Report> reports);

    /**
     * Map {@link ReportDTO} to {@link Report}.
     *
     * @param reportDTO entity to be mapped.
     * @return instance of the {@link Report}.
     */
    @Named("reportDTOToReport")
    @Mapping(source = "taskId", target = "task")
    Report reportDTOToReport(ReportDTO reportDTO);

    /**
     * Map {@link ReportDTO} to {@link Report}.
     * Skipping id.
     *
     * @param reportDTO entity to be mapped.
     * @return instance of the {@link Report}.
     */
    @IterableMapping(qualifiedByName = "reportDTOToReport")
    @Mapping(source = "taskId", target = "task")
    @Mapping(target = "id", ignore = true)
    Report reportDTOToNewReport(ReportDTO reportDTO);

    /**
     * Map list of {@link ReportDTO} to list of {@link Report}.
     *
     * @param reportDTOs list of entities to be mapped.
     * @return list of {@link Report}.
     */
    List<Report> reportDTOsToReports(List<ReportDTO> reportDTOs);

    /**
     * Generate {@link Poster} based on id.
     *
     * @param id Identifier.
     * @return {@link Poster}.
     */
    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        return Task.builder()
                .id(id)
                .build();
    }
}
