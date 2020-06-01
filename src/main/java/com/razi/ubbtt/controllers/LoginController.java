package com.razi.ubbtt.controllers;

import com.razi.ubbtt.Utils.HomepageTimetableUtils;
import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Note;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.NoteRepository;
import com.razi.ubbtt.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/login")
    public String tryLogin(Model model, String error, String logout) {
        System.out.println("Attempting login");

        if (error != null)
            model.addAttribute("error", "Invalid username and password combination");
        else if (logout != null)
            model.addAttribute("message", "Logged out successfully");

        return "/login";
    }

    @GetMapping({"/", "/admin/home"})
    public String home(Model model, Principal principal) {
        model.addAttribute("currentWeek", weekRepository.getCurrentWeek());

        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        HomepageTimetableUtils homepageTimetableUtils = new HomepageTimetableUtils(courseRepository, noteRepository, weekRepository);
        // LinkedHashMap retains the order of insertion
        List<Map<Course, Note>> courseNoteMapList = homepageTimetableUtils.generateAllCourseNoteMaps();

        model.addAttribute("courseNoteMap921", courseNoteMapList.get(0));
        model.addAttribute("courseNoteMap926", courseNoteMapList.get(1));

        return "admin/home";
    }
}