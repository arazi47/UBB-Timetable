package com.razi.ubbtt.job_shop;

public class ClassJob extends Job {
    private boolean week1; // if it's in week 1 or not

    public ClassJob(int name, int week1) {
        // A class always takes 2 hours
        super(name, 2);
    }
}
