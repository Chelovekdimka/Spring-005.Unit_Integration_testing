package com.example.homework_05_testing.repositories;

import com.example.homework_05_testing.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
