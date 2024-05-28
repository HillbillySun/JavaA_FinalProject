package view;

import controller.GameController;
import model.User;
import util.ColorMap;
import util.Filer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

    protected JButton setBck;

    protected ActionListener actionListener;
    private JPanel backgroundPanel;

    private JButton musicBtn;

    private JLabel stepLabel;

    private JLabel pointLabel;

    private JLabel TimeLabel;
    private GameController controller;
    private GamePanel gamePanel;
    private ModeFrame modeFrame;
    private boolean isTimelimit;
    private int TimeLimit;
    private int tempTime;
    private Timer timer;
    private Clip audioClip;
    private boolean isplay;

    public GameFrame(int width, int height, int COUNT, int Target, int Point, int step,String path, boolean isTimelimit, int timeLimit) {
        isplay = true;
        this.TimeLimit = timeLimit;
        this.isTimelimit = isTimelimit;
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        ColorMap.InitialColorMap();
        gamePanel = new GamePanel((int) (this.getHeight() * 0.8), COUNT, Target);
        gamePanel.setGameFrame(this);
        gamePanel.setPoints(Point);
        gamePanel.setLocation(this.getHeight() / 15, this.getWidth() / 15);
        this.add(gamePanel);
        Font buttonfont = util.Font.creatFont("ttfFont/Jersey10-Regular.ttf",20f);
        this.restartBtn = createButton("Restart", new Point(700, 135), 110, 50);
        this.mode = createButton("Mode", new Point(700, 275), 110, 50);
        this.musicBtn = createButton("Music", new Point(700, 205), 110, 50);
        this.UpBtn = createButton("W", new Point(725, 480), 60, 60);
        this.DownBtn = createButton("S", new Point(725, 550), 60, 60);
        this.LeftBtn = createButton("A", new Point(655, 550), 60, 60);
        this.RightBtn = createButton("D", new Point(795, 550), 60, 60);
        this.SaveBtn = createButton("Save", new Point(700, 415), 110, 50);
        this.stepLabel = createLabel("Start", new Font("Arial", Font.PLAIN, 22), new Point(700, 30), 180, 50);
        this.pointLabel = createLabel("Point: "+Point, new Font("Arial", Font.PLAIN, 22), new Point(700, 55), 180, 50);
        this.TimeLabel = createLabel("Time: No Limit", new Font("Arial", Font.PLAIN, 22), new Point(700, 80), 180, 50);
        this.setBck = createButton("Theme", new Point(700, 345), 110, 50);
        restartBtn.setFont(buttonfont);
        mode.setFont(buttonfont);
        musicBtn.setFont(buttonfont);
        UpBtn.setFont(buttonfont);
        DownBtn.setFont(buttonfont);
        LeftBtn.setFont(buttonfont);
        RightBtn.setFont(buttonfont);
        SaveBtn.setFont(buttonfont);
        setBck.setFont(buttonfont);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setPointLabel(pointLabel);
        gamePanel.setTimeLabel(TimeLabel);
        gamePanel.getModel().setTarget(Target);
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.mode.addActionListener(e ->
        {
            if (!isplay)
            {
                audioClip.stop();
                audioClip.close();
            }
            gamePanel.getModel().playAction("Music/Win.wav");
            ModeFrame.OpenMode(this.modeFrame);
            this.dispose();
        });
        this.musicBtn.addActionListener(e -> {
            System.out.println(gamePanel.getisOver());
            if (!gamePanel.getisOver())
            {
                playmusic("Music/Yellow Magic Orchestra - ファイアークラッカー.wav");
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
        this.SaveBtn.addActionListener(e -> {
            controller.saveGame();
            gamePanel.requestFocusInWindow();
        });
        actionListener = e -> {
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
        };
        this.setBck.addActionListener(actionListener);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Save and exit", "Exit"};
                int result = JOptionPane.showOptionDialog(
                        GameFrame.this,
                        "Do you want to save this game?",
                        "Exit",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (result == 0) {
                    controller.saveGame();
                } else if (result == 1) {
                    dispose();
                }
            }
        });
        //todo: add other button here
        setbkg(path, false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public GameFrame(int width, int height, int COUNT, int Target, String path, String tourist, boolean isTimelimit, int timeLimit) {
        isplay = true;
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
        Font buttonfont = util.Font.creatFont("ttfFont/Jersey10-Regular.ttf",20f);
        this.restartBtn = createButton("Restart", new Point(700, 135), 110, 50);
        this.mode = createButton("Mode", new Point(700, 205), 110, 50);
        this.UpBtn = createButton("W", new Point(725, 480), 60, 60);
        this.DownBtn = createButton("S", new Point(725, 550), 60, 60);
        this.LeftBtn = createButton("A", new Point(655, 550), 60, 60);
        this.RightBtn = createButton("D", new Point(795, 550), 60, 60);
        this.stepLabel = createLabel("Start", new Font("Arial", Font.PLAIN, 22), new Point(700, 30), 180, 50);
        this.pointLabel = createLabel("Point: 0", new Font("Arial", Font.PLAIN, 22), new Point(700, 55), 180, 50);
        this.TimeLabel = createLabel("Time: No Limit", new Font("Arial", Font.PLAIN, 22), new Point(700, 80), 180, 50);
        this.setBck = createButton("Theme", new Point(700, 275), 110, 50);
        this.musicBtn = createButton("Music", new Point(700, 345), 110, 50);
        restartBtn.setFont(buttonfont);
        mode.setFont(buttonfont);
        UpBtn.setFont(buttonfont);
        DownBtn.setFont(buttonfont);
        LeftBtn.setFont(buttonfont);
        RightBtn.setFont(buttonfont);
        setBck.setFont(buttonfont);
        musicBtn.setFont(buttonfont);
        gamePanel.setStepLabel(stepLabel);
        gamePanel.setPointLabel(pointLabel);
        gamePanel.setTimeLabel(TimeLabel);
        gamePanel.getModel().setTarget(Target);
        if (isTimelimit) {
            timer = new Timer();
            limitMode();
        }
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.mode.addActionListener(e ->
        {
            if (!isplay)
            {
                audioClip.stop();
                audioClip.close();
            }
            if (timer != null)
            {
                timer.cancel();
            }
            gamePanel.getModel().playAction("Music/Win.wav");
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
        this.musicBtn.addActionListener(e -> {
            if (!gamePanel.getisOver())
            {
                playmusic("Music/Yellow Magic Orchestra - ファイアークラッカー.wav");
            }
            gamePanel.requestFocusInWindow();
        });
        actionListener = e -> {
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
        };
        this.setBck.addActionListener(actionListener);
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
            ImageIcon bg = new ImageIcon(this.getClass().getResource(path));
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
            backgroundPanel.add(musicBtn);
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
            backgroundPanel.add(musicBtn);
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

    public boolean getisTimelimit() {
        return this.isTimelimit;
    }

    public int getTempTime() {
        return tempTime;
    }

    public void setTimelimit(int tempTime) {
        TimeLimit = tempTime;
    }

    public void resetTimer() {
        timer.cancel();
        timer = new Timer();
    }

    public Clip getAudioClip()
    {
        return audioClip;
    }

    public boolean getsiplay()
    {
        return isplay;
    }

    public void setIsplay(boolean b)
    {isplay = b;}

    public JPanel getBackgroundPanel()
    {
        return backgroundPanel;
    }
    public void playmusic(String path) {
        new Thread(() ->
        {
            try {
                File audioFile = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                if (isplay) {
                    audioClip = (Clip) AudioSystem.getLine(info);
                    audioClip.open(audioStream);
                    System.out.println(isplay);
                    System.out.println(audioClip);
                    audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                    audioClip.start();
                    isplay = false;
                    System.out.println("bgm播放开始");
                }
                else
                {
                    System.out.println("isplay = "+isplay);
                    System.out.println(audioClip);
                    audioClip.stop();
                    audioClip.close();
                    isplay = true;
                    System.out.println("bgm停止");
                }
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                System.out.println(ex);
            }
        }).start();
    }

    public Timer getTimer()
    {
        return timer;
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
                        if (!isplay)
                        {
                            audioClip.stop();
                            audioClip.close();
                            isplay = true;
                        }
                        gamePanel.getModel().playAction("Music/failEnd.wav");
                        System.out.println("Game Over!");
                        controller.endGame();
                        JOptionPane.showMessageDialog(null, "Times Out");
                    }
                    if (gamePanel.getisOver()) {
                        this.cancel();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(countTime, 0, 1000);
    }
}