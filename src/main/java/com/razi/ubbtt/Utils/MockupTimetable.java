package com.razi.ubbtt.Utils;

import com.razi.ubbtt.JobShop.jobs.ClassJob;
import com.razi.ubbtt.JobShop.jobs.Job;
import com.razi.ubbtt.JobShop.solvers.TimetableSolver;
import com.razi.ubbtt.JobShop.utils.AdditionalInfo;

import java.util.*;

public class MockupTimetable {
    private int jobCount = 6;
    private int machineCount = 5;

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob1() {
        // key = pair <machineId, jobId, operationId>
        // value = additional class info
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1,1), new AdditionalInfo("curs", "12-14", "2/I", "IE2"));
        //operationsDurationMap.put(new Tuple3<>(1, 1,2), new AdditionalInfo("seminar", "12-14", "C310", "931"));
        operationsDurationMap.put(new Tuple3<>(1, 1,2), new AdditionalInfo("laborator", "8-10", "L301", "932"));

        //operationsDurationMap.put(new Tuple3<>(2, 1, 1), new AdditionalInfo("curs", "10-12", "6/II", "IE2"));
        //operationsDurationMap.put(new Tuple3<>(2, 1, 2), new AdditionalInfo("seminar", "16-18", "C510", "933"));
        operationsDurationMap.put(new Tuple3<>(2, 1, 2), new AdditionalInfo("laborator", "10-12", "L308", "936"));

        //operationsDurationMap.put(new Tuple3<>(3, 1, 1), new AdditionalInfo("curs", "10-12", "6/II", "IE2"));
        //operationsDurationMap.put(new Tuple3<>(3, 1, 2), new AdditionalInfo("seminar", "16-18", "C310", "934"));
        operationsDurationMap.put(new Tuple3<>(3, 1, 2), new AdditionalInfo("laborator", "18-20", "L306", "937"));

        //operationsDurationMap.put(new Tuple3<>(4, 1, 1), new AdditionalInfo("curs", "8-10", "6/II", "IE2"));
        //operationsDurationMap.put(new Tuple3<>(4, 1, 2), new AdditionalInfo("seminar", "12-14", "C510", "934"));
        operationsDurationMap.put(new Tuple3<>(4, 1, 2), new AdditionalInfo("laborator", "16-18", "L336", "935"));

        //operationsDurationMap.put(new Tuple3<>(5, 1, 1), new AdditionalInfo("curs", "10-12", "6/II", "IE2"));
        //operationsDurationMap.put(new Tuple3<>(5, 1, 2), new AdditionalInfo("seminar", "16-18", "2/I", "933"));
        operationsDurationMap.put(new Tuple3<>(5, 1, 2), new AdditionalInfo("laborator", "14-16", "2/I", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob2() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 2,1), new AdditionalInfo("curs", "14-16", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 2,2), new AdditionalInfo("seminar", "10-12", "2/I", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 2,3), new AdditionalInfo("laborator", "14-16", "L301", "935"));

        //operationsDurationMap.put(new Tuple3<>(2, 2,1), new AdditionalInfo("curs", "8-10", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(2, 2,2), new AdditionalInfo("seminar", "12-14", "C410", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 2,3), new AdditionalInfo("laborator", "18-20", "L302", "932"));

        //operationsDurationMap.put(new Tuple3<>(3, 2,1), new AdditionalInfo("curs", "12-14", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 2,2), new AdditionalInfo("seminar", "8-10", "C308", "936"));
        operationsDurationMap.put(new Tuple3<>(3, 2,3), new AdditionalInfo("laborator", "12-14", "L303", "937"));

        //operationsDurationMap.put(new Tuple3<>(4, 2,1), new AdditionalInfo("curs", "10-12", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 2,2), new AdditionalInfo("seminar", "10-12", "6/II", "936"));
        operationsDurationMap.put(new Tuple3<>(4, 2,3), new AdditionalInfo("laborator", "18-20", "L307", "931"));

        //operationsDurationMap.put(new Tuple3<>(5, 2,1), new AdditionalInfo("curs", "8-10", "C510", "IE2"));
        operationsDurationMap.put(new Tuple3<>(5, 2,2), new AdditionalInfo("seminar", "16-18", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(5, 2,3), new AdditionalInfo("laborator", "8-10", "L308", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob3() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        //operationsDurationMap.put(new Tuple3<>(1, 3,1), new AdditionalInfo("curs", "12-14", "Beta", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 3,2), new AdditionalInfo("seminar", "8-10", "Beta", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 3,3), new AdditionalInfo("laborator", "12-14", "Pi", "934"));

        //operationsDurationMap.put(new Tuple3<>(2, 3,1), new AdditionalInfo("curs", "14-16", "Beta", "IE2"));
        operationsDurationMap.put(new Tuple3<>(2, 3,2), new AdditionalInfo("seminar", "12-14", "Beta", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 3,3), new AdditionalInfo("laborator", "14-16", "Pi", "936"));

        operationsDurationMap.put(new Tuple3<>(3, 3,1), new AdditionalInfo("curs", "8-10", "Gamma", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 3,2), new AdditionalInfo("seminar", "16-18", "Beta", "932"));
        operationsDurationMap.put(new Tuple3<>(3, 3,3), new AdditionalInfo("laborator", "8-10", "L302", "931"));

        //operationsDurationMap.put(new Tuple3<>(4, 3,1), new AdditionalInfo("curs", "10-12", "Beta", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 3,2), new AdditionalInfo("seminar", "16-18", "Beta", "934"));
        operationsDurationMap.put(new Tuple3<>(4, 3,3), new AdditionalInfo("laborator", "12-14", "Pi", "935"));

        //operationsDurationMap.put(new Tuple3<>(5, 3,1), new AdditionalInfo("curs", "12-14", "Beta", "IE2"));
        operationsDurationMap.put(new Tuple3<>(5, 3,2), new AdditionalInfo("seminar", "14-16", "Beta", "937"));
        operationsDurationMap.put(new Tuple3<>(5, 3,3), new AdditionalInfo("laborator", "18-20", "Pi", "937"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob4() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        //operationsDurationMap.put(new Tuple3<>(1, 4,1), new AdditionalInfo("curs", "10-12", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 4,2), new AdditionalInfo("seminar", "8-10", "Gamma", "932"));
        operationsDurationMap.put(new Tuple3<>(1, 4,3), new AdditionalInfo("laborator", "16-18", "L306", "931"));

        //operationsDurationMap.put(new Tuple3<>(2, 4,1), new AdditionalInfo("curs", "16-18", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(2, 4,2), new AdditionalInfo("seminar", "14-16", "Pi", "934"));
        operationsDurationMap.put(new Tuple3<>(2, 4,3), new AdditionalInfo("laborator", "8-10", "L305", "934"));

        //operationsDurationMap.put(new Tuple3<>(3, 4,1), new AdditionalInfo("curs", "10-12", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 4,2), new AdditionalInfo("seminar", "12-14", "Gamma", "936"));
        operationsDurationMap.put(new Tuple3<>(3, 4,3), new AdditionalInfo("laborator", "8-10", "L304", "936"));

        operationsDurationMap.put(new Tuple3<>(4, 4,1), new AdditionalInfo("curs", "10-12", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 4,2), new AdditionalInfo("seminar", "16-18", "C310", "936"));
        operationsDurationMap.put(new Tuple3<>(4, 4,3), new AdditionalInfo("laborator", "12-14", "L307", "932"));

        //operationsDurationMap.put(new Tuple3<>(5, 4,1), new AdditionalInfo("curs", "12-14", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(5, 4,2), new AdditionalInfo("seminar", "10-12", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(5, 4,3), new AdditionalInfo("laborator", "8-10", "L303", "933"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob5() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        //operationsDurationMap.put(new Tuple3<>(1, 5,1), new AdditionalInfo("curs", "10-12", "C310", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 5,2), new AdditionalInfo("seminar", "8-10", "6/II", "933"));
        operationsDurationMap.put(new Tuple3<>(1, 5,3), new AdditionalInfo("laborator", "8-10", "L311", "935"));

        //operationsDurationMap.put(new Tuple3<>(2, 5,1), new AdditionalInfo("curs", "14-16", "C310", "IE2"));
        operationsDurationMap.put(new Tuple3<>(2, 5,2), new AdditionalInfo("seminar", "10-12", "C512", "936"));
        operationsDurationMap.put(new Tuple3<>(2, 5,3), new AdditionalInfo("laborator", "12-14", "L402", "937"));

        //operationsDurationMap.put(new Tuple3<>(3, 5,1), new AdditionalInfo("curs", "14-16", "C310", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 5,2), new AdditionalInfo("seminar", "8-10", "C514", "931"));
        operationsDurationMap.put(new Tuple3<>(3, 5,3), new AdditionalInfo("laborator", "12-14", "L313", "934"));

        operationsDurationMap.put(new Tuple3<>(4, 5,1), new AdditionalInfo("curs", "18-20", "6/II", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 5,2), new AdditionalInfo("seminar", "12-14", "C512", "934"));
        operationsDurationMap.put(new Tuple3<>(4, 5,3), new AdditionalInfo("laborator", "10-12", "L308", "933"));

        //operationsDurationMap.put(new Tuple3<>(5, 5,1), new AdditionalInfo("curs", "8-10", "C310", "IE2"));
        operationsDurationMap.put(new Tuple3<>(5, 5,2), new AdditionalInfo("seminar", "16-18", "6/II", "934"));
        operationsDurationMap.put(new Tuple3<>(5, 5,3), new AdditionalInfo("laborator", "8-10", "L303", "937"));

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getOperationsDurationMapForJob6() {
        Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMap = new HashMap<>();

        //operationsDurationMap.put(new Tuple3<>(1, 6,1), new AdditionalInfo("curs", "8-10", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(1, 6,1), new AdditionalInfo("seminar", "14-16", "Gamma", "932"));
        //operationsDurationMap.put(new Tuple3<>(1, 6,3), new AdditionalInfo("laborator", "16-18", "2/I", "934"));

        //operationsDurationMap.put(new Tuple3<>(2, 6,1), new AdditionalInfo("curs", "16-18", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(2, 6,1), new AdditionalInfo("seminar", "8-10", "Pi", "937"));
        //operationsDurationMap.put(new Tuple3<>(2, 6,3), new AdditionalInfo("laborator", "10-12", "2/I", "935"));

        //operationsDurationMap.put(new Tuple3<>(3, 6,1), new AdditionalInfo("curs", "16-18", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(3, 6,1), new AdditionalInfo("seminar", "18-20", "Gamma", "935"));
        //operationsDurationMap.put(new Tuple3<>(3, 6,3), new AdditionalInfo("laborator", "16-18", "Gamma", "931"));

        //operationsDurationMap.put(new Tuple3<>(4, 6,1), new AdditionalInfo("curs", "10-12", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(4, 6,1), new AdditionalInfo("seminar", "14-16", "Pi", "932"));
        //operationsDurationMap.put(new Tuple3<>(4, 6,3), new AdditionalInfo("laborator", "8-10", "L303", "934"));

        //operationsDurationMap.put(new Tuple3<>(5, 6,1), new AdditionalInfo("curs", "14-16", "2/I", "IE2"));
        operationsDurationMap.put(new Tuple3<>(5, 6,1), new AdditionalInfo("seminar", "12-14", "Gamma", "936"));
        //operationsDurationMap.put(new Tuple3<>(5, 6,3), new AdditionalInfo("laborator", "10-12", "Pi", "935"));

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
            return 1; // English - only seminary

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

        //FJSSPSolver solver = new FJSSPSolver(jobCount, machineCount, jobs);
        TimetableSolver solver = new TimetableSolver(jobCount, machineCount, jobs);
        solver.solve();

        return solver.getSequences();
    }
}
