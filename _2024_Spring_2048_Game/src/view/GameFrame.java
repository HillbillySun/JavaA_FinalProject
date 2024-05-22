package view;

import controller.GameController;
import model.User;
import util.ColorMap;
import util.Filer;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {

    private JButton SaveBtn;
    private JButton restartBtn;
    private JButton mode;

    private JButton UpBtn;

    private JButton DownBtn;

    private JButton LeftBtn;

    private JButton RightBtn;

    private JButton setBck;

    private JPanel backgroundPanel;

    private JLabel stepLabel;

    private JLabel pointLabel;

    private JLabel TimeLabel;
    private GameController controller;
    private GamePanel gamePanel;
    private ModeFrame modeFrame;
    private boolean isTimelimit;
    private int TimeLimit;
    private int tempTime;
    private Timer timer = new Timer();

    public GameFrame(int width, int height, int COUNT, int Target, String path, boolean isTimelimit, int timeLimit) {
        this.TimeLimit = timeLimit;
        this.isTimelimit = isTimelimit;
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        ColorMap.InitialColorMap();
        gamePanel = new GamePanel((int) (this.getHeight() * 0.8), COUNT, Target);
        gamePanel.setGameFrame(this);
        gamePanel.setLocation(this.getHeight() / 15, this.getWidth() / 15);
        this.add(gamePanel);
        this.restartBtn = createButton("Restart", new Point(700, 135), 110, 50);
        this.mode = createButton("Mode", new Point(700, 275), 110, 50);
        this.UpBtn = createButton("↑", new Point(725, 480), 60, 60);
        this.DownBtn = createButton("↓", new Point(725, 550), 60, 60);
        this.LeftBtn = createButton("←", new Point(655, 550), 60, 60);
        this.RightBtn = createButton("→", new Point(795, 550), 60, 60);
        this.SaveBtn = createButton("Save", new Point(700, 415), 110, 50);
        this.stepLabel = createLabel("Start", new Font("Arial", Font.PLAIN, 22), new Point(700, 30), 180, 50);
        this.pointLabel = createLabel("Point: 0", new Font("Arial", Font.PLAIN, 22), new Point(700, 55), 180, 50);
        this.TimeLabel = createLabel("Time: No Limit", new Font("Arial", Font.PLAIN, 22), new Point(700, 80), 180, 50);
        this.setBck = createButton("Theme", new Point(700, 345), 110, 50);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setPointLabel(pointLabel);
        gamePanel.setTimeLabel(TimeLabel);
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.mode.addActionListener(e ->
        {
            ModeFrame.OpenMode(this.modeFrame);
            this.dispose();
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
        this.SaveBtn.addActionListener(e -> {
            controller.saveGame();
            gamePanel.requestFocusInWindow();
            JOptionPane.showMessageDialog(GameFrame.this, "保存成功！");
        });
        this.setBck.addActionListener(e -> {
            Object[] options = {"香港", "天文台", "曼哈顿", "默认"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "选择您的主题！",
                    "选择主题",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (result == 0) {
                reOpen("/Pictures/香港.jpg");
            } else if (result == 1) {
                reOpen("/pictures/天文台.jpg");
            } else if (result == 2) {
                reOpen("/Pictures/曼哈顿.jpg");
            } else if (result == 3) {
                reOpen(null);
            }
        });
        //todo: add other button here
        setbkg(path, false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public GameFrame(int width, int height, int COUNT, int Target, String path, String tourist, boolean isTimelimit, int timeLimit) {
        this.TimeLimit = timeLimit;
        this.isTimelimit = isTimelimit;
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        ColorMap.InitialColorMap();
        gamePanel = new GamePanel((int) (this.getHeight() * 0.8), COUNT, Target);
        gamePanel.setGameFrame(this);
        gamePanel.setLocation(this.getHeight() / 15, this.getWidth() / 15);
        this.add(gamePanel);
        this.restartBtn = createButton("Restart", new Point(700, 135), 110, 50);
        this.mode = createButton("Mode", new Point(700, 205), 110, 50);
        this.UpBtn = createButton("↑", new Point(725, 480), 60, 60);
        this.DownBtn = createButton("↓", new Point(725, 550), 60, 60);
        this.LeftBtn = createButton("←", new Point(655, 550), 60, 60);
        this.RightBtn = createButton("→", new Point(795, 550), 60, 60);
        this.stepLabel = createLabel("Start", new Font("Arial", Font.PLAIN, 22), new Point(700, 30), 180, 50);
        this.pointLabel = createLabel("Point: 0", new Font("Arial", Font.PLAIN, 22), new Point(700, 55), 180, 50);
        this.TimeLabel = createLabel("Time: No Limit", new Font("Arial", Font.PLAIN, 22), new Point(700, 80), 180, 50);
        this.setBck = createButton("Theme", new Point(700, 275), 110, 50);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setPointLabel(pointLabel);
        gamePanel.setTimeLabel(TimeLabel);
        if (isTimelimit) {
            limitMode();
        }
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.mode.addActionListener(e ->
        {
            ModeFrame.OpenMode(this.modeFrame);
            this.dispose();
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
        this.setBck.addActionListener(e -> {
            Object[] options = {"香港", "天文台", "曼哈顿", "默认"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "选择您的主题！",
                    "选择主题",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (result == 0) {
                reOpen("/Pictures/香港.jpg");
            } else if (result == 1) {
                reOpen("/pictures/天文台.jpg");
            } else if (result == 2) {
                reOpen("/Pictures/曼哈顿.jpg");
            } else {
                reOpen(null);
            }

        });
        //todo: add other button here
        setbkg(path, true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    public static void StartGame(GameFrame GameFrame) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = GameFrame;
            gameFrame.setVisible(true);
        });
    }

    public void setModeFrame(ModeFrame modeFrame) {
        this.modeFrame = modeFrame;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setbkg(String path, boolean isTour) {
        try {
//            this.repaint();
            ImageIcon bg = new ImageIcon(GameFrame.class.getResource(path));
            JPanel bkgPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    float alpha = 0.5f; // 0.0f 完全透明，1.0f 完全不透明
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            };
            this.backgroundPanel = bkgPanel;
            backgroundPanel.setLayout(null); // 使用绝对布局
            this.setContentPane(backgroundPanel);
            backgroundPanel.add(gamePanel);
            backgroundPanel.add(restartBtn);
            backgroundPanel.add(mode);
            backgroundPanel.add(UpBtn);
            backgroundPanel.add(DownBtn);
            backgroundPanel.add(LeftBtn);
            backgroundPanel.add(RightBtn);
            backgroundPanel.add(stepLabel);
            backgroundPanel.add(pointLabel);
            backgroundPanel.add(setBck);
            backgroundPanel.add(TimeLabel);
            if (!isTour) {
                backgroundPanel.add(SaveBtn);
            }
        } catch (NullPointerException ignore) {
            JPanel bkgPanel = new JPanel();
            bkgPanel.setBackground(new Color(255, 237, 211));
            this.backgroundPanel = bkgPanel;
            backgroundPanel.setLayout(null); // 使用绝对布局
            this.setContentPane(backgroundPanel);
            backgroundPanel.add(gamePanel);
            backgroundPanel.add(restartBtn);
            backgroundPanel.add(mode);
            backgroundPanel.add(UpBtn);
            backgroundPanel.add(DownBtn);
            backgroundPanel.add(LeftBtn);
            backgroundPanel.add(RightBtn);
            backgroundPanel.add(stepLabel);
            backgroundPanel.add(pointLabel);
            backgroundPanel.add(setBck);
            backgroundPanel.add(TimeLabel);
            if (!isTour) {
                backgroundPanel.add(SaveBtn);
            }
        }
    }

    public void reOpen(String path) {
        GameFrame temp = this;
        this.dispose();
        temp.setbkg(path, modeFrame.getloadframe().isTour);
        StartGame(temp);
    }

    public boolean getisTimelimit()
    {
        return this.isTimelimit;
    }

    public int getTempTime()
    {
        return tempTime;
    }

    public void setTimelimit(int tempTime)
    {
        TimeLimit = tempTime;
    }
    public void resetTimer()
    {
        timer.cancel();
        timer = new Timer();
    }
    public void limitMode() {
        TimeLabel.setText("Time: " + TimeLimit);
        tempTime = TimeLimit;
        TimeLimit++;
        TimerTask countTime = new TimerTask() {
            @Override
            public void run() {
                if (TimeLimit > 0) {
                    TimeLimit--;
                    TimeLabel.setText("Time: " + TimeLimit);
                    if (TimeLimit == 0 && !gamePanel.getisOver()) {
                        System.out.println("Game Over!");
                        gamePanel.getModel().setisMove(false);
                        gamePanel.setifOver(true);
                        JOptionPane.showMessageDialog(null, "Times Out");
                    }
                    if (gamePanel.getisOver())
                    {
                        this.cancel();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(countTime,0,1000);
    }
}