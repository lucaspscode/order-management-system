package com.example.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UpdateStatusRequest;
import com.example.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
public OrderResponse findById(@PathVariable Long id) {
    return orderService.findById(id);
}

@GetMapping
public List<OrderResponse> findAll() {
    return orderService.findAll();
}

@PostMapping
public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
    OrderResponse response = orderService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@PatchMapping("/{id}/status")
public OrderResponse updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
    return orderService.updateStatus(id, request.status());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
}
}