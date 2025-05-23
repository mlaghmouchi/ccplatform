package com.sqli.ccplatform.controller;

import com.sqli.ccplatform.domain.dto.CustomOrderRequest;
import com.sqli.ccplatform.domain.dto.CustomOrderResponse;
import com.sqli.ccplatform.service.CustomOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CustomOrderController {

    private final CustomOrderService orderService;

    @PostMapping
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomOrderResponse> createOrder(@Valid @RequestBody CustomOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CustomOrderResponse>> getCustomerOrders() {
        return ResponseEntity.ok(orderService.getCustomerOrders());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<CustomOrderResponse> getOrderById(@PathVariable Long id) {
//        return ResponseEntity.ok(orderService.getOrderById(id));
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/status")
//    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<String> getOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderStatus(id));
    }
}