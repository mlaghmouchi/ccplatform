//package com.sqli.ccplatform.repository;
//
//import com.sqli.ccplatform.domain.entity.Notification;
//import com.sqli.ccplatform.domain.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//
//public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findByRecipient(User recipient);
//    List<Notification> findByRecipientAndReadFalse(User recipient);
//    long countByRecipientAndReadFalse(User recipient);
//}