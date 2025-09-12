package com.example.Service;

import com.example.DTO.PurchaseResponseDTO;

import java.time.LocalDate;

public interface RewardsService {
    PurchaseResponseDTO getRewards(Long customerId, LocalDate start, LocalDate end);
}
