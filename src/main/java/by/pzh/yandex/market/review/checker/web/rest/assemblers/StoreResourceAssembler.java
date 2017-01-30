package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.ClientController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.StoreController;
import by.pzh.yandex.market.review.checker.web.rest.resources.StoreResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
@Component
public class StoreResourceAssembler extends ResourceAssemblerSupport<Store, StoreResource> {

    public StoreResourceAssembler() {
        super(StoreController.class, StoreResource.class);
    }

    @Override
    public StoreResource toResource(Store entity) {
        return toResource(entity, entity.getOwner().getId());
    }

    public StoreResource toResource(Store entity, Long clientId) {
        StoreResource resource = new StoreResource();
        resource.setActive(entity.getActive());
        resource.setNumber(entity.getId());
        resource.setDesiredReviewsNumber(entity.getDesiredReviewsNumber());
        resource.setUrl(entity.getUrl());
        resource.setCreated(entity.getCreated());
        resource.add(linkTo(methodOn(StoreController.class).getStore(clientId, entity.getId()))
                .withSelfRel());
        resource.add(linkTo(methodOn(ClientController.class).getClient(clientId)).withRel("owner"));

        return resource;
    }
}
