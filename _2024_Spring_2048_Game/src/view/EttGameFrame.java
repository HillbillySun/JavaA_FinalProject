package view;

import util.Font;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EttGameFrame extends GameFrame {
    private boolean isTool = false;
    private JButton Tool;

    public EttGameFrame(int width, int height, int COUNT, int Target, String path, String tourist, boolean isTimelimit, int timeLimit) {
        super(width, height, COUNT, Target, path, tourist, isTimelimit, timeLimit);
        setBck.removeActionListener(actionListener);
        this.setBck.addActionListener(e -> {
            Object[] options = {"香港", "天文台", "曼哈顿", "默认"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "选择你的主题!",
                    "选择主题",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (result == 0) {
                ettreOpen("/Pictures/香港.jpg");
            } else if (result == 1) {
                ettreOpen("/pictures/天文台.jpg");
            } else if (result == 2) {
                ettreOpen("/Pictures/曼哈顿.jpg");
            } else if (result == 3){
                ettreOpen(null);
            }
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

    public void creatTool() {
        if (isTool) {
            Tool = createButton("Tool", new Point(700, 415), 110, 50);
            Tool.setFont(Font.creatFont("src/ttfFont/Jersey10-Regular.ttf",20f));
            super.getBackgroundPanel().add(Tool);
            Tool.addActionListener(e -> {
                if (!super.getGamePanel().getisOver())
                {
                    Object[] options = {"炸弹","翻倍"};
                    int result = JOptionPane.CLOSED_OPTION;
                    result = JOptionPane.showOptionDialog(null,
                            "选择道具",
                            "道具",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (result == 0)
                    {
                        if (super.getGamePanel().getModel().isBomb())
                        {
                            super.getGamePanel().getModel().ReviveNumbers();
                            playaudio("src/Music/Bomb.wav");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"无数字无法继续!");
                        }
                    }
                    else if (result == 1)
                    {
                        if (super.getGamePanel().getModel().mutiNumbers())
                        {
                            playaudio("src/Music/mutiaction.wav");
                        }
                    }
                    super.getGamePanel().updateGridsNumber();
                    super.getGamePanel().requestFocusInWindow();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"游戏已结束");
                }
            });
        }
    }

    public void setisTool(boolean b) {
        isTool = b;
    }

    public void ettsetBck(String path, boolean isTour) {
        super.setbkg(path, true);
        if (isTool) {
            creatTool();
        }
    }
    private void playaudio(String path)
    {
        new Thread(()->
        {
            try {
                File audioFile = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                // 获取音频格式
                AudioFormat format = audioStream.getFormat();

                // 获取音频数据行信息
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                // 获取音频数据行
                Clip audioClip = (Clip) AudioSystem.getLine(info);

                // 打开音频剪辑并加载样本
                audioClip.open(audioStream);
                audioClip.start();
                System.out.println("播放 "+path);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                System.out.println(ex);
            }
        }).start();
    }
    public void ettreOpen(String path) {
        EttGameFrame temp = this;
        this.dispose();
        temp.ettsetBck(path, true);
        StartGame(temp);
    }
}
