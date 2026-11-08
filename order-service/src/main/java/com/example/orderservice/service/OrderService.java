package com.example.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.entity.OrderItem;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.repository.OrderRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;       
     
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setProductId(item.getProductId());
        response.setProductName(item.getProductName());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        return response;
    }

    private OrderResponse toResponse(Order order) {
    OrderResponse response = new OrderResponse();
    response.setId(order.getId());
    response.setOrderNumber(order.getOrderNumber());
    response.setStatus(order.getStatus());
    response.setTotalAmount(order.getTotalAmount());
    response.setCreatedAt(order.getCreatedAt());
    response.setItems(order.getItems().stream().map(this::toItemResponse).toList());
    return response;
    }

    private Order findOrderOrThrow(Long id) {
    return orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
}
    
    @Transactional(readOnly = true)
public OrderResponse findById(Long id) {
    return toResponse(findOrderOrThrow(id));
}

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
    return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    
@Transactional
public OrderResponse create(CreateOrderRequest request) {
    Order order = new Order();
    order.setOrderNumber("ORD-" + UUID.randomUUID());
    order.setStatus(OrderStatus.PENDING);
    order.setCreatedAt(LocalDateTime.now());
    order.setUpdatedAt(LocalDateTime.now());
    order.setItems(new ArrayList<>());
    order.setTotalAmount(BigDecimal.ZERO);

    BigDecimal total = BigDecimal.ZERO;

    for (OrderItemRequest itemRequest : request.getItems()) {
        OrderItem item = new OrderItem();
        item.setProductId(itemRequest.getProductId());
        item.setProductName(itemRequest.getProductName());
        item.setQuantity(itemRequest.getQuantity());
        item.setUnitPrice(itemRequest.getUnitPrice());
        item.setOrder(order);

        order.getItems().add(item);

        BigDecimal subtotal = itemRequest.getUnitPrice()
                .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
        total = total.add(subtotal);
    }

    order.setTotalAmount(total);

Order saved = orderRepository.save(order);

return toResponse(saved);
}

@Transactional
public OrderResponse updateStatus(Long id, OrderStatus status) {
    Order order = findOrderOrThrow(id);

    order.setStatus(status);
    order.setUpdatedAt(LocalDateTime.now());

    return toResponse(order);
}

@Transactional
public void delete(Long id) {
    orderRepository.delete(findOrderOrThrow(id));
}
}