package com.ecommerce.orderservice.service;

import org.springframework.data.domain.Page;

import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderDetailResponseDto;
import com.ecommerce.orderservice.dto.OrderResponseDTO;

public interface OrderService {

	
	OrderResponseDTO createOrder(CreateOrderRequestDTO dto);
	
	OrderResponseDTO getOrderById(Long orderId);
	
//	OrderResponseDTO getOrderByUserId(Long userId);
	
//	List<OrderDetailResponseDto> getOrderDetailsByUserId(Long userId);
	
	Page<OrderDetailResponseDto> getOrderDetailsByUserId(Long userId, int page, int size);

	OrderDetailResponseDto getOrderDetailsByOrderId(Long orderId);

}
