package com.ecommerce.orderservice.clients.fallback;

import org.springframework.stereotype.Component;

import com.ecommerce.orderservice.clients.InventoryClient;
import com.ecommerce.orderservice.dto.InventoryResponseDTO;
import com.ecommerce.orderservice.dto.StockRequestDTO;
import com.ecommerce.orderservice.exception.InventoryNotAvailableException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InventoryClientFallback implements InventoryClient{

	@Override
	public InventoryResponseDTO reserveStock(StockRequestDTO dto) {
		// TODO Auto-generated method stub
		log.error("Fallback: reserveStock failed for productId={}", dto.productId());
        throw new InventoryNotAvailableException("Inventory service unavailable (reserve)");
		
	}

	@Override
	public InventoryResponseDTO releaseStock(StockRequestDTO dto) {
		// TODO Auto-generated method stub
		log.warn("Fallback: releaseStock called for productId={}", dto.productId());
		throw new InventoryNotAvailableException("Inventory service unavailable (reserve)");
		
	}

	@Override
	public InventoryResponseDTO confirmStock(StockRequestDTO dto) {
		// TODO Auto-generated method stub
		log.error("Fallback: confirmStock failed for productId={}", dto.productId());
        throw new InventoryNotAvailableException("Inventory service unavailable (reserve)");
	}

}
