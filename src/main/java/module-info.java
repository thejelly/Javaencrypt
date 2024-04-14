module com.example.final6205 {
    requires javafx.controls;
    requires javafx.fxml;
    requires redis.clients.jedis; // 注意这里需要添加分号

    opens com.example.final6205 to javafx.fxml;
    exports com.example.final6205;
}
