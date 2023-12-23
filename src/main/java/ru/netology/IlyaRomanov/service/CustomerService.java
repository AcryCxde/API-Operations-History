package ru.netology.IlyaRomanov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.netology.IlyaRomanov.domain.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
@AllArgsConstructor
@Component
public class CustomerService {
    private final ArrayList<Customer> customers;

    public ArrayList<Customer> getCustomers() {
        return customers;
    }


    public Customer addCustomer(Customer item) {
        customers.add(item);
        return item;
    }

    @PostConstruct
    public void initStorage() {
        customers.add(new Customer(1, "Spring"));
        customers.add(new Customer(2, "Boot"));
    }
}

