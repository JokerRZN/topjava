package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {

    @Autowired
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll(int userId) {
        return service.getAll(userId);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public void delete(int userId, int id) {
        service.delete(userId, id);
    }

    public void update(int userId, Meal meal) {
        service.update(userId, meal);
    }

    public void create(Meal meal) {
        service.create(meal.getUserId(), meal);
    }
}