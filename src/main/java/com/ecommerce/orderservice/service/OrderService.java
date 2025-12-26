package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;

public interface OrderService {

	
	OrderResponseDTO createOrder(CreateOrderRequestDTO dto);
	
	OrderResponseDTO getOrderById(Long orderId);
	
	OrderResponseDTO getOrderByUserId(Long userId);
}
