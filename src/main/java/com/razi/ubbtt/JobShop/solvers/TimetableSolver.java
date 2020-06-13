package com.razi.ubbtt.JobShop.solvers;

import com.razi.ubbtt.JobShop.jobs.ClassJob;
import com.razi.ubbtt.JobShop.utils.AdditionalInfo;
import com.razi.ubbtt.Utils.Tuple3;
import net.bytebuddy.implementation.bytecode.Addition;

import java.util.*;

public class TimetableSolver {
    int jobCount;
    List<ClassJob> jobs;
    int machineCount;

    // For all the jobs
    Map<Tuple3<Integer, Integer, Integer>, AdditionalInfo> operationsDurationMapAll = new HashMap<>();
    Map<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> sequences = new HashMap<>();

    public TimetableSolver(int jobCount, int machineCount, List<ClassJob> jobs) {
        this.jobCount = jobCount;
        this.machineCount = machineCount;
        this.jobs = jobs;

        combineOperationMaps();
        initializeSequences();
    }

    /**
     * Generate the operation sequences for the given jobs and machines.
     * The difference between this and the function from FJSSPSolver is
     * that this one tries to complete the job operations in increasing order
     * on the machines (aka days).
     */
    public void solve() {
        List<Integer> jobOperations = new ArrayList<>(Collections.nCopies(jobCount, 1));

        int j = 0;
        boolean roomHeuristicApplied;
        while (jobOperationsStillNeedToBeProcessed(jobOperations)) {
            roomHeuristicApplied = true;
            Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
            if (jobOperations.get(j) > 1) {
                bestEntry = tryGetBestMachineForJobOperationAfterMachineWithRoomHeuristic(getMachineThatCompletedJobOperationBefore(j + 1, jobOperations.get(j)), j + 1, jobOperations.get(j));

                while (bestEntry != null && !studentIsFreeAtDayAndHour(bestEntry.getKey().first(), bestEntry.getValue().getHours())) {
                    removeEntryFromOperationDurationMap(bestEntry);

                    bestEntry = tryGetBestMachineForJobOperationAfterMachineWithRoomHeuristic(getMachineThatCompletedJobOperationBefore(j + 1, jobOperations.get(j)), j + 1, jobOperations.get(j));
                }
            }

            if (bestEntry == null) {
                roomHeuristicApplied = false;
                bestEntry = getBestMachineForJobOperation(j + 1, jobOperations.get(j));

                while (bestEntry != null && !studentIsFreeAtDayAndHour(bestEntry.getKey().first(), bestEntry.getValue().getHours())) {
                    removeEntryFromOperationDurationMap(bestEntry);

                    bestEntry = getBestMachineForJobOperation(j + 1, jobOperations.get(j));
                }
            }

            if (bestEntry != null) {
                removeEntriesForJobAndOperation(j + 1, jobOperations.get(j));

                List<Tuple3<Integer, Integer, AdditionalInfo>> sequenceList = sequences.get(bestEntry.getKey().first());

                if (sequenceList.size() == 0)
                    bestEntry.getValue().setHours(TimetableSolver.indexToHours(sequenceList.size()));
                else {
                    //if (roomHeuristicApplied)
                        bestEntry.getValue().setHours(TimetableSolver.hoursAfter(sequenceList.get(sequenceList.size() - 1).third().getHours()));
                    //else
                    //    bestEntry.getValue().setHours(TimetableSolver.hoursAfterPlusGapIfPossible(sequenceList.get(sequenceList.size() - 1).third().getHours()));
                }
                sequenceList.add(new Tuple3<>(bestEntry.getKey().second(), bestEntry.getKey().third(), bestEntry.getValue()));

                jobOperations.set(j, jobOperations.get(j) + 1);
            }

            // Start again from the first job
            if (j == jobOperations.size() - 1)
                j = 0;
            else
                ++j;

            //if (jobOperations.get(j) == jobs.get(j).getOperations() + 1)
            //    ++j;
        }

        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> entry : sequences.entrySet()) {
            //for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {

            //}

            for (int i = 0; i < entry.getValue().size() - 1; ++i) {
                if (!isSameBuilding(entry.getValue().get(i).third().getRoom(), entry.getValue().get(i + 1).third().getRoom())) {
                    if (!entry.getValue().get(entry.getValue().size() - 1).third().getHours().equals("18-20")) {
                        advanceHours(entry.getValue(), i + 1);
                    }
                }
            }
        }
    }

    private void advanceHours(List<Tuple3<Integer, Integer, AdditionalInfo>> sequenceList, int fromIndex) {
        for (int i = fromIndex; i < sequenceList.size(); ++i) {
            sequenceList.get(i).third().setHours(hoursAfter(sequenceList.get(i).third().getHours()));
        }
    }

    private static String hoursAfter(String hours) {
        switch (hours) {
            case "8-10":
                return "10-12";
            case "10-12":
                return "12-14";
            case "12-14":
                return "14-16";
            case "14-16":
                return "16-18";
            case "16-18":
                return "18-20";
            case "18-20":
                return "18-20";
            default:
                System.out.println(hours);
                return "ERROR";
        }
    }

    private static String hoursAfterPlusGapIfPossible(String hours) {
        switch (hours) {
            case "8-10":
                return "12-14";
            case "10-12":
                return "14-16";
            case "12-14":
                return "16-18";
            case "14-16":
                return "18-20";
            default:
                return hours;
        }
    }

    private void reset() {
        operationsDurationMapAll.clear();
        combineOperationMaps();
        sequences.clear();
        initializeSequences();
    }

    private static String indexToHours(int i) {
        switch (i) {
            case 0:
                return "8-10";

            case 1:
                return "10-12";

            case 2:
                return "12-14";

            case 3:
                return "14-16";

            case 4:
                return "16-18";

            case 5:
                return "18-20";

            default:
                return "8-10";
        }
    }

    private void removeEntryFromOperationDurationMap(Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry) {
        final int machineId = bestEntry.getKey().first();
        final int jobId = bestEntry.getKey().second();
        final int operationId = bestEntry.getKey().third();
        operationsDurationMapAll.entrySet().removeIf(e -> e.getKey().first() == machineId && e.getKey().second() == jobId && e.getKey().third() == operationId);
    }

    public boolean studentIsFreeAtDayAndHour(int day, String hours) {/*
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> entry : sequences.entrySet()) {
            for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {
                if (entry.getKey() == day && hours.equals(tuple.third().getHours()))
                    return false;
            }
        }*/

        return true;
    }

    public Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getBestMachineForJobOperationWithRoomHeuristic(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> entry : operationsDurationMapAll.entrySet()) {
            if (sequences.get(entry.getKey().first()).size() > 0 && isSameBuilding(entry.getValue().getRoom(), sequences.get(entry.getKey().first()).get(sequences.get(entry.getKey().first()).size() - 1).third().getRoom())) {
                if (entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                    if (bestEntry == null)
                        bestEntry = entry;
                    else if (entry.getValue().getPriority() < bestEntry.getValue().getPriority())
                        bestEntry = entry;
                }
            }
        }

        return bestEntry;
    }

    /**
     *
     * @param jobId ranging from 1 to jobCount
     * @param operationId ranging from 1 to each job's number of operations
     * @return the entry from operationDurationMapAll which takes the least time to complete
     */
    public Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> getBestMachineForJobOperation(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().second() == jobId && entry.getKey().third() == operationId && sequences.get(entry.getKey().first()).size() < 6) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue().getPriority() < bestEntry.getValue().getPriority())
                    bestEntry = entry;

                /*if (entry.getValue().getPriority() == bestEntry.getValue().getPriority()
                && entry.getKey().first() <= bestEntry.getKey().first())
                    bestEntry = entry;*/

                /*if (entry.getValue().getPriority() == bestEntry.getValue().getPriority()
                        && entry.getKey().first() <= bestEntry.getKey().first()
                        && isEarlier(entry.getValue().getHours(), bestEntry.getValue().getHours()))
                    bestEntry = entry;*/
            }
        }

        return bestEntry;
    }

    private boolean isEarlier(String hours1, String hours2) {
        return Integer.valueOf(hours1.split("-")[1]) <= Integer.valueOf(hours2.split("-")[1]);
    }

    private boolean isSameBuilding(String room1, String room2) {
        List<String> central = Arrays.asList("2/I", "6/II", "7/II");
        List<String> fsega = Arrays.asList("L301", "L302", "L303", "L304", "L305", "L306", "L307", "L308", "L309", "L336", "L338", "L300", "C510", "C512", "L311", "L313", "C308", "C312", "C310");
        List<String> matematicum = Arrays.asList("Gamma", "Beta", "Pi");

        if (matematicum.contains(room1) && matematicum.contains(room2))
            return true;

        if (fsega.contains(room1) && fsega.contains(room2))
            return true;

        if (central.contains(room1) && central.contains(room2))
            return true;

        return false;
    }

    /**
     *
     * @param machineId machine of the id that completed (jobId, operationId - 1)
     * @return the entry from operationDurationMapAll which takes the least time to complete
     */
    private Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> tryGetBestMachineForJobOperationAfterMachineWithRoomHeuristic(int machineId, int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().first() >= machineId && entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                if (sequences.get(entry.getKey().first()).size() > 0 && sequences.get(entry.getKey().first()).size() < 6 && isSameBuilding(entry.getValue().getRoom(), sequences.get(entry.getKey().first()).get(sequences.get(entry.getKey().first()).size() - 1).third().getRoom())) {
                    if (bestEntry == null)
                        bestEntry = entry;
                    else if (entry.getValue().getPriority() < bestEntry.getValue().getPriority())
                        bestEntry = entry;
                }
            }
        }

        return bestEntry;
    }

    /**
     *
     * @param machineId machine of the id that completed (jobId, operationId - 1)
     * @return the entry from operationDurationMapAll which takes the least time to complete
     */
    private Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> tryGetBestMachineForJobOperationAfterMachine(int machineId, int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> entry : operationsDurationMapAll.entrySet()) {
            if (entry.getKey().first() >= machineId && entry.getKey().second() == jobId && entry.getKey().third() == operationId) {
                if (bestEntry == null)
                    bestEntry = entry;
                else if (entry.getValue().getPriority() < bestEntry.getValue().getPriority())
                    bestEntry = entry;

                //if (Math.random() < 0.2)
                //    if (dayWithLeastClassesThatCanRun(jobId, operationId) != null)
                //        return dayWithLeastClassesThatCanRun(jobId, operationId);
            }
        }

        return bestEntry;
    }

    private Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> dayWithLeastClassesThatCanRun(int jobId, int operationId) {
        Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> bestEntry = null;
        for (Map.Entry<Tuple3<Integer, Integer, Integer>, AdditionalInfo> entry : operationsDurationMapAll.entrySet()) {
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
     *
     * @param jobOperations list of the processed jobs operation (index i == job(i) + 1)
     * @return true, if there are unprocessed job operations
     *         false, otherwise
     */
    private boolean jobOperationsStillNeedToBeProcessed(List<Integer> jobOperations) {
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
    int getMachineThatCompletedJobOperationBefore(int jobId, int operationId) {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> entry: sequences.entrySet()) {
            for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {
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
        for (ClassJob job : jobs) {
            operationsDurationMapAll.putAll(job.getOperationsDurationMap());
        }
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
     * For debugging purposes
     */
    public void printSequences() {
        for (Map.Entry<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> entry : sequences.entrySet()) {
            System.out.println("=============== MACHINE " + entry.getKey() + " ===============");
            for (Tuple3<Integer, Integer, AdditionalInfo> tuple : entry.getValue()) {
                System.out.println("Job = " + tuple.first() + "; Operation = " + tuple.second() + "; Duration = " + tuple.third());
            }

            System.out.println("=======================================");
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public Map<Integer, List<Tuple3<Integer, Integer, AdditionalInfo>>> getSequences() {
        return sequences;
    }
}
