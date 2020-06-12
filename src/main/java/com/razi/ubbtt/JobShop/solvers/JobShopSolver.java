package com.razi.ubbtt.JobShop.solvers;

import com.razi.ubbtt.JobShop.jobs.Job;
import com.razi.ubbtt.Utils.Tuple3;

import java.util.*;

public abstract class JobShopSolver {
    int jobCount;
    List<Job> jobs;
    int machineCount;

    // For all the jobs
    Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMapAll = new HashMap<>();
    Map<Integer, List<Tuple3<Integer, Integer, Integer>>> sequences = new HashMap<>();

    // Contains the makespans for each machine
    List<Integer> makespans = new ArrayList<>();

    public JobShopSolver(int jobCount, int machineCount, List<Job> jobs) {
        this.jobCount = jobCount;
        this.machineCount = machineCount;
        this.jobs = jobs;
    }

    public Map<Integer, List<Tuple3<Integer, Integer, Integer>>> getSequences() {
        return sequences;
    }

    public abstract void solve();
    public abstract int getMakespan();
}
