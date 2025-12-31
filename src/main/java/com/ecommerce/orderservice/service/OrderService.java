package com.ecommerce.orderservice.service;

import java.util.List;

import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderDetailResponseDto;
import com.ecommerce.orderservice.dto.OrderResponseDTO;

public interface OrderService {

	
	OrderResponseDTO createOrder(CreateOrderRequestDTO dto);
	
	OrderResponseDTO getOrderById(Long orderId);
	
//	OrderResponseDTO getOrderByUserId(Long userId);
	
	List<OrderDetailResponseDto> getOrderDetailsByUserId(Long userId);

	OrderDetailResponseDto getOrderDetailsByOrderId(Long orderId);

}
