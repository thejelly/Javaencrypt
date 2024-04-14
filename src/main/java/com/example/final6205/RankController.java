package com.example.final6205;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RankController {

    @FXML
    private ListView<String> ranklist;
    public void initialize() {
        // 创建一个包含元素的 ObservableList
        ObservableList<String> items = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4"
        );

        // 将 ObservableList 与 ListView 关联
        ranklist.setItems(items);
    }

}

