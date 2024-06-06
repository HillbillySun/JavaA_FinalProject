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

    public RankFrame(int width, int height) {
        setTitle("排行榜");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        Font labelFont = util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf", 30f);
        Font titleFont = util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf", 100f);

        loadDataFromFile();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < names.size(); i++) {
            JLabel label = createLabel("第" + (i + 1) + "名: " + names.get(i) + " ---------------- " + scores.get(i) + "分", labelFont, new Color(175, 158, 137));
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBackground(new Color(255, 237, 211));

        JLabel titleLabel = createLabel("排行榜", titleFont, new Color(255, 69, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel bkgPanel = new JPanel(new BorderLayout());
        bkgPanel.setBackground(new Color(255, 237, 211));
        bkgPanel.add(titleLabel, BorderLayout.NORTH);
        bkgPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(bkgPanel);
    }

    private void loadDataFromFile() {
        names = new ArrayList<>();
        scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("RankPackage\\Rank"))) {
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

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static void OpenRank() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RankFrame(900, 700).setVisible(true);
            }
        });
    }
}
