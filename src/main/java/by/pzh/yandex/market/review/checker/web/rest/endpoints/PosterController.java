package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.impl.PosterService;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.PosterResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.PosterResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing Poster.
 */
@RestController
@RequestMapping("/api")
public class PosterController {

    @Inject
    private PosterService posterService;

    @Inject
    private PosterResourceAssembler posterResourceAssembler;

    @Inject
    private PagedResourcesAssembler<PosterResource> pagedAssembler;

    /**
     * POST  /posters : Create a new poster.
     *
     * @param posterDTO the posterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posterDTO,
     * or with status 200 (OK) if the poster has already an ID and was updated.
     */
    @PostMapping(value = "/posters", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PosterResource> createPoster(@Valid @RequestBody PosterDTO posterDTO) {
        Poster poster = posterService.create(posterDTO);
        PosterResource resource = posterResourceAssembler.toResource(poster);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    /**
     * PUT  /posters : Updates an existing poster.
     *
     * @param posterDTO the posterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated posterDTO,
     * or with status 422 (Bad Request) if the posterDTO is not valid,
     * or with status 500 (Internal Server Error) if the posterDTO could not be updated
     */
    @PutMapping(value = "/posters/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PosterResource> updatePoster(@PathVariable Long id,
                                                       @Valid @RequestBody PosterDTO posterDTO) {
        Poster poster = posterService.update(id, posterDTO);
        PosterResource resource = posterResourceAssembler.toResource(poster);
        return ResponseEntity.ok(resource);
    }

    /**
     * GET  /posters : get all the posters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posters in body
     */
    @GetMapping(value = "/posters", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<Resource<PosterResource>>> getPosters(@PageableDefault Pageable p) {
        Page<Poster> posters = posterService.getPosters(p.getPageNumber(), p.getPageSize());

        Page<PosterResource> map = posters.map(posterResourceAssembler::toResource);
        PagedResources<Resource<PosterResource>> resources = pagedAssembler.toResource(map);
        return ResponseEntity.ok(resources);
    }

    /**
     * GET  /posters/:id : get the "id" poster.
     *
     * @param id the id of the posterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the posterDTO,
     * or with status 404 (Not Found)
     */
    @RequestMapping(value = "/posters/{id}", method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PosterResource> getPoster(@PathVariable Long id) {
        Poster poster = posterService.findOne(id);

        return Optional.ofNullable(poster).map(c -> {
            PosterResource resource = posterResourceAssembler.toResource(poster);
            return ResponseEntity.ok(resource);
        }).orElseThrow(() -> new EntityNotFoundException(Poster.class,
                String.format("client with id %s not found", id)));
    }

    /**
     * DELETE  /posters/:id : delete the "id" poster.
     *
     * @param id the id of the posterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posters/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        posterService.delete(id);
        return ResponseEntity.ok().build();
    }

}
