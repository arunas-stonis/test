package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Invitation.
 */

@Document(collection = "invitation")
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("created_at")
    private ZonedDateTime createdAt;

    @NotNull
    @Field("valid_until")
    private ZonedDateTime validUntil;

    @NotNull
    @Field("catagery_id")
    private Integer catageryId;

    @Field("customer_id")
    private Integer customerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Invitation createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    public Invitation validUntil(ZonedDateTime validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(ZonedDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public Integer getCatageryId() {
        return catageryId;
    }

    public Invitation catageryId(Integer catageryId) {
        this.catageryId = catageryId;
        return this;
    }

    public void setCatageryId(Integer catageryId) {
        this.catageryId = catageryId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Invitation customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invitation invitation = (Invitation) o;
        if(invitation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, invitation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invitation{" +
            "id=" + id +
            ", createdAt='" + createdAt + "'" +
            ", validUntil='" + validUntil + "'" +
            ", catageryId='" + catageryId + "'" +
            ", customerId='" + customerId + "'" +
            '}';
    }
}
