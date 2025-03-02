package com.weng.messaging.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class SoftDeleteEntity extends IdEntity {
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
}