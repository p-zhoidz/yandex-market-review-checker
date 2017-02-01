package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskEntryController;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskEntryResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
public class TaskEntryResourceAssembler extends ResourceAssemblerSupport<TaskEntry, TaskEntryResource> {

    public TaskEntryResourceAssembler() {
        super(TaskEntryController.class, TaskEntryResource.class);
    }

    @Override
    public TaskEntryResource toResource(TaskEntry entity) {
        TaskEntryResource resource = TaskEntryResource.builder()
                .number(entity.getId())
                .store(entity.getStore())
                .task(entity.getTask())
                .build();

        resource.add(linkTo(methodOn(TaskEntryController.class)
                .getTaskEntry(entity.getId()))
                .withSelfRel());

/*build.add(linkTo(methodOn(StoreController.class).getStore()).withRel("store"));

        build.add(linkTo(methodOn(TaskController.class).getTask()));*/


        return resource;
    }
}
