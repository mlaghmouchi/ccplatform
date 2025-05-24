package com.sqli.ccplatform.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.ccplatform.domain.dto.CustomOrderRequest;
import com.sqli.ccplatform.domain.dto.CustomOrderResponse;
import com.sqli.ccplatform.domain.entity.CustomOrder;
import com.sqli.ccplatform.domain.entity.Customer;
import com.sqli.ccplatform.domain.enums.OrderStatus;
import com.sqli.ccplatform.event.model.OrderCreatedEvent;
import com.sqli.ccplatform.repository.CustomOrderRepository;
import com.sqli.ccplatform.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOrderService {

    private final CustomOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public CustomOrderResponse createOrder(CustomOrderRequest orderRequest) {
        Customer customer = customerRepository.findByUsername("amine")
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomOrder order = getCustomOrder(orderRequest, customer);
        CustomOrder savedOrder = orderRepository.save(order);
        
        // Publish event for task creation
        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder));
        
        return convertToDto(savedOrder);
    }

    private static CustomOrder getCustomOrder(CustomOrderRequest orderRequest, Customer customer) {
        CustomOrder order = new CustomOrder();
        order.setCustomer(customer);
        order.setTitle(orderRequest.getTitle());
        order.setDescription(orderRequest.getDescription());
        order.setPrice(orderRequest.getPrice());
        order.setSpecifications(orderRequest.getSpecifications());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    @Transactional(readOnly = true)
    public List<CustomOrderResponse> getCustomerOrders() {
        return orderRepository.findByCustomerUsername("amine").stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CustomOrderResponse> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public String getOrderStatus(Long id) {
        return orderRepository.findById(id)
                .map(order -> order.getStatus().name())
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private CustomOrderResponse convertToDto(CustomOrder order) {
        CustomOrderResponse response = modelMapper.map(order, CustomOrderResponse.class);
        response.setCustomerUsername(order.getCustomer().getUsername());
        return response;
    }
}