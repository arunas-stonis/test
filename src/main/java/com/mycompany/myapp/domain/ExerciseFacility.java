package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExerciseFacility.
 */

@Document(collection = "exercise_facility")
public class ExerciseFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("created_at")
    private ZonedDateTime createdAt;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("category_id")
    private Integer categoryId;

    @NotNull
    @Field("address")
    private String address;

    @Field("coordinates")
    @GeoSpatialIndexed
    private double[] coordinates;

    @NotNull
    @Field("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ExerciseFacility createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public ExerciseFacility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ExerciseFacility categoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAddress() {
        return address;
    }

    public ExerciseFacility address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public ExerciseFacility description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseFacility exerciseFacility = (ExerciseFacility) o;
        if(exerciseFacility.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseFacility.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseFacility{" +
            "id=" + id +
            ", createdAt='" + createdAt + "'" +
            ", name='" + name + "'" +
            ", categoryId='" + categoryId + "'" +
            ", address='" + address + "'" +
            ", coordinates='" + coordinates + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
