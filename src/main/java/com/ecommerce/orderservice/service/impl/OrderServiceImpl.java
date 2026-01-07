package com.ecommerce.orderservice.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.orderservice.clients.InventoryClient;
import com.ecommerce.orderservice.clients.InventoryServiceClient;
import com.ecommerce.orderservice.clients.ProductClient;
import com.ecommerce.orderservice.dto.CreateOrderRequestDTO;
import com.ecommerce.orderservice.dto.InventoryResponseDTO;
import com.ecommerce.orderservice.dto.OrderDetailResponseDto;
import com.ecommerce.orderservice.dto.OrderItemResponseDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.dto.ProductResponseDTO;
import com.ecommerce.orderservice.dto.StockRequestDTO;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.enums.OrderStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final InventoryServiceClient inventoryServiceClient;

	OrderServiceImpl(OrderRepository orderRepository, ProductClient productClient, InventoryServiceClient inventoryServiceClient) {

		this.orderRepository = orderRepository;
		this.productClient = productClient;
		this.inventoryServiceClient = inventoryServiceClient;
	}

	
	@Override
	public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
		// TODO Auto-generated method stub

		log.info("Creating order for userId={}, productId={}, qty={}", dto.userId(), dto.productId(), dto.quantity());

		ProductResponseDTO product = productClient.getProduct(dto.productId());

		double totalAmount = (product.price() * dto.quantity());

		Order order = setOrders(dto.userId(), OrderStatus.CREATED, totalAmount);

		OrderItem setOrderitems = setOrderitems(dto.productId(), dto.quantity(), product.price(), order);

		order.addOrderItem(setOrderitems);

		Order savedOrder = orderRepository.save(order);
		log.info("Order created with id={}", savedOrder.getId());

		try {
			// reserved inventory stock
			InventoryResponseDTO reserveStock = inventoryServiceClient
					.reserveStock(new StockRequestDTO(dto.productId(), dto.quantity()));
			log.info("Stock reserved for productId={}", dto.productId(), reserveStock);
			savedOrder.setStatus(OrderStatus.STOCK_RESERVED);

			// mock payment status
			boolean paymentSuccess = true;

			if (paymentSuccess) {

				InventoryResponseDTO confirmStock = inventoryServiceClient
						.confirmStock(new StockRequestDTO(dto.productId(), dto.quantity()));
				log.info("Stock confirm for productId={}", dto.productId(), confirmStock);
				savedOrder.setStatus(OrderStatus.CONFIRMED);

			} else {

				InventoryResponseDTO releaseStock = inventoryServiceClient
						.releaseStock(new StockRequestDTO(dto.productId(), dto.quantity()));
				log.warn("Order FAILED id={}", releaseStock);
				savedOrder.setStatus(OrderStatus.FAILED);

			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Order failed, rolling back inventory", e);

			inventoryServiceClient.releaseStock(new StockRequestDTO(dto.productId(), dto.quantity()));

			savedOrder.setStatus(OrderStatus.FAILED);
		}

		orderRepository.save(savedOrder);

		return new OrderResponseDTO(savedOrder.getId(), savedOrder.getStatus().name(), savedOrder.getTotalAmount());
	}

	@Override
	public OrderResponseDTO getOrderById(Long orderId) {
		// TODO Auto-generated method stub

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

		return new OrderResponseDTO(order.getId(), order.getStatus().name(), order.getTotalAmount());
	}

//	@Override
//	public OrderResponseDTO getOrderByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		Order order = orderRepository.findByUserId(userId).orElseThrow(() -> new OrderNotFoundException(userId));
//
//		return new OrderResponseDTO(order.getId(), order.getStatus().name(), order.getTotalAmount());
//	}


//	@Override
//	public List<OrderDetailResponseDto> getOrderDetailsByUserId(Long userId) {
//		// TODO Auto-generated method stub
//
//		List<Order> byUserId = orderRepository.findByUserId(userId);
//
//		return byUserId.stream().map(this::mapToOrderDetailDto).toList();
//	}

	@Override
	public OrderDetailResponseDto getOrderDetailsByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

		return mapToOrderDetailDto(order);

	}

	@Override
	public Page<OrderDetailResponseDto> getOrderDetailsByUserId(Long userId, int page, int size) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

		Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);

		return orderPage.map(this::mapToOrderDetailDto);

	}

	private OrderDetailResponseDto mapToOrderDetailDto(Order order) {

		List<OrderItemResponseDTO> items = order.getItems().stream()
				.map(item -> new OrderItemResponseDTO(item.getProductId(), item.getQuantity(), item.getPrice()))
				.toList();

		return new OrderDetailResponseDto(order.getId(), order.getUserId(), order.getStatus().name(),
				order.getTotalAmount(), order.getCreatedAt(), items);
	}
	
	

	private Order setOrders(Long userId, OrderStatus status, Double amount) {

		Order order = new Order();
		order.setUserId(userId);
		order.setStatus(status);
		order.setTotalAmount(amount);

		return order;
	}

	private OrderItem setOrderitems(Long productId, Integer quantity, Double price, Order order) {

		OrderItem orderItem = new OrderItem();
		orderItem.setProductId(productId);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(price);
		orderItem.setOrder(order);

		return orderItem;
	}

}
