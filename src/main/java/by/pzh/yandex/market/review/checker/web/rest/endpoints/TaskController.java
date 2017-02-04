package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportGenerationService;
import by.pzh.yandex.market.review.checker.service.impl.TaskEntryService;
import by.pzh.yandex.market.review.checker.service.impl.TaskService;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskEntryResource;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskResource;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
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


    @Inject
    public TaskController(TaskService taskService, ReportGenerationService reportGenerationService,
                          TaskEntryService taskEntryService) {
        this.taskService = taskService;
        this.reportGenerationService = reportGenerationService;
        this.taskEntryService = taskEntryService;
    }

    //// TODO: 30.1.17 Should return statistics over generated data??? +
    @PostMapping("/tasks/distribute")
    public ResponseEntity<List<TaskEntry>> distribute() {
        List<Task> distribute = taskService.distribute();
        return ResponseEntity.ok(distribute.get(0).getTaskEntries());
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
    public ResponseEntity<TaskResource> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        TaskResource resource = taskService.create(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resource);
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
    public ResponseEntity<TaskResource> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {

        TaskResource resource = taskService.update(id, taskDTO);
        return ResponseEntity.ok(resource);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param p pageable params.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<TaskResource>> getTasks(@PageableDefault Pageable p) {
        PagedResources<TaskResource> tasks = taskService.getTasks(p.getPageNumber(), p.getPageSize());
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param id task identifier.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks/{id}/entries", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<List<TaskEntryResource>> getTaskEntries(@PathVariable Long id) {
        List<TaskEntryResource> taskEntries = taskEntryService.getTaskEntries(id);
        return ResponseEntity.ok(taskEntries);
    }

    /**
     * GET  /tasks/:id : get the "id" task.
     *
     * @param id the id of the taskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<TaskResource> getTask(@PathVariable Long id) {
        return taskService.findOne(id).map(ResponseEntity::ok)
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
