package com.razi.ubbtt.controllers;

import com.razi.ubbtt.JobShop.utils.AdditionalInfo;
import com.razi.ubbtt.Utils.ClassUtils;
import com.razi.ubbtt.Utils.MockupTimetable;
import com.razi.ubbtt.Utils.Tuple3;
import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class TimetableController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/generate_timetable")
    public String GenerateTimetable(Model model, Principal principal) {
        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        List<Course> courseList = new ArrayList<>();

        MockupTimetable mockupTimetable = new MockupTimetable();
        Map<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> sequences = mockupTimetable.initializeMockupTimetable();
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> entry : sequences.entrySet()) {
            //System.out.println("=============== MACHINE " + entry.getKey() + " ===============");
            for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {
                //System.out.println("Job = " + tuple.first() + "; Operation = " + tuple.second() + "; Duration = " + tuple.third());
                courseList.add(createCourseFromMapEntry(entry.getKey(), tuple));
            }
        }

        this.sortCourses(courseList);

        for (Course course: courseList) {
            course.setDay(ClassUtils.dayFromRoToEn(course.getDay()));
            course.setType(ClassUtils.typeFromRoToEn(course.getType()));
            course.setDiscipline(ClassUtils.disciplineFromRoToEn(course.getDiscipline()));
            course.setFrequency(ClassUtils.frequencyFromRoToEn(course.getFrequency()));
        }

        model.addAttribute("timetable", courseList);

        return "generate_timetable";
    }

    private int getDayOfWeekFromString(String dayOfWeek) {
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

    private int getHourIndexFromString(String hours) {
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

    List<Course> sortCourses(List<Course> courses) {
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

    private Map<Integer, String> intToDay = Map.of(
            1, "Luni",
            2, "Marti",
            3, "Miercuri",
            4, "Joi",
            5, "Vineri"
    );

    private Map<Integer, String> intToDiscipline = Map.of(
            1, "Medii de proiectare si programare",
            2, "Inteligenta artificiala",
            3, "Ingineria sistemelor soft",
            4, "Sisteme de gestiune a bazelor de date",
            5, "Programare Web",
            6, "Limba engleza (2)"
    );

    private Map<Integer, String> intToClassType = Map.of(
            1, "Curs",
            2, "Seminar",
            3, "Laborator"
    );

    private String intToClassType(int subjectId, int type) {
        if (subjectId == 6)
            return "Seminar";
        else if (subjectId == 1 && type == 2)
            return "Laborator";

        return intToClassType.get(type);
    }

    private Course createCourseFromMapEntry(int dayInt, Tuple3<Integer, Integer, AdditionalInfo> tuple)
    {
        Course course = new Course();
        course.setYear(2);
        course.setSemester(2);
        course.setDay(intToDay.get(dayInt));
        course.setDiscipline(intToDiscipline.get(tuple.first()));
        course.setFrequency("");
        course.setHours(tuple.third().getHours());
        course.setGroupOrYear(tuple.third().getGroup());
        course.setRoom(tuple.third().getRoom());
        course.setType(intToClassType(tuple.first(), tuple.second()));
        return course;
    }

    /*
     * 1 - Medii de proiectare si programare (1 - curs, 2 - seminar, 3 - laborator)
     * 2 - Inteligenta artificiala (1 - curs, 2 - seminar, 3 - laborator)
     * 3 - Ingineria sistemelor soft (1 - curs, 2 - seminar, 3 - laborator)
     * 4 - Sisteme de gestiune a bazelor de date (1 - curs, 2 - seminar, 3 - laborator)
     * 5 - Programare Web - (1 - curs, 3 - laborator)
     * 6 - Limba engleza (2) (2 - seminar)
     */
}
