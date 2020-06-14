package com.razi.ubbtt.Utils;

import com.razi.ubbtt.JobShop.jobs.ClassJob;
import com.razi.ubbtt.JobShop.jobs.Job;
import com.razi.ubbtt.JobShop.solvers.TimetableSolver;
import com.razi.ubbtt.JobShop.utils.AdditionalInfo;
import com.razi.ubbtt.domain.Course;

import java.util.*;

public class MockupTimetable {
    private int jobCount = 6;
    private int machineCount = 5;

    public static Map<Integer, String> intToDay = Map.of(
            1, "Luni",
            2, "Marti",
            3, "Miercuri",
            4, "Joi",
            5, "Vineri"
    );

    public static Map<Integer, String> intToDiscipline = Map.of(
            1, "Medii de proiectare si programare",
            2, "Inteligenta artificiala",
            3, "Ingineria sistemelor soft",
            4, "Sisteme de gestiune a bazelor de date",
            5, "Programare Web",
            6, "Limba engleza (2)"
    );

    public static Map<Integer, String> intToClassType = Map.of(
            1, "Curs",
            2, "Seminar",
            3, "Laborator"
    );

    public static String intToClassType(int subjectId, int type) {
        if (subjectId == 6)
            return "Seminar";
        else if (subjectId == 1 && type == 2)
            return "Laborator";

        return intToClassType.get(type);
    }

