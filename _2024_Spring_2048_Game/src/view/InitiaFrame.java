package view;

import javax.swing.*;
import java.awt.*;

public class InitiaFrame extends JFrame {
    JButton NewGameBtn;
    JButton LoadGameBtn;
    JButton RankGameBtn;
    public InitiaFrame(int width,int height) {
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        NewGameBtn = createButton("New Game", new Point(150, 35), 300, 90);
        LoadGameBtn=createButton("Load Game",new Point(150,155),300,90);
        RankGameBtn=createButton("Rank",new Point(150,275),300,90);
        this.NewGameBtn.addActionListener(e->
        {
            this.dispose();
            ModeFrame.OpenMode();
        });
        this.LoadGameBtn.addActionListener(e->
        {
            //todo: 加载已有游戏
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
        this.add(button);
        return button;
    }
    public static void initialGame()
    {
        SwingUtilities.invokeLater(() ->
        {
            InitiaFrame initiaFrame=new InitiaFrame(600,400);
            initiaFrame.setVisible(true);
        });
    }
}
