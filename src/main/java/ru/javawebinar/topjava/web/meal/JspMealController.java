package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.RootController;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private MealService service;

    @GetMapping
    public String getMeals(Model model) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        List<MealTo> mealTos = MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealTos);
        return "meals";
    }

    @GetMapping("/update/{id}")
    public String updateMeal(Model model, @PathVariable("id") int id) {
        log.info("updateMeal {}", id);
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meals", meal);
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        log.info("deleteMeal {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @PostMapping("/save")
    public String createMeal(HttpServletRequest request) {
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String sss = request.getParameter("id");
        int id = 0;
        if (!sss.equals("")) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        Meal meal = new Meal(localDateTime, desc, calories);
        if (meal.isNew()) {
            service.create(meal, SecurityUtil.authUserId());
        }
        else {
            meal = service.get(id, SecurityUtil.authUserId());
            service.update(meal, id);
        }
        return "meals";
    }

    @GetMapping("/save")
    public String getMealForm(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meals", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    public String getMealWithFilter(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> list = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> mealTos = MealsUtil.getFilteredTos(list, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", mealTos);
        return "meals";
    }
}
