package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Customer;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Store and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    StoreDTO storeToStoreDTO(Store store);

    List<StoreDTO> storesToStoreDTOs(List<Store> stores);

    @Mapping(source = "ownerId", target = "owner")
    Store storeDTOToStore(StoreDTO storeDTO);

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
