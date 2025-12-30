package com.ecommerce.orderservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	
	Optional<Order> findByUserId(Long userId);

}
