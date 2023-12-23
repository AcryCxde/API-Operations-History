package ru.netology.IlyaRomanov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.IlyaRomanov.controller.CustomerController;
import ru.netology.IlyaRomanov.controller.dto.CustomerDTO;
import ru.netology.IlyaRomanov.controller.dto.CustomersGetResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerControllerTest extends OperationHistoryApiApplicationTest {
    @Autowired
    private CustomerController customerController;

    @Test
    public void getCustomersTest() {
        CustomersGetResponse customers = customerController.getCustomers();
        CustomerDTO customer1 = customers.getCustomers().get(0);
        CustomerDTO customer2 = customers.getCustomers().get(1);

        assertEquals(1, customer1.getId());
        assertEquals("Spring", customer1.getName());
        assertEquals(2, customer2.getId());
        assertEquals("Boot", customer2.getName());
    }

    @Test
    public void getCustomerTest() {
        CustomerDTO customer = customerController.getCustomer(1);

        assertEquals(1, customer.getId());
        assertEquals("Spring", customer.getName());
    }

    @Test
    public void addCustomerTest() {
        CustomerDTO customer = new CustomerDTO(3, "App");
        customerController.addCustomer(customer);

        assertEquals(customer, customerController.getCustomer(3));
    }
}
