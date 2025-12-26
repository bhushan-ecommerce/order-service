package com.ecommerce.orderservice.service.impl;

import org.springframework.stereotype.Service;

import com.ecommerce.orderservice.clients.InventoryClient;
import com.ecommerce.orderservice.clients.ProductClient;
import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.dto.ProductResponseDTO;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final InventoryClient inventoryClient;
	private final ProductClient productClient;
	
	
	OrderServiceImpl(OrderRepository orderRepository, InventoryClient inventoryClient, ProductClient productClient){
		
		this.orderRepository = orderRepository;
		this.inventoryClient = inventoryClient;
		this.productClient = productClient;
	}

	@Override
	public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
		// TODO Auto-generated method stub
		
		log.info("Creating order for userId={}, productId={}, qty={}", dto.userId(), dto.productId(),dto.quantity());
		
		ProductResponseDTO product = productClient.getProduct(dto.productId());
		
		double totalAmount =  (product.price() * dto.quantity());
		
		
		return null;
	}

	@Override
	public OrderResponseDTO getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderResponseDTO getOrderByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
