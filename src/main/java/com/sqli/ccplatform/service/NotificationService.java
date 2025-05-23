//package com.sqli.ccplatform.service;
//
//import com.sqli.ccplatform.domain.entity.Customer;
//import com.sqli.ccplatform.domain.entity.Notification;
//import com.sqli.ccplatform.domain.entity.User;
//import com.sqli.ccplatform.repository.NotificationRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//
//    private final NotificationRepository notificationRepository;
//
//    /**
//     * Creates a notification for a user
//     */
//    @Transactional
//    public Notification createNotification(User user, String title, String message) {
//        log.info("Creating notification for user: {}", user.getUsername());
//
//        Notification notification = new Notification();
//        notification.setUser(user);
//        notification.setTitle(title);
//        notification.setMessage(message);
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//        Notification savedNotification = notificationRepository.save(notification);
//        log.info("Created notification with ID: {}", savedNotification.getId());
//
//        return savedNotification;
//    }
//
//    /**
//     * Creates a notification for a customer (convenience method)
//     */
//    @Transactional
//    public Notification createNotification(Customer customer, String title, String message) {
//        return createNotification((User) customer, title, message);
//    }
//
//    /**
//     * Marks a notification as read
//     */
//    @Transactional
//    public void markAsRead(Long notificationId) {
//        Notification notification = notificationRepository.findById(notificationId)
//                .orElseThrow(() -> new RuntimeException("Notification not found"));
//
//        notification.setRead(true);
//        notification.setReadAt(LocalDateTime.now());
//        notificationRepository.save(notification);
//
//        log.info("Marked notification {} as read", notificationId);
//    }
//
//    /**
//     * Gets all notifications for a user
//     */
//    public List<Notification> getUserNotifications(Long userId) {
//        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
//    }
//
//    /**
//     * Gets unread notifications for a user
//     */
//    public List<Notification> getUnreadNotifications(Long userId) {
//        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
//    }
//
//    /**
//     * Gets count of unread notifications for a user
//     */
//    public long getUnreadCount(Long userId) {
//        return notificationRepository.countByUserIdAndReadFalse(userId);
//    }
//
//    /**
//     * Deletes old read notifications
//     */
//    @Transactional
//    public void deleteOldNotifications(int daysOld) {
//        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysOld);
//        notificationRepository.deleteByReadTrueAndCreatedAtBefore(cutoffDate);
//        log.info("Deleted old notifications older than {} days", daysOld);
//    }
//}