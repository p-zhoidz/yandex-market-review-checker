package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.impl.PosterService;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * REST controller for managing Poster.
 */
@RestController
@RequestMapping("/api")
public class PosterController {
    private PosterService posterService;
    private PosterMapper posterMapper;

    @Inject
    public PosterController(PosterService posterService, PosterMapper posterMapper) {
        this.posterService = posterService;
        this.posterMapper = posterMapper;
    }

    /**
     * POST  /posters : Create a new poster.
     *
     * @param posterDTO the posterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posterDTO,
     * or with status 200 (OK) if the poster has already an ID and was updated.
     */
    @PostMapping(value = "/posters", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PosterDTO> createPoster(@Valid @RequestBody PosterDTO posterDTO) {
        Poster poster = posterService.create(posterDTO);
        PosterDTO dto = posterMapper.posterToPosterDTO(poster);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
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
    public ResponseEntity<PosterDTO> updatePoster(@PathVariable Long id,
                                                  @Valid @RequestBody PosterDTO posterDTO) {
        Poster client = posterService.update(id, posterDTO);
        PosterDTO dto = posterMapper.posterToPosterDTO(client);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET  /posters : get all the posters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posters in body
     */
    @RequestMapping(value = "/posters", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<PosterDTO>> getPosters(@PageableDefault Pageable p) {
        Page<Poster> posters = posterService.getPosters(p.getPageNumber(), p.getPageSize());
        Page<PosterDTO> dtos = posters.map(posterMapper::posterToPosterDTO);
        return ResponseEntity.ok(dtos);
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
    public ResponseEntity<PosterDTO> getPoster(@PathVariable Long id) {
        return posterService.findOne(id)
                .map(posterMapper::posterToPosterDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Poster.class,
                        String.format("poster with id %s not found", id)));
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
