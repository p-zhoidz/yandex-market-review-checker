package by.pzh.yandex.market.review.checker.service.impl;


import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskEntryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.fetchStore;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.filterTaskId;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author p.zhoidz.
 */
@Service
@Transactional
public class TaskEntryService {
    private TaskEntryMapper taskEntryMapper;
    private TaskEntryRepository taskEntryRepository;

    //private TaskEntryResourceAssembler taskEntryResourceAssembler;

    /**
     * Parametrized constructor.
     *
     * @param taskEntryRepository task entry repository.
     * @param taskEntryMapper     task entry mapper instance.
     */
    @Inject
    public TaskEntryService(TaskEntryRepository taskEntryRepository, TaskEntryMapper taskEntryMapper) {
        this.taskEntryMapper = taskEntryMapper;
        this.taskEntryRepository = taskEntryRepository;
    }

    /**
     * Create a task entry.
     *
     * @param taskEntryDTO the entity to save
     * @return the persisted entity
     */
    public TaskEntryDTO create(TaskEntryDTO taskEntryDTO) {
        TaskEntry taskEntry = taskEntryMapper.taskEntryDTOToNewTaskEntry(taskEntryDTO);
        return save(taskEntry);
    }

    /**
     * Get all the stores.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TaskEntryDTO> findAll() {
        return taskEntryRepository.findAll().stream()
                .map(taskEntryMapper::taskEntryToTaskEntryDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskEntryDTO findOne(Long id) {
        TaskEntry taskEntry = taskEntryRepository.findOne(id);
        return taskEntryMapper.taskEntryToTaskEntryDTO(taskEntry);
    }

    @Transactional(readOnly = true)
    public List<TaskEntry> getTaskEntries(Long taskId) {
        return taskEntryRepository.findAll(
                where(filterTaskId(taskId))
                        .and(fetchStore()));
    }

    /**
     * Delete the  store by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        taskEntryRepository.delete(id);
    }

    /**
     * Save a task entry.
     *
     * @param taskEntry the entity to save
     * @return the persisted entity
     */
    private TaskEntryDTO save(TaskEntry taskEntry) {
        taskEntryRepository.save(taskEntry);
        return taskEntryMapper.taskEntryToTaskEntryDTO(taskEntry);
    }
}
