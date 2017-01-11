package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TaskMapper taskMapper;

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task = taskRepository.save(task);
        TaskDTO result = taskMapper.taskToTaskDTO(task);
        return result;
    }

    /**
     * Get all the tasks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        log.debug("Request to get all Tasks");
        List<TaskDTO> result = taskRepository.findAll().stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one task by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskDTO findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        Task task = taskRepository.findOne(id);
        TaskDTO taskDTO = taskMapper.taskToTaskDTO(task);
        return taskDTO;
    }

    /**
     * Delete the  task by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }
}
