package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.debug("getAll");
        List<Meal> userMeals = service.getAll(SecurityUtil.authUserId());
        return MealsUtil.getTos(userMeals, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getMealTos(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.debug("getMealTos");
        List<Meal> userMeal = service.getAll(SecurityUtil.authUserId());
        return MealsUtil.filterByPredicate(userMeal,
                SecurityUtil.authUserCaloriesPerDay(), meal -> meal.getDate().isAfter(startDate)
                        && meal.getDate().isBefore(endDate)
                        && DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public Meal get(int id) {
        log.debug("get meal by id");
        return service.get(SecurityUtil.authUserId(), id);
    }

    public void delete(int id) {
        log.debug("delete meal by id");
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.debug("update meal by id");
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public void create(Meal meal) {
        log.debug("create meal");
        ValidationUtil.checkNew(meal);
        service.create(SecurityUtil.authUserId(), meal);
    }
}