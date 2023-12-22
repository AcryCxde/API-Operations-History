package ru.netology.IlyaRomanov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.IlyaRomanov.controller.dto.CustomerDTO;
import ru.netology.IlyaRomanov.controller.dto.CustomersGetResponse;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public CustomersGetResponse getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName()))
                .collect(Collectors.toList());
        return new CustomersGetResponse(customerDTOS);
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomer(@PathVariable int id){
        return customerService.getCustomers().stream()
                .filter(customer -> customer.getId() == id)
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName()))
                .findFirst().orElse(null);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer newCustomer = new Customer(customerDTO.getId(), customerDTO.getName());
        Customer addedCustomer = customerService.addCustomer(newCustomer);
        if (addedCustomer != null) {
            CustomerDTO addedCustomerDTO = new CustomerDTO(addedCustomer.getId(), addedCustomer.getName());
            return new ResponseEntity<>(addedCustomerDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}