package com.charter.test;

import com.charter.DTO.PurchaseResponseDTO;
import com.charter.Repository.PurchaseRepository;
import com.charter.Service.RewardsServiceImpl;
import com.charter.Entity.Purchase;
import com.charter.Exception.Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RewardsServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private RewardsServiceImpl rewardsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCustomerWithPurchases() {
        Long customerId = 1L;
        LocalDate start = LocalDate.now().minusMonths(3);
        LocalDate end = LocalDate.now();


        List<Purchase> purchases = Arrays.asList(
                new Purchase(1L, customerId, 120.0, LocalDate.now().minusDays(10)),
                new Purchase(2L, customerId, 75.0, LocalDate.now().minusDays(20)),
                new Purchase(3L, customerId, 40.0, LocalDate.now().minusDays(30))
        );

        when(purchaseRepository.findByCustomerIdAndDateBetween(customerId, start, end))
                .thenReturn(purchases);

        PurchaseResponseDTO response = rewardsService.getRewards(customerId, start, end);

        assertEquals(115, response.getTotalPoints()); // 90 + 25 + 0
    }

    @Test
    void testCustomerWithNoPurchases() {
        Long customerId = 2L;
        LocalDate start = LocalDate.now().minusMonths(3);
        LocalDate end = LocalDate.now();

        when(purchaseRepository.findByCustomerIdAndDateBetween(customerId, start, end))
                .thenReturn(Collections.emptyList());

        Exception thrown = assertThrows(Exception.class,
                () -> rewardsService.getRewards(customerId, start, end));

        assertEquals("No purchases found for customer id: " + customerId, thrown.getMessage());
    }
}
