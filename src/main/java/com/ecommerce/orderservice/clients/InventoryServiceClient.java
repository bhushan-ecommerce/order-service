package com.ecommerce.orderservice.clients;

import org.springframework.stereotype.Component;

import com.ecommerce.orderservice.dto.InventoryResponseDTO;
import com.ecommerce.orderservice.dto.StockRequestDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class InventoryServiceClient {

	private final InventoryClient inventoryClient;

	public InventoryServiceClient(InventoryClient inventoryClient) {

		this.inventoryClient = inventoryClient;
	}

	@CircuitBreaker(name= "inventoryService")
	@Retry(name= "inventoryService")
	public InventoryResponseDTO reserveStock(StockRequestDTO dto) {
		return inventoryClient.reserveStock(dto);
	}

	@CircuitBreaker(name = "inventoryService")
	public InventoryResponseDTO releaseStock(StockRequestDTO dto) {
		return inventoryClient.releaseStock(dto);
	}
	
	@CircuitBreaker(name = "inventoryService")
	public InventoryResponseDTO confirmStock(StockRequestDTO dto) {
		return inventoryClient.confirmStock(dto);
	}

}
