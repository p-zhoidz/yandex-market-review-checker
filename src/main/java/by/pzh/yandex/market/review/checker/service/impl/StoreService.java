package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.mappers.StoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreService {
    private StoreRepository storeRepository;
    private StoreMapper storeMapper;

    @Inject
    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public StoreDTO create(StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToNewStore(storeDTO);
        return save(store);
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public StoreDTO update(StoreDTO storeDTO) {
        Store store = storeMapper.storeDTOToStore(storeDTO);
        return save(store);
    }

    /**
     * Get all the stores.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StoreDTO> findAll() {
        return storeRepository.findAll().stream()
                .map(storeMapper::storeToStoreDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StoreDTO findOne(Long id) {
        Store store = storeRepository.findOne(id);
        return storeMapper.storeToStoreDTO(store);
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
    private StoreDTO save(Store store) {
        store = storeRepository.save(store);
        return storeMapper.storeToStoreDTO(store);
    }
}