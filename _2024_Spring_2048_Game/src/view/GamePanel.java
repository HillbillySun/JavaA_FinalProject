package view;

import model.GridNumber;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends ListenerPanel {
    private int COUNT = 4;
    private GridComponent[][] grids;

    private int Target=2048;
    private GridNumber model;
    private JLabel stepLabel;
    private JLabel pointLabel;
    private int steps;
    private int points;
    private final int GRID_SIZE;

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
        System.out.println("Click VK_RIGHT");
        this.model.moveRight();
        this.updateGridsNumber();
        this.afterMove();
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_Left");
        this.model.moveLeft();
        this.updateGridsNumber();
        this.afterMove();
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        this.model.moveUp();
        this.updateGridsNumber();
        this.afterMove();
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_Down");
        this.model.moveDown();
        this.updateGridsNumber();
        this.afterMove();
    }

    public void afterMove() {
        boolean isOver=false;
        if (model.getIfGenerate())
        {
            this.steps++;
        }
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        points+=model.getMarkPoint();
        this.pointLabel.setText(String.format("Points: %d",this.points));
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                if (model.getNumber(i,j)==Target)
                {
                    isOver=true;
                }
            }
        }
        if (isOver)
        {
            JOptionPane.showMessageDialog(this,"You Win!");
        }
    }

    public void refreshGame()
    {
        this.points=0;
        this.steps=0;
        model.initialNumbers();
        this.updateGridsNumber();
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        this.pointLabel.setText(String.format("Point: %d",this.points));
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
}
