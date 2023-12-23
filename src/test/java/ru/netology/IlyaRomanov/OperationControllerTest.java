package ru.netology.IlyaRomanov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.IlyaRomanov.controller.OperationController;
import ru.netology.IlyaRomanov.controller.dto.OperationDTO;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.domain.Operation;
import ru.netology.IlyaRomanov.service.StatementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationControllerTest extends OperationHistoryApiApplicationTest {
    @Autowired
    private OperationController operationController;

    @Autowired
    private StatementService statementService;  // Теперь внедряется в тест

    @Test
    public void addOperationTest() throws InterruptedException {
        OperationDTO operationDTO1 = new OperationDTO(1, 123, "rub", "merch", 1);
        OperationDTO operationDTO2 = new OperationDTO(2, 1231, "rub", "merch1", 2);
        operationController.addOperation(operationDTO1);
        operationController.addOperation(operationDTO2);

        Operation newOperation1 = new Operation(
                operationDTO1.getId(),
                operationDTO1.getSum(),
                operationDTO1.getCurrency(),
                operationDTO1.getMerchant(),
                operationDTO1.getClientId());

        Operation newOperation2 = new Operation(
                operationDTO2.getId(),
                operationDTO2.getSum(),
                operationDTO2.getCurrency(),
                operationDTO2.getMerchant(),
                operationDTO2.getClientId());

        Map<Customer, List<Operation>> eqMap = new HashMap<>();
        List<Operation> l1 = new ArrayList<>();
        l1.add(newOperation1);
        List<Operation> l2 = new ArrayList<>();
        l2.add(newOperation2);
        l2.add(newOperation2);
        Customer c1 = new Customer(1, "Spring");
        Customer c2 = new Customer(2, "Boot");
        eqMap.put(c1, l1);
        eqMap.put(c2, l2);
        Thread.sleep(3000);

        assertEquals(eqMap, statementService.getStatement());
    }

    @Test
    public void deleteOperationTest() throws InterruptedException {
        OperationDTO operationDTO1 = new OperationDTO(1, 123, "rub", "merch", 1);
        OperationDTO operationDTO2 = new OperationDTO(2, 1231, "rub", "merch1", 2);
        operationController.addOperation(operationDTO1);
        operationController.addOperation(operationDTO2);

        Operation newOperation2 = new Operation(
                operationDTO2.getId(),
                operationDTO2.getSum(),
                operationDTO2.getCurrency(),
                operationDTO2.getMerchant(),
                operationDTO2.getClientId());

        Map<Customer, List<Operation>> eqMap = new HashMap<>();
        List<Operation> l1 = new ArrayList<>();
        List<Operation> l2 = new ArrayList<>();
        l2.add(newOperation2);
        Customer c1 = new Customer(1, "Spring");
        Customer c2 = new Customer(2, "Boot");
        eqMap.put(c1, l1);
        eqMap.put(c2, l2);

        Thread.sleep(2000);

        operationController.deleteOperation(1);

        // Remove the operation with id 1 from the expected map
        eqMap.get(c1).removeIf(operation -> operation.getId() == 1);

        // Вместо создания нового экземпляра используйте внедренный в контроллер StatementService
        assertEquals(eqMap, statementService.getStatement());
    }

    @Test
    public void getCustomerOperationsTest() throws InterruptedException {
        statementService.deleteOperationById(1);
        statementService.deleteOperationById(2);
        OperationDTO operationDTO1 = new OperationDTO(1, 123, "rub", "merch", 1);
        OperationDTO operationDTO3 = new OperationDTO(1, 123, "rub", "merch", 1);
        OperationDTO operationDTO2 = new OperationDTO(2, 1231, "rub", "merch1", 1);
        operationController.addOperation(operationDTO1);
        operationController.addOperation(operationDTO2);
        operationController.addOperation(operationDTO3);

        List<OperationDTO> l1 = new ArrayList<>();
        l1.add(operationDTO1);
        l1.add(operationDTO2);
        l1.add(operationDTO3);
        Thread.sleep(3000);

        assertEquals(l1, operationController.getCustomerOperations(1));
    }
}
