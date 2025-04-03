package com.qa_task.constants;

public class enums {
    public enum IssueType {
        BUG,
        STORY,
        TASK
    }

    public enum IssuePriority {
        LOWEST,
        LOW,
        MEDIUM,
        HIGH,
        HIGHEST
    }

    public enum IssueStatus {
        BACKLOG("BACKLOG"),
        SELECTED_FOR_DEVELOPMENT("SELECTED FOR DEVELOPMENT"),
        IN_PROGRESS("IN PROGRESS"),
        DONE("DONE");

        public final String stringValue;

        IssueStatus(String string) {
            this.stringValue = string;
        }

        @Override
        public String toString() {
            return this.stringValue;
        }
    }
}
