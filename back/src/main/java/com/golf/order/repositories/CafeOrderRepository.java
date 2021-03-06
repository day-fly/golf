package com.golf.order.repositories;

import com.golf.order.entity.CafeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CafeOrderRepository extends JpaRepository<CafeOrder, Long> {
    List<CafeOrder> findAllByOrderDateBetween(LocalDateTime from, LocalDateTime to);
}
