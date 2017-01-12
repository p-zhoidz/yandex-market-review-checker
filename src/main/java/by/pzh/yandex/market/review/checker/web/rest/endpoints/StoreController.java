package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.impl.StoreService;
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
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreController {

    @Inject
    private StoreService storeService;

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        StoreDTO result = storeService.create(storeDTO);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
                .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 400 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        if(storeDTO.getId() == null) {
            return createStore(storeDTO);
        }
        StoreDTO result = storeService.update(storeDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/stores")
    public List<StoreDTO> getAllStores() {
        return storeService.findAll();
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        StoreDTO storeDTO = storeService.findOne(id);
        return Optional.ofNullable(storeDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
