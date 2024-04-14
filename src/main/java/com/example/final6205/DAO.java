package com.example.final6205;
import java.util.UUID;
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
    public void dequeAdd(String uuid){

        // 在队列头部添加元素
        jedis.lpush(dequeKey, uuid);

       /* // 从队列头部移除元素
        String headElement = jedis.lpop(dequeKey);
        System.out.println("Popped from head: " + headElement);*/

        /*// 从队列尾部移除元素
        String tailElement = jedis.rpop(dequeKey);
        System.out.println("Popped from tail: " + tailElement);

        // 获取队列的剩余元素
        System.out.println("Remaining elements: " + jedis.lrange(dequeKey, 0, -1));*/

    }
    public void getRestDeque(){
        java.util.List<String> elements = jedis.lrange(dequeKey, 0, -1);  // 返回队列中所有元素的列表
        for (String element : elements) {
            System.out.println(element);
        }
    }


    public void getmap(){
        Map<String, String> filemap = jedis.hgetAll("filesystem");
        filemap.forEach((field, value) -> System.out.println(field + ": " + value));
        //return filemap;
    }

}
