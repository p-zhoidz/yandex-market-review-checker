package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Client;
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
    @Mapping(target = "owner", expression = "java(clientFromId(clientId))")
    Store storeDTOToStore(Long clientId, StoreDTO storeDTO);

    @Mapping(target = "owner", expression = "java(clientFromId(clientId))")
    @Mapping(target = "id", expression = "java(id)")
    Store storeDTOToStore(Long id, Long clientId, StoreDTO storeDTO);

    /**
     * Generate {@link Client} based on id.
     *
     * @param id Identifier.
     * @return {@link Client}.
     */
    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
