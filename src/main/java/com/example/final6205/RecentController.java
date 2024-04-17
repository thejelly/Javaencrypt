package com.example.final6205;
import com.example.final6205.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.Map;


public class RecentController {

        @FXML
        private ListView<String> ranklist;

        public void initialize() {
            DAO dao = new DAO();
            List<Map.Entry<String, Long>> filesWithSizes = dao.getFilesWithSizes();

            if (filesWithSizes.isEmpty()) {
                System.out.println("No file sizes found in Redis.");
            } else {
                ObservableList<String> items = FXCollections.observableArrayList();
                for (Map.Entry<String, Long> entry : filesWithSizes) {
                    items.add(entry.getKey() + " - " + entry.getValue() + " bytes");
                    System.out.println("Loaded: " + entry.getKey() + " - " + entry.getValue() + " bytes");
                }
                ranklist.setItems(items);
            }
        }
    }


