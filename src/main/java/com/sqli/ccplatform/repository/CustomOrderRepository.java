package com.sqli.ccplatform.repository;

import com.sqli.ccplatform.domain.entity.CustomOrder;
import com.sqli.ccplatform.domain.entity.Customer;
import com.sqli.ccplatform.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CustomOrderRepository extends JpaRepository<CustomOrder, Long> {
    List<CustomOrder> findByCustomer(Customer customer);
    List<CustomOrder> findByCustomerAndStatus(Customer customer, OrderStatus status);
    List<CustomOrder> findByStatus(OrderStatus status);
    @Query("SELECT o FROM CustomOrder o WHERE o.customer.username = :username")
    List<CustomOrder> findByCustomerUsername(@Param("username") String username);
}