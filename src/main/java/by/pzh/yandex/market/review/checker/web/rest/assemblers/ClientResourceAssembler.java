package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.ClientController;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.StoreController;
import by.pzh.yandex.market.review.checker.web.rest.resources.ClientResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
@Component
public class ClientResourceAssembler extends ResourceAssemblerSupport<Client, ClientResource> {

    public ClientResourceAssembler() {
        super(ClientController.class, ClientResource.class);
    }


    @Override
    public ClientResource toResource(Client entity) {
        ClientResource clientResource = new ClientResource();
        clientResource.setNumber(entity.getId());
        clientResource.setActive(entity.getActive());
        clientResource.setEmail(entity.getEmail());
        clientResource.setComment(entity.getComment());
        clientResource.setCreated(entity.getCreated());
        clientResource.add(linkTo(methodOn(ClientController.class).getClient(entity.getId())).withSelfRel());
        clientResource.add(linkTo(methodOn(StoreController.class)
                .getClientStores(entity.getId(), new PageRequest(0, 20))).withRel("stores"));

        return clientResource;
    }
}
