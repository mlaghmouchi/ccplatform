package com.sqli.ccplatform.event.listener;

import com.sqli.ccplatform.domain.entity.CustomOrder;
import com.sqli.ccplatform.domain.entity.PreparationTask;
import com.sqli.ccplatform.event.model.OrderCreatedEvent;
//import com.sqli.ccplatform.service.NotificationService;
import com.sqli.ccplatform.service.PreparationTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final PreparationTaskService taskService;
//    private final NotificationService notificationService;

    @Async
    @EventListener
    @Transactional
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Handling OrderCreatedEvent for order ID: {}", event.getOrder().getId());

        CustomOrder order = event.getOrder();

        // Create preparation task for the order
        PreparationTask task = taskService.createTaskFromOrder(order);

        // Send notification to customer
//        notificationService.createNotification(
//                order.getCustomer(),
//                "Order Received",
//                "Your order #" + order.getId() + " has been received and is pending processing."
//        );

        log.info("Created preparation task ID: {} for order ID: {}", task.getId(), order.getId());
    }
}