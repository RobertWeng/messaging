package com.weng.messaging.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends SoftDeleteEntity {

    @Version
    @Column
    private int version;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = LAZY)
    @CreatedBy
    private User createdBy;

    @ManyToOne(fetch = LAZY)
    @LastModifiedBy
    private User updatedBy;
}