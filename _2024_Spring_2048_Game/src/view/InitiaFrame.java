package view;

import controller.GameController;
import model.User;
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
    private RankFrame rankFrame;
    private JLabel welcomeLabel;
    private java.awt.Font font;

    public InitiaFrame(int width, int height) {
        font = util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf", 50);
        controller = new GameController();
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeLabel = createLabel("2048", util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf", 100), new Point(220, 100), 400, 100);
        welcomeLabel.setForeground(new Color(128, 102, 74));
        NewGameBtn = createButton("New Game", new Point(150, 235), 300, 90);
        LoadGameBtn = createButton("Load Game", new Point(150, 365), 300, 90);
        RankGameBtn = createButton("Rank", new Point(150, 495), 300, 90);
        NewGameBtn.setFont(font);
        LoadGameBtn.setFont(font);
        RankGameBtn.setFont(font);
        JPanel bkgPanel = new JPanel();
        bkgPanel.setBackground(new Color(255, 237, 211));
        this.backgroundPanel = bkgPanel;
        backgroundPanel.setLayout(null); // 使用绝对布局
        this.setContentPane(backgroundPanel);
        backgroundPanel.add(NewGameBtn);
        backgroundPanel.add(LoadGameBtn);
        backgroundPanel.add(RankGameBtn);
        backgroundPanel.add(welcomeLabel);
        this.NewGameBtn.addActionListener(e ->
        {
            this.dispose();
            modeFrame = new ModeFrame(900, 700, this);
            modeFrame.setLoadFrame(loadFrame);
            ModeFrame.OpenMode(this.modeFrame);
        });
        this.LoadGameBtn.addActionListener(e ->
        {
            controller.CheckRead();
            controller.CheckSafety();
            if (controller.isRead()) {
                System.out.println("isRead Successfully");
                if (controller.isSafe()) {
                    System.out.println("isSafe Successfully");
                    this.dispose();
                    JOptionPane.showMessageDialog(InitiaFrame.this, "可以继续游戏啦！");
                    modeFrame = new ModeFrame(900, 700, this);
                    gameFrame = new GameFrame(900, 700, Filer.ReadCount(), Filer.ReadTarget(), Filer.ReadStep(), Filer.ReadPoint(), null, false, 0);
                    if(Filer.ReadHard()==1){
                        gameFrame.getGamePanel().getModel().setisHard(true);
                    }
                    gameFrame.setModeFrame(this.modeFrame);
                    gameFrame.getGamePanel().setModeFrame(this.modeFrame);
                    modeFrame.setLoadFrame(this.loadFrame);
                    gameFrame.setController(this.controller);
                    gameFrame.getGamePanel().setController(this.controller);
                    gameFrame.getGamePanel().getModel().LoadNumbers(Filer.ReadArray());
                    gameFrame.getGamePanel().updateGridsNumber();
                    gameFrame.getGamePanel().setSteps(Filer.ReadStep());
                    gameFrame.getGamePanel().setPoints(Filer.ReadPoint());
                    controller.setModel(gameFrame.getGamePanel().getModel());
                    controller.setView(gameFrame.getGamePanel());
                    controller.setGameFrame(gameFrame);
                    GameFrame.StartGame(gameFrame);
                    gameFrame.autoSave(60000);
                    gameFrame.getGamePanel().afterMove();
                } else {
                    JOptionPane.showMessageDialog(InitiaFrame.this, "存档文件损坏，无法读取！");
                }
            }
            else {
                JOptionPane.showMessageDialog(InitiaFrame.this, "您无存档可读！");
            }
        });
        this.RankGameBtn.addActionListener(e ->
        {
            Filer.Rank();
            rankFrame = new RankFrame(800, 800);
            rankFrame.setVisible(true);
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

    public static void initialGame() {
        SwingUtilities.invokeLater(() ->
        {
            InitiaFrame initiaFrame = new InitiaFrame(600, 800);
            initiaFrame.setVisible(true);
        });
    }

    public static void OpenInitial(InitiaFrame initiaFrame) {
        SwingUtilities.invokeLater(() -> {
            initiaFrame.setVisible(true);
        });
    }

    public void setLoadFrame(LoadFrame loadFrame) {
        this.loadFrame = loadFrame;
    }

    public LoadFrame getLoadFrame() {
        return loadFrame;
    }

    private JLabel createLabel(String name, java.awt.Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        this.add(label);
        return label;
    }
}