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

    /**
     * Map {@link Task} to {@link TaskDTO}.
     *
     * @param task entity to be mapped.
     * @return instance of the {@link TaskDTO}.
     */
    @Mapping(source = "poster.id", target = "posterId")
    TaskDTO taskToTaskDTO(Task task);

    /**
     * Map list of {@link Task} to list of {@link TaskDTO}.
     *
     * @param tasks list of entities to be mapped.
     * @return list of {@link TaskDTO}.
     */
    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);

    /**
     * Map {@link TaskDTO} to {@link Task}.
     *
     * @param taskDTO entity to be mapped.
     * @return instance of the {@link Task}.
     */
    @Named("taskDTOToTask")
    @Mapping(source = "posterId", target = "poster")
    Task taskDTOToTask(TaskDTO taskDTO);

    /**
     * Map {@link TaskDTO} to {@link Task}.
     * Skipping id.
     *
     * @param taskDTO entity to be mapped.
     * @return instance of the {@link Task}.
     */
    @Mapping(source = "posterId", target = "poster")
    @Mapping(target = "id", ignore = true)
    Task taskDTOToNewTask(TaskDTO taskDTO);

    /**
     * Map list of {@link TaskDTO} to list of {@link Task}.
     *
     * @param taskDTOs list of entities to be mapped.
     * @return list of {@link Task}.
     */
    @IterableMapping(qualifiedByName = "taskDTOToTask")
    List<Task> taskDTOsToTasks(List<TaskDTO> taskDTOs);

    /**
     * Generate {@link Poster} based on id.
     *
     * @param id Identifier.
     * @return {@link Poster}.
     */
    default Poster posterFromId(Long id) {
        if (id == null) {
            return null;
        }
        Poster poster = new Poster();
        poster.setId(id);
        return poster;
    }
}
