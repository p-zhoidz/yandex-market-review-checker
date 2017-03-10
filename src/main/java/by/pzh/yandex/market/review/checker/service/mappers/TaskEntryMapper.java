package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity TaskEntry and its DTO TaskEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public interface TaskEntryMapper {

    /**
     * Map {@link TaskEntry} to {@link TaskEntryDTO}.
     *
     * @param taskEntry entity to be mapped.
     * @return instance of the {@link TaskEntryDTO}.
     */
    @Mapping(source = "task.id", target = "taskId")
    TaskEntryDTO taskEntryToTaskEntryDTO(TaskEntry taskEntry);

    /**
     * Map list of {@link TaskEntry} to list of {@link TaskEntryDTO}.
     *
     * @param taskEntries list of entities to be mapped.
     * @return list of {@link TaskEntryDTO}.
     */
    List<TaskEntryDTO> taskEntriesToTaskEntryDTOs(List<TaskEntry> taskEntries);

    /**
     * Map {@link TaskEntryDTO} to {@link TaskEntry}.
     *
     * @param taskEntryDTO entity to be mapped.
     * @return instance of the {@link TaskEntry}.
     */
/*    @Mapping(source = "taskId", target = "task")
    @Named("taskEntryToDTO")
    TaskEntry taskEntryDTOToTaskEntry(TaskEntryDTO taskEntryDTO);*/


    /**
     * Map {@link TaskEntryDTO} to {@link TaskEntry}.
     * Skipping id.
     *
     * @param taskEntryDTO entity to be mapped.
     * @return instance of the {@link TaskEntry}.
     */
    @Mapping(source = "taskId", target = "task")
    @Mapping(target = "id", ignore = true)
    TaskEntry taskEntryDTOToNewTaskEntry(TaskEntryDTO taskEntryDTO);

    /**
     * Map list of {@link TaskEntryDTO} to list of {@link TaskEntry}.
     *
     * @param taskEntryDTOs list of entities to be mapped.
     * @return list of {@link TaskEntry}.
     */
/*    @IterableMapping(qualifiedByName = "taskEntryToDTO")
    List<TaskEntry> taskEntryDTOsToTaskEntries(List<TaskEntryDTO> taskEntryDTOs);*/

    /**
     * Generate {@link Store} based on id.
     *
     * @param id Identifier.
     * @return {@link Store}.
     */
/*    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }*/

/*default Store map(StoreDTO storeDTO) {
    return new Store();
}*/

    /**
     * Generate {@link Task} based on id.
     *
     * @param id Identifier.
     * @return {@link Task}.
     */
    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }

}