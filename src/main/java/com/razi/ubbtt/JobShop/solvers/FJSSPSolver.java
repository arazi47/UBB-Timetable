package com.razi.ubbtt.JobShop.solvers;

import com.razi.ubbtt.JobShop.jobs.Job;
import com.razi.ubbtt.Utils.Tuple3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FJSSPSolver extends JobShopSolver {
    private List<Integer> jobOperations;
    public FJSSPSolver(int jobCount, int machineCount, List<Job> jobs) {
        super(jobCount, machineCount, jobs);

        combineOperationMaps();
        initializeSequences();
        initializeMakespans();
        initializeJobOperations();
    }

    /**
     * Generate the operation sequences for the given jobs and machines
     */
    @Override
    public void solve() {
        int j = 0;
        while (jobOperationsStillNeedToBeProcessed()) {
            Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = getBestMachineForJobOperation(j + 1, jobOperations.get(j));
            if (bestEntry != null) {
                removeEntriesForJobAndOperation(j + 1, jobOperations.get(j));

                List<Tuple3<Integer, Integer, Integer>> sequenceList = sequences.get(bestEntry.getKey().first());
                sequenceList.add(new Tuple3<>(bestEntry.getKey().second(), bestEntry.getKey().third(), bestEntry.getValue()));

                // If it's the first operation, no previous operation was executed
                if (bestEntry.getKey().third() == 1 || makespans.get(getMachineThatCompletedJobOperationBefore(bestEntry.getKey().second(), bestEntry.getKey().third()) - 1) < makespans.get(bestEntry.getKey().first() - 1))
                    makespans.set(bestEntry.getKey().first() - 1, makespans.get(bestEntry.getKey().first() - 1) + bestEntry.getValue());
                else
                    makespans.set(bestEntry.getKey().first() - 1, makespans.get(getMachineThatCompletedJobOperationBefore(bestEntry.getKey().second(), bestEntry.getKey().third()) - 1) + bestEntry.getValue());

                jobOperations.set(j, jobOperations.get(j) + 1);
            }

            // Start again from the first job
            if (j == jobOperations.size() - 1)
                j = 0;
            else
                ++j;
        }
    }

    /**
     *
     * @return true, if there are unprocessed job operations
     *         false, otherwise
     */
     public boolean jobOperationsStillNeedToBeProcessed() {
        for (int j = 0; j < jobOperations.size(); ++j) {
            if (jobOperations.get(j) <= jobs.get(j).getOperations())
                return true;
        }

        return false;
    }

    /**
     * @param jobId ID of the job
     * @param operationId ID of the job operation
     * @return returns the machine ID which completed operation operationId - 1 for jobId
     */
    public int getMachineThatCompletedJobOperationBefore(int jobId, int operationId) {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry: sequences.entrySet()) {
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                if (tuple.first() == jobId && tuple.second() == operationId - 1)
                    return entry.getKey();
            }
        }

        return 0;
    }

    /**
     * Initialize all the job sequences as empty ArrayLists
     */
    private void initializeSequences() {
        for (int m = 1; m <= machineCount; ++m) {
            sequences.put(m, new ArrayList<>());
        }
    }

    /**
     * Combine all the jobs' maps
     */
    private void combineOperationMaps() {
        for (Job job : jobs) {
            operationsDurationMapAll.putAll(job.getOperationsDurationMap());
        }
    }

    /**
     * Initialize each machine's makespan to 0
     */
    private void initializeMakespans() {
        for (int i = 0; i < machineCount; ++i)
            makespans.add(0);
    }

    /**
     * Initialize the job operation for each job to the first one
     */
    private void initializeJobOperations() {
        jobOperations = new ArrayList<>(Collections.nCopies(jobCount, 1));
    }

    /**
     *
     * @param jobId ranging from 1 to jobCount
     * @param operationId ranging from 1 to each job's number of operations
     * @return the entry from operationDurationMapAll which takes the least time to complete
     */
    public Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> getBestMachineForJobOperation(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue() < bestEntry.getValue()
                && makespans.get(entry.getKey().first() - 1) != getMakespan())
                    bestEntry = entry;

                if (Math.random() < 0.20) {
                    bestEntry = lowestUsedMachineThatCanRun(jobId, operationId);
                }
            }
        }

        return bestEntry;
    }

    /**
     *
     * @param jobId job id that contains operation id which needs to be executed
     * @param operationId operation id that is contained by job id
     * @return machine that contains the least number of operations that can also run operationId of jobId
     */
    public Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> lowestUsedMachineThatCanRun(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                if (bestEntry == null)
                    bestEntry = entry;
                if (sequences.get(entry.getKey().first()).size() < sequences.get(bestEntry.getKey().first()).size())
                    bestEntry = entry;
            }
        }

        return bestEntry;
    }

    /**
     * Remove from operationsDurationMapAll the machines that can executed jobId and operationId,
     * because we already chose the best one
     * @param jobId  ranging from 1 to jobCount
     * @param operationId ranging from 1 to the number of operations there are for the specified jobId
     */
    void removeEntriesForJobAndOperation(int jobId, int operationId) {
        operationsDurationMapAll.entrySet().removeIf(e -> e.getKey().second() == jobId && e.getKey().third() == operationId);
    }

    /**
     * @return the makespan of the solved sequence
     */
    @Override
    public int getMakespan() {
        return Collections.max(makespans);
    }

    /**
     * For debugging purposes
     */
    public void printSequences() {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry : sequences.entrySet()) {
            System.out.println("=============== MACHINE " + entry.getKey() + " ===============");
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                System.out.println("Job = " + tuple.first() + "; Operation = " + tuple.second() + "; Duration = " + tuple.third());
            }

            System.out.println("Makespan for this machine: " + makespans.get(entry.getKey() - 1));
            System.out.println("=======================================");
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }
}
