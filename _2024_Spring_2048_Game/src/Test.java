import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String TxtPath = "Users/5";
        File file = new File(TxtPath);
        int[][] array = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                if (row >= 3) {
                    String[] values = line.split("\\s+");
                    if (array == null) {
                        array = new int[values.length][];
                    }
                    for (int i = 0; i < values.length; i++) {
                        if (array[i] == null) {
                            array[i] = new int[values.length];
                        }
                        array[i][row - 3] = Integer.parseInt(values[i]);
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
    }
}
