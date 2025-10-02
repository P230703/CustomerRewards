package com.charter.test;

import com.charter.DTO.PurchaseResponseDTO;
import com.charter.Repository.PurchaseRepository;
import com.charter.Entity.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void testGetRewards() {
        // given: seed purchases for this customer
        purchaseRepository.save(new Purchase(null, 1L, 120.0, LocalDate.now().minusDays(5)));
        purchaseRepository.save(new Purchase(null, 1L, 75.0, LocalDate.now().minusDays(10)));

        // when: call /rewards/{customerId}
        ResponseEntity<PurchaseResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "/rewards/1",
                PurchaseResponseDTO.class
        );

        // then: validate
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getTotalPoints()).isGreaterThan(0);
    }

    @Test
    void testGetRewardsWhenNoPurchases() {
        // given: no purchases for customerId=99

        // when: call /rewards/99
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "/rewards/99",
                String.class
        );

        // then: should return 404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("No purchases found for customer id");
    }
}
