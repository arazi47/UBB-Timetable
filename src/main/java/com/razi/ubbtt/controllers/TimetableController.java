package com.razi.ubbtt.controllers;

import com.razi.ubbtt.Utils.Tuple3;
import com.razi.ubbtt.job_shop.JobShop;
import com.razi.ubbtt.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 1 - Medii de proiectare si programare (1 - curs, 2 - seminar, 3 - laborator)
     * 2 - Inteligenta artificiala (1 - curs, 2 - seminar, 3 - laborator)
     * 3 - Ingineria sistemelor soft (1 - curs, 2 - seminar, 3 - laborator)
     * 4 - Sisteme de gestiune a bazelor de date (1 - curs, 2 - seminar, 3 - laborator)
     * 5 - Programare Web - (1 - curs, 3 - laborator)
     * 6 - Limba engleza (2) (2 - seminar)
     */
    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForGroup1() {
        // key = day (1 - monday, 2 - tuesday...), class (mpp, iss...), class type (curs/seminar/laborator)
        // value = how much that class takes
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1, 1), 3);
        operationsDurationMap.put(new Tuple3<>(1, 2, 1), 2);

        operationsDurationMap.put(new Tuple3<>(2, 1, 3), 4);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 2);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 3);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 3);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 1);

        operationsDurationMap.put(new Tuple3<>(3, 7, 1), 2);
        operationsDurationMap.put(new Tuple3<>(3, 7, 2), 1);
        operationsDurationMap.put(new Tuple3<>(3, 7, 3), 4);
        operationsDurationMap.put(new Tuple3<>(3, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(3, 7, 2), 3);

        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 3);
        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 1);
        operationsDurationMap.put(new Tuple3<>(4, 7, 2), 3);

        operationsDurationMap.put(new Tuple3<>(5, 7, 1), 3);
        operationsDurationMap.put(new Tuple3<>(5, 7, 2), 1);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 4);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 2);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForGroup2() {
        // key = day (1 - monday, 2 - tuesday...), class (mpp, iss...), class type (curs/seminar/laborator)
        // value = how much that class takes
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1, 3), 1);
        operationsDurationMap.put(new Tuple3<>(1, 2, 3), 4);
        operationsDurationMap.put(new Tuple3<>(1, 1, 1), 1);
        operationsDurationMap.put(new Tuple3<>(1, 7, 1), 4);
        operationsDurationMap.put(new Tuple3<>(1, 7, 3), 4);

        operationsDurationMap.put(new Tuple3<>(3, 7, 1), 2);
        operationsDurationMap.put(new Tuple3<>(3, 7, 2), 4);
        operationsDurationMap.put(new Tuple3<>(3, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(3, 7, 3), 1);

        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 4);
        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 3);
        operationsDurationMap.put(new Tuple3<>(4, 7, 2), 1);
        operationsDurationMap.put(new Tuple3<>(4, 7, 3), 2);
        operationsDurationMap.put(new Tuple3<>(4, 7, 3), 2);

        operationsDurationMap.put(new Tuple3<>(5, 7, 1), 4);
        operationsDurationMap.put(new Tuple3<>(5, 7, 2), 4);
        operationsDurationMap.put(new Tuple3<>(5, 7, 2), 3);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 3);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForGroup3() {
        // key = day (1 - monday, 2 - tuesday...), class (mpp, iss...), class type (curs/seminar/laborator)
        // value = how much that class takes
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1, 1), 4);
        operationsDurationMap.put(new Tuple3<>(1, 2, 1), 2);

        operationsDurationMap.put(new Tuple3<>(2, 1, 3), 3);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 3);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 4);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 3);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 4);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 1);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 2);
        operationsDurationMap.put(new Tuple3<>(2, 7, 3), 4);

        operationsDurationMap.put(new Tuple3<>(3, 7, 1), 4);

        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 1);
        operationsDurationMap.put(new Tuple3<>(4, 7, 1), 3);
        operationsDurationMap.put(new Tuple3<>(4, 7, 2), 4);
        operationsDurationMap.put(new Tuple3<>(4, 7, 2), 3);
        operationsDurationMap.put(new Tuple3<>(4, 7, 2), 1);

        operationsDurationMap.put(new Tuple3<>(5, 7, 1), 3);
        operationsDurationMap.put(new Tuple3<>(5, 7, 2), 2);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 2);

        return operationsDurationMap;
    }
}
