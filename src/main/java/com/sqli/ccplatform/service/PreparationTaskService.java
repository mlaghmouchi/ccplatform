package com.sqli.ccplatform.service;

import com.sqli.ccplatform.domain.entity.CustomOrder;
import com.sqli.ccplatform.domain.entity.Operator;
import com.sqli.ccplatform.domain.entity.PreparationTask;
import com.sqli.ccplatform.domain.enums.TaskStatus;
import com.sqli.ccplatform.repository.OperatorRepository;
import com.sqli.ccplatform.repository.PreparationTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreparationTaskService {

    private final PreparationTaskRepository taskRepository;
    private final OperatorRepository operatorRepository;

    /**
     * Creates a preparation task from a custom order
     */
    @Transactional
    public PreparationTask createTaskFromOrder(CustomOrder order) {
        log.info("Creating preparation task for order ID: {}", order.getId());

        PreparationTask task = new PreparationTask();
        task.setOrder(order);
        task.setTitle("Prepare Order #" + order.getId() + " - " + order.getTitle());
        task.setDescription(buildTaskDescription(order));
        task.setStatus(TaskStatus.TODO);
        task.setNotes("Task created automatically from order");

        // Try to assign to an available operator
        try {
            Optional<Operator> availableOperator = findAvailableOperator();
            if (availableOperator.isPresent()) {
                task.setOperator(availableOperator.get());
                log.info("Assigned task to operator: {}", availableOperator.get().getUsername());
            } else {
                log.info("No available operator found, task will remain unassigned");
            }
        } catch (Exception e) {
            log.warn("Error finding available operator: {}", e.getMessage());
            log.info("Task will remain unassigned due to operator lookup error");
        }

        PreparationTask savedTask = taskRepository.save(task);
        log.info("Created preparation task with ID: {}", savedTask.getId());

        return savedTask;
    }

    /**
     * Assigns a task to an operator
     */
    @Transactional
    public PreparationTask assignTaskToOperator(Long taskId, Long operatorId) {
        log.info("Assigning task {} to operator {}", taskId, operatorId);

        PreparationTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found with ID: " + operatorId));

        if (!operator.isAvailability()) {
            throw new RuntimeException("Operator is not available");
        }

        task.setOperator(operator);
        if (task.getStatus() == TaskStatus.TODO) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }

        return taskRepository.save(task);
    }

    /**
     * Updates task status
     */
    @Transactional
    public PreparationTask updateTaskStatus(Long taskId, TaskStatus newStatus) {
        log.info("Updating task {} status to {}", taskId, newStatus);

        PreparationTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        TaskStatus oldStatus = task.getStatus();
        task.setStatus(newStatus);

        // Set completion time if task is completed
        if (newStatus == TaskStatus.COMPLETED && oldStatus != TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
            log.info("Task {} marked as completed", taskId);
        }

        return taskRepository.save(task);
    }

    /**
     * Adds notes to a task
     */
    @Transactional
    public PreparationTask addTaskNotes(Long taskId, String notes) {
        log.info("Adding notes to task {}", taskId);

        PreparationTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        String existingNotes = task.getNotes();
        if (existingNotes != null && !existingNotes.isEmpty()) {
            task.setNotes(existingNotes + "\n" + LocalDateTime.now() + ": " + notes);
        } else {
            task.setNotes(LocalDateTime.now() + ": " + notes);
        }

        return taskRepository.save(task);
    }

    /**
     * Gets all tasks for a specific order
     */
    public List<PreparationTask> getTasksByOrderId(Long orderId) {
        return taskRepository.findByOrderId(orderId);
    }

    /**
     * Gets all tasks assigned to a specific operator
     */
    public List<PreparationTask> getTasksByOperatorId(Long operatorId) {
        return taskRepository.findByOperatorId(operatorId);
    }

    /**
     * Gets all unassigned tasks
     */
    public List<PreparationTask> getUnassignedTasks() {
        return taskRepository.findByOperatorIsNull();
    }

    /**
     * Gets tasks by status
     */
    public List<PreparationTask> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Gets all tasks
     */
    public List<PreparationTask> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Gets a task by ID
     */
    public Optional<PreparationTask> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    /**
     * Deletes a task (only if not in progress or completed)
     */
    @Transactional
    public void deleteTask(Long taskId) {
        PreparationTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        if (task.getStatus() == TaskStatus.IN_PROGRESS || task.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Cannot delete task that is in progress or completed");
        }

        taskRepository.delete(task);
        log.info("Deleted task with ID: {}", taskId);
    }

    /**
     * Finds an available operator for task assignment
     * Fixed to handle potential type conversion issues
     */
    private Optional<Operator> findAvailableOperator() {
//        try {
//            // Use explicit type casting to handle the conversion
//            @SuppressWarnings("unchecked")
//            List<Operator> availableOperators = (List<Operator>) operatorRepository.findByAvailability(true);
//
//            if (availableOperators == null || availableOperators.isEmpty()) {
//                log.info("No available operators found");
//                return Optional.empty();
//            }
//
//            // Simple round-robin assignment - get operator with fewer assigned tasks
//            return availableOperators.stream()
//                    .min((o1, o2) -> Integer.compare(
//                            getActiveTaskCount(o1.getId()),
//                            getActiveTaskCount(o2.getId())
//                    ));
//        } catch (Exception e) {
//            log.error("Error finding available operators: {}", e.getMessage(), e);
//            return Optional.empty();
//        }
            log.info("Operator Founded : {}", operatorRepository.findByUsername("mohamed").isPresent());
        return operatorRepository.findById(Long.valueOf(0));
    }

    /**
     * Gets count of active tasks for an operator
     */
    private int getActiveTaskCount(Long operatorId) {
        try {
            return taskRepository.countByOperatorIdAndStatusIn(
                    operatorId,
                    List.of(TaskStatus.TODO, TaskStatus.IN_PROGRESS)
            );
        } catch (Exception e) {
            log.warn("Error counting active tasks for operator {}: {}", operatorId, e.getMessage());
            return 0; // Return 0 as fallback
        }
    }

    /**
     * Builds task description from order details
     */
    private String buildTaskDescription(CustomOrder order) {
        StringBuilder description = new StringBuilder();
        description.append("Prepare custom order: ").append(order.getTitle()).append("\n");

        if (order.getDescription() != null) {
            description.append("Order Description: ").append(order.getDescription()).append("\n");
        }

        description.append("Order Value: $").append(order.getPrice()).append("\n");
        description.append("Customer: ").append(order.getCustomer().getUsername()).append("\n");

        if (order.getSpecifications() != null && !order.getSpecifications().isEmpty()) {
            description.append("Specifications: ");
            order.getSpecifications().forEach((key, value) ->
                    description.append(key).append(": ").append(value).append("; ")
            );
        }

        return description.toString();
    }
}