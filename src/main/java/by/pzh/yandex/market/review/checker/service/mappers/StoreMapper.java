package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Customer;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Store and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper {

    /**
     * Map {@link Store} to {@link StoreDTO}.
     *
     * @param store entity to be mapped.
     * @return instance of the {@link StoreDTO}.
     */
    @Mapping(source = "owner.id", target = "ownerId")
    StoreDTO storeToStoreDTO(Store store);

    /**
     * Map list of {@link Store} to list of {@link StoreDTO}.
     *
     * @param stores list of entities to be mapped.
     * @return list of {@link StoreDTO}.
     */
    List<StoreDTO> storesToStoreDTOs(List<Store> stores);

    /**
     * Map {@link StoreDTO} to {@link Store}.
     *
     * @param storeDTO entity to be mapped.
     * @return instance of the {@link Store}.
     */
    @Named("storeDTOToStore")
    @Mapping(source = "ownerId", target = "owner")
    Store storeDTOToStore(StoreDTO storeDTO);

    /**
     * Map {@link StoreDTO} to {@link Store}.
     * Skipping id.
     *
     * @param storeDTO entity to be mapped.
     * @return instance of the {@link Store}.
     */
    @IterableMapping(qualifiedByName = "storeDTOToStore")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "id", ignore = true)
    Store storeDTOToNewStore(StoreDTO storeDTO);

    /**
     * Map list of {@link StoreDTO} to list of {@link Store}.
     *
     * @param storeDTOs list of entities to be mapped.
     * @return list of {@link Store}.
     */
    List<Store> storeDTOsToStores(List<StoreDTO> storeDTOs);

    /**
     * Generate {@link Customer} based on id.
     *
     * @param id Identifier.
     * @return {@link Customer}.
     */
    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
