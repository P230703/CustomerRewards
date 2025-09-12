package com.example.demo;

import com.example.DTO.PurchaseResponseDTO;
import com.example.Repository.PurchaseRepository;
import com.example.Service.RewardsServiceImpl;
import com.example.entity.Purchase;
import com.example.Exception.exception;
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

        // Purchases → 120 (90 pts), 75 (25 pts), 40 (0 pts) → Total = 115
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

        exception thrown = assertThrows(exception.class,
                () -> rewardsService.getRewards(customerId, start, end));

        assertEquals("No purchases found for customer id: " + customerId, thrown.getMessage());
    }
}
