package com.example.final6205;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;
import java.io.File;
import java.util.HexFormat;

import com.google.gson.Gson;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import java.util.Map;
public class DAO {
    //dao 实现的主要是数据库的访问，修改，增加，删除；
    // dao 实现了返回 hashmap和双端队列
    //快排又返回hashmap生成
    Jedis jedis;
    String dequeKey = "myDeque";

    public DAO() {
        //建立对指定数据库访问
        jedis = new Jedis("redis://default:AZakgtcbhlfdAvJx3DFrHHz7S3i8szYs@redis-15882.c10.us-east-1-4.ec2.cloud.redislabs.com:15882");
        // 测试连接
        System.out.println("连接成功，服务正在运行: " + jedis.ping());
    }

    public void saveFileToRedis(String uuid,String redisJson) {

        jedis.hset("filesystem", uuid, redisJson);  // 存储文件路径和 UUID
        System.out.println("File saved with UUID: " + uuid);
    }

    public void delHashmap(String uuid) {
        // 删除filesystem按键删除数据
        jedis.del("filesystem", uuid);
        System.out.println("filesystem " + uuid + " deleted.");
    }

    public String findValueByUUID(String uuid) {
        // 从名为'filesystem'的哈希表中获取字段为uuid的值
        String value = jedis.hget("filesystem", uuid);
        if (value != null) {
            System.out.println("Found path for UUID " + uuid + ": " + value);
        } else {
            System.out.println("No entry found for UUID " + uuid);
        }
        return value;
    }


    //返回双端队列
    public List<String> getRestDeque(){
        java.util.List<String> elements = jedis.lrange(dequeKey, 0, -1);  // 返回队列中所有元素的列表
        for (String element : elements) {
            System.out.println(element);
        }
        return  elements;
    }

    //返回全部文件hashmap，
    public SimpleHashMap<String,String> getmap(){
        Map<String, String> filemap = jedis.hgetAll("filesystem");
        filemap.forEach((field, value) -> System.out.println(field + ": " + value));

        SimpleHashMap<String, String> myMap = new SimpleHashMap<>();

        filemap.forEach((field, value) -> {
            myMap.put(field, value);  // 将每个键值对添加到 SimpleHashMap
        });
        return myMap;



    }


    /*把文件加入队列
    /
     */
    public void addOrUpdateFile(String filePath) {
        String fileHash = Controller.calculateFileHash(filePath); // 假设这个方法返回文件内容的哈希值

        boolean exists = jedis.hexists("fileIndex", fileHash);

        if (exists) {
            // 如果文件已存在，移除旧的队列项
            System.out.println("该文件已经存在，移除旧的队列项目");
            jedis.lrem(dequeKey, 0, filePath);
        }

        // 将文件加到队列头部
        jedis.lpush(dequeKey, filePath);
        // 更新哈希表信息，例如使用当前时间戳作为“最后访问”
        jedis.hset("fileIndex", fileHash, String.valueOf(System.currentTimeMillis()));
        System.out.println("该文件已经压入双端队列");
    }
    public boolean checkhave(String filePath){
        String fileHash = Controller.calculateFileHash(filePath); // 假设这个方法返回文件内容的哈希值
        boolean check = jedis.hexists("fileIndex", fileHash);

        return check;

    }
    private void quickSort(List<Map.Entry<String, Long>> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);

            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private int partition(List<Map.Entry<String, Long>> list, int low, int high) {
        long pivot = list.get(high).getValue();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j).getValue() < pivot) {
                i++;

                // swap arr[i] and arr[j]
                Map.Entry<String, Long> temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Map.Entry<String, Long> temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    public List<Map.Entry<String, Long>> getFilesWithSizes() {
        Map<String, String> fileSizeMap = jedis.hgetAll("filesystemSizes");
        List<Map.Entry<String, Long>> fileList = new ArrayList<>();
        for (Map.Entry<String, String> entry : fileSizeMap.entrySet()) {
            fileList.add(new AbstractMap.SimpleEntry<>(entry.getKey(), Long.parseLong(entry.getValue())));
        }

        // 使用 Sorter 类的方法进行排序
        Sorter.quickSortFilesBySize(fileList);

        return fileList;
    }
    public void saveFileSizeToRedis(String filePath, long fileSize) {
        jedis.hset("filesystemSizes", filePath, String.valueOf(fileSize));
        System.out.println("Saved file size to Redis: Path=" + filePath + ", Size=" + fileSize);
    }
}





