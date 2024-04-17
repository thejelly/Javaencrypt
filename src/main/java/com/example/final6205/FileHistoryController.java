package com.example.final6205;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class FileHistoryController {
    @FXML
    private ListView<String> recentl1;
    public void initialize() {
        // 创建一个包含元素的 ObservableList
        DAO dao = new DAO();
        List<TreeNode<String, String>> list = dao.getmap().midTraversal();
       List<String> stringList = list.stream()
                .map(node ->  node.value)
                .collect(Collectors.toList());
        ObservableList<String> items = FXCollections.observableArrayList(
                stringList
        );

        recentl1.setItems(items);
    }

}