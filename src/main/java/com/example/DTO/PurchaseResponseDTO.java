package com.example.DTO;

import com.example.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Long customerId;
    private Integer totalPoints;
    private Map<String, Integer> pointsPerMonth;
    private List<Purchase> purchases;
}
