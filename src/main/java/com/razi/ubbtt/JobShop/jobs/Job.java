package com.razi.ubbtt.JobShop.jobs;

import com.razi.ubbtt.JobShop.utils.AdditionalInfo;
import com.razi.ubbtt.Utils.Tuple3;

import java.util.HashMap;
import java.util.Map;

public class Job {
    private int index;
    private int operations;

    // key = pair <machineId, jobId, operationId>
    // value = duration
    Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap;

    public Job(int index, int operations, Map<Tuple3<Integer, Integer, Integer>, Integer> operationsDurationMap) {
        this.index = index;
        this.operations = operations;
        this.operationsDurationMap = operationsDurationMap;
    }

    public int getOperations() {
        return operations;
    }

    public Map<Tuple3<Integer, Integer, Integer>, Integer> getOperationsDurationMap() {
        return operationsDurationMap;
    }
}
