package com.example.orderservice.dto;

import java.util.List;

public class CreateOrderRequest {
    private List<OrderItemRequest> items;

    // Getters and Setters  

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
