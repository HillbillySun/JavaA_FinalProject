package view;

import controller.GameController;
import model.GridNumber;
import util.Filer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private JLabel TimeLabel;
    private int steps;
    private int points;
    private boolean isOver1 = false;
    private boolean isOver2 = false;
    private final int GRID_SIZE;
    private GameController controller;
    private GameFrame gameFrame;
    private ArrayList<int[][]> History = new ArrayList<>();
    private int[][] historyNumbers;
    private ArrayList<Integer> Marks = new ArrayList<>();
    private int HistoryCount = 0;
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
        model.setGamePanel(this);
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
            createHistory();
            this.model.moveRight();
            this.afterMove();
//            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveLeft() {
        if(!isOver1||!isOver2)
        {
            System.out.println("Click VK_Left");
            createHistory();
            this.model.moveLeft();
            this.afterMove();
//            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveUp() {
        if(!isOver1||!isOver2){
            System.out.println("Click VK_Up");
            createHistory();
            this.model.moveUp();
            this.afterMove();
//            this.updateGridsNumber();
        }
    }

    @Override
    public void doMoveDown() {
        if(!isOver1||!isOver2){
            System.out.println("Click VK_Down");
            createHistory();
            this.model.moveDown();
            this.afterMove();
//            this.updateGridsNumber();
        }
    }

    public void afterMove() {
        if (model.getIfGenerate())
        {
            this.steps++;
            History.add(historyNumbers);
            Marks.add(model.getMarkPoint());
            HistoryCount++;
        }
        model.generateNumberRandomly();
        updateGridsNumber();
        System.out.println("History length = "+History.size());
        System.out.println("HistoryCount = "+HistoryCount);
        System.out.println("Steps = "+steps);
        System.out.println("Marks length = "+Marks.size());
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        points+=model.getMarkPoint();
        this.pointLabel.setText(String.format("Points: %d",this.points));
        int[][] clonenumber=new int[COUNT+2][COUNT+2];
        Loop1:
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                clonenumber[i+1][j+1]=model.getNumber(i,j);
                if (model.getNumber(i,j) >= Target)
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
            controller.endGame();
            this.getModel().playAction("Music/Win.wav");
            JOptionPane.showMessageDialog(this,"You Win!");
            if(!gameFrame.getisTour()){
            Filer.RecordPoint(String.valueOf(this.points));
            }
        }
        else if (isOver2)
        {
            getModel().playAction("Music/failEnd.wav");
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
                if (gameFrame.getAudioClip() != null)
                {
                    gameFrame.getAudioClip().stop();
                    System.out.println("volume turn to 0");
                }
                playAudio("Music/adsBGM.wav",15000);
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
                ImageIcon gifIcon = new ImageIcon("Pictures/IMG_9298.gif");
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
        if (gameFrame.getisTimelimit())
        {
            this.TimeLabel.setText("Time: "+gameFrame.getTempTime());
            gameFrame.resetTimer();
            gameFrame.setTimelimit(gameFrame.getTempTime());
            gameFrame.limitMode();
        }
        System.out.println("isTour = "+gameFrame.isTour);
        if (!gameFrame.isTour)
        {
            try {
                gameFrame.getSaveTimer().cancel();
                System.out.println( "cancel Timer Successfully" );
                gameFrame.autoSave(10000);
                System.out.println("Begin Save automatically");
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void reviveGame()
    {
        model.setisMove(true);
        model.ReviveNumbers();
        this.updateGridsNumber();
        if (gameFrame.getisTimelimit())
        {
            gameFrame.setTimelimit(gameFrame.getTempTime());
            gameFrame.resetTimer();
            gameFrame.limitMode();
        }
    }
    public void setSteps(int steps)
    {
        this.steps=steps;
    }

    public int getSteps() {
        return steps;
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

    public void setTimeLabel(JLabel timeLabel)
    {
        this.TimeLabel = timeLabel;
    }

    public void setCOUNT(int COUNT)
    {
        this.COUNT=COUNT;
    }

    public int getCOUNT() {
        return COUNT;
    }

    public void setTarget(int target)
    {
        this.Target=target;
    }
    public int getTarget() {
        return Target;
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
    public boolean getisOver()
    {return (isOver2||isOver1);}
    private void playAudio(String filePath,int time) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
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
            timer.cancel();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }
    public GridComponent[][] getGrids()
    {
        return grids;
    }

    public void undo()
    {
        if (!getisOver())
        {
            try {
                for (int i = 0; i < model.getNumbers().length; i++) {
                    for (int j = 0; j < model.getNumbers()[0].length; j++) {
                        model.getNumbers()[i][j] = History.get(HistoryCount-1)[i][j];
                    }
                }
                History.remove(HistoryCount-1);
                points -= Marks.get(HistoryCount-1);
                Marks.remove(HistoryCount-1);
                this.steps--;
                this.HistoryCount--;
                stepLabel.setText(String.format("Step: %d", this.steps));
                pointLabel.setText(String.format("Points: %d",this.points));
                updateGridsNumber();
            }catch (IndexOutOfBoundsException e)
            {
                playAudio("Music/siuuu.wav",2500);
                System.out.println(e);
                JFrame SQframe =new JFrame();
                SQframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                SQframe.setResizable(false);
                SQframe.setLayout(new BorderLayout());
                SQframe.setSize(600,600);
                SQframe.setLocationRelativeTo(null);
                ImageIcon SQwatching = new ImageIcon("Pictures/Watching.jpg");
                JPanel SQPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
                        g2d.drawImage(SQwatching.getImage(), 0, 0, getWidth(), getHeight(), this);
                        g2d.dispose();
                    }
                };
                SQframe.add(SQPanel, BorderLayout.CENTER);
                SQframe.setVisible(true);
            }
        }
        else if (isOver1)
        {
            JOptionPane.showMessageDialog(null,"你已经很完美了");
        }
        else if (isOver2)
        {
            JOptionPane.showMessageDialog(null,"生命中成功总是一时的，失败才是主旋律");
        }
    }

    @Override
    public void BackHome() {
        if (!gameFrame.getisTour())
        {
            controller.saveGame();
        }
        controller.endGame();
        gameFrame.dispose();
        LoadFrame.OpenLoad();
    }

    public void createHistory()
    {
        historyNumbers = new int[COUNT][COUNT];
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                historyNumbers[i][j] = this.getModel().getNumber(i,j);
            }
        }
        System.out.println("记录历史");
        for (int[] line : historyNumbers) {
            System.out.println(Arrays.toString(line));
        }
    }
    public void removeHistory() {
        History.clear();
        Marks.clear();
        HistoryCount = 0;
    }
}