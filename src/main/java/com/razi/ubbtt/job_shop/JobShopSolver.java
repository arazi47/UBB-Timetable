package com.razi.ubbtt.job_shop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class JobShopSolver {
    int jobCount;
    Set<JSSP_Job> jobs;
    int machineCount;

    // For all the jobs
    Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMapAll = new HashMap<>();
    Map<Integer, List<Tuple3<Integer, Integer, Integer>>> sequences = new HashMap<>();

    /**
     * Map of operation sequences
     * Key is machine index
     * Value is list of operations to be executed on that machine
     */
    public Map<Integer, List<Integer>> sequence;

    public JobShopSolver(int jobCount, int machineCount, Set<JSSP_Job> jobs) {
        this.jobCount = jobCount;
        this.machineCount = machineCount;
        this.jobs = jobs;
    }

    public Set<JSSP_Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JSSP_Job> jobs) {
        this.jobs = jobs;
    }

    public Map<Integer, List<Integer>> getSequence() {
        return sequence;
    }

    public void setSequence(Map<Integer, List<Integer>> sequence) {
        this.sequence = sequence;
    }

    public abstract void solve();
    public abstract int getMakespan();
}
