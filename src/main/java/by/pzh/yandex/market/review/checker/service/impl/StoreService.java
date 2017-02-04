package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.mappers.StoreMapper;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.StoreResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.StoreResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

import static by.pzh.yandex.market.review.checker.repository.specifications.StoreSpecifications.forOwner;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreService {
    private StoreRepository storeRepository;
    private StoreMapper storeMapper;
    private StoreResourceAssembler storeResourceAssembler;
    private PagedResourcesAssembler<Store> pagedAssembler;

    /**
     * Parametrized constructor.
     *
     * @param storeRepository store repository instance.
     * @param storeMapper     store mapper instance.
     */
    @Inject
    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper,
                        StoreResourceAssembler storeResourceAssembler,
                        PagedResourcesAssembler<Store> pagedAssembler) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.storeResourceAssembler = storeResourceAssembler;
        this.pagedAssembler = pagedAssembler;
    }

    /**
     * Create a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public StoreResource create(Long clientId, StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(clientId, storeDTO);
        return save(store);
    }

    /**
     * Update a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public StoreResource update(Long id, Long ownerId, StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(id, ownerId, storeDTO);
        return save(store);
    }


    @Transactional(readOnly = true)
    public PagedResources<StoreResource> getCustomerStores(long ownerId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);

        Page<Store> stores = storeRepository.findAll(where(forOwner(ownerId)), pageRequest);
        return pagedAssembler.toResource(stores, storeResourceAssembler);
    }

    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StoreResource> findOne(Long id) {

        return Optional.ofNullable(storeRepository.findOne(id))
                .map(storeResourceAssembler::toResource);
    }

    /**
     * Delete the  store by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        storeRepository.delete(id);
    }

    /**
     * Save a store.
     *
     * @param store the entity to save
     * @return the persisted entity
     */
    private StoreResource save(Store store) {
        store = storeRepository.save(store);
        storeMapper.storeToStoreDTO(store);
        return storeResourceAssembler.toResource(store);
    }
}
