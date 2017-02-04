package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.PosterController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskEntryController;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
@Component
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

    public TaskResourceAssembler() {
        super(TaskController.class, TaskResource.class);
    }

    @Override
    public TaskResource toResource(Task entity) {
        TaskResource resource = TaskResource.builder()
                .number(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .comment(entity.getComment())
                .status(entity.getStatus())
                .build();

        resource.add(linkTo(methodOn(TaskController.class)
                .getTask(entity.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(PosterController.class)
                .getPoster(entity.getPoster().getId()))
                .withRel("poster"));

        resource.add(linkTo(methodOn(TaskController.class)
                .getTaskEntries(entity.getId()))
                .withRel("task_entries"));

        return resource;
    }
}
