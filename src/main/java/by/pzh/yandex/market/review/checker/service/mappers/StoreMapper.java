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

    @Mapping(source = "owner.id", target = "ownerId")
    StoreDTO storeToStoreDTO(Store store);

    List<StoreDTO> storesToStoreDTOs(List<Store> stores);

    @Named("storeDTOToStore")
    @Mapping(source = "ownerId", target = "owner")
    Store storeDTOToStore(StoreDTO storeDTO);

    @IterableMapping(qualifiedByName = "storeDTOToStore")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "id", ignore = true)
    Store storeDTOToNewStore(StoreDTO storeDTO);

    List<Store> storeDTOsToStores(List<StoreDTO> storeDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
