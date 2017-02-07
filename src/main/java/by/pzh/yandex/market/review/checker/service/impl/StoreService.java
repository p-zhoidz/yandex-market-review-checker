package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.mappers.StoreMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * Parametrized constructor.
     *
     * @param storeRepository store repository instance.
     * @param storeMapper     store mapper instance.
     */
    @Inject
    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Create a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public Store create(Long clientId, StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(clientId, storeDTO);
        return storeRepository.save(store);
    }

    /**
     * Update a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public Store update(Long id, Long ownerId, StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(id, ownerId, storeDTO);
        return storeRepository.save(store);
    }


    @Transactional(readOnly = true)
    public Page<Store> getCustomerStores(long ownerId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return storeRepository.findAll(where(forOwner(ownerId)), pageRequest);
    }

    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Store> findOne(Long id) {
        return Optional.ofNullable(storeRepository.findOne(id));
    }

    /**
     * Delete the  store by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        storeRepository.delete(id);
    }

}
