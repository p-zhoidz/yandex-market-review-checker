package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskController;
import by.pzh.yandex.market.review.checker.web.rest.resources.TaskResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * @author p.zhoidz.
 */
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

    public TaskResourceAssembler() {
        super(TaskController.class, TaskResource.class);
    }

    @Override
    public TaskResource toResource(Task entity) {
        return null;
    }
}
