package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Customer;
import by.pzh.yandex.market.review.checker.service.dto.CustomerDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customers);

    @Named("customerDTOToCustomer")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    @Mapping(target = "id", ignore = true)
    Customer customerDTOToNewCustomer(CustomerDTO customerDTO);

    @IterableMapping(qualifiedByName = "customerDTOToCustomer")
    List<Customer> customerDTOsToCustomers(List<CustomerDTO> customerDTOs);
}
