package com.ecommerce.orderservice.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
		
		 String message,
	    Integer status,
	    LocalDateTime timestamp) {
			 
			 public ErrorResponseDTO(String message, int status) {
			      this(message, status, LocalDateTime.now());
			 }

}
