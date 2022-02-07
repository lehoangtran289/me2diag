package com.hust.backend.constant;

public enum ResourceType {
    USER("user"),
    ;
    public final String folderName;

    ResourceType(String folderName) {
        this.folderName = folderName;
    }
}
