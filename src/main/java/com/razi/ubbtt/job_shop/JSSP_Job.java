package com.razi.ubbtt.job_shop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.HashMap;
import java.util.Map;

public class JSSP_Job {
    private int index;
    private int operations;

    // key = pair <machineId, jobId, operationId>
    // value = duration
    Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap = new HashMap<>();

    public JSSP_Job(int index, int operations, Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap) {
        this.index = index;
        this.operations = operations;
        this.operationsDurationMap = operationsDurationMap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOperations() {
        return operations;
    }

    public void setOperations(int operations) {
        this.operations = operations;
    }

    public Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMap() {
        return operationsDurationMap;
    }

    public void setOperationsDurationMap(Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap) {
        this.operationsDurationMap = operationsDurationMap;
    }
}
