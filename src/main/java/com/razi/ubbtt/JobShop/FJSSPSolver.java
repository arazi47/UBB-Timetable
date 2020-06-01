package com.razi.ubbtt.JobShop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        int machine = 1;
        while (!operationsDurationMapAll.isEmpty()) {
            Tuple3<Integer, Integer, Integer> bestOperation = getBestJobOperationForMachine(machine);
            // Maybe there are no more operations supported by this machine
            if (bestOperation != null) {
                // Remove all entries for job id, operation id for other machines, we do not need them anymore
                removeEntriesForJobAndOperation(bestOperation.first(), bestOperation.second());

                List<Tuple3<Integer, Integer, Integer>> sequenceList = sequences.get(machine);
                sequenceList.add(bestOperation);
            }
            ++machine;
            if (machine == machineCount)
                machine = 1;
        }
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
     *
     * @param machineId ranging from 1 to machineCount
     * @return a tuple containing the jobId, operationId and the
     * duration of executing that job's operationId on the specified machineId
     */
    private Tuple3<Integer, Integer, Integer> getBestJobOperationForMachine(int machineId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().first() == machineId) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue() < bestEntry.getValue())
                    bestEntry = entry;
            }
        }

        if (bestEntry == null)
            return null;

        // <jobId, operationId, duration>
        return new Tuple3<>(bestEntry.getKey().second(), bestEntry.getKey().third(), bestEntry.getValue());
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
        int totalMakespan = -1;
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry : sequences.entrySet()) {
            int currMakespan = 0;
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                currMakespan += tuple.third();
            }

            if (currMakespan > totalMakespan)
                totalMakespan = currMakespan;
        }

        return totalMakespan;
    }

    /**
     * For debugging purposes
     */
    public void printSequences() {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, Integer>>> entry : sequences.entrySet()) {
            System.out.println("=============== MACHINE " + entry.getKey() + " ===============");
            int makespan = 0;
            for (Tuple3<Integer, Integer, Integer> tuple : entry.getValue()) {
                System.out.println("Job = " + tuple.first() + "; Operation = " + tuple.second() + "; Duration = " + tuple.third());
                makespan += tuple.third();
            }
            System.out.println("Makespan for this machine: " + makespan);
            System.out.println("=======================================");
        }
    }
}
