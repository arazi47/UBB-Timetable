package com.razi.ubbtt.job_shop;

public class Job {
    private int name;
    private int length;

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
}
