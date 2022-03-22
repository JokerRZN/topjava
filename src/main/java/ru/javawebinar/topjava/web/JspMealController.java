package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        List<MealTo> mealTos = MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealTos);
        return "meals";
    }

    @PostMapping("/meals/{id}")
    public String updateMeal(HttpServletRequest request, @PathVariable("id") int id) {
        log.info("updateMeal {}", id);
        int userId = Integer.parseInt(request.getParameter("userId"));
        Meal meal = service.get(id, userId);
        service.update(meal, userId);
        return "meals";
    }

    @GetMapping("/meals/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        log.info("deleteMeal {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @PostMapping("/meals")
    public String createMeal(Meal meal) {
        service.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getMealWithFilter(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> list = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> mealTos = MealsUtil.getFilteredTos(list,SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", mealTos);
        return "meals";
    }
}
