package com.ecommerce.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.orderservice.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OrderNotFoundException.class)
	ResponseEntity<ErrorResponseDTO> handleOrderNotFoundException(OrderNotFoundException exception){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorResponseDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
}
