package com.example.final6205;

import java.io.File;


public class RedisFile {
    private File file;
    private String filepath;
    private int count;
    String uuid;
    public RedisFile(File file, String filePath,String uuid) {
        this.file = file;
        this.filepath = filePath;
        this.count = 0;
        this.uuid = uuid;
    }
    public String getFilePath() {
        return this.filepath;
    }


}

