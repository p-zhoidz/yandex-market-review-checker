package by.pzh.yandex.market.review.checker.web.rest.endpoints.utils;

import by.pzh.yandex.market.review.checker.web.rest.endpoints.ClientController;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
public final class ClientControllerLinkBuilder {

    private ClientControllerLinkBuilder() {

    }

    public static Link getSelfLink(Long id) {
        return linkTo(methodOn(ClientController.class).getClient(id))
                .withSelfRel();
    }
}
