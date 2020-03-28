package com.razi.ubbtt.controllers;

import com.razi.ubbtt.job_shop.JobShop;
import com.razi.ubbtt.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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

        // 5 days in a week
        JobShop jobShop = new JobShop(courseRepository);
        jobShop.solve(2, 2);
        model.addAttribute("timetable", jobShop.getFinishedTimetable());
        return "generate_timetable";
    }
}
