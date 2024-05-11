package view;

import controller.GameController;
import model.GridNumber;

import javax.swing.*;
import java.awt.*;


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
            Object[] options = {"Restart", "Change Mode"};
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
}