    public static int getDayOfWeekFromString(String dayOfWeek) {
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

    public static int getHourIndexFromString(String hours) {
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

    public static List<Course> sortCourses(List<Course> courses) {
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

    public static Course createCourseFromMapEntry(int dayInt, Tuple3<Integer, Integer, AdditionalInfo> tuple)
    {
        Course course = new Course();
        course.setYear(2);
        course.setSemester(2);
        course.setDay(intToDay.get(dayInt));
        course.setDiscipline(intToDiscipline.get(tuple.first()));
        course.setFrequency("");
        course.setHours(tuple.third().getHours());
        course.setGroupOrYear(tuple.third().getGroup());
        course.setRoom(tuple.third().getRoom());
        course.setType(intToClassType(tuple.first(), tuple.second()));
        List<String> randomTeacherNames = Arrays.asList("Andrew Young", "Michael Hester", "Porter Haynes", "Adrianna Lin", "Valentina Jenkins", "Carla Krueger");
        // Choose a random name for the teacher
        course.setTeacher(randomTeacherNames.get(new Random().nextInt(randomTeacherNames.size())));
        return course;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob1() {
        // key = pair <machineId, jobId, operationId>
        // value = additional class info
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1,1), new AdditionalInfo("curs", "12-14", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 1,2), new AdditionalInfo("laborator", "8-10", "L301", "932"));

        operationsDurationMap.put(new Tuple3<>(2, 1, 2), new AdditionalInfo("laborator", "10-12", "L308", "936"));

        operationsDurationMap.put(new Tuple3<>(3, 1, 2), new AdditionalInfo("laborator", "18-20", "L306", "937"));

        operationsDurationMap.put(new Tuple3<>(4, 1, 2), new AdditionalInfo("laborator", "16-18", "L336", "935"));

        operationsDurationMap.put(new Tuple3<>(5, 1, 2), new AdditionalInfo("laborator", "14-16", "2/I", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob2() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 2,1), new AdditionalInfo("curs", "14-16", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 2,2), new AdditionalInfo("seminar", "10-12", "2/I", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 2,3), new AdditionalInfo("laborator", "14-16", "L301", "935"));

        operationsDurationMap.put(new Tuple3<>(2, 2,2), new AdditionalInfo("seminar", "12-14", "C410", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 2,3), new AdditionalInfo("laborator", "16-18", "L302", "932"));

        operationsDurationMap.put(new Tuple3<>(3, 2,2), new AdditionalInfo("seminar", "8-10", "C308", "936"));
        operationsDurationMap.put(new Tuple3<>(3, 2,3), new AdditionalInfo("laborator", "12-14", "L303", "937"));

        operationsDurationMap.put(new Tuple3<>(4, 2,2), new AdditionalInfo("seminar", "10-12", "6/II", "936"));
        operationsDurationMap.put(new Tuple3<>(4, 2,3), new AdditionalInfo("laborator", "18-20", "L307", "931"));

        operationsDurationMap.put(new Tuple3<>(5, 2,2), new AdditionalInfo("seminar", "14-16", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(5, 2,3), new AdditionalInfo("laborator", "8-10", "L308", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob3() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 3,2), new AdditionalInfo("seminar", "8-10", "6/II", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 3,3), new AdditionalInfo("laborator", "12-14", "L306", "934"));

        operationsDurationMap.put(new Tuple3<>(2, 3,2), new AdditionalInfo("seminar", "12-14", "C310", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 3,3), new AdditionalInfo("laborator", "14-16", "L307", "936"));

        operationsDurationMap.put(new Tuple3<>(3, 3,1), new AdditionalInfo("curs", "8-10", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 3,2), new AdditionalInfo("seminar", "16-18", "C512", "932"));
        operationsDurationMap.put(new Tuple3<>(3, 3,3), new AdditionalInfo("laborator", "8-10", "L310", "931"));

        operationsDurationMap.put(new Tuple3<>(4, 3,2), new AdditionalInfo("seminar", "16-18", "C512", "934"));
        operationsDurationMap.put(new Tuple3<>(4, 3,3), new AdditionalInfo("laborator", "12-14", "L308", "935"));

        operationsDurationMap.put(new Tuple3<>(5, 3,2), new AdditionalInfo("seminar", "14-16", "C514", "937"));
        operationsDurationMap.put(new Tuple3<>(5, 3,3), new AdditionalInfo("laborator", "18-20", "L301", "937"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob4() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 4,2), new AdditionalInfo("seminar", "8-10", "Gamma", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 4,3), new AdditionalInfo("laborator", "16-18", "L306", "931"));

        operationsDurationMap.put(new Tuple3<>(2, 4,2), new AdditionalInfo("seminar", "14-16", "Pi", "934"));
        operationsDurationMap.put(new Tuple3<>(2, 4,3), new AdditionalInfo("laborator", "8-10", "L305", "934"));

        operationsDurationMap.put(new Tuple3<>(3, 4,2), new AdditionalInfo("seminar", "12-14", "Gamma", "936"));
        operationsDurationMap.put(new Tuple3<>(3, 4,3), new AdditionalInfo("laborator", "8-10", "L304", "936"));

        operationsDurationMap.put(new Tuple3<>(4, 4,1), new AdditionalInfo("curs", "10-12", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 4,2), new AdditionalInfo("seminar", "16-18", "C310", "936"));
        operationsDurationMap.put(new Tuple3<>(4, 4,3), new AdditionalInfo("laborator", "12-14", "L307", "932"));

        operationsDurationMap.put(new Tuple3<>(5, 4,2), new AdditionalInfo("seminar", "10-12", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(5, 4,3), new AdditionalInfo("laborator", "8-10", "L303", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob5() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 5,2), new AdditionalInfo("seminar", "8-10", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(1, 5,3), new AdditionalInfo("laborator", "8-10", "L311", "935"));

        operationsDurationMap.put(new Tuple3<>(2, 5,2), new AdditionalInfo("seminar", "10-12", "C512", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 5,3), new AdditionalInfo("laborator", "12-14", "L402", "937"));

        operationsDurationMap.put(new Tuple3<>(3, 5,2), new AdditionalInfo("seminar", "12-14", "C514", "931"));
        operationsDurationMap.put(new Tuple3<>(3, 5,3), new AdditionalInfo("laborator", "10-12", "L313", "934"));

        operationsDurationMap.put(new Tuple3<>(4, 5,1), new AdditionalInfo("curs", "18-20", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 5,2), new AdditionalInfo("seminar", "8-10", "C512", "934"));
        operationsDurationMap.put(new Tuple3<>(4, 5,3), new AdditionalInfo("laborator", "10-12", "L308", "933"));

        operationsDurationMap.put(new Tuple3<>(5, 5,2), new AdditionalInfo("seminar", "8-10", "6/II", "934"));
        operationsDurationMap.put(new Tuple3<>(5, 5,3), new AdditionalInfo("laborator", "12-14", "L303", "937"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob6() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 6,1), new AdditionalInfo("seminar", "14-16", "Gamma", "932"));

        operationsDurationMap.put(new Tuple3<>(2, 6,1), new AdditionalInfo("seminar", "8-10", "Pi", "937"));

        operationsDurationMap.put(new Tuple3<>(3, 6,1), new AdditionalInfo("seminar", "18-20", "Gamma", "935"));

        operationsDurationMap.put(new Tuple3<>(4, 6,1), new AdditionalInfo("seminar", "14-16", "Pi", "932"));

        operationsDurationMap.put(new Tuple3<>(5, 6,1), new AdditionalInfo("seminar", "12-14", "Gamma", "936"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob(int jobNumber) {
        switch (jobNumber) {
            case 1:
                return getOperationsDurationMapForJob1();
            case 2:
                return getOperationsDurationMapForJob2();
            case 3:
                return getOperationsDurationMapForJob3();
            case 4:
                return getOperationsDurationMapForJob4();
            case 5:
                return getOperationsDurationMapForJob5();
            case 6:
                return getOperationsDurationMapForJob6();

            default:
                return null;
        }
    }

    private int getOperationsCountForJob(int jobId) {
        if (jobId == 1 || jobId == 6)
            return 1;

        return 3;
    }

    private void generateJobs(List<ClassJob> jobs) {
        for (int j = 0; j < this.jobCount; ++j) {
            ClassJob job = new ClassJob(j + 1, getOperationsCountForJob(j + 1), getOperationsDurationMapForJob(j + 1));
            jobs.add(job);
        }
    }

    public Map<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> initializeMockupTimetable() {
        List<ClassJob> jobs = new ArrayList<>();
        generateJobs(jobs);

        TimetableSolver solver = new TimetableSolver(jobCount, machineCount, jobs);
        solver.solve();

        return solver.getSequences();
    }
}
