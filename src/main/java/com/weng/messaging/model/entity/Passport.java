package com.weng.messaging.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_passport", indexes = {
        @Index(name = "t_passport_deleted_idx", columnList = "deleted"),
        @Index(name = "t_passport_created_at_idx", columnList = "createdAt"),
        @Index(name = "t_passport_updated_at_idx", columnList = "updatedAt"),
        @Index(name = "t_passport_client_type_idx", columnList = "clientType"),
        @Index(name = "t_passport_client_id_idx", columnList = "clientId")
})
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE {h-schema}t_passport SET deleted = true WHERE id = ? and version = ?")
// Used to support multiple login methods for the same user
public class Passport extends BaseEntity {

    public enum ClientType {
        EMAIL, MOBILE_NO
    }

    @ManyToOne(fetch = LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    // Email address, mobile no and other
    private String clientId;

    private String password;

    private boolean verified;
}
