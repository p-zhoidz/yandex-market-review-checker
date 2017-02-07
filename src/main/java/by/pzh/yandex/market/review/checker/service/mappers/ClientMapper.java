package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper {

    /**
     * Map {@link Client} to {@link ClientDTO}.
     *
     * @param client entity to be mapped.
     * @return instance of the {@link ClientDTO}.
     */
    ClientDTO clientToClientDTO(Client client);

    /**
     * Map list of {@link Client} to list of {@link ClientDTO}.
     *
     * @param clients list of entities to be mapped.
     * @return list of {@link ClientDTO}.
     */
    List<ClientDTO> clientsToClientDTOs(List<Client> clients);

    /**
     * Map {@link ClientDTO} to {@link Client}.
     *
     * @param id       resource identifier.
     * @param dto entity to be mapped.
     * @return instance of the {@link Client}.
     */
    @Named("clientDTOToClient")
    @Mapping(target = "id", expression = "java(id)")
    Client clientDTOToClient(Long id, ClientDTO dto);

    /**
     * Map {@link ClientDTO} to {@link Client}.
     * Skipping id.
     *
     * @param dto entity to be mapped.
     * @return instance of the {@link Client}.
     */
    Client clientDTOToClient(ClientDTO dto);

    /**
     * Map list of {@link ClientDTO} to list of {@link Client}.
     *
     * @param clientDTOs list of entities to be mapped.
     * @return list of {@link Client}.
     */
    @IterableMapping(qualifiedByName = "clientDTOToClient")
    List<Client> clientDTOsToClients(List<ClientDTO> clientDTOs);
}
