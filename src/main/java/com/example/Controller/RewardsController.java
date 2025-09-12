package com.example.Controller;

import com.example.DTO.PurchaseRequestDTO;
import com.example.DTO.PurchaseResponseDTO;
import com.example.Repository.PurchaseRepository;
import com.example.Service.RewardsService;
import com.example.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class RewardsController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RewardsService rewardsService;

    @PostMapping("/purchases")
    public Purchase createPurchase(@RequestBody PurchaseRequestDTO request) {
        Purchase purchase = new Purchase();
        purchase.setCustomerId(request.getCustomerId());
        purchase.setAmount(request.getAmount());
        purchase.setDate(request.getDate());

        return purchaseRepository.save(purchase);
    }

    @GetMapping("/rewards/{customerId}")
    public PurchaseResponseDTO getRewards(
            @PathVariable Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        if (start == null) start = LocalDate.now().minusMonths(3);
        if (end == null) end = LocalDate.now();

        return rewardsService.getRewards(customerId, start, end);
    }
}
