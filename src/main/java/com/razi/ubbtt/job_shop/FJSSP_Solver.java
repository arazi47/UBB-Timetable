package com.razi.ubbtt.job_shop;

import com.razi.ubbtt.Utils.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FJSSP_Solver extends JobShopSolver {
    public FJSSP_Solver(int jobCount, int machineCount, Set<JSSP_Job> jobs) {
        super(jobCount, machineCount, jobs);
    }

    @Override
    public void solve() {
        // Initialize all the job sequences as empty ArrayLists
        for (int m = 1; m <= machineCount; ++m) {
            sequences.put(m, new ArrayList<>());
        }

        // Combine all the jobs' maps
        for (JSSP_Job job : jobs) {
            operationsDurationMapAll.putAll(job.getOperationsDurationMap());
        }

        int machine = 1;
        while (!operationsDurationMapAll.isEmpty()) {
            Tuple3<Integer, Integer, Integer> bestOperation = getBestJobOperationForMachineAndRemoveAssociatedEntries(machine);
            // Maybe there are no more operations supported by this machine
            if (bestOperation != null) {

                List<Tuple3<Integer, Integer, Integer>> sequenceList = sequences.get(machine);
                sequenceList.add(bestOperation);
            }
            ++machine;
            if (machine == machineCount)
                machine = 1;
        }
    }

    private Tuple3<Integer, Integer, Integer> getBestJobOperationForMachineAndRemoveAssociatedEntries(int machine) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, Integer> entry : operationsDurationMapAll.entrySet()) {
            // first = machine id
            if (entry.getKey().first() == machine) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue() < bestEntry.getValue())
                    bestEntry = entry;
            }
        }

        if (bestEntry == null)
            return null;

        // Remove all entries for job id, operation id for other machines, we do not need them anymore
        removeEntriesForJobAndOperation(bestEntry.getKey().second(), bestEntry.getKey().third());

        // <jobId, operationId, duration>
        return new Tuple3<>(bestEntry.getKey().second(), bestEntry.getKey().third(), bestEntry.getValue());
    }

    private void removeEntriesForJobAndOperation(int job, int operation) {
        operationsDurationMapAll.entrySet().removeIf(e -> e.getKey().second() == job && e.getKey().third() == operation);
    }

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
