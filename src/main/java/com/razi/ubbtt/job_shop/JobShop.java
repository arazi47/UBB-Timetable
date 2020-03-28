package com.razi.ubbtt.job_shop;

import com.razi.ubbtt.Utils.ClassesUtils;
import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class JobShop {
    private CourseRepository courseRepository;
    private List<Course> myCourses;

    public JobShop(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getFinishedTimetable() {
        return this.myCourses;
    }

    private int classCount(String courseName, String type) {
        int cnt = 0;
        for (Course mondayClass : myCourses) {
            if (mondayClass.getDiscipline().equals(courseName) &&
                    mondayClass.getType().equals(type))
                ++cnt;
        }

        return cnt;
    }

    /**
     * Checks if the timetable contains all the needed classes for year 2, semester 2
     * @return true, if the timetable contains all the needed classes for year 2, semester 2
     *
     */
    private boolean checkIfSolved() {
        if (classCount("Medii de proiectare si programare", "Laborator") != 1)
            return false;

        if (classCount("Inteligenta artificiala", "Seminar") != 1)
            return false;

        if (classCount("Inteligenta artificiala", "Laborator") != 1)
            return false;

        if (classCount("Ingineria sistemelor soft", "Seminar") != 1)
            return false;

        if (classCount("Ingineria sistemelor soft", "Laborator") != 1)
            return false;

        if (classCount("Sisteme de gestiune a bazelor de date", "Seminar") != 1)
            return false;

        if (classCount("Sisteme de gestiune a bazelor de date", "Laborator") != 1)
            return false;

        if (classCount("Programare Web", "Laborator") != 1)
            return false;

        if (classCount("Limba engleza (2)", "Seminar") != 1)
            return false;

        return true;
    }

    /**
     *
     * @param disciplineName the discipline's name
     * @param classType the class' type (lecture/seminary/laboratory)
     * @return true if the class should be added to the timetable
     */
    private boolean needClass(String disciplineName, String classType) {
        // We don't want to go to lectures
        if (classType.equals("Curs"))
            return false;

        // Optional
        if (disciplineName.equals("Didactica Informaticii"))
            return false;

        return classCount(disciplineName, classType) == 0;
    }

    private boolean studentIsFreeAtDayAndHour(String day, String hours) {
        for (Course myCourse : this.myCourses) {
            if (myCourse.getDay().equals(day) && myCourse.getHours().equals(hours)) {
                return false;
            }
        }

        return true;
    }

    public void solve(int year, int semester) {
        List<Course> allCourses = courseRepository.getCoursesByYearAndSemester(year, semester);
        allCourses = ClassesUtils.sortCourses(allCourses);
        // The finished timetable
        this.myCourses = new ArrayList<>();
        //int iteration = 1;
        for (Course allCours : allCourses) {
            if (needClass(allCours.getDiscipline(), allCours.getType())
                    && studentIsFreeAtDayAndHour(allCours.getDay(), allCours.getHours())) {
                this.myCourses.add(allCours);
                if (checkIfSolved()) {
                    printFinishedTimetable();
                    break;
                }
            }
        }
    }

    private void printFinishedTimetable() {
        System.out.println(":::::::::::::::::::::::::::::: START FINISHED TIMETABLE ::::::::::::::::::::::::::::::");
        this.myCourses = ClassesUtils.sortCourses(this.myCourses);
        for (int j = 0; j < this.myCourses.size(); ++j) {
            System.out.println(this.myCourses.get(j));

            // Day separator
            if (j < this.myCourses.size() - 1 && !this.myCourses.get(j).getDay().equals(this.myCourses.get(j + 1).getDay()))
                System.out.println("============================================");
        }
        System.out.println(":::::::::::::::::::::::::::::: END FINISHED TIMETABLE ::::::::::::::::::::::::::::::");
    }
}
