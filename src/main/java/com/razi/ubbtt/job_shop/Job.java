package com.razi.ubbtt.job_shop;

import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public class Job {
    private int name;
    private int length;

    private int operations;

    /**
     * Key is job number
     * Value is list of pair of operation number and processing time on machine at the index in list
     * -1 for the second element of the pair means that machine cannot process this operation
     */
    private Map<Integer, List<Pair<Integer, Integer>>> jobOperationTimeMap;

    public Job(int name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void setOperations(int operations) {
        this.operations = operations;
    }

    public int getOperations() {
        return this.operations;
    }

    public Map<Integer, List<Pair<Integer, Integer>>> getJobOperationTimeMap() {
        return jobOperationTimeMap;
    }

    public void setJobOperationTimeMap(Map<Integer, List<Pair<Integer, Integer>>> jobOperationTimeMap) {
        this.jobOperationTimeMap = jobOperationTimeMap;
    }
}
