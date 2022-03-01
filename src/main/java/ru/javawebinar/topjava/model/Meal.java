package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m where m.user.id =: user_id and m.id =: id"),
        @NamedQuery(name = Meal.GET_BY_ID, query = "select m from Meal m where m.user.id =: user_id and m.id =: id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.user.id =: user_id ORDER BY m.dateTime desc"),
        @NamedQuery(name = Meal.BETWEEN, query = "select m from Meal m where m.user.id =: user_id and m.dateTime >=: start_date and m.dateTime <: end_date ORDER BY m.dateTime desc")
})
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {

    public static final String GET_BY_ID = "Meal.getById";
    public static final String DELETE = "Meal.delete";
    public static final String BETWEEN = "Meal.getBetween";
    public static final String ALL_SORTED = "Meal.getAllSorted";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()", updatable = false, unique = true)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    @Size(min = 1, max = 100, message = "Description must be between 1 and 100 characters")
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
