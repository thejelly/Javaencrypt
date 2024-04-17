package com.example.final6205;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.UUID;
import com.google.gson.Gson;
import java.security.MessageDigest;

public class Controller {

    @FXML
    private Button RankButton;

    @FXML
    private Button decryptButton;

    @FXML
    private Button encryptButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button recentButton;

    private ArrayList<FileHistoryRecord> fileHistoryRecords = new ArrayList<>();

    @FXML
    //encrupt按钮点击事件，打开一个对话框选中文件并且执行processfile
    private void onEncryptButtonClick() {
        File file = chooseFile("Choose File to Encrypt");
        if (file != null) {
            processFile(file, true);
        }
    }

    @FXML
    private void onDecryptButtonClick() {
        File file = chooseFile("Choose File to Decrypt");
        if (file != null) {
            processFile(file, false);
        }
    }

    private File chooseFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(null);
    }

    private void processFile(File file, boolean encrypt) {
        byte[] fileContent = readFile(file);
        if (fileContent != null) {
            DAO dao = new DAO();
            byte[] processedContent = encrypt ? messUp(fileContent) : fix(fileContent);
            String processedFileName = generateProcessedFileName(file.getName(), encrypt);
            //用加密后的名字创建新的文件 outfile
            File outFile = new File(file.getParent(), processedFileName);
            writeFile(processedContent, outFile);
            //获取outFile的地址
            String filePath = outFile.getPath();
            String uuid = UUID.randomUUID().toString();
            //创建可传入redis的redisFile
            RedisFile redisFile = new RedisFile(outFile, filePath, uuid);
            long fileSize = outFile.length();
            dao.saveFileSizeToRedis(filePath, fileSize);
            //把redisFile转换成gson
            Gson gson = new Gson();
            String redisJson = gson.toJson(redisFile);
            //把redisFile 和uuid 导入哈希表
           if(!dao.checkhave(filePath)) {
               dao.saveFileToRedis(uuid, redisJson);
               System.out.println("压入hashmap");
           }
           else {
               System.out.println("该文件已经存在于hashmap,不会再加入数据库hashmap统计");
           }
           //判断是否重复，然后推入队列
            dao.addOrUpdateFile(filePath);
            recordFileHistory(file.getAbsolutePath(), outFile.getAbsolutePath(), encrypt ? "Encrypt" : "Decrypt");
        }
    }


    private byte[] readFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] messUp(byte[] content) {
        byte[] result = new byte[content.length];
        for (int i = 0; i < content.length - 1; i += 2) {
            result[i] = content[i + 1];
            result[i + 1] = content[i];
        }
        if (content.length % 2 != 0) {
            result[content.length - 1] = content[content.length - 1];
        }
        return result;
    }

    private byte[] fix(byte[] content) {
        return messUp(content); // Since we just swapped bytes, decrypting is the same as encrypting.
    }

    private void writeFile(byte[] content, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateProcessedFileName(String originalName, boolean encrypt) {
        String baseName = originalName;
        String extension = "";

        int extIndex = originalName.lastIndexOf('.');
        if (extIndex != -1) {
            baseName = originalName.substring(0, extIndex);
            extension = originalName.substring(extIndex);
        }

        if (encrypt) {
            if (!baseName.endsWith("_encry")) {
                baseName += "_encry";
            }
        } else {
            if (baseName.endsWith("_encry")) {
                baseName = baseName.substring(0, baseName.length() - "_encry".length());
            }
        }

        return baseName + extension;
    }

    private void recordFileHistory(String originalPath, String processedPath, String operation) {
       FileHistoryRecord record = new FileHistoryRecord(originalPath, processedPath, operation);
        fileHistoryRecords.add(record);
        // Use the processed path for quick lookup of the history.

    }

    @FXML
    private void onHistoryButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dog.fxml"));
        Parent root = loader.load();

        // 创建新窗口的 Stage 和 Scene
        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.setScene(scene);
        newStage.setTitle("Whole file ");
        newStage.show();
    }

    // Other methods and logic as needed
    @FXML
    private void onRankButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rankpage.fxml"));
        Parent root = loader.load();

        // 创建新窗口的 Stage 和 Scene
        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.setScene(scene);
        newStage.setTitle("Files Rank");
        newStage.show();

    }

    @FXML
    private void onRecentButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recentlist.fxml"));
        Parent root = loader.load();

        // 创建新窗口的 Stage 和 Scene
        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.setScene(scene);
        newStage.setTitle("Recent file");
        newStage.show();

    }

    /**
     * 计算文件的 SHA-256 哈希值
     *
     * @param filePath 文件路径
     * @return 返回文件的哈希值，以十六进制字符串形式
     */
    public static String calculateFileHash(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(filePath);

            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            // 读取文件数据并更新到摘要
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            ;

            fis.close();

            // 获取文件的哈希值字节
            byte[] bytes = digest.digest();

            // 把哈希值转换成十六进制值
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
