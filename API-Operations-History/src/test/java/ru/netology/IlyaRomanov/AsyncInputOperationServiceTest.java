package ru.netology.IlyaRomanov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.IlyaRomanov.domain.Operation;
import ru.netology.IlyaRomanov.service.AsyncInputOperationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncInputOperationServiceTest extends OperationHistoryApiApplicationTest {
    @Autowired
    private AsyncInputOperationService asyncInputOperationService;

    @Test
    public void testOfferOperation() {
        Operation operation = new Operation(1, 123, "rub", "Merch", 1);
        Operation result = asyncInputOperationService.offerOperation(operation);

        assertEquals(operation, result);
    }
}
