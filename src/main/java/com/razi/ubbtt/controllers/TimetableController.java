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

import static com.razi.ubbtt.Utils.MockupTimetable.*;

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
            for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {
                courseList.add(createCourseFromMapEntry(entry.getKey(), tuple));
            }
        }

        sortCourses(courseList);

        for (Course course: courseList) {
            course.setDay(ClassUtils.dayFromRoToEn(course.getDay()));
            course.setType(ClassUtils.typeFromRoToEn(course.getType()));
            course.setDiscipline(ClassUtils.disciplineFromRoToEn(course.getDiscipline()));
            course.setFrequency(ClassUtils.frequencyFromRoToEn(course.getFrequency()));
        }

        model.addAttribute("timetable", courseList);

        return "generate_timetable";
    }
}
