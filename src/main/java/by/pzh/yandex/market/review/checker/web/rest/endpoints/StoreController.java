package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.impl.StoreService;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.StoreResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.StoreResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreController {

    @Inject
    private StoreService storeService;

    @Inject
    private StoreResourceAssembler storeResourceAssembler;

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO,
     * or with status 200 (OK) if the store has already an ID and was updated.
     */
    @RequestMapping(value = "/clients/{client-id}/stores", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<StoreResource> createStore(@PathVariable("client-id") Long clientId,
                                                     @Valid @RequestBody StoreDTO storeDTO) {
        Store result = storeService.create(clientId, storeDTO);
        StoreResource resource = storeResourceAssembler.toResource(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 422 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO could not be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping(value = "/clients/{client-id}/stores/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<StoreResource> updateStore(@PathVariable Long id,
                                                     @PathVariable("client-id") Long clientID,
                                                     @Valid @RequestBody StoreDTO storeDTO) {
        Store result = storeService.update(id, clientID, storeDTO);
        StoreResource resource = storeResourceAssembler.toResource(result);
        return ResponseEntity.ok()
                .body(resource);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @RequestMapping(value = "/clients/{client-id}/stores", method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<StoreResource>> getClientStores(@PathVariable("client-id") long clientId,
                                                               @PageableDefault Pageable p) {
        Page<StoreResource> result = storeService.getCustomerStores(clientId, p.getPageNumber(),
                p.getPageSize())
                .map(store -> storeResourceAssembler.toResource(store));

        return ResponseEntity.ok(result);
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/clients/{client-id}/stores/{id}", method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<StoreResource> getStore(@PathVariable("client-id") Long clientId,
                                                  @PathVariable("id") Long id) {
        Store store = storeService.findOne(id);
        StoreResource resource = storeResourceAssembler.toResource(store);

        return ResponseEntity.ok(resource);
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{client-id}/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable("client-id") Long clientId,
                                            @PathVariable Long id) {
        storeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
