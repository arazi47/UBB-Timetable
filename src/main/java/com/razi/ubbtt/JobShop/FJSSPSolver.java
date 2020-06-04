package com.razi.ubbtt.JobShop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.*;

public class FJSSPSolver extends JobShopSolver {
    public FJSSPSolver(int jobCount, int machineCount, Set<Job> jobs) {
        super(jobCount, machineCount, jobs);
    }

    /**
     * Generate the operation sequences for the given jobs and machines
     */

    @Override
    public void solve() {
        initializeSequences();
        combineOperationMaps();
        initializeMakespans();

        for (Job job: jobs) {
            for (int o = 1; o <= job.getOperations(); ++o) {
                Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = getBestMachineForJobOperation(job.getIndex(), o);
                if (bestEntry != null) {
                    removeEntriesForJobAndOperation(job.getIndex(), o);

                    List<Tuple3<Integer, Integer, Integer>> sequenceList = sequences.get(bestEntry.getKey().first());
                    sequenceList.add(new Tuple3<>(bestEntry.getKey().second(), bestEntry.getKey().third(), bestEntry.getValue()));

                    // If it's the first operation, no previous operation was executed
                    if (bestEntry.getKey().third() == 1 || makespans.get(getMachineThatCompletedJobOperationBefore(bestEntry.getKey().second(), bestEntry.getKey().third()) - 1) < makespans.get(bestEntry.getKey().first() - 1))
                        makespans.set(bestEntry.getKey().first() - 1, makespans.get(bestEntry.getKey().first() - 1) + bestEntry.getValue());
                    else
                        makespans.set(bestEntry.getKey().first() - 1, makespans.get(getMachineThatCompletedJobOperationBefore(bestEntry.getKey().second(), bestEntry.getKey().third()) - 1) + bestEntry.getValue());
                }
            }
        }
    }

    int getMachineThatCompletedJobOperationBefore(int jobId, int operationId) {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry: sequences.entrySet()) {
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                if (tuple.first() == jobId && tuple.second() == operationId - 1)
                    return entry.getKey();
            }
        }

        System.out.println("WTF");
        return 0;
    }

    /**
     * Initialize all the job sequences as empty ArrayLists
     */
    public void initializeSequences() {
        for (int m = 1; m <= machineCount; ++m) {
            sequences.put(m, new ArrayList<>());
        }
    }

    /**
     * Combine all the jobs' maps
     */
    public void combineOperationMaps() {
        for (Job job : jobs) {
            operationsDurationMapAll.putAll(job.getOperationsDurationMap());
        }
    }

    /**
     * Initialize each machine's makespan to 0
     */
    public void initializeMakespans() {
        for (int i = 0; i < machineCount; ++i)
            makespans.add(0);
    }

    /**
     *
     * @param jobId ranging from 1 to jobCount
     * @param operationId ranging from 1 to each job's number of operations
     * @return the entry from operationDurationMapAll which takes the least time to complete
     */
    private Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> getBestMachineForJobOperation(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue() < bestEntry.getValue())
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
    private void removeEntriesForJobAndOperation(int jobId, int operationId) {
        operationsDurationMapAll.entrySet().removeIf(e -> e.getKey().second() == jobId && e.getKey().third() == operationId);
    }

    /**
     *
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
