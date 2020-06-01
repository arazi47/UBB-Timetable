package com.razi.ubbtt.controllers;

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
        Map<Integer, List<Tuple3<Integer, Integer, Integer>>> sequences = mockupTimetable.initializeMockupTimetable();
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry : sequences.entrySet()) {
            System.out.println("=============== MACHINE " + entry.getKey() + " ===============");
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                //System.out.println("Job = " + tuple.first() + "; Operation = " + tuple.second() + "; Duration = " + tuple.third());
                courseList.add(createCourseFromMapEntry(entry.getKey(), tuple));
            }
        }

        model.addAttribute("timetable", courseList);

        return "generate_timetable";
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

    private Course createCourseFromMapEntry(int dayInt, Tuple3<Integer, Integer, Integer> tuple)
    {
        Course course = new Course();
        course.setYear(2);
        course.setSemester(2);
        course.setDay(intToDay.get(dayInt));
        course.setDiscipline(intToDiscipline.get(tuple.first()));
        course.setFrequency("");
        course.setHours("");
        course.setGroupOrYear("");
        course.setRoom("");
        course.setType(intToClassType.get(tuple.second()));
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
