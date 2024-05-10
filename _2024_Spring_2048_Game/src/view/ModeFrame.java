package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ModeFrame extends JFrame {
    private JButton ClassicBtn;
    private JButton EasyBtn;
    private JButton HardBtn;
    private JButton EditBtn;
    private GameFrame gameFrame;
    private GameController controller;
    protected boolean ifDispole;

    ModeFrame(int width,int height)
    {
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.ClassicBtn=createButton("Classic",new Point(350,150),200,70);
        this.EasyBtn=createButton("Easy",new Point(350,250),200,70);
        this.HardBtn=createButton("Hard",new Point(350,350),200,70);
        this.EditBtn=createButton("Edit by Yourself",new Point(350,450),200,70);
        ifDispole=false;
        this.ClassicBtn.addActionListener(e->
        {
            gameFrame=new GameFrame(900,700,4,2048);
            gameFrame.setModeFrame(this);
            this.controller = new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this);
            gameFrame.setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"经典4*4，合成2048!");
            ifDispole=true;
        });
        this.EasyBtn.addActionListener(e->
        {
            gameFrame=new GameFrame(900,700,4,1024);
            this.controller = new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this);
            gameFrame.setModeFrame(this);
            gameFrame.setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"简单4*4，合成1024即可!");
            ifDispole=true;
        });
        this.HardBtn.addActionListener(e->
        {
            gameFrame=new GameFrame(900,700,3,2048);
            this.controller = new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this);
            gameFrame.setModeFrame(this);
            gameFrame.setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"使用3*3，合成2048!");
            ifDispole=true;
        });
        this.EditBtn.addActionListener(e->
        {
            String str1 = JOptionPane.showInputDialog(this, "输入你想要的棋盘尺寸(2-10中的整数)");
            String str2 = JOptionPane.showInputDialog(this, "输入你的目标数字(128到2048中的2的幂次)：");
            System.out.println("size: "+str1);
            System.out.println("target: "+str2);
            int boardSize;
            int target;
            if (GameFrame.isInteger(str1)&&GameFrame.isInteger(str2))
            {
                boardSize=Integer.parseInt(str1);
                target=Integer.parseInt(str2);
                if (boardSize >= 2 && boardSize <= 10 && target > 0 && GameFrame.isPowerOfTwo(target)){
                    gameFrame=new GameFrame(900,700,boardSize,target);
                    this.controller = new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this);
                    gameFrame.setModeFrame(this);
                    gameFrame.setController(this.controller);
                    this.dispose();
                    GameFrame.StartGame(gameFrame);
                    JOptionPane.showMessageDialog(gameFrame,String.format("使用%d*%d，合成%d!",boardSize,boardSize,target));
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
        });
    }
    private JButton createButton(String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        this.add(button);
        return button;
    }
    public static void OpenMode()
    {
        SwingUtilities.invokeLater(() -> {
            ModeFrame modeFrame = new ModeFrame(900, 700);
            modeFrame.setVisible(true);
        });
    }

}
