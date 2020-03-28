package com.razi.ubbtt.job_shop;

import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class JobShop {
    private CourseRepository courseRepository;

    private int machines;
    private List<Job> jobs;

    private List<Course> myCourses, tuesdayCourses, wednesdayCourses, thursdayCourses, fridayCourses;

    public JobShop(int machines, CourseRepository courseRepository) {
        this.machines = machines;
        this.courseRepository = courseRepository;
    }

    public int getMachines() {
        return this.machines;
    }

    public List<Course> getFinishedTimetable() {
        return this.myCourses;
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
     * Checks if the solved timetable contains all the needed classes for year 2, semester 2
     * @return
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
     * @param className
     * @param classType
     * @return true if the class should be added to the timetable
     */
    private boolean needClass(String className, String classType) {
        // We don't want to go to lectures
        if (classType.equals("Curs"))
            return false;

        // Optional
        if (className.equals("Didactica Informaticii"))
            return false;

        return classCount(className, classType) == 0;
    }

    private boolean studentIsFreeAtDayAndHour(String day, String hours) {
        for (Course myCours : this.myCourses) {
            if (myCours.getDay().equals(day) && myCours.getHours().equals(hours)) {
                return false;
            }
        }

        return true;
    }

    public void solve(int year, int semester) {
        List<Course> allCourses = courseRepository.getCoursesByYearAndSemester(year, semester);
        allCourses = this.sortCourses(allCourses);
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
        this.myCourses = this.sortCourses(this.myCourses);
        for (int j = 0; j < this.myCourses.size(); ++j) {
            System.out.println(this.myCourses.get(j));

            // Day separator
            if (j < this.myCourses.size() - 1 && !this.myCourses.get(j).getDay().equals(this.myCourses.get(j + 1).getDay()))
                System.out.println("============================================");
        }
        System.out.println(":::::::::::::::::::::::::::::: END FINISHED TIMETABLE ::::::::::::::::::::::::::::::");
    }
}
