package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.impl.StoreService;
import by.pzh.yandex.market.review.checker.service.mappers.StoreMapper;
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

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoreController {

    private StoreService storeService;
    private StoreMapper storeMapper;

    @Inject
    public StoreController(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
    }

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO,
     * or with status 200 (OK) if the store has already an ID and was updated.
     */
    @RequestMapping(value = "/clients/{client-id}/stores", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StoreDTO> createStore(@PathVariable("client-id") Long clientId,
                                                @Valid @RequestBody StoreDTO storeDTO) {
        Store store = storeService.create(clientId, storeDTO);
        StoreDTO dto = storeMapper.storeToStoreDTO(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 422 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO could not be updated
     */
    @PutMapping(value = "/clients/{client-id}/stores/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id,
                                                @PathVariable("client-id") Long clientID,
                                                @Valid @RequestBody StoreDTO storeDTO) {
        Store store = storeService.update(id, clientID, storeDTO);
        StoreDTO dto = storeMapper.storeToStoreDTO(store);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @RequestMapping(value = "/clients/{client-id}/stores", method = RequestMethod.GET)
    public ResponseEntity<Page<StoreDTO>> getClientStores(@PathVariable("client-id") long clientId,
                                                          @PageableDefault Pageable p) {
        Page<Store> customerStores = storeService.getCustomerStores(clientId,
                p.getPageNumber(), p.getPageSize());
        Page<StoreDTO> dtos = customerStores.map(storeMapper::storeToStoreDTO);
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/clients/{client-id}/stores/{id}", method = RequestMethod.GET)
    public ResponseEntity<StoreDTO> getStore(@PathVariable("client-id") Long clientId,
                                             @PathVariable("id") Long id) {
        return storeService.findOne(id)
                .map(storeMapper::storeToStoreDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Poster.class,
                        String.format("store with id %s not found", id)));
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
