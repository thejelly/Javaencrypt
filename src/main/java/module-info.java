module com.example.final6205 {
    requires javafx.controls;
    requires javafx.fxml;
    requires redis.clients.jedis;
    requires com.google.gson; // 注意这里需要添加分号

    opens com.example.final6205 to javafx.fxml, com.google.gson;
    exports com.example.final6205;
}
