package com.example.final6205;
import redis.clients.jedis.Jedis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/final6205/sample.fxml"));
        primaryStage.setTitle("SafeVault");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
       // DAO dao = new DAO();
       // dao.getmap();
        //dao.getRestDeque();
        /*try {
            Jedis jedis = new Jedis("redis://default:AZakgtcbhlfdAvJx3DFrHHz7S3i8szYs@redis-15882.c10.us-east-1-4.ec2.cloud.redislabs.com:15882");
            System.out.println("连接成功，服务正在运行: " + jedis.ping());
        } catch (Exception e) {
            e.printStackTrace(); // 打印出错误详情
            System.out.println("连接失败: " + e.getMessage());
        } */
        launch(args);
        //测试数据库返回map和deque


    }
}
