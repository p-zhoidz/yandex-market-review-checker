package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import by.pzh.yandex.market.review.checker.service.impl.ClientService;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.ClientResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.ClientResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientController {

    @Inject
    private ClientResourceAssembler clientResourceAssembler;

    @Inject
    private ClientService clientService;

    @Inject
    private PagedResourcesAssembler<Client> pagedAssembler;

    /**
     * POST  /customers : Create a new customer.
     *
     * @param clientDTO the clientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientDTO,
     * or with status 422 (Bad Request) if the customer DTO is not valid.
     */
    @PostMapping("/clients")
    public ResponseEntity<ClientResource> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = clientService.create(clientDTO);
        ClientResource resource = clientResourceAssembler.toResource(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    /**
     * PUT  /customers : Updates an existing customer.
     *
     * @param clientDTO the clientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientDTO,
     * or with status 422 (Bad Request) if the clientDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientDTO could not be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientResource> updateClient(@PathVariable Long id,
                                                       @Valid @RequestBody ClientDTO clientDTO) {
        Client client = clientService.update(id, clientDTO);
        ClientResource resource = clientResourceAssembler.toResource(client);

        return ResponseEntity.ok(resource);
    }


    /**
     * GET  /customers : get all the customers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public PagedResources<ClientResource> getClients(@PageableDefault Pageable p) {
        Page<Client> clients = clientService.getClients(p.getPageNumber(), p.getPageSize());
        return pagedAssembler.toResource(clients, clientResourceAssembler);
    }

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/clients/{id}")
    public ResponseEntity<ClientResource> getClient(@PathVariable Long id) {
        Client client = clientService.findOne(id);
        return Optional.ofNullable(client).map(c -> {
            ClientResource resource = clientResourceAssembler.toResource(client);
            return ResponseEntity.ok(resource);
        }).orElseThrow(() -> new EntityNotFoundException(Client.class,
                String.format("client with id %s not found", id)));
    }

    /**
     * DELETE  /customers/:id : delete the "id" customer.
     *
     * @param id the id of the customerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.ok().build();
    }

}
