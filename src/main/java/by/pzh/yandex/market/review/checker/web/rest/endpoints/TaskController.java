package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportGenerationService;
import by.pzh.yandex.market.review.checker.service.impl.TaskEntryService;
import by.pzh.yandex.market.review.checker.service.impl.TaskService;
import by.pzh.yandex.market.review.checker.service.mappers.TaskEntryMapper;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Task.
 */
@RestController
@RequestMapping("/api")
public class TaskController {
    private TaskService taskService;
    private ReportGenerationService reportGenerationService;
    private TaskEntryService taskEntryService;
    private TaskEntryMapper taskEntryMapper;
    private TaskMapper taskMapper;

    @Inject
    public TaskController(TaskService taskService, ReportGenerationService reportGenerationService,
                          TaskEntryService taskEntryService, TaskEntryMapper taskEntryMapper,
                          TaskMapper taskMapper) {
        this.taskService = taskService;
        this.reportGenerationService = reportGenerationService;
        this.taskEntryService = taskEntryService;
        this.taskEntryMapper = taskEntryMapper;
        this.taskMapper = taskMapper;
    }

    //// TODO: 30.1.17 Should return statistics over generated data??? +
    @PostMapping("/tasks/distribute")
    public ResponseEntity<List<Task>> distribute() {
        List<Task> tasks = taskService.distribute();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}/report", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity generateReport(@PathVariable Long id) throws DocumentException, IOException {
        byte[] bytes = reportGenerationService.generatePDF(id);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", "report"))
                .body(bytes);
    }

    /**
     * POST  /tasks : Create a new task.
     *
     * @param taskDTO the taskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskDTO,
     * or with status 200 (OK) if the task has already an ID and was updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        Task task = taskService.create(taskDTO);
        TaskDTO dto = taskMapper.taskToTaskDTO(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    /**
     * PUT  /tasks : Updates an existing task.
     *
     * @param taskDTO the taskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskDTO,
     * or with status 422 (Bad Request) if the taskDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {

        Task task = taskService.update(id, taskDTO);
        TaskDTO dto = taskMapper.taskToTaskDTO(task);
        return ResponseEntity.ok(dto);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param p pageable params.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<TaskDTO>> getTasks(@PageableDefault Pageable p) {
        Page<Task> tasks = taskService.getTasks(p.getPageNumber(), p.getPageSize());
        Page<TaskDTO> dtos = tasks.map(taskMapper::taskToTaskDTO);
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param id task identifier.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks/{id}/entries", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<List<TaskEntryDTO>> getTaskEntries(@PathVariable Long id) {
        List<TaskEntry> taskEntries = taskEntryService.getTaskEntries(id);
        List<TaskEntryDTO> dtos = taskEntryMapper.taskEntriesToTaskEntryDTOs(taskEntries);
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET  /tasks/:id : get the "id" task.
     *
     * @param id the id of the taskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        return taskService.findOne(id)
                .map(taskMapper::taskToTaskDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Poster.class,
                        String.format("task with id %s not found", id)));
    }

    /**
     * DELETE  /tasks/:id : delete the "id" task.
     *
     * @param id the id of the taskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

}
