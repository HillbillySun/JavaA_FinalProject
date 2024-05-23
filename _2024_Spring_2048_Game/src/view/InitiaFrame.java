package view;

import controller.GameController;
import util.Filer;
import javax.swing.*;
import java.awt.*;

public class InitiaFrame extends JFrame {
    JButton NewGameBtn;
    JButton LoadGameBtn;
    JButton RankGameBtn;
    JPanel backgroundPanel;
    private LoadFrame loadFrame;
    private GameController controller;
    private ModeFrame modeFrame;
    private GameFrame gameFrame;
    public InitiaFrame(int width,int height) {
        controller = new GameController();
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
            modeFrame.setLoadFrame(loadFrame);
            ModeFrame.OpenMode(this.modeFrame);
        });
        this.LoadGameBtn.addActionListener(e->
        {
            controller.CheckRead();
            if(controller.isRead()){
                this.dispose();
                JOptionPane.showMessageDialog(InitiaFrame.this, "可以继续游戏啦！");
                modeFrame=new ModeFrame(900,700,this);
                gameFrame=new GameFrame(900,700,Filer.ReadCount(),Filer.ReadTarget(),Filer.ReadPoint(),null,false,0);
                gameFrame.setModeFrame(this.modeFrame);
                gameFrame.getGamePanel().setModeFrame(this.modeFrame);
                modeFrame.setLoadFrame(this.loadFrame);
                gameFrame.setController(this.controller);
                gameFrame.getGamePanel().setController(this.controller);
                gameFrame.getGamePanel().getModel().LoadNumbers(Filer.ReadArray());
                gameFrame.getGamePanel().updateGridsNumber();
                controller.setModel(gameFrame.getGamePanel().getModel());
                controller.setView(gameFrame.getGamePanel());
                controller.setGameFrame(gameFrame);
                GameFrame.StartGame(gameFrame);
            }
            else{
                JOptionPane.showMessageDialog(InitiaFrame.this, "您无存档可读！");
            }
        });
        this.RankGameBtn.addActionListener(e->
        {

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
    public void setLoadFrame(LoadFrame loadFrame)
    {
        this.loadFrame = loadFrame;
    }

    public LoadFrame getLoadFrame()
    {
        return loadFrame;
    }
}
