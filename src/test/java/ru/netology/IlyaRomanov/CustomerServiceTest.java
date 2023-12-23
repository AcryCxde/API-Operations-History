package ru.netology.IlyaRomanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest extends OperationHistoryApiApplicationTest {
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        // Очищаем список клиентов перед каждым тестом
        customerService.getCustomers().clear();
    }

    @Test
    public void addCustomerTest() {
        Customer customer1 = new Customer(1, "Spring");
        Customer customer2 = new Customer(2, "Boot");
        Customer customer3 = new Customer(3, "Cust");

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        assertEquals(customers, customerService.getCustomers());
    }
}
