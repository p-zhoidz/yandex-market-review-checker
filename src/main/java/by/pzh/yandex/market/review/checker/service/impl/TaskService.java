package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskStatus;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.TaskResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
    //FIXME
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private TaskDistributionService taskDistributionService;
    private TaskEntryRepository taskEntryRepository;
    private TaskResourceAssembler taskResourceAssembler;
    private PagedResourcesAssembler<Task> pagedAssembler;

    /**
     * Parametrized constructor.
     *
     * @param taskRepository task repository instance.
     * @param taskMapper     task mapper instance.
     */
    @Inject
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper,
                       TaskDistributionService taskDistributionService, TaskEntryRepository taskEntryRepository,
                       TaskResourceAssembler taskResourceAssembler, PagedResourcesAssembler<Task> pagedAssembler) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskDistributionService = taskDistributionService;
        this.taskEntryRepository = taskEntryRepository;
        this.taskResourceAssembler = taskResourceAssembler;
        this.pagedAssembler = pagedAssembler;
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
    public TaskResource create(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setStatus(TaskStatus.OPEN);
        return save(task);
    }

    /**
     * Update a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    //FIXME
    public TaskResource update(Long id, TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setId(id);
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
    public PagedResources<TaskResource> getTasks(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Task> tasks = taskRepository.findAll(pageable);
        return pagedAssembler.toResource(tasks, taskResourceAssembler);
    }

    /**
     * Get one task by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TaskResource> findOne(Long id) {
        return Optional.ofNullable(taskRepository.findOne(id))
                .map(taskResourceAssembler::toResource);
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
    private TaskResource save(Task task) {
        task = taskRepository.save(task);
        return taskResourceAssembler.toResource(task);
    }
}
