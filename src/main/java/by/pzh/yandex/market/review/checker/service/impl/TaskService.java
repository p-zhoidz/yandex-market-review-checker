package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskStatus;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    /**
     * Parametrized constructor.
     *
     * @param taskRepository task repository instance.
     * @param taskMapper     task mapper instance.
     */
    @Inject
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    /**
     * Create a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    public Task create(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setStatus(TaskStatus.OPEN);
        return taskRepository.save(task);
    }

    /**
     * Update a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    //FIXME
    public Task update(Long id, TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setId(id);
        return taskRepository.save(task);
    }

    /**
     * Get tasks based on page nad page size params.
     *
     * @param page page number param.
     * @param size page size param.
     * @return {@link Page} of tasks based on provided params.
     */
    @Transactional(readOnly = true)
    public Page<TaskDTO> getTasks(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return taskRepository.findAll(pageable)
                .map(taskMapper::taskToTaskDTO);
    }

    /**
     * Get one task by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Task> findOne(Long id) {
        return Optional.ofNullable(taskRepository.findOne(id));
    }

    /**
     * Delete the  task by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        taskRepository.delete(id);
    }

}
