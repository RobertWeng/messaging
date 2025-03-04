package com.weng.messaging.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", indexes = {
        @Index(name = "t_user_deleted_idx", columnList = "deleted"),
        @Index(name = "t_user_created_at_idx", columnList = "createdAt"),
        @Index(name = "t_user_updated_at_idx", columnList = "updatedAt"),
})
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE {h-schema}t_user SET deleted = true WHERE id = ? and version = ?")
public class User extends BaseEntity {

    public enum Role {
        USER, ADMIN // Temporary no ADMIN role
    }

    public enum AccountStatus {
        ACTIVE, BLOCKED
    }

    public enum OnlineStatus {
        ACTIVE, OFFLINE, BUSY
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobileNo;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;

    private LocalDateTime accountBlockedUntil;

    private Locale locale = Locale.ENGLISH;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    private Set<Passport> passports = new HashSet<>();

    public void addPassport(Passport passport) {
        this.passports.add(passport);
    }

    public void addPassport(Set<Passport> passports) {
        this.passports.addAll(passports);
    }
}