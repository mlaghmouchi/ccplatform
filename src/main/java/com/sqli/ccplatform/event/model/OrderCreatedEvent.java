package com.sqli.ccplatform.event.model;

import com.sqli.ccplatform.domain.entity.CustomOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private CustomOrder order;
}