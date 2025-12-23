package com.ecommerce.orderservice.dto;

public record CreateOrderRequestDTO(
		Long userId,
        Long productId,
        Integer quantity
        ) {

}
