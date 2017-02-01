package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportGenerationService;
import by.pzh.yandex.market.review.checker.service.impl.TaskService;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.TaskResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskResource;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
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
import java.net.URI;
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
    private TaskResourceAssembler taskResourceAssembler;
    private PagedResourcesAssembler<TaskResource> pagedAssembler;

    @Inject
    public TaskController(TaskService taskService, ReportGenerationService reportGenerationService,
                          PagedResourcesAssembler<TaskResource> pagedAssembler,
                          TaskResourceAssembler taskResourceAssembler) {
        this.taskService = taskService;
        this.reportGenerationService = reportGenerationService;
        this.taskResourceAssembler = taskResourceAssembler;
        this.pagedAssembler = pagedAssembler;
    }

    //// TODO: 30.1.17 Should return statistics over generated data??? +
    @PostMapping("/tasks/distribute")
    public ResponseEntity<List<TaskEntry>> distribute() {
        List<Task> distribute = taskService.distribute();
        return ResponseEntity.ok(distribute.get(0).getTaskEntries());
    }

    @RequestMapping(value = "/tasks/{id}/pdf", method = RequestMethod.GET, produces = "application/pdf")
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
        TaskDTO result = taskService.create(taskDTO);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
                .body(result);
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
    @PutMapping("/tasks")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        if (taskDTO.getId() == null) {
            return createTask(taskDTO);
        }
        TaskDTO result = taskService.update(taskDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param p pageable params.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<Resource<TaskResource>>> getTasks(@PageableDefault Pageable p) {
        Page<Task> posters = taskService.getTasks(p.getPageNumber(), p.getPageSize());

        Page<TaskResource> page = posters.map(taskResourceAssembler::toResource);
        PagedResources<Resource<TaskResource>> resources = pagedAssembler.toResource(page);
        return ResponseEntity.ok(resources);
    }

    /**
     * Get task resources based on incoming paging settings.
     *
     * @param p pageable params.
     * @return {@link ResponseEntity} with 200 http status, which contain list of resources.
     */
    @RequestMapping(value = "/tasks/{id}/entries", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<Resource<TaskResource>>> getTaskEntries(@PathVariable Long id) {
        List<TaskEntry> taskEntries = taskService.getTaskEntries(id);
/*        taskEntries.stream().map(ta)

        Page<TaskResource> page = posters.map(taskResourceAssembler::toResource);
        PagedResources<Resource<TaskResource>> resources = pagedAssembler.toResource(page);*/
        return ResponseEntity.ok(null);
    }

    /**
     * GET  /tasks/:id : get the "id" task.
     *
     * @param id the id of the taskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<TaskResource> getTask(@PathVariable Long id) {
        Task task = taskService.findOne(id);
        TaskResource taskResource = taskResourceAssembler.toResource(task);
        return ResponseEntity.ok(taskResource);
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
