package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper {

    @Mapping(source = "poster.id", target = "posterId")
    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);

    @Named("taskDTOToTask")
    @Mapping(source = "posterId", target = "poster")
    Task taskDTOToTask(TaskDTO taskDTO);

    @Mapping(source = "posterId", target = "poster")
    @Mapping(target = "id", ignore = true)
    Task taskDTOToNewTask(TaskDTO taskDTO);

    @IterableMapping(qualifiedByName = "taskDTOToTask")
    List<Task> taskDTOsToTasks(List<TaskDTO> taskDTOs);

    default Poster posterFromId(Long id) {
        if (id == null) {
            return null;
        }
        Poster poster = new Poster();
        poster.setId(id);
        return poster;
    }
}
