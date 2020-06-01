package com.razi.ubbtt.JobShop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class JobShopSolver {
    int jobCount;
    Set<Job> jobs;
    int machineCount;

    // For all the jobs
    Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMapAll = new HashMap<>();
    Map<Integer, List<Tuple3<Integer, Integer, Integer>>> sequences = new HashMap<>();

    public JobShopSolver(int jobCount, int machineCount, Set<Job> jobs) {
        this.jobCount = jobCount;
        this.machineCount = machineCount;
        this.jobs = jobs;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Map<Integer, List<Tuple3<Integer, Integer, Integer>>> getSequences() {
        return sequences;
    }

    public abstract void solve();
    public abstract int getMakespan();
}
