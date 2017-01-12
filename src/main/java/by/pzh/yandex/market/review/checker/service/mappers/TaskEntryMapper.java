package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity TaskEntry and its DTO TaskEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskEntryMapper {

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "task.id", target = "taskId")
    TaskEntryDTO taskEntryToTaskEntryDTO(TaskEntry taskEntry);

    List<TaskEntryDTO> taskEntriesToTaskEntryDTOs(List<TaskEntry> taskEntries);

    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "taskId", target = "task")
    @Named("taskEntryToDTO")
    TaskEntry taskEntryDTOToTaskEntry(TaskEntryDTO taskEntryDTO);


    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "taskId", target = "task")
    @Mapping(target = "id", ignore = true)
    TaskEntry taskEntryDTOToNewTaskEntry(TaskEntryDTO taskEntryDTO);

    @IterableMapping(qualifiedByName = "taskEntryToDTO")
    List<TaskEntry> taskEntryDTOsToTaskEntries(List<TaskEntryDTO> taskEntryDTOs);

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }

    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}