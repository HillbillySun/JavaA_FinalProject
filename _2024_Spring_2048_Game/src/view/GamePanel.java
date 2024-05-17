package view;

import controller.GameController;
import model.GridNumber;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends ListenerPanel {
    private int COUNT = 4;
    private GridComponent[][] grids;

    private int Target=2048;
    private GridNumber model;
    private ModeFrame modeFrame;
    private JLabel stepLabel;
    private JLabel pointLabel;
    private int steps;
    private int points;
    private boolean isOver1 = false;
    private boolean isOver2 = false;
    private final int GRID_SIZE;
    private GameController controller;
    private GameFrame gameFrame;

    public GamePanel(int size,int COUNT,int target) {
        this.setTarget(target);
        this.setCOUNT(COUNT);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(size, size);
        this.GRID_SIZE = size / this.COUNT;
        this.grids = new GridComponent[this.COUNT][this.COUNT];
        this.model = new GridNumber(this.COUNT,this. COUNT);
        initialGame();
    }

    public GridNumber getModel() {
        return model;
    }

    public void initialGame() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = new GridComponent(i, j, model.getNumber(i, j), this.GRID_SIZE);
                grids[i][j].setLocation(j * GRID_SIZE, i * GRID_SIZE);
                this.add(grids[i][j]);
            }
        }
        model.printNumber();//check the 4*4 numbers in game
        this.repaint();
    }

    public void updateGridsNumber() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setNumber(model.getNumber(i, j));
            }
        }
        repaint();
    }
    /**
     * Implement the abstract method declared in ListenerPanel.
     * Do move right.
     */
    @Override
    public void doMoveRight() {
        if(!isOver1||!isOver2)
        {
            System.out.println("Click VK_RIGHT");
            this.model.moveRight();
            this.updateGridsNumber();
            this.afterMove();
        }
    }

    @Override
    public void doMoveLeft() {
        if(!isOver1||!isOver2)
        {
            System.out.println("Click VK_Left");
            this.model.moveLeft();
            this.updateGridsNumber();
            this.afterMove();
        }
    }

    @Override
    public void doMoveUp() {
        if(!isOver1||!isOver2){
            System.out.println("Click VK_Up");
            this.model.moveUp();
            this.updateGridsNumber();
            this.afterMove();
        }
    }

    @Override
    public void doMoveDown() {
        if(!isOver1||!isOver2){
            System.out.println("Click VK_Down");
            this.model.moveDown();
            this.updateGridsNumber();
            this.afterMove();
        }
    }

    public void afterMove() {
        if (model.getIfGenerate())
        {
            this.steps++;
        }
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        points+=model.getMarkPoint();
        this.pointLabel.setText(String.format("Points: %d",this.points));
        int[][] clonenumber=new int[COUNT+2][COUNT+2];
        Loop1:
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                clonenumber[i+1][j+1]=model.getNumber(i,j);
                if (model.getNumber(i,j)==Target)
                {
                    isOver1 =true;
                    break Loop1;
                }
            }
        }
        boolean isMax=true;
        Loop2:
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                int x=i+1;
                int y=j+1;
                if (clonenumber[x][y]==0)
                {
                    isMax=false;
                    break Loop2;
                }
            }
        }
        Loop3:
        if (isMax)
        {
            int count=0;
            for (int i = 0; i < COUNT; i++) {
                for (int j = 0; j < COUNT; j++) {
                    int x=i+1;
                    int y=j+1;
                    int mark=clonenumber[x][y];
                    if (clonenumber[x-1][y]!=mark&&clonenumber[x][y-1]!=mark&&clonenumber[x+1][y]!=mark&&clonenumber[x][y+1]!=mark)
                    {
                       count++;
                    }
                }
            }
            if (count==COUNT*COUNT)
            {
                isOver2=true;
            }
        }
        if (isOver1)
        {
            JOptionPane.showMessageDialog(this,"You Win!");
            controller.endGame();
        }
        else if (isOver2)
        {
            Object[] options = {"Restart", "Change Mode","Revive"};
            int result = JOptionPane.CLOSED_OPTION; // 初始值设为 CLOSED_OPTION，表示用户还没有做出选择
            controller.endGame();
            while (result == JOptionPane.CLOSED_OPTION) {
                result = JOptionPane.showOptionDialog(null, "You lose, try again!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            }
            if (result == 0) {
                controller.restartGame();
            } else if (result == 1) {
                ModeFrame.OpenMode(modeFrame);
                gameFrame.dispose();
            } else if (result==2) {
                playAudio("C:\\Users\\Lenovo\\Downloads\\SUSTech_CSE_Projects-main\\SUSTech_CSE_Projects-main\\CS109_2022_Fall\\Chess\\out\\production\\Chess\\Chess\\Resources\\bgm1.wav",15000);
                JFrame adsframe=new JFrame();
                adsframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                adsframe.setResizable(false);
                adsframe.addWindowStateListener(new WindowStateListener() {
                    @Override
                    public void windowStateChanged(WindowEvent e) {
                        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
                            adsframe.setState(Frame.NORMAL);
                        }
                    }
                });

                adsframe.setLayout(new BorderLayout());
                adsframe.setSize(300,300);
                adsframe.setLocationRelativeTo(null);
                ImageIcon gifIcon = new ImageIcon("C:\\Users\\Lenovo\\Documents\\GitHub\\JavaA_FinalProject\\_2024_Spring_2048_Game\\src\\Pictures\\IMG_9298.gif");
                JLabel gifLabel = new JLabel(gifIcon);
                adsframe.add(gifLabel, BorderLayout.CENTER);
                adsframe.setVisible(true);
                Timer timer = new Timer();
                TimerTask revive = new TimerTask() {
                    @Override
                    public void run() {
                        controller.Revive();
                        timer.cancel();
                        adsframe.dispose();
                        JOptionPane.showMessageDialog(null,"Revive! Continue! ");
                    }
                };
                timer.schedule(revive,15000);
            }
        }
    }

    public void refreshGame()
    {
        this.isOver1=false;
        this.isOver2=false;
        model.setisMove(true);
        this.points=0;
        this.steps=0;
        model.initialNumbers();
        this.updateGridsNumber();
        this.stepLabel.setText("Start");
        this.pointLabel.setText(String.format("Points: %d",this.points));
    }

    public void reviveGame()
    {
        this.isOver1=false;
        this.isOver2=false;
        model.setisMove(true);
        model.ReviveNumbers();
        this.updateGridsNumber();
    }
    public void setSteps(int steps)
    {
        this.steps=steps;
    }

    public void setPoints(int point)
    {
        this.points =point;
    }
    public int getPoints()
    {
        return this.points;
    }


    public void setStepLabel()
    {
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public void setPointLabel(JLabel pointLabel)
    {
        this.pointLabel=pointLabel;
    }

    public int getCOUNT()
    {
        return COUNT;
    }

    public void setCOUNT(int COUNT)
    {
        this.COUNT=COUNT;
    }

    public void setTarget(int target)
    {
        this.Target=target;
    }
    public void setController(GameController controller)
    {
        this.controller=controller;
    }
    public void setifOver(boolean b)
    {
        isOver1=b;
        isOver2=b;
    }
    public void setModeFrame(ModeFrame modeFrame)
    {
        this.modeFrame=modeFrame;
    }
    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame=gameFrame;
    }
    private void playAudio(String filePath,int time) {
        try {
            // 打开音频文件
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // 获取音频格式
            AudioFormat format = audioStream.getFormat();

            // 获取音频数据行信息
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // 获取音频数据行
            Clip audioClip = (Clip) AudioSystem.getLine(info);

            // 打开音频剪辑并加载样本
            audioClip.open(audioStream);

            // 播放音频
            audioClip.start();
            Timer timer = new Timer();
            TimerTask stopMusic=new TimerTask() {
                @Override
                public void run() {
                    if (audioClip != null && audioClip.isRunning()) {
                        audioClip.stop();
                        audioClip.close();
                        System.out.println("播放停止");
                    }
                }
            };
            timer.schedule(stopMusic,time);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
