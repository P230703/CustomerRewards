package com.charter.Controller;

import com.charter.DTO.PurchaseResponseDTO;
import com.charter.Service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @GetMapping("/rewards/{customerId}")
    public PurchaseResponseDTO getRewards(
            @PathVariable Long customerId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDate start,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDate end
    ) {
        return rewardsService.getRewards(customerId, start, end);
    }
}
