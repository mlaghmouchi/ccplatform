package com.sqli.ccplatform.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sqli.ccplatform.domain.entity.CustomOrder;
import com.sqli.ccplatform.event.model.OrderCreatedEvent;
import com.sqli.ccplatform.service.PreparationTaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final PreparationTaskService taskService;

    @Async
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        try {
            CustomOrder order = event.getOrder();
            createTaskInNewTransaction(order);
        } catch (Exception e) {
            log.error("Failed to create preparation task for order {}: {}", 
                event.getOrder().getId(), e.getMessage());
            // Don't rethrow - we want to keep the order even if task creation fails
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createTaskInNewTransaction(CustomOrder order) {
        taskService.createTaskFromOrder(order);
        log.info("Successfully created preparation task for order {}", order.getId());
    }
}