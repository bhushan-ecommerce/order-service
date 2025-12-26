package com.ecommerce.orderservice.dto;

public record InventoryResponseDTO(
		Long productId,
		Integer availableQty,
		Integer reserveQty
		) 
{}
