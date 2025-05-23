package com.sqli.ccplatform.domain.enums;

//public enum TaskStatus {
//    TODO,
//    IN_PROGRESS,
//    DONE
//}

public enum TaskStatus {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}