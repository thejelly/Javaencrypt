package com.example.final6205;

public class FileHistoryRecord {
    private String originalPath;
    private String processedPath;
    private String operation;  // "Encrypt" 或 "Decrypt"

    public FileHistoryRecord(String originalPath, String processedPath, String operation) {
        this.originalPath = originalPath;
        this.processedPath = processedPath;
        this.operation = operation;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public String getProcessedPath() {
        return processedPath;
    }

    public String getOperation() {
        return operation;
    }
}
