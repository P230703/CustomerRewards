package com.example.Service;

import com.example.DTO.PurchaseResponseDTO;
import com.example.Repository.PurchaseRepository;
import com.example.entity.Purchase;
import com.example.Exception.exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50;
        } else if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

    @Override
    public PurchaseResponseDTO getRewards(Long customerId, LocalDate start, LocalDate end) {

        List<Purchase> purchases = purchaseRepository.findByCustomerIdAndDateBetween(customerId, start, end);

        if (purchases.isEmpty()) {
            throw new exception("No purchases found for customer id: " + customerId);
        }

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int total = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Purchase p : purchases) {
            int points = calculatePoints(p.getAmount());
            String month = p.getDate().format(formatter);

            monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
            total += points;
        }

        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setCustomerId(customerId);
        response.setTotalPoints(total);
        response.setPointsPerMonth(monthlyPoints);
        response.setPurchases(purchases);

        return response;
    }
}
