package com.razi.ubbtt.OnAppEvent;

import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Week;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class OnAppEvent implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Run this once
        // year 2, sem 2
        /*
        for (Course c: this.loadSubjects("D:\\ubbtt\\src\\main\\java\\com\\razi\\ubbtt\\OnAppEvent\\y2s2.txt", 2, 2)) {
            courseRepository.save(c);
            System.out.println("Saved");
        }

        for (Course c: this.loadSubjects("D:\\ubbtt\\src\\main\\java\\com\\razi\\ubbtt\\OnAppEvent\\fake_y1s2.txt", 1, 2)) {
            courseRepository.save(c);
            System.out.println("Saved");
        }

        for (Course c: this.loadSubjects("D:\\ubbtt\\src\\main\\java\\com\\razi\\ubbtt\\OnAppEvent\\fake_y3s2.txt", 3, 2)) {
            courseRepository.save(c);
            System.out.println("Saved");
        }
         */

        //loadAllWeeks();
        //buildWeekCourseNotes();
    }

    private List<Course> loadSubjects(String fullPath, int year, int semester) {
        List<Course> courseList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fullPath)));

            String line;
            while ((line = br.readLine()) != null) {
                Course c = Course.parseFromFile(line);
                c.setYear(year);
                c.setSemester(semester);
                courseList.add(c);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return courseList;
    }

    private void loadAllWeeks() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\necso\\Desktop\\ubbtt\\src\\main\\java\\com\\razi\\ubbtt\\OnAppEvent\\weeks.txt")));

            int weekNumber = 1;
            int semester = 1;
            String line;
            while ((line = br.readLine()) != null) {
                Week week = Week.parseFromFile(line);
                if (week != null) {
                    week.setSemester(semester);
                    week.setWeekNumber(weekNumber++);
                    weekRepository.save(week);

                    // sem 1 -> sem 2
                    if (weekNumber == 15) {
                        weekNumber = 1;
                        semester = 2;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}