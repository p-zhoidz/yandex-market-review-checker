package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.web.rest.endpoints.BaseController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.ClientController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.PosterController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.TaskController;
import by.pzh.yandex.market.review.checker.web.rest.resources.BaseResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Base resource assembler component.
 *
 * @author p.zhoidz.
 */
@Component
public class BaseResourceAssembler extends ResourceAssemblerSupport<Object, BaseResource> {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Default constructor. Initializes assembler support.
     */
    public BaseResourceAssembler() {
        super(BaseController.class, BaseResource.class);
    }

    @Override
    public BaseResource toResource(Object entity) {
        return toResource();
    }

    /**
     * Provides {@link BaseResource} instance.
     *
     * @return {@link BaseResource} which contains set of base API URIs.
     */
    public BaseResource toResource() {
        BaseResource resource = new BaseResource();
        resource.add(linkTo(methodOn(ClientController.class)
                .getClients(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)))
                .withRel("clients"));

        resource.add(linkTo(methodOn(PosterController.class)
                .getPosters(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)))
                .withRel("posters"));

        resource.add(linkTo(methodOn(TaskController.class)
                .getTasks(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)))
                .withRel("tasks"));

        return resource;
    }
}
