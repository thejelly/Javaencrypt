package com.example.final6205;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RankController {

    @FXML
    private ListView<String> ranklist;
    DAO dao = new DAO();
    private SimpleDeque<String> myDeque = new SimpleDeque<>(dao.getRestDeque());
    public void initialize() {
        // 创建一个包含元素的 ObservableList
        ObservableList<String> items = FXCollections.observableArrayList();
        while (!myDeque.isEmpty()) {
            items.add(myDeque.removeFirst());
        }


        // 将 ObservableList 与 ListView 关联
        ranklist.setItems(items);
    }

}

