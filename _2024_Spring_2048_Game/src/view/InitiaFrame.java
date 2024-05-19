package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class InitiaFrame extends JFrame {
    JButton NewGameBtn;
    JButton LoadGameBtn;
    JButton RankGameBtn;

    JPanel backgroundPanel;
    private GameController controller;
    private ModeFrame modeFrame;
    public InitiaFrame(int width,int height) {
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        NewGameBtn = createButton("New Game", new Point(150, 35), 300, 90);
        LoadGameBtn=createButton("Load Game",new Point(150,155),300,90);
        RankGameBtn=createButton("Rank",new Point(150,275),300,90);
        JPanel bkgPanel = new JPanel();
        bkgPanel.setBackground(new Color(255, 237, 211));
        this.backgroundPanel = bkgPanel;
        backgroundPanel.setLayout(null); // 使用绝对布局
        this.setContentPane(backgroundPanel);
        backgroundPanel.add(NewGameBtn);
        backgroundPanel.add(LoadGameBtn);
        backgroundPanel.add(RankGameBtn);
        this.NewGameBtn.addActionListener(e->
        {
            this.dispose();
            modeFrame=new ModeFrame(900,700,this);
            ModeFrame.OpenMode(this.modeFrame);
        });
        this.LoadGameBtn.addActionListener(e->
        {

        });
        this.RankGameBtn.addActionListener(e->
        {
            //todo: 读取用户分数排名
        });

    }
    private JButton createButton(String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        button.setBackground(new Color(175, 158, 137));
        button.setForeground(Color.WHITE);
        this.add(button);
        return button;
    }
    public static void initialGame()
    {
        SwingUtilities.invokeLater(() ->
        {
            InitiaFrame initiaFrame=new InitiaFrame(600,500);
            initiaFrame.setVisible(true);
        });
    }
    public static void OpenInitial(InitiaFrame initiaFrame)
    {
        SwingUtilities.invokeLater(() -> {
            initiaFrame.setVisible(true);
        });
    }
}
