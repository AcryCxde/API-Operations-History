package ru.netology.IlyaRomanov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.IlyaRomanov.controller.dto.CustomerDTO;
import ru.netology.IlyaRomanov.controller.dto.OperationDTO;
import ru.netology.IlyaRomanov.controller.dto.OperationsGetResponse;
import ru.netology.IlyaRomanov.domain.Customer;
import ru.netology.IlyaRomanov.domain.Operation;
import ru.netology.IlyaRomanov.service.AsyncInputOperationService;
import ru.netology.IlyaRomanov.service.StatementService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/operations")
@RequiredArgsConstructor
public class OperationController {
    private final StatementService statementService;
    private final AsyncInputOperationService asyncInputOperationService;

    @GetMapping
    public OperationsGetResponse getStatement() {
        Map<Customer, List<Operation>> statement = statementService.getStatement();
        Map<CustomerDTO, List<OperationDTO>> statementDTOS = statement.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new CustomerDTO(entry.getKey().getId(), entry.getKey().getName()),
                        entry -> entry.getValue().stream()
                                .map(operation -> new OperationDTO(
                                        operation.getId(),
                                        operation.getSum(),
                                        operation.getCurrency(),
                                        operation.getMerchant(),
                                        operation.getClientId()))
                                .collect(Collectors.toList())));
        return new OperationsGetResponse(statementDTOS);
    }

    @GetMapping("{id}")
    public List<OperationDTO> getCustomerOperations(@PathVariable int id) {
        Map<Customer, List<Operation>> statement = statementService.getStatement();
        List<OperationDTO> customerOperations = statement.entrySet().stream()
                .filter(entry -> entry.getKey().getId() == id)
                .flatMap(entry -> entry.getValue().stream()
                        .map(operation -> new OperationDTO(
                                operation.getId(),
                                operation.getSum(),
                                operation.getCurrency(),
                                operation.getMerchant(),
                                operation.getClientId())))
                .collect(Collectors.toList());
        return customerOperations;
    }

    @PostMapping
    public ResponseEntity<OperationDTO> addOperation(@RequestBody OperationDTO operationDTO) {
        Operation newOperation = new Operation(
                operationDTO.getId(),
                operationDTO.getSum(),
                operationDTO.getCurrency(),
                operationDTO.getMerchant(),
                operationDTO.getClientId());
        Operation offeredOperation = asyncInputOperationService.offerOperation(newOperation);

        if (offeredOperation != null) {
            OperationDTO offeredOperationDTO = new OperationDTO(
                    offeredOperation.getId(),
                    offeredOperation.getSum(),
                    offeredOperation.getCurrency(),
                    offeredOperation.getMerchant(),
                    offeredOperation.getClientId());
            return new ResponseEntity<>(offeredOperationDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{operationId}")
    public ResponseEntity<Void> deleteOperation(@PathVariable int operationId) {
        boolean operationDeleted = statementService.deleteOperationById(operationId);

        if (operationDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
