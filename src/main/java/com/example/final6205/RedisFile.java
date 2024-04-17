package com.example.final6205;

import java.io.File;


public class RedisFile {
    private File file;
    private String filepath;
    String uuid;
    public RedisFile(File file, String filePath,String uuid) {
        this.file = file;
        this.filepath = filePath;
        this.uuid = uuid;
    }
    public String getFilePath() {
        return this.filepath;
    }


}

