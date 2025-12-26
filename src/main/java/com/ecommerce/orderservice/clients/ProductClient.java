package com.ecommerce.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.orderservice.dto.ProductResponseDTO;

@FeignClient(name="product-service")
public interface ProductClient {
	
	
	 @GetMapping("/api/products/{id}")
	    ProductResponseDTO getProduct(@PathVariable Long id);
	

}
