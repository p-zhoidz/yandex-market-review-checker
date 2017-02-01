package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
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

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.fetchStore;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.filterTaskId;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specifications.where;

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
     * Get tasks based on page nad page size params.
     *
     * @param page page number param.
     * @param size page size param.
     * @return {@link Page} of tasks based on provided params.
     */
    @Transactional(readOnly = true)
    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return taskRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<TaskEntry> getTaskEntries(Long taskId) {
        return taskEntryRepository.findAll(
                where(filterTaskId(taskId))
                        .and(fetchStore()));
    }

    /**
     * Get one task by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    //// TODO: 30.1.17 return optional
    public Task findOne(Long id) {
        return taskRepository.findOne(id);
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
