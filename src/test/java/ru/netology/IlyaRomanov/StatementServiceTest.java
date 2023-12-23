package ru.netology.IlyaRomanov;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.domain.Operation;
import ru.netology.IlyaRomanov.service.StatementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementServiceTest extends OperationHistoryApiApplicationTest {
    private Map<Customer, List<Operation>> statement = new HashMap<>();

    @Autowired
    private StatementService statementService;

    @BeforeEach
    public void setUp() {
        statementService.getStatement().clear();
    }

    @Test
    public void AddToStatementTest() {
        Operation operation = new Operation(1, 123, "Rub", "Merch", 1);
        statementService.addOperation(operation);

        statementService.addToStatement(1);

        Map<Customer, List<Operation>> eqStatement = new HashMap<>();

        // Первый клиент
        Customer customer1 = new Customer(1, "Spring");
        List<Operation> opers1 = new ArrayList<>();
        opers1.add(operation);
        eqStatement.put(customer1, opers1);

        // Второй клиент с пустым списком операций
        Customer customer2 = new Customer(2, "Boot");

        assertEquals(eqStatement, statementService.getStatement());
    }




    public Map<Customer, List<Operation>> getStatement() {
        return statement;
    }

    @Test
    public void deleteOperationById() {
        int operationIdToDelete = 1; // Идентификатор операции, которую вы хотите удалить
        // Находим операцию по ID
        Operation operationToDelete = null;
        for (List<Operation> operations : statement.values()) {
            for (Operation operation : operations) {
                if (operation.getId() == operationIdToDelete) {
                    operationToDelete = operation;
                    break;
                }
            }
            if (operationToDelete != null) {
                operations.remove(operationToDelete);
                System.out.println("Deleted operation: " + operationToDelete);
            }
        }
    }
}