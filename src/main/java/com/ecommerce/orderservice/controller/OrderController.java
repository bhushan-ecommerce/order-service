package com.ecommerce.orderservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderDetailResponseDto;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private final OrderService orderService;
	
	 OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
	 @PostMapping("/create")
	ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO dto){
		
		OrderResponseDTO order = orderService.createOrder(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);	
	}
	 
	 @GetMapping("/{id}")
	 ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
		 
		 OrderResponseDTO orderById = orderService.getOrderById(id);
		 return ResponseEntity.status(HttpStatus.OK).body(orderById);
	 }
	 
//	 @GetMapping("user/{uid}")
//	 ResponseEntity<OrderResponseDTO> getOrderByuserId(@PathVariable Long uid){
//		 
//		 OrderResponseDTO orderByUserId = orderService.getOrderByUserId(uid);
//		 return ResponseEntity.status(HttpStatus.OK).body(orderByUserId);
//	 }
	 
	 @GetMapping("/user/{userId}/")
	 public ResponseEntity<Page<OrderDetailResponseDto>> getOrdersWithPagination(
	         @PathVariable Long userId,
	         @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "5") int size){
		 
		 Page<OrderDetailResponseDto> orderDetailsByUserId = orderService.getOrderDetailsByUserId(userId, page, size);
		 
		 return ResponseEntity.status(HttpStatus.OK).body(orderDetailsByUserId);
	 }

}
