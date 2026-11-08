package com.example.orderservice.dto;

import com.example.orderservice.domain.enums.OrderStatus;

public record UpdateStatusRequest(OrderStatus status) {

    public UpdateStatusRequest {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
}
