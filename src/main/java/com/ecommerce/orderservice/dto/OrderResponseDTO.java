package com.ecommerce.orderservice.dto;

public record OrderResponseDTO(Long orderId, String status, Double totalAmount) {

}
