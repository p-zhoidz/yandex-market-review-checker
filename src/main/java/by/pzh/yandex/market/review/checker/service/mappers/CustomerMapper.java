package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Customer;
import by.pzh.yandex.market.review.checker.service.dto.CustomerDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customers);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    List<Customer> customerDTOsToCustomers(List<CustomerDTO> customerDTOs);
}
