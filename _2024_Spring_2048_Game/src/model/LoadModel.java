package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadModel {
    private static final String FILE_PATH = "C:\\Users\\吕梓翀\\Documents\\GitHub\\JavaA_FinalProject\\_2024_Spring_2048_Game\\src\\model\\用户名录.txt";
    private static final String FILE_PATH2 = "C:\\Users\\吕梓翀\\Documents\\GitHub\\JavaA_FinalProject\\_2024_Spring_2048_Game\\src\\model\\游戏存档.txt";
    static UserNow user=new UserNow();
    public static boolean register(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE_PATH, true));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(FILE_PATH2, true))) {

            String data = username + " " + password;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                String firstWord = words[0];
                if (username.equals(firstWord)) {
                    return false;
                }
            }
            writer1.write(data);
            writer1.newLine();
            writer2.write(username);
            writer2.newLine();
            UserNow.setUsername(username);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String username, String password) {
        String data = username + " " + password;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (data.equals(line)) {
                    UserNow.setUsername(username);
                    return true;
                }
            }
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
