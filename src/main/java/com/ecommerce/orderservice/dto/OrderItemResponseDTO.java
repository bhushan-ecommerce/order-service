package com.ecommerce.orderservice.dto;

public record OrderItemResponseDTO(
		
		Long productId,
        Integer quantity,
        Double price
        
		) {

}
