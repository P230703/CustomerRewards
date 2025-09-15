package com.example.demo;

import com.example.DTO.PurchaseRequestDTO;
import com.example.DTO.PurchaseResponseDTO;
import com.example.entity.Purchase;
import com.example.Repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api";
        purchaseRepository.deleteAll(); // clean DB before each test
    }

    @Test
    void testCreatePurchase() {
        PurchaseRequestDTO request = new PurchaseRequestDTO(1L, 120.0, LocalDate.now());

        ResponseEntity<Purchase> response = restTemplate.postForEntity(
                baseUrl + "/purchases",
                request,
                Purchase.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getAmount()).isEqualTo(120.0);
    }

    @Test
    void testGetRewards() {
        purchaseRepository.save(new Purchase(null, 1L, 120.0, LocalDate.now().minusDays(5)));
        purchaseRepository.save(new Purchase(null, 1L, 75.0, LocalDate.now().minusDays(10)));

        ResponseEntity<PurchaseResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "/rewards/1",
                PurchaseResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getTotalPoints()).isGreaterThan(0);
    }

    @Test
    void testGetRewardsWhenNoPurchases() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "/rewards/99",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("No purchases found for customer id");
    }
}

