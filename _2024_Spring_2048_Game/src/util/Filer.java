package util;

import model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Filer {
    public static void WriteUsersInitial(String username,String password)throws IOException {
        String BasePath="Users";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BasePath + "/" + username,true));
            writer.write(password);
            writer.newLine();
            writer.close();
        } catch (IOException | NullPointerException e) {
            System.out.println(e);
        }
    }
    public static boolean CheckDirectory(String username){
        String TxtPath= String.format("Users/%s",username);
        File file=new File(TxtPath);
        return file.exists()&&file.isFile();
    }
    public static String ReadPassword(String username) {
        String TxtPath = "Users/" + username;
        String Password;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(TxtPath));
            Password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Password;
    }
    public static void SaveNumber(int[][]a,int step,int target,int point,int count){
        String password = null;
        String TxtPath = "Users\\"+User.CurrentUser;
        try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
            password= reader.readLine();
        } catch (IOException e) {
            System.err.println("读取密码时出错：" + e.getMessage());
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(TxtPath));
            if (password != null) {
                writer.write(password);
                writer.newLine();
            }
            writer.write(String.valueOf(target));
            writer.newLine();
            writer.write(String.valueOf(step));
            writer.newLine();
            writer.write(String.valueOf(point));
            writer.newLine();
            writer.write(String.valueOf(count));
            writer.newLine();
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    writer.write(Integer.toString(a[i][j]));
                    if (j < a[i].length - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
            writer.close();

            System.out.println("已成功保存");
        } catch (IOException e) {
            System.err.println("保存游戏时出错：" + e.getMessage());
        }
    }
    public static int[][] ReadArray(){
        String TxtPath = "Users\\" + User.CurrentUser;
        File file = new File(TxtPath);
        int[][] array = null;
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            int row = 0;
                            while ((line = reader.readLine()) != null) {
                                if (row >= 5) {
                                    String[] values = line.split("\\s+");
                                    if (array == null) {
                                        array = new int[values.length][values.length];
                                    }
                                    for (int i = 0; i < values.length; i++) {
                                        array[row-5][i] = Integer.parseInt(values[i]);
                                    }
                                }
                                row++;
                            }
                            if (array != null) {
                                System.out.println("Read array from file:");
                                for (int i = 0; i < array.length; i++) {
                                    for (int j = 0; j < array[i].length; j++) {
                                        System.out.print(array[i][j] + " ");
                                    }
                                    System.out.println();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
        return array;
    }
    public static int ReadTarget(){
        String filePath = "Users\\"+User.CurrentUser;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
    public static int ReadStep(){
        String TxtPath = "Users\\"+User.CurrentUser;
       try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
            reader.readLine();
            reader.readLine();
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
        public static boolean CheckRead(){
            String TxtPath = "Users\\"+User.CurrentUser;
            try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
                String line = reader.readLine();
                line = reader.readLine();
                return !(line == null || line.trim().isEmpty());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    public static int ReadPoint(){
        String filePath = "Users\\"+User.CurrentUser;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
public static int ReadCount(){
    String filePath = "Users\\"+User.CurrentUser;
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line = reader.readLine();
        line = reader.readLine();
        line = reader.readLine();
        line = reader.readLine();
        line = reader.readLine();
        return Integer.parseInt(line.trim());
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
        return Integer.MIN_VALUE;
    }
}
public static void RecordPoint(String Point){
    String filePath = "Users\\RankMember";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
        writer.write(User.CurrentUser);
        writer.write("   ");
        writer.write(Point);
        writer.newLine();
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }
}
public static void Rank(){
    String filePath1 = "Users\\RankMember";
    String filePath2="Users\\Rank";
    Map<String, Integer> dataMap = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                String key = parts[0];
                int value = Integer.parseInt(parts[1]);
                dataMap.put(key, value);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // 按值降序排序Map
    List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(dataMap.entrySet());
    Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue()); // 降序排列
        }
    });

    // 将排序后的数据写入文件
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {
        for (Map.Entry<String, Integer> entry : sortedList) {
            writer.write(entry.getKey() + " " + entry.getValue());
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("数据已经成功写入到文件 Rank 中。");
}
public static boolean CheckSafety(){
        return false;
}
}





