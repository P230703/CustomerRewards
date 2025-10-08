package com.charter.Service;
import com.charter.DTO.PurchaseResponseDTO;
import com.charter.Repository.PurchaseRepository;
import com.charter.Entity.Purchase;
import com.charter.Exception.CustomerRewardsException;
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

    //Main Logic
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
        // Apply default 3-month period logic here
        if (start == null) start = LocalDate.now().minusMonths(3);
        if (end == null) end = LocalDate.now();

        List<Purchase> purchases = purchaseRepository.findByCustomerIdAndDateBetween(customerId, start, end);

        if (purchases.isEmpty()) {
            throw new CustomerRewardsException("No purchases found for customer id: " + customerId);
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

        return new PurchaseResponseDTO(customerId, total, monthlyPoints, purchases);
    }
}

