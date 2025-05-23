package com.sqli.ccplatform.domain.dto;

import com.sqli.ccplatform.domain.enums.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class CustomOrderResponse {
    private Long id;
    private String title;
    private String description;
    private Map<String, Object> specifications;
    private BigDecimal price;
    private OrderStatus status;
    private String customerUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private List<TaskResponse> tasks;
}