package com.razi.ubbtt.controllers;

import com.razi.ubbtt.Utils.GroupUtils;
import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Note;
import com.razi.ubbtt.domain.User;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.NoteRepository;
import com.razi.ubbtt.repositories.WeekRepository;
import com.razi.ubbtt.services.SecurityService;
import com.razi.ubbtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class LoginController {

    private SecurityService securityService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private NoteRepository noteRepository;

    /*
    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    */

    @GetMapping("/login")
    public String tryLogin(Model model, String error, String logout) {
        System.out.println("Attempting login my boi");
        //if (username != null && password != null)
        //    System.out.println(username + "..." + password);

        if (error != null) {
            model.addAttribute("error", "Invalid username and password combination");
            return "/login";
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully");
            //return "/login";
        }

        return "/login";
    }

/*
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
     */
/*
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            //bindingResult
             //       .rejectValue("username", "error.user",
              //              "There is already a user registered with the username provided");
            System.out.println("USER EXISTS != NULL");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
            System.out.println("BINDING RESULT HAS ERRORS");
        } else {
            userService.saveUser(user);
            securityService.autoLogin(user.getUsername(), user.getPassword());
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            //modelAndView.setViewName("registration");
            modelAndView.setViewName("/");
        }
        return modelAndView;
    }
 */

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", User.builder().build());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }

        userService.saveUser(userForm);

        // Not working currently!
        //securityService.autoLogin(userForm.getUsername(), userForm.getUsername());

        return "redirect:/admin/home";
    }

    public int getDayOfWeekFromString(String dayOfWeek) {
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

    public int getHourIndexFromString(String hours) {
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

    // MOVE THIS ASAP
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
        for (Course c: sortCourses(courseRepository.getCoursesForGroupForCurrentSemesterAndWeek(group, groupAndSubgroupsAndYear.get(1), groupAndSubgroupsAndYear.get(2), groupAndSubgroupsAndYear.get(3), weekRepository.getCurrentSemester()))) {
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

    /*
    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + " (" + user.getUsername() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }*/
}