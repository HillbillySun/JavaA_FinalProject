import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Background Change Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.BLUE);

        JButton changeBackgroundButton = new JButton("Change Background");
        changeBackgroundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更换背景颜色为红色
                if (backgroundPanel.getBackground().equals(Color.BLUE))
                {
                    backgroundPanel.setBackground(Color.RED);
                }
                else
                {
                    backgroundPanel.setBackground(Color.BLUE);
                }                // 或者替换背景Panel
                // JPanel newBackgroundPanel = new JPanel();
                // newBackgroundPanel.setBackground(Color.RED);
                // frame.setContentPane(newBackgroundPanel);
            }
        });

        frame.getContentPane().add(backgroundPanel, BorderLayout.CENTER);
        frame.getContentPane().add(changeBackgroundButton, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
