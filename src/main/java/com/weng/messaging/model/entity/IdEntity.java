package com.weng.messaging.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@MappedSuperclass
public class IdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
}