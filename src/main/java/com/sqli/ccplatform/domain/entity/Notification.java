//package com.sqli.ccplatform.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "notifications")
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "recipient_id", nullable = false)
//    private User recipient;
//
//    private String title;
//
//    private String message;
//
//    private boolean read = false;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//}