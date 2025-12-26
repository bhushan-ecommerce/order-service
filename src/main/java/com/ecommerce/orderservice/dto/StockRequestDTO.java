package com.ecommerce.orderservice.dto;

public record StockRequestDTO(
		Long productId,
        Integer quantity
        ) {

}
