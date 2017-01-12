package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Customer;
import by.pzh.yandex.market.review.checker.repository.CustomerRepository;
import by.pzh.yandex.market.review.checker.service.dto.CustomerDTO;
import by.pzh.yandex.market.review.checker.service.mappers.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Inject
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        return save(customer);
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToNewCustomer(customerDTO);
        return save(customer);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> result = customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerDTO findOne(Long id) {
        Customer customer = customerRepository.findOne(id);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        return customerDTO;
    }

    /**
     * Delete the  customer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        customerRepository.delete(id);
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    private CustomerDTO save(Customer customer) {
        customer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(customer);
    }
}
