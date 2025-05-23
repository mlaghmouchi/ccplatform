package com.sqli.ccplatform.domain.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CustomOrderRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Order specifications are required")
    private Map<String, Object> specifications;

    private BigDecimal price;

    // Add these fields if needed by your service
    private Long customerId;
    private String status;
}