package view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RankFrame extends JFrame {

    private List<String> names;
    private List<Integer> scores;

    public RankFrame() {
        setTitle("排行榜");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);

        loadDataFromFile();

        JPanel panel = new JPanel(new GridLayout(names.size(), 1));
        for (int i = 0; i < names.size(); i++) {
            JLabel label = new JLabel("第"+(i+1)+"名: "+names.get(i) + " ---------------- " + scores.get(i)+"分");
            panel.add(label);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDataFromFile() {
        names = new ArrayList<>();
        scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Users\\Rank"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    names.add(parts[0]);
                    scores.add(Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
