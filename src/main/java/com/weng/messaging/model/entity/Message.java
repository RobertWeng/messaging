package com.weng.messaging.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_message", indexes = {
        @Index(name = "t_message_deleted_idx", columnList = "deleted"),
        @Index(name = "t_message_created_at_idx", columnList = "createdAt"),
        @Index(name = "t_message_updated_at_idx", columnList = "updatedAt"),
})
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE {h-schema}t_message SET deleted = true WHERE id = ? and version = ?")
public class Message extends BaseEntity {
    public enum Status {
        SENT, // Sent but not yet received
        DELIVERED, // Received but not yet read
        READ
    }

    @ManyToOne(fetch = LAZY)
    private User sender;

    @ManyToOne(fetch = LAZY)
    private User receiver;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SENT;
}