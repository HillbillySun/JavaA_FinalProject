package model;
import view.GridComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class SaveModel {
    public static boolean save(String username, GridComponent[][]a){
        final String FILE_PATH = "model/游戏存档.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            boolean foundString = false;
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
                if (line.equals(username)) {
                    foundString = true;
                }
            }
            for (int i = 0; i < a.length; i++) {
                // 遍历当前行的每个元素
                for (int j = 0; j < a[i].length; j++) {
                    // 将元素写入文件
                    writer.write(String.valueOf(a[i][j]));
                    // 如果不是当前行的最后一个元素，则写入分隔符（例如空格）
                    if (j < a[i].length - 1) {
                        writer.write(" "); // 使用空格分隔每个元素
                    }
                }
                // 写入换行符
                writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
