package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.impl.PosterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Poster.
 */
@RestController
@RequestMapping("/api")
public class PosterController {

    @Inject
    private PosterService posterService;

    /**
     * POST  /posters : Create a new poster.
     *
     * @param posterDTO the posterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posterDTO,
     * or with status 200 (OK) if the poster has already an ID and was updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posters")
    public ResponseEntity<PosterDTO> createPoster(@Valid @RequestBody PosterDTO posterDTO) throws URISyntaxException {
        PosterDTO result = posterService.create(posterDTO);
        return ResponseEntity.created(new URI("/api/posters/" + result.getId()))
                .body(result);
    }

    /**
     * PUT  /posters : Updates an existing poster.
     *
     * @param posterDTO the posterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated posterDTO,
     * or with status 422 (Bad Request) if the posterDTO is not valid,
     * or with status 500 (Internal Server Error) if the posterDTO could not be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posters")
    public ResponseEntity<PosterDTO> updatePoster(@Valid @RequestBody PosterDTO posterDTO) throws URISyntaxException {
        if (posterDTO.getId() == null) {
            return createPoster(posterDTO);
        }
        PosterDTO result = posterService.update(posterDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET  /posters : get all the posters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posters in body
     */
    @GetMapping("/posters")
    public List<PosterDTO> getAllPosters() {
        return posterService.findAll();
    }

    /**
     * GET  /posters/:id : get the "id" poster.
     *
     * @param id the id of the posterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the posterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/posters/{id}")
    public ResponseEntity<PosterDTO> getPoster(@PathVariable Long id) {
        PosterDTO posterDTO = posterService.findOne(id);
        return Optional.ofNullable(posterDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
