package com.sqli.ccplatform.repository;

import com.sqli.ccplatform.domain.entity.PreparationTask;
import com.sqli.ccplatform.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreparationTaskRepository extends JpaRepository<PreparationTask, Long> {

    /**
     * Find all tasks for a specific order
     */
    List<PreparationTask> findByOrderId(Long orderId);

    /**
     * Find all tasks assigned to a specific operator
     */
    List<PreparationTask> findByOperatorId(Long operatorId);

    /**
     * Find all unassigned tasks
     */
    List<PreparationTask> findByOperatorIsNull();

    /**
     * Find all tasks with a specific status
     */
    List<PreparationTask> findByStatus(TaskStatus status);

    /**
     * Find all tasks assigned to a specific operator with specific statuses
     */
    List<PreparationTask> findByOperatorIdAndStatusIn(Long operatorId, List<TaskStatus> statuses);

    /**
     * Count active tasks for an operator
     */
    int countByOperatorIdAndStatusIn(Long operatorId, List<TaskStatus> statuses);

    /**
     * Find tasks by operator availability and status
     */
    @Query("SELECT t FROM PreparationTask t WHERE t.operator.availability = true AND t.status = :status")
    List<PreparationTask> findTasksByAvailabilityOperatorAndStatus(@Param("status") TaskStatus status);

    /**
     * Find overdue tasks (could be extended with date logic)
     */
    @Query("SELECT t FROM PreparationTask t WHERE t.status IN ('TODO', 'IN_PROGRESS') AND t.createdAt < :cutoffDate")
    List<PreparationTask> findOverdueTasks(@Param("cutoffDate") java.time.LocalDateTime cutoffDate);

    /**
     * Find tasks by customer
     */
    @Query("SELECT t FROM PreparationTask t WHERE t.order.customer.id = :customerId")
    List<PreparationTask> findByCustomerId(@Param("customerId") Long customerId);
}