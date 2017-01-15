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

    /**
     * Map {@link Customer} to {@link CustomerDTO}.
     *
     * @param customer entity to be mapped.
     * @return instance of the {@link CustomerDTO}.
     */
    CustomerDTO customerToCustomerDTO(Customer customer);

    /**
     * Map list of {@link Customer} to list of {@link CustomerDTO}.
     *
     * @param customers list of entities to be mapped.
     * @return list of {@link CustomerDTO}.
     */
    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customers);

    /**
     * Map {@link CustomerDTO} to {@link Customer}.
     *
     * @param customerDTO entity to be mapped.
     * @return instance of the {@link Customer}.
     */
    @Named("customerDTOToCustomer")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    /**
     * Map {@link CustomerDTO} to {@link Customer}.
     * Skipping id.
     *
     * @param customerDTO entity to be mapped.
     * @return instance of the {@link Customer}.
     */
    @Mapping(target = "id", ignore = true)
    Customer customerDTOToNewCustomer(CustomerDTO customerDTO);

    /**
     * Map list of {@link CustomerDTO} to list of {@link Customer}.
     *
     * @param customerDTOs list of entities to be mapped.
     * @return list of {@link Customer}.
     */
    @IterableMapping(qualifiedByName = "customerDTOToCustomer")
    List<Customer> customerDTOsToCustomers(List<CustomerDTO> customerDTOs);
}
