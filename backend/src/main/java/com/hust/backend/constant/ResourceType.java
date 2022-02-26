package com.hust.backend.constant;

public enum ResourceType {
    USER("user"),
    PATIENT("patient")
    ;
    public final String folderName;

    ResourceType(String folderName) {
        this.folderName = folderName;
    }
}
