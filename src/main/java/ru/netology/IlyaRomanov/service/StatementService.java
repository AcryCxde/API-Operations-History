package ru.netology.IlyaRomanov.service;

import org.springframework.stereotype.Component;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.domain.Operation;

import java.util.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class StatementService {
    private static ArrayList<Operation> operations = new ArrayList<>();
    private static Map<Customer, List<Operation>> statement = new HashMap<>();
    private static CustomerService customerService;
    private static final Lock lock = new ReentrantLock();

    @Autowired
    public StatementService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void addOperation(Operation operation) {
        lock.lock();
        try {
            operations.add(operation);
        } finally {
            lock.unlock();
        }
    }

    public boolean deleteOperationById(int id) {
        lock.lock();
        try {
            Iterator<Operation> operationIterator = operations.iterator();
            while (operationIterator.hasNext()) {
                Operation operation = operationIterator.next();
                if (operation.getId() == id) {
                    operationIterator.remove();
                    removeFromStatement(id);
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void removeFromStatement(int id) {
        Iterator<Map.Entry<Customer, List<Operation>>> statementIterator = statement.entrySet().iterator();
        while (statementIterator.hasNext()) {
            Map.Entry<Customer, List<Operation>> entry = statementIterator.next();
            List<Operation> customerOperations = entry.getValue();

            Iterator<Operation> customerOperationIterator = customerOperations.iterator();
            while (customerOperationIterator.hasNext()) {
                Operation operation = customerOperationIterator.next();
                if (operation.getId() == id) {
                    customerOperationIterator.remove();
                }
            }
        }
    }

    public synchronized void addToStatement(int clientID) {
        lock.lock();
        try {
            ArrayList<Customer> customers = customerService.getCustomers();
            for (Customer customer : customers) {
                if (!statement.containsKey(customer)) {
                    statement.put(customer, new ArrayList<>());
                }
                if (customer.getId() == clientID) {
                    statement.get(customer).add(operations.get(operations.size() - 1));
                    break;  // Add only once for the specified client
                }
            }
        } finally {
            lock.unlock();
        }
    }



    public Map<Customer, List<Operation>> getStatement() {
        lock.lock();
        try {
            return statement;
        } finally {
            lock.unlock();
        }
    }
    
    public List<Operation> getOperations() {
        lock.lock();
        try {
            return new ArrayList<>(operations);
        } finally {
            lock.unlock();
        }
    }

}

