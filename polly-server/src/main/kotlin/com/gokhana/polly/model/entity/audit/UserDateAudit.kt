package com.gokhana.polly.model.entity.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import javax.persistence.Column
import javax.persistence.MappedSuperclass


@MappedSuperclass
@JsonIgnoreProperties(value = ["createdBy", "updatedBy"], allowGetters = true)
abstract class UserDateAudit : DateAudit() {
    @CreatedBy
    @Column(updatable = false)
    var createdBy: Long = 0

    @LastModifiedBy
    var updatedBy: Long = 0
}