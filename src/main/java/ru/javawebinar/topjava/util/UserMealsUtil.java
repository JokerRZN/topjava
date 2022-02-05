package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //mealsTo.forEach(System.out::println);
        List<UserMealWithExcess> mealsTo1 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        for (UserMealWithExcess meal:mealsTo) {
            System.out.println(meal);
        }

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Boolean> map = new HashMap<>();
        Map<LocalDate, Integer> maps = new HashMap<>();

        for (UserMeal userMeal:meals) {
            maps.put(userMeal.getDateTime().toLocalDate(), maps.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories());
        }

        for (Map.Entry<LocalDate, Integer> s: maps.entrySet()) {
            boolean excess = false;
            if (s.getValue() <= caloriesPerDay) {
                excess = true;
            }
            map.put(s.getKey(), excess);
        }

        List<UserMealWithExcess> list = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            for (Map.Entry<LocalDate, Boolean> s: map.entrySet()) {
                if (userMeal.getDateTime().toLocalDate().equals(s.getKey())) {
                    if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        list.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), s.getValue()));
                    }
                }
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        /*Stream<UserMeal> stream = meals.stream();

        Map<LocalDate, Integer> map = stream
                .collect(Collectors.toMap(k -> k.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        Map<LocalDate, Boolean> map2 = map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue() <= caloriesPerDay));

        Map<UserMeal, Boolean> user = meals.stream()
                .map()
                .collect(Collectors.toMap(k -> k, UserMeal::getCalories, Integer::sum));

        List<UserMeal> list = meals.stream().filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());

        List<UserMealWithExcess> userMealWithExcess = meals.stream()
                //.filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map();


        //Map<LocalDate, Integer> map = new HashMap<>();
        //IntStream.range(0, meals.size()).parallel().forEach(o -> map.put(k, v));

        //List<UserMeal> list = stream.filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
       //        .collect(Collectors.toList());
       // Stream<UserMeal> str = list.stream();
        //List<UserMealWithExcess> list1 = stream.mapToInt(UserMeal::getCalories)
         //       .collect(Collectors.toList());

         */

        return null;
    }
}