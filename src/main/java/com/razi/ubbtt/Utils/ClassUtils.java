package com.razi.ubbtt.Utils;

import com.razi.ubbtt.domain.Course;

import java.util.List;

public class ClassUtils {
    private static int getDayOfWeekFromString(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "luni":
                return 1;
            case "marti":
                return 2;
            case "miercuri":
                return 3;
            case "joi":
                return 4;
            case "vineri":
                return 5;
            default:
                return 1;
        }
    }

    private static int getHourIndexFromString(String hours) {
        switch (hours) {
            case "8-10":
                return 1;
            case "10-12":
                return 2;
            case "12-14":
                return 3;
            case "14-16":
                return 4;
            case "16-18":
                return 5;
            case "18-20":
                return 6;
            default:
                return 1;
        }
    }

    public static List<Course> sortCourses(List<Course> courses) {
        courses.sort((c1, c2) -> {
            if (getDayOfWeekFromString(c1.getDay()) < getDayOfWeekFromString(c2.getDay()))
                return -1;
            else if (getDayOfWeekFromString(c1.getDay()) > getDayOfWeekFromString(c2.getDay()))
                return 1;
            else {
                return Integer.compare(getHourIndexFromString(c1.getHours()), getHourIndexFromString(c2.getHours()));
            }
        });

        return courses;
    }
}
