package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.repository.ClientRepository;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ClientMapper;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.ClientResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.ClientResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private ClientResourceAssembler clientResourceAssembler;
    private PagedResourcesAssembler<Client> pagedAssembler;

    /**
     * Parametrized constructor.
     *
     * @param clientRepository customer repository instance..
     * @param clientMapper     customer mapper instance.
     */
    @Inject
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper,
                         ClientResourceAssembler clientResourceAssembler,
                         PagedResourcesAssembler<Client> pagedAssembler) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.clientResourceAssembler = clientResourceAssembler;
        this.pagedAssembler = pagedAssembler;
    }

    /**
     * Update a customer.
     *
     * @param id  entity identifier
     * @param dto the entity to save
     * @return the persisted entity
     */
    public ClientResource update(Long id, ClientDTO dto) {
        Client client = clientMapper.clientDTOToClient(id, dto);
        clientRepository.save(client);
        return clientResourceAssembler.toResource(client);
    }

    /**
     * Create a customer.
     *
     * @param dto the entity to save
     * @return the persisted entity
     */
    public ClientResource create(ClientDTO dto) {
        Client client = clientMapper.clientDTOToClient(dto);
        clientRepository.save(client);
        return clientResourceAssembler.toResource(client);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public PagedResources<ClientResource> getClients(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        return pagedAssembler.toResource(clients, clientResourceAssembler);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ClientResource> findOne(Long id) {
        return Optional.ofNullable(clientRepository.findOne(id))
                .map(clientResourceAssembler::toResource);
    }

    /**
     * Delete the  customer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        clientRepository.delete(id);
    }

}
