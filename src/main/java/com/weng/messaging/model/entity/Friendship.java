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
@Table(name = "t_friendship", indexes = {
        @Index(name = "t_friendship_deleted_idx", columnList = "deleted"),
        @Index(name = "t_friendship_created_at_idx", columnList = "createdAt"),
        @Index(name = "t_friendship_updated_at_idx", columnList = "updatedAt"),
})
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE {h-schema}t_friendship SET deleted = true WHERE id = ? and version = ?")
public class Friendship extends BaseEntity {

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @ManyToOne(fetch = LAZY)
    private User owner;

    @ManyToOne(fetch = LAZY)
    private User friend;

    private boolean favourite;
}