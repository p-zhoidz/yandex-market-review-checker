package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import by.pzh.yandex.market.review.checker.service.impl.TaskEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskEntry.
 */
@RestController
@RequestMapping("/api")
public class TaskEntryController {

    private final Logger log = LoggerFactory.getLogger(TaskEntryController.class);

    @Inject
    private TaskEntryService taskEntryService;


    /**
     * POST  /task-entries : Create a new taskEntry.
     *
     * @param taskEntryDTO the taskEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskEntryDTO,
     * or with status 200 (OK) if the taskEntry has already an ID and was updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-entries")
    public ResponseEntity<TaskEntryDTO> createTaskEntry(@RequestBody TaskEntryDTO taskEntryDTO)
            throws URISyntaxException {
        TaskEntryDTO result = taskEntryService.create(taskEntryDTO);
        return ResponseEntity.created(new URI("/api/task-entries/" + result.getId()))
                .body(result);
    }

    /**
     * PUT  /task-entries : Updates an existing taskEntry.
     *
     * @param taskEntryDTO the taskEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskEntryDTO,
     * or with status 422 (Bad Request) if the taskEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskEntryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-entries")
    public ResponseEntity<TaskEntryDTO> updateTaskEntry(@RequestBody TaskEntryDTO taskEntryDTO)
            throws URISyntaxException {
        if (taskEntryDTO.getId() == null) {
            return createTaskEntry(taskEntryDTO);
        }
        TaskEntryDTO result = taskEntryService.update(taskEntryDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET  /task-entries : get all the taskEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskEntries in body
     */
    @GetMapping("/task-entries")
    public List<TaskEntryDTO> getAllTaskEntries() {
        return taskEntryService.findAll();
    }

    /**
     * GET  /task-entries/:id : get the "id" taskEntry.
     *
     * @param id the id of the taskEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-entries/{id}")
    public ResponseEntity<TaskEntryDTO> getTaskEntry(@PathVariable Long id) {
        TaskEntryDTO taskEntryDTO = taskEntryService.findOne(id);
        return Optional.ofNullable(taskEntryDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /task-entries/:id : delete the "id" taskEntry.
     *
     * @param id the id of the taskEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-entries/{id}")
    public ResponseEntity<Void> deleteTaskEntry(@PathVariable Long id) {
        log.debug("REST request to delete TaskEntry : {}", id);
        taskEntryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
