package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC")
    List<Meal> getAll(int userId);


    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    Meal getById(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    int delete(int id, int userID);

    @Transactional
    @Query("SELECT m from Meal m WHERE m.user.id=?1 AND m.dateTime >=?2 AND m.dateTime <?3 ORDER BY m.dateTime DESC")
    List<Meal> getBetween(int userId, LocalDateTime startTime, LocalDateTime endTime);

}
