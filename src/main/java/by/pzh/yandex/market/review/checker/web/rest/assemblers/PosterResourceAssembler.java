package by.pzh.yandex.market.review.checker.web.rest.assemblers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.PosterController;
import by.pzh.yandex.market.review.checker.web.rest.resources.PosterResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author p.zhoidz.
 */
@Component
public class PosterResourceAssembler extends ResourceAssemblerSupport<Poster, PosterResource> {

    /**
     * Creates a new {@link ResourceAssemblerSupport} using the given controller
     * class and resource type.
     */
    public PosterResourceAssembler() {
        super(PosterController.class, PosterResource.class);
    }

    @Override
    public PosterResource toResource(Poster entity) {
        PosterResource resource = PosterResource.builder()
                .rate(entity.getRate())
                .name(entity.getName())
                .email(entity.getEmail())
                .velocity(entity.getVelocity())
                .active(entity.getActive())
                .created(entity.getCreated())
                .number(entity.getId())
                .build();

        resource.add(linkTo(methodOn(PosterController.class).getPoster(entity.getId()))
                .withSelfRel());

        return resource;
    }
}
