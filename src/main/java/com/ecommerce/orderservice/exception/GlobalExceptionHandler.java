package com.ecommerce.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.orderservice.dto.ErrorResponseDTO;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OrderNotFoundException.class)
	ResponseEntity<ErrorResponseDTO> handleOrderNotFoundException(OrderNotFoundException exception){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponseDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
	
	@ExceptionHandler(InventoryNotAvailableException.class)
	ResponseEntity<ErrorResponseDTO> handleInventoryNotAvailableException(InventoryNotAvailableException exception){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponseDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
	
	
	 @ExceptionHandler(CallNotPermittedException.class)
	    public ResponseEntity<ErrorResponseDTO> handleCircuitOpen(
	            CallNotPermittedException ex) {
	        return ResponseEntity
	                .status(HttpStatus.SERVICE_UNAVAILABLE) // 503
	                .body(new ErrorResponseDTO(
	                        "CIRCUIT_OPEN",
	                        HttpStatus.SERVICE_UNAVAILABLE.value()
	                ));
	    }
}
