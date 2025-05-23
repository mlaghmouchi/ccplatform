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
//@Table(name = "process_images")
//public class ProcessImage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id", nullable = false)
//    private PreparationTask task;
//
//    private String imagePath;
//
//    private String caption;
//
//    @CreationTimestamp
//    private LocalDateTime uploadedAt;
//}