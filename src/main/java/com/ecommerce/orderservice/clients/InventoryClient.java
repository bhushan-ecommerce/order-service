package com.ecommerce.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.orderservice.clients.fallback.InventoryClientFallback;
import com.ecommerce.orderservice.dto.InventoryResponseDTO;
import com.ecommerce.orderservice.dto.StockRequestDTO;
import com.netflix.appinfo.RefreshableAmazonInfoProvider.FallbackAddressProvider;

@FeignClient(name= "inventory-service",
fallback = InventoryClientFallback.class)
public interface InventoryClient {

	
	 	@PostMapping("/api/inventory/reserve")
	    InventoryResponseDTO reserveStock(@RequestBody StockRequestDTO dto);

	    @PostMapping("/api/inventory/release")
	    InventoryResponseDTO releaseStock(@RequestBody StockRequestDTO dto);

	    @PostMapping("/api/inventory/confirm")
	    InventoryResponseDTO confirmStock(@RequestBody StockRequestDTO dto);
}
