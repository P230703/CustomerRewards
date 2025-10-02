package com.charter.Service;

import com.charter.DTO.PurchaseResponseDTO;

import java.time.LocalDate;

public interface RewardsService {
    PurchaseResponseDTO getRewards(Long customerId, LocalDate start, LocalDate end);
}
