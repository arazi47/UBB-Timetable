package com.razi.ubbtt.controllers;

import com.razi.ubbtt.Utils.ClassesUtils;
import com.razi.ubbtt.Utils.GroupUtils;
import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Note;
import com.razi.ubbtt.domain.User;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.NoteRepository;
import com.razi.ubbtt.repositories.WeekRepository;
import com.razi.ubbtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    //@Autowired
    //private UserService userService;

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

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", User.builder().build());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult) {
        //userService.saveUser(userForm);
        return "redirect:/admin/home";
    }

    @GetMapping({"/", "/admin/home"})
    public String home(Model model, Principal principal) {
        model.addAttribute("currentWeek", weekRepository.getCurrentWeek());

        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        //model.addAttribute("notes921", noteRepository.getNotesForCurrentSemesterAndWeekAndGroupOrYear(weekRepository.getCurrentSemester(), weekRepository.getCurrentWeek().getWeekNumber(), "921"));
        //model.addAttribute("courses921", sortCourses(courseRepository.getCoursesForGroupForCurrentSemesterAndWeek("921", "921/1", "921/2", "IE2", weekRepository.getCurrentSemester())));
        //model.addAttribute("courses926", sortCourses(courseRepository.getCoursesForGroupForCurrentSemesterAndWeek("926", "926/1", "926/2", "IE2", weekRepository.getCurrentSemester())));

        //System.out.println(noteRepository.getNotesForCurrentSemesterAndWeekAndGroupOrYear(weekRepository.getCurrentSemester(), weekRepository.getCurrentWeek().getWeekNumber(), "921", "921/1", "921/2", "IE2").size() + "SIZWE +== ");

        // LinkedHashMap retains the order of insertion
        List<Map<Course, Note>> courseNoteMapList = generateAllCourseNoteMaps();

        model.addAttribute("courseNoteMap", courseNoteMapList.get(0)); // TODO rename this to courseNoteMap921
        model.addAttribute("courseNoteMap926", courseNoteMapList.get(1));

        // TODO add the rest of the groups

        return "admin/home";
    }

    public Map<Course, Note> generateCourseNoteMapForGroup(String group) {
        List<String> groupAndSubgroupsAndYear = GroupUtils.getGroupAndSubgroupsAndYearAsList(group);
        // LinkedHashMap retains the order of insertion
        Map<Course, Note> courseNoteMap = new LinkedHashMap<>();
        for (Course c: ClassesUtils.sortCourses(courseRepository.getCoursesForGroupForCurrentSemesterAndWeek(group, groupAndSubgroupsAndYear.get(1), groupAndSubgroupsAndYear.get(2), groupAndSubgroupsAndYear.get(3), weekRepository.getCurrentSemester()))) {
            boolean courseAdded = false;
            for (Note n: noteRepository.getNotesForCurrentSemesterAndWeekAndGroupOrYear(weekRepository.getCurrentSemester(), weekRepository.getCurrentWeek().getWeekNumber(), new ArrayList<>(GroupUtils.getGroupAndSubgroupsAndYearAsList("926")))) {
                /*
                System.out.println("============================");
                System.out.println(c.getDiscipline() + " == " + n.getDiscipline());
                System.out.println(c.getGroupOrYear() + " == " + n.getGroupOrYear());
                System.out.println(c.getType() + " == " + n.getCourseType());
                System.out.println("============================");
                */

                if (c.getDiscipline().equals(n.getDiscipline()) && c.getGroupOrYear().equals(n.getGroupOrYear()) && c.getType().equals(n.getCourseType())) {
                    /*
                    System.out.println("ADDED");
                    System.out.println("============================");
                    System.out.println("Course ID = " + c.getId());
                    System.out.println("Note ID = " + n.getId());
                    System.out.println(c.getDiscipline() + " == " + n.getDiscipline());
                    System.out.println(c.getGroupOrYear() + " == " + n.getGroupOrYear());
                    System.out.println(c.getType() + " == " + n.getCourseType());
                    System.out.println("============================");
                     */
                    courseNoteMap.put(c, n);
                    if (!courseAdded)
                        courseAdded = true;
                }
            }

            if (!courseAdded)
                courseNoteMap.put(c, null);
        }

        return courseNoteMap;
    }

    public List<Map<Course, Note>> generateAllCourseNoteMaps() {
        List<Map<Course, Note>> courseNoteMapList = new ArrayList<>();
        courseNoteMapList.add(generateCourseNoteMapForGroup("921"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("926"));

        return courseNoteMapList;
    }
}