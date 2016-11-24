package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Pass.
 */

@Document(collection = "pass")
public class Pass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("created_at")
    private ZonedDateTime createdAt;

    @NotNull
    @Field("facility_id")
    private Integer facilityId;

    @NotNull
    @Field("customer_id")
    private Integer customerId;

    @NotNull
    @Field("start_date")
    private ZonedDateTime startDate;

    @NotNull
    @Field("end_date")
    private ZonedDateTime endDate;

    @NotNull
    @Field("price")
    private Long price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Pass createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public Pass facilityId(Integer facilityId) {
        this.facilityId = facilityId;
        return this;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Pass customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Pass startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Pass endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPrice() {
        return price;
    }

    public Pass price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pass pass = (Pass) o;
        if(pass.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pass{" +
            "id=" + id +
            ", createdAt='" + createdAt + "'" +
            ", facilityId='" + facilityId + "'" +
            ", customerId='" + customerId + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
