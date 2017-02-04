package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.StoreController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskEntryController;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskEntryResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
@Component
public class TaskEntryResourceAssembler extends ResourceAssemblerSupport<TaskEntry, TaskEntryResource> {

    public TaskEntryResourceAssembler() {
        super(TaskEntryController.class, TaskEntryResource.class);
    }

    @Override
    public TaskEntryResource toResource(TaskEntry entity) {
        TaskEntryResource resource = TaskEntryResource.builder()
                .number(entity.getId())
                .store(entity.getStore())
                //.task(entity.getTask())
                .build();

        resource.add(linkTo(methodOn(TaskEntryController.class)
                .getTaskEntry(entity.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(StoreController.class)
                .getStore(entity.getStore().getOwner().getId(),
                        entity.getStore().getId()))
                .withRel("store"));

        resource.add(linkTo(methodOn(TaskController.class)
                .getTask(entity.getTask().getId()))
                .withRel("task"));

        return resource;
    }
}
