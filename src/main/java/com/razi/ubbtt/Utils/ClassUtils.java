package com.razi.ubbtt.Utils;

import com.razi.ubbtt.domain.Course;

import java.util.List;

public class ClassUtils {
    public static String frequencyFromRoToEn(String frequency) {
        switch (frequency.toLowerCase()) {
            case "sapt. 1":
                return "Week 1";

            case "sapt. 2":
                return "Week 2";

            default:
                return frequency;
        }
    }

    public static String disciplineFromRoToEn(String discipline) {
        switch (discipline.toLowerCase()) {
            // Year 1
            case "programare orientata obiect":
                return "Object Oriented Programming";
            case "algoritmica grafelor":
                return "Graphs";
            case "sisteme dinamice":
                return "Dynamic Systems";
            case "structuri de date si algoritmi":
                return "Data Structures and Algorithms";
            case "sisteme de operare":
                return "Operating Systems";
            case "geometrie":
                return "Geometry";
            case "fundamentele pedagogiei":
                return "Fundamentals of pedagogy";

            // Year 2
            case "medii de proiectare si programare":
                return "Systems for design and implementation";
            case "inteligenta artificiala":
                return "Artificial intelligence";
            case "ingineria sistemelor soft":
                return "Software engineering";
            case "sisteme de gestiune a bazelor de date":
                return "Database management systems";
            case "programare web":
                return "Web programming";
            case "limba engleza (2)":
                return "English (2)";
            case "didactica informaticii":
                return "Didactics of Informatics";
            default:
                //return "Error in disciplineFromRoToEn";
                return discipline;
        }
    }

    public static String year3DisciplineFromRoToEn(String discipline) {
        switch (discipline.toLowerCase()) {
            case "programare orientata obiect":
                return "Object Oriented Programming";
            case "algoritmica grafelor":
                return "Graphs";
            case "sisteme dinamice":
                return "Dynamic Systems";
            case "structuri de date si algoritmi":
                return "Data Structures and Algorithms";
            case "sisteme de operare":
                return "Operating Systems";
            case "geometrie":
                return "Geometry";
            case "fundamentele pedagogiei":
                return "Fundamentals of pedagogy";
            default:
                //return "Error in disciplineFromRoToEn";
                return discipline;
        }
    }

    public static String typeFromRoToEn(String type) {
        switch (type.toLowerCase()) {
            case "curs":
                return "Lecture";

            case "seminar":
                return "Seminary";

            case "laborator":
                return "Laboratory";

            default:
                //return "Error in typeFromRoToEn";
                return type;
        }
    }

    public static String dayFromRoToEn(String day) {
        switch (day.toLowerCase()) {
            case "luni":
                return "Monday";
            case "marti":
                return "Tuesday";
            case "miercuri":
                return "Wednesday";
            case "joi":
                return "Thursday";
            case "vineri":
                return "Friday";
            default:
                //return "Error in dayFromRoToEn";
                return day;
        }
    }

    private static int getDayOfWeekFromString(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "luni":
            case "monday":
                return 1;
            case "marti":
            case "tuesday":
                return 2;
            case "miercuri":
            case "wednesday":
                return 3;
            case "joi":
            case "thursday":
                return 4;
            case "vineri":
            case "friday":
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
