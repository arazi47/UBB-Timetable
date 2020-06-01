package com.razi.ubbtt.Utils;

import com.razi.ubbtt.JobShop.FJSSPSolver;
import com.razi.ubbtt.JobShop.Job;

import java.util.*;

public class MockupTimetable {
    private int jobCount = 6;
    private int machineCount = 5;
    private List<Integer> operationCountForJob = Arrays.asList(4, 3, 4, 5, 4, 5, 4, 5);

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob1() {
        // key = pair <machineId, jobId, operationId>
        // value = duration
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 1, 1), 2);
        operationsDurationMap.put(new Tuple3<>(1, 1,2), 5);

        operationsDurationMap.put(new Tuple3<>(2, 1, 2), 7);
        operationsDurationMap.put(new Tuple3<>(2, 1, 3), 6);

        operationsDurationMap.put(new Tuple3<>(3, 1, 1), 4);
        operationsDurationMap.put(new Tuple3<>(3, 1, 3), 5);

        operationsDurationMap.put(new Tuple3<>(4, 1, 2), 8);
        //operationsDurationMap.put(new Tuple3<>(4, 1, 4), 7);

        operationsDurationMap.put(new Tuple3<>(5, 1, 1), 10);
        operationsDurationMap.put(new Tuple3<>(5, 1, 3), 10);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob2() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 2, 1), 10);
        operationsDurationMap.put(new Tuple3<>(1, 2, 2), 9);

        operationsDurationMap.put(new Tuple3<>(2, 2, 3), 6);

        operationsDurationMap.put(new Tuple3<>(3, 2, 1), 4);
        operationsDurationMap.put(new Tuple3<>(3, 2, 2), 8);

        operationsDurationMap.put(new Tuple3<>(4, 2, 3), 5);

        operationsDurationMap.put(new Tuple3<>(5, 2, 1), 10);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob3() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(2, 3, 3), 9);

        operationsDurationMap.put(new Tuple3<>(3, 3, 1), 9);
        operationsDurationMap.put(new Tuple3<>(3, 3, 2), 6);
        //operationsDurationMap.put(new Tuple3<>(3, 3, 4), 4);

        operationsDurationMap.put(new Tuple3<>(5, 3, 1), 6);
        operationsDurationMap.put(new Tuple3<>(5, 3, 2), 8);
        //operationsDurationMap.put(new Tuple3<>(5, 3, 4), 3);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob4() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 4, 3), 5);

        operationsDurationMap.put(new Tuple3<>(2, 4, 1), 8);
        //operationsDurationMap.put(new Tuple3<>(2, 4, 4), 3);

        operationsDurationMap.put(new Tuple3<>(3, 4, 2), 6);
        //operationsDurationMap.put(new Tuple3<>(3, 4, 5), 2);

        operationsDurationMap.put(new Tuple3<>(4, 4, 1), 5);
        operationsDurationMap.put(new Tuple3<>(4, 4, 3), 8);

        //operationsDurationMap.put(new Tuple3<>(5, 4, 4), 11);
        //operationsDurationMap.put(new Tuple3<>(5, 4, 5), 5);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob5() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 5, 1), 6);
        operationsDurationMap.put(new Tuple3<>(1, 5, 2), 3);

        //operationsDurationMap.put(new Tuple3<>(2, 5, 4), 7);

        operationsDurationMap.put(new Tuple3<>(3, 5, 3), 9);

        operationsDurationMap.put(new Tuple3<>(4, 5, 1), 4);
        operationsDurationMap.put(new Tuple3<>(4, 5, 2), 7);

        //operationsDurationMap.put(new Tuple3<>(5, 5, 4), 6);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob6() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        //operationsDurationMap.put(new Tuple3<>(1, 6, 4), 10);

        operationsDurationMap.put(new Tuple3<>(2, 6, 3), 3);

        //operationsDurationMap.put(new Tuple3<>(3, 6, 5), 7);

        operationsDurationMap.put(new Tuple3<>(4, 6, 2), 3);
        //operationsDurationMap.put(new Tuple3<>(4, 6, 4), 6);

        //operationsDurationMap.put(new Tuple3<>(5, 6, 1), 10);
        operationsDurationMap.put(new Tuple3<>(5, 6, 3), 10);
        //operationsDurationMap.put(new Tuple3<>(5, 6, 5), 8);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob(int jobNumber) {
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

    private void generateJobs(Set<Job> jobs) {
        for (int j = 0; j < this.jobCount; ++j) {
            Job job = new Job(j + 1, operationCountForJob.get(j), getOperationsDurationMapForJob(j + 1));
            jobs.add(job);
        }
    }

    public Map<Integer, List<Tuple3<Integer, Integer, Integer>>> initializeMockupTimetable() {
        Set<Job> jobs = new HashSet<>();
        generateJobs(jobs);

        FJSSPSolver solver = new FJSSPSolver(jobCount, machineCount, jobs);
        solver.solve();

        return solver.getSequences();
    }
}
