package com.example.homework_05_testing.services;

import com.example.homework_05_testing.entities.Order;
import com.example.homework_05_testing.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
