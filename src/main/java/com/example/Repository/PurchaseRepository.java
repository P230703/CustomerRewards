package com.example.Repository;

import com.example.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findByCustomerIdAndDateBetween(Long customerId, LocalDate start, LocalDate end);
}
