package util;

import model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

public class Filer {
    public static void WriteUsersInitial(String username,String password)throws IOException {
        String BasePath="src/Users";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BasePath + "/" + username,true));
            writer.write(password);
            writer.close();
        } catch (IOException | NullPointerException e) {
            System.out.println(e);
        }
    }
    public static boolean CheckDirectory(String username){
        String TxtPath= String.format("src/Users/%s",username);
        File file=new File(TxtPath);
        return file.exists()&&file.isFile();
    }
    public static String ReadPassword(String username) {
        String TxtPath = "src/Users/" + username;
        String Password;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(TxtPath));
            Password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Password;
    }
    public static void SaveNumber(int[][]a,int step,int target,int point,int count,int hard){
        String password = null;
        String TxtPath = "src\\Store\\"+User.CurrentUser+".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(TxtPath));
            writer.write(String.valueOf(target));
            writer.newLine();
            writer.write(String.valueOf(step));
            writer.newLine();
            writer.write(String.valueOf(point));
            writer.newLine();
            writer.write(String.valueOf(count));
            writer.newLine();
            writer.write(String.valueOf(hard));
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
        String TxtPath =  "src\\store\\"+User.CurrentUser+".txt";
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
        String filePath = "src\\store\\"+User.CurrentUser+".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
    public static int ReadStep(){
        String TxtPath = "src\\store\\"+User.CurrentUser+".txt";
       try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
            reader.readLine();
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
    public static int ReadHard(){
        String TxtPath =  "src\\store\\"+User.CurrentUser+".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
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
        public static boolean CheckRead(){
            String TxtPath = "src\\store\\"+User.CurrentUser+".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(TxtPath))) {
                String line = reader.readLine();
                return !(line == null || line.trim().isEmpty());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    public static int ReadPoint(){
        String filePath =  "src\\store\\"+User.CurrentUser+".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
public static int ReadCount(){
    String filePath = "src\\store\\"+User.CurrentUser+".txt";
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
public static void RecordPoint(String Point){
    String filePath = "RankPackage\\RankMember";
    List<String> lines = new ArrayList<>();
    boolean updated = false;
    int t=0;
    try {
        lines = Files.readAllLines(Paths.get(filePath));
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("\\s+");
            if (parts.length >= 2 && parts[0].equals(User.CurrentUser)) {
                int currentPoints = Integer.parseInt(parts[1]);
                int newPoints = Integer.parseInt(Point);
                t=t+1;
                if (newPoints > currentPoints) {
                    lines.set(i, User.CurrentUser + "   " + Point);
                    updated = true;
                }
                break;
            }
        }

        if (updated) {
            Files.write(Paths.get(filePath), lines);
        }
        if (t==0){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(User.CurrentUser + "   " + Point);
                writer.newLine();
            }
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }
}
public static void Rank(){
    String filePath1 = "RankPackage\\RankMember";
    String filePath2="RankPackage\\Rank";
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
    List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(dataMap.entrySet());
    Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    });
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
public static void DeleteWrong(boolean safe){
        String filePath= "src\\store\\"+User.CurrentUser+".txt";
    if (!safe) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
            System.out.println("File deleted: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    public static boolean isTextFile() {
        String filePath= "src\\store\\"+User.CurrentUser+".txt";
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        return fileName.toLowerCase().endsWith(".txt");
    }
}





