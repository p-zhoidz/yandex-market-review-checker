package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.repository.ClientRepository;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import by.pzh.yandex.market.review.checker.service.mappers.ClientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    /**
     * Parametrized constructor.
     *
     * @param clientRepository customer repository instance..
     * @param clientMapper     customer mapper instance.
     */
    @Inject
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Update a customer.
     *
     * @param id  entity identifier
     * @param dto the entity to save
     * @return the persisted entity
     */
    public Client update(Long id, ClientDTO dto) {
        Client client = clientMapper.clientDTOToClient(id, dto);
        return clientRepository.save(client);
    }

    /**
     * Create a customer.
     *
     * @param dto the entity to save
     * @return the persisted entity
     */
    public Client create(ClientDTO dto) {
        Client client = clientMapper.clientDTOToClient(dto);
        return clientRepository.save(client);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Client> getClients(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return clientRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        return clientRepository.findOne(id);
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
