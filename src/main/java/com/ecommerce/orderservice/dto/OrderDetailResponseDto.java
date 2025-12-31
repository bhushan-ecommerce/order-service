package com.ecommerce.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponseDto(
		
		 Long orderId,
	        Long userId,
	        String status,
	        Double totalAmount,
	        LocalDateTime createdAt,
	        List<OrderItemResponseDTO> items) {

}
