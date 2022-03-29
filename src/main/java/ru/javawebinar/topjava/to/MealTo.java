package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo() {
        this.id = null;
        this.dateTime = null;
        this.description = null;
        this.calories = 0;
        this.excess = false;
    }

    public MealTo(@JsonProperty("id") Integer id, @JsonProperty("dateTime") LocalDateTime dateTime,
                  @JsonProperty("description") String description, @JsonProperty("calories") int calories, @JsonProperty("excess") boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
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

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealTo mealTo = (MealTo) o;

        if (getCalories() != mealTo.getCalories()) return false;
        if (isExcess() != mealTo.isExcess()) return false;
        if (getId() != null ? !getId().equals(mealTo.getId()) : mealTo.getId() != null) return false;
        if (getDateTime() != null ? !getDateTime().equals(mealTo.getDateTime()) : mealTo.getDateTime() != null)
            return false;
        return getDescription() != null ? getDescription().equals(mealTo.getDescription()) : mealTo.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getCalories();
        result = 31 * result + (isExcess() ? 1 : 0);
        return result;
    }
}
