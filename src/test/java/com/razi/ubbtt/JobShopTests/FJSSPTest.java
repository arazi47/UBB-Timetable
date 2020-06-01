package com.razi.ubbtt.JobShopTests;

import com.razi.ubbtt.Utils.Tuple3;
import com.razi.ubbtt.JobShop.FJSSPSolver;
import com.razi.ubbtt.JobShop.Job;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FJSSPTest {
    private int jobCount = 8;
    private int machineCount = 10;
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
        operationsDurationMap.put(new Tuple3<>(4, 1, 4), 7);

        operationsDurationMap.put(new Tuple3<>(5, 1, 1), 10);
        operationsDurationMap.put(new Tuple3<>(5, 1, 3), 10);

        operationsDurationMap.put(new Tuple3<>(6, 1, 1), 3);
        operationsDurationMap.put(new Tuple3<>(6, 1, 2), 9);
        operationsDurationMap.put(new Tuple3<>(6, 1, 3), 9);
        operationsDurationMap.put(new Tuple3<>(6, 1, 4), 5);

        operationsDurationMap.put(new Tuple3<>(8, 1, 1), 8);
        operationsDurationMap.put(new Tuple3<>(8, 1, 4), 8);

        operationsDurationMap.put(new Tuple3<>(9, 1, 3), 4);

        operationsDurationMap.put(new Tuple3<>(10, 1, 1), 5);
        operationsDurationMap.put(new Tuple3<>(10, 1, 2), 11);

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

        operationsDurationMap.put(new Tuple3<>(6, 2, 2), 7);
        operationsDurationMap.put(new Tuple3<>(6, 2, 3), 8);

        operationsDurationMap.put(new Tuple3<>(7, 2, 3), 7);

        operationsDurationMap.put(new Tuple3<>(8, 2, 1), 8);
        operationsDurationMap.put(new Tuple3<>(8, 2, 2), 6);

        operationsDurationMap.put(new Tuple3<>(10, 2, 3), 4);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob3() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(2, 3, 3), 9);

        operationsDurationMap.put(new Tuple3<>(3, 3, 1), 9);
        operationsDurationMap.put(new Tuple3<>(3, 3, 2), 6);
        operationsDurationMap.put(new Tuple3<>(3, 3, 4), 4);

        operationsDurationMap.put(new Tuple3<>(5, 3, 1), 6);
        operationsDurationMap.put(new Tuple3<>(5, 3, 2), 8);
        operationsDurationMap.put(new Tuple3<>(5, 3, 4), 3);

        operationsDurationMap.put(new Tuple3<>(6, 3, 3), 5);
        operationsDurationMap.put(new Tuple3<>(6, 3, 4), 6);

        operationsDurationMap.put(new Tuple3<>(7, 3, 1), 4);

        operationsDurationMap.put(new Tuple3<>(8, 3, 2), 7);
        operationsDurationMap.put(new Tuple3<>(8, 3, 3), 6);

        operationsDurationMap.put(new Tuple3<>(9, 3, 3), 4);

        operationsDurationMap.put(new Tuple3<>(10, 3, 1), 5);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob4() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 4, 3), 5);

        operationsDurationMap.put(new Tuple3<>(2, 4, 1), 8);
        operationsDurationMap.put(new Tuple3<>(2, 4, 4), 3);

        operationsDurationMap.put(new Tuple3<>(3, 4, 2), 6);
        operationsDurationMap.put(new Tuple3<>(3, 4, 5), 2);

        operationsDurationMap.put(new Tuple3<>(4, 4, 1), 5);
        operationsDurationMap.put(new Tuple3<>(4, 4, 3), 8);

        operationsDurationMap.put(new Tuple3<>(5, 4, 4), 11);
        operationsDurationMap.put(new Tuple3<>(5, 4, 5), 5);

        operationsDurationMap.put(new Tuple3<>(6, 4, 4), 6);
        operationsDurationMap.put(new Tuple3<>(6, 4, 5), 8);

        operationsDurationMap.put(new Tuple3<>(7, 4, 1), 2);
        operationsDurationMap.put(new Tuple3<>(7, 4, 2), 8);
        operationsDurationMap.put(new Tuple3<>(7, 4, 3), 4);

        operationsDurationMap.put(new Tuple3<>(8, 4, 1), 4);
        operationsDurationMap.put(new Tuple3<>(8, 4, 4), 8);

        operationsDurationMap.put(new Tuple3<>(9, 4, 2), 5);
        operationsDurationMap.put(new Tuple3<>(9, 4, 5), 8);

        operationsDurationMap.put(new Tuple3<>(10, 4, 3), 9);
        operationsDurationMap.put(new Tuple3<>(10, 4, 4), 10);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob5() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 5, 1), 6);
        operationsDurationMap.put(new Tuple3<>(1, 5, 2), 3);

        operationsDurationMap.put(new Tuple3<>(2, 5, 4), 7);

        operationsDurationMap.put(new Tuple3<>(3, 5, 3), 9);

        operationsDurationMap.put(new Tuple3<>(4, 5, 1), 4);
        operationsDurationMap.put(new Tuple3<>(4, 5, 2), 7);

        operationsDurationMap.put(new Tuple3<>(5, 5, 4), 6);

        operationsDurationMap.put(new Tuple3<>(6, 5, 1), 3);
        operationsDurationMap.put(new Tuple3<>(6, 5, 2), 6);

        operationsDurationMap.put(new Tuple3<>(7, 5, 3), 4);

        operationsDurationMap.put(new Tuple3<>(8, 5, 2), 10);
        operationsDurationMap.put(new Tuple3<>(8, 5, 4), 7);

        operationsDurationMap.put(new Tuple3<>(9, 5, 1), 8);

        operationsDurationMap.put(new Tuple3<>(10, 5, 2), 8); // Or 9?
        operationsDurationMap.put(new Tuple3<>(10, 5, 3), 11);
        operationsDurationMap.put(new Tuple3<>(10, 5, 4), 9);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob6() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 6, 4), 10);

        operationsDurationMap.put(new Tuple3<>(2, 6, 3), 3);

        operationsDurationMap.put(new Tuple3<>(3, 6, 5), 7);

        operationsDurationMap.put(new Tuple3<>(4, 6, 2), 3);
        operationsDurationMap.put(new Tuple3<>(4, 6, 4), 6);

        operationsDurationMap.put(new Tuple3<>(5, 6, 1), 10);
        operationsDurationMap.put(new Tuple3<>(5, 6, 3), 10);
        operationsDurationMap.put(new Tuple3<>(5, 6, 5), 8);

        operationsDurationMap.put(new Tuple3<>(6, 6, 1), 8);
        operationsDurationMap.put(new Tuple3<>(6, 6, 4), 10);
        operationsDurationMap.put(new Tuple3<>(6, 6, 5), 9);

        operationsDurationMap.put(new Tuple3<>(7, 6, 2), 2);

        operationsDurationMap.put(new Tuple3<>(8, 6, 1), 5);
        operationsDurationMap.put(new Tuple3<>(8, 6, 2), 7);
        operationsDurationMap.put(new Tuple3<>(8, 6, 5), 10);

        operationsDurationMap.put(new Tuple3<>(9, 6, 3), 6);
        operationsDurationMap.put(new Tuple3<>(9, 6, 4), 9);

        operationsDurationMap.put(new Tuple3<>(10, 6, 2), 3);
        operationsDurationMap.put(new Tuple3<>(10, 6, 4), 8);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob7() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 7, 1), 9);
        operationsDurationMap.put(new Tuple3<>(1, 7, 2), 2);
        operationsDurationMap.put(new Tuple3<>(1, 7, 4), 9);

        operationsDurationMap.put(new Tuple3<>(3, 7, 1), 5);
        operationsDurationMap.put(new Tuple3<>(3, 7, 2), 6);
        operationsDurationMap.put(new Tuple3<>(3, 7, 4), 15);

        operationsDurationMap.put(new Tuple3<>(4, 7, 3), 5);
        operationsDurationMap.put(new Tuple3<>(4, 7, 4), 5);

        operationsDurationMap.put(new Tuple3<>(5, 7, 1), 7);
        operationsDurationMap.put(new Tuple3<>(5, 7, 2), 8);
        operationsDurationMap.put(new Tuple3<>(5, 7, 3), 6);
        operationsDurationMap.put(new Tuple3<>(5, 7, 4), 3);

        operationsDurationMap.put(new Tuple3<>(6, 7, 1), 9);
        operationsDurationMap.put(new Tuple3<>(6, 7, 3), 2);

        operationsDurationMap.put(new Tuple3<>(7, 7, 2), 3);

        operationsDurationMap.put(new Tuple3<>(8, 7, 2), 7);
        operationsDurationMap.put(new Tuple3<>(8, 7,3), 9);
        operationsDurationMap.put(new Tuple3<>(8, 7, 4), 6);

        operationsDurationMap.put(new Tuple3<>(9, 7, 3), 3);

        operationsDurationMap.put(new Tuple3<>(10, 7, 1), 8);
        operationsDurationMap.put(new Tuple3<>(10, 7, 4), 5);

        return operationsDurationMap;
    }

    private Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMapForJob8() {
        Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

        operationsDurationMap.put(new Tuple3<>(1, 8,2), 4);
        operationsDurationMap.put(new Tuple3<>(1, 8,4), 2);
        operationsDurationMap.put(new Tuple3<>(1, 8,5), 3);

        operationsDurationMap.put(new Tuple3<>(2, 8,1), 3);
        operationsDurationMap.put(new Tuple3<>(2, 8,3), 5);

        operationsDurationMap.put(new Tuple3<>(3, 8,2), 6);
        operationsDurationMap.put(new Tuple3<>(3, 8,3), 8);
        operationsDurationMap.put(new Tuple3<>(3, 8,4), 6);

        operationsDurationMap.put(new Tuple3<>(4, 8,1), 6);
        operationsDurationMap.put(new Tuple3<>(4, 8,2), 3);
        operationsDurationMap.put(new Tuple3<>(4, 8,5), 5);

        operationsDurationMap.put(new Tuple3<>(6, 8,1), 8);
        operationsDurationMap.put(new Tuple3<>(6, 8,3), 6);
        operationsDurationMap.put(new Tuple3<>(6, 8,5), 3);

        operationsDurationMap.put(new Tuple3<>(7, 8,1), 5);
        operationsDurationMap.put(new Tuple3<>(7, 8,4), 6);

        operationsDurationMap.put(new Tuple3<>(8, 8,2), 8);
        operationsDurationMap.put(new Tuple3<>(8, 8,5), 5);

        operationsDurationMap.put(new Tuple3<>(9, 8,2), 10);
        operationsDurationMap.put(new Tuple3<>(9, 8,3), 5);
        operationsDurationMap.put(new Tuple3<>(9, 8,4), 3);
        operationsDurationMap.put(new Tuple3<>(9, 8,5), 9);

        operationsDurationMap.put(new Tuple3<>(10, 8,4), 8);
        operationsDurationMap.put(new Tuple3<>(10, 8,5), 8);

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
            case 7:
                return getOperationsDurationMapForJob7();
            case 8:
                return getOperationsDurationMapForJob8();

            default:
                fail();
                return null;
        }
    }

    private void generateJobs(Set<Job> jobs) {
        for (int j = 0; j < this.jobCount; ++j) {
            Job job = new Job(j + 1, operationCountForJob.get(j), getOperationsDurationMapForJob(j + 1));
            jobs.add(job);
        }
    }

    @Test
    public void ACOExampleTest() {
        Set<Job> jobs = new HashSet<>();
        generateJobs(jobs);

        assertEquals(jobs.size(), jobCount);

        FJSSPSolver solver = new FJSSPSolver(jobCount, machineCount, jobs);
        solver.solve();

        System.out.println("Makespan = " + solver.getMakespan());

        solver.printSequences();

        // TODO assert the sequence
    }
}
