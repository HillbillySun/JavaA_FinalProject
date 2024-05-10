package view;

import controller.GameController;
import util.ColorMap;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;

    private JButton mode;

    private JButton UpBtn;

    private JButton DownBtn;

    private JButton LeftBtn;

    private JButton RightBtn;

    private JLabel stepLabel;

    private JLabel pointLabel;
    private GamePanel gamePanel;

    public GameFrame(int width, int height, int COUNT,int Target) {
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        ColorMap.InitialColorMap();
        gamePanel = new GamePanel((int) (this.getHeight() * 0.8),COUNT,Target);
        gamePanel.setLocation(this.getHeight() / 15, this.getWidth() / 15);
        this.add(gamePanel);

        this.controller = new GameController(gamePanel, gamePanel.getModel(),this);
        this.restartBtn = createButton("Restart", new Point(700, 150), 110, 50);
        this.loadBtn = createButton("Load", new Point(700, 220), 110, 50);
        this.mode = createButton("Mode", new Point(700, 290), 110, 50);
        this.UpBtn = createButton("↑", new Point(725, 480), 60, 60);
        this.DownBtn = createButton("↓", new Point(725, 550), 60, 60);
        this.LeftBtn = createButton("←", new Point(655, 550), 60, 60);
        this.RightBtn = createButton("→", new Point(795, 550), 60, 60);
        this.stepLabel = createLabel("Start", new Font("Arial", Font.PLAIN, 22), new Point(700, 30), 180, 50);
        this.pointLabel = createLabel("Point: 0", new Font("Arial", Font.PLAIN, 22), new Point(700, 80), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setPointLabel(pointLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.mode.addActionListener(e ->
        {
            String str1 = JOptionPane.showInputDialog(this, "输入你想要的棋盘尺寸(2-10中的整数)");
            String str2 = JOptionPane.showInputDialog(this, "输入你的目标数字(128到2048中的2的幂次)：");
            System.out.println("size: "+str1);
            System.out.println("target: "+str2);
            int boardSize;
            int target;
            if (isInteger(str1)&&isInteger(str2))
            {
                boardSize=Integer.parseInt(str1);
                target=Integer.parseInt(str2);
                if (boardSize >= 2 && boardSize <= 10 && target > 0 && isPowerOfTwo(target)) {
                    this.dispose();
                    StartGame(boardSize,target);
                    gamePanel.refreshGame();
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"请重新输入!");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this,"请重新输入!");
            }
            gamePanel.requestFocusInWindow();
        });
        this.RightBtn.addActionListener(e ->
        {
            gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();
        });
        this.LeftBtn.addActionListener(e ->
        {
            gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();
        });
        this.UpBtn.addActionListener(e -> {
            gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();
        });
        this.DownBtn.addActionListener(e ->
        {
            gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();
        });
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private JButton createButton(String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        this.add(button);
        return button;
    }

    private JLabel createLabel(String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        this.add(label);
        return label;
    }

    public static boolean isPowerOfTwo(int number) {
        // 如果一个数是2的幂次，那么它的二进制表示中只有一位是1
        // 使用按位与操作来检查
        // 例如，如果number是2的幂次，那么number & (number - 1)将等于0
        return (number > 0) && ((number & (number - 1)) == 0);
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void StartGame(int COUNT,int Target)
    {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame(900, 700,COUNT,Target);
            gameFrame.setVisible(true);
        });
    }
}