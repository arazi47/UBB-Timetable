package com.razi.ubbtt.JobShop.utils;

public class AdditionalInfo {
    public String type;
    public String hours;
    public String room;
    public String group;
    public int priority;

    public AdditionalInfo(String type, String hours, String room, String group) {
        this.type = type;
        this.hours = hours;
        this.room = room;
        this.group = group;
        this.priority = AdditionalInfo.getPriority(type);
    }

    public String getType() {
        return type;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getRoom() {
        return room;
    }


    public String getGroup() {
        return group;
    }

    public int getPriority() {
        return priority;
    }

    private static int getPriority(String type) {
        switch (type.toLowerCase()) {
            case "curs":
                return 1;

            case "seminar":
                return 2;

            case "laborator":
                return 3;

            default:
                return 1;
        }
    }
}
