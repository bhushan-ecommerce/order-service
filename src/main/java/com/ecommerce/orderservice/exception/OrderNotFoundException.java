package com.ecommerce.orderservice.exception;

public class OrderNotFoundException extends RuntimeException{
	
			public OrderNotFoundException(Long ProductId) {
				// TODO Auto-generated constructor stub
				super("Order not found for below ID: "+ ProductId);
			}

}
