package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.web.rest.assemblers.BaseResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.BaseResource;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Base controller. Provided initial entry point for the API.
 *
 * @author p.zhoidz.
 */
@RestController("/api")
public class BaseController {

    private BaseResourceAssembler baseResourceAssembler;

    /**
     * Parametrized constructor.
     *
     * @param baseResourceAssembler base resource assembler.
     */
    @Inject
    public BaseController(BaseResourceAssembler baseResourceAssembler) {
        this.baseResourceAssembler = baseResourceAssembler;
    }

    /**
     * Base end point.
     *
     * @return returns base set of URIs.
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<BaseResource> getBaseURIs() {
        BaseResource resource = baseResourceAssembler.toResource(null);
        return ResponseEntity.ok(resource);
    }
}
