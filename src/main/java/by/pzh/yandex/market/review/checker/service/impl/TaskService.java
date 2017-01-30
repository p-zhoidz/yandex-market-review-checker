package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private TaskDistributionService taskDistributionService;
    private TaskEntryRepository taskEntryRepository;

    /**
     * Parametrized constructor.
     *
     * @param taskRepository task repository instance.
     * @param taskMapper     task mapper instance.
     */
    @Inject
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper,
                       TaskDistributionService taskDistributionService, TaskEntryRepository taskEntryRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskDistributionService = taskDistributionService;
        this.taskEntryRepository = taskEntryRepository;
    }

    public List<Task> distribute() {
        List<Task> tasks = taskDistributionService.distribute();
        taskRepository.save(tasks);

        List<TaskEntry> entries = tasks.stream()
                .map(Task::getTaskEntries)
                .flatMap(Collection::stream)
                .collect(toList());

        taskEntryRepository.save(entries);


        return tasks;

    }

    /**
     * Create a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    public TaskDTO create(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToNewTask(taskDTO);
        return save(task);
    }

    /**
     * Update a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    public TaskDTO update(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        return save(task);
    }

    /**
     * Get all the tasks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
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
        taskRepository.delete(id);
    }

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    private TaskDTO save(Task task) {
        task = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }
}
