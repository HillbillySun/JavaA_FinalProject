package view;

import controller.GameController;
import jdk.jshell.execution.Util;
import util.Font;

import javax.swing.*;
import java.awt.*;

public class ModeFrame extends JFrame {
    private JButton ClassicBtn;
    private JButton EasyBtn;
    private JButton HardBtn;
    private JButton EttBtn;
    private JPanel backgroundPanel;
    private GameFrame gameFrame;
    private EttGameFrame ettGameFrame;
    private InitiaFrame initiaFrame;
    private GameController controller;
    private LoadFrame loadFrame;
    protected boolean ifDispole;
    private JLabel ChooseMode;
    private JLabel begingame;
    ModeFrame(int width,int height,InitiaFrame initiaFrame)
    {
        this.initiaFrame=initiaFrame;
        this.setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.ClassicBtn=createButton("Classic",new Point(100,400),300,80);
        this.EasyBtn=createButton("Easy",new Point(100,510),300,80);
        this.HardBtn=createButton("Hard",new Point(500,400),300,80);
        this.EttBtn =createButton("Entertaiment",new Point(500,510),300,80);
        begingame = createLabel("Begin Your Game",util.Font.creatFont("ttfFont/Jersey10-Regular.ttf",100f),new Point(150,235),900,100);
        ChooseMode = createLabel("Choose Your Mode", util.Font.creatFont("ttfFont/Jersey10-Regular.ttf",100f),new Point(125,100),900,100);
        ifDispole=false;
        JPanel bkgPanel = new JPanel();
        bkgPanel.setBackground(new Color(255, 237, 211));
        this.backgroundPanel = bkgPanel;
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);
        backgroundPanel.add(ClassicBtn);
        backgroundPanel.add(HardBtn);
        backgroundPanel.add(EttBtn);
        backgroundPanel.add(EasyBtn);
        backgroundPanel.add(ChooseMode);
        backgroundPanel.add(begingame);
        this.ClassicBtn.addActionListener(e->
        {
            if (loadFrame.getisTour())
            {
                gameFrame=new GameFrame(900,700,4,2048,null,"Tourist",false,0);
            }
            else
            {
                gameFrame=new GameFrame(900,700,4,2048,0,null,false,0);
            }
            gameFrame.setModeFrame(this);
            gameFrame.getGamePanel().setModeFrame(this);
            this.controller=new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this,this.initiaFrame);
            gameFrame.setController(this.controller);
            gameFrame.getGamePanel().setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"经典4*4，合成2048!");
            ifDispole=true;
        });
        this.EasyBtn.addActionListener(e->
        {
            if (loadFrame.getisTour())
            {
                gameFrame=new GameFrame(900,700,4,1024,null,"Tourist",false,0);
            }
            else
            {
                gameFrame=new GameFrame(900,700,4,1024,0,null,false,0);
            }
            gameFrame.setModeFrame(this);
            gameFrame.getGamePanel().setModeFrame(this);
            this.controller=new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this,this.initiaFrame);
            gameFrame.setController(this.controller);
            gameFrame.getGamePanel().setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"简单4*4，合成1024即可!");
            ifDispole=true;
        });
        this.HardBtn.addActionListener(e->
        {
            if (loadFrame.getisTour())
            {
                gameFrame=new GameFrame(900,700,3,2048,null,"Tourist",false,0);
            }
            else
            {
                gameFrame=new GameFrame(900,700,3,2048,0,null,false,0);
            }
            gameFrame.setModeFrame(this);
            gameFrame.getGamePanel().setModeFrame(this);
            this.controller=new GameController(gameFrame.getGamePanel(),gameFrame.getGamePanel().getModel(),gameFrame,this,this.initiaFrame);
            gameFrame.setController(this.controller);
            gameFrame.getGamePanel().setController(this.controller);
            this.dispose();
            GameFrame.StartGame(gameFrame);
            JOptionPane.showMessageDialog(gameFrame,"使用3*3，合成2048!");
            ifDispole=true;
        });
        this.EttBtn.addActionListener(e->
        {
            String str1 = JOptionPane.showInputDialog(this, "输入你想要的棋盘尺寸(2-10中的整数)");
            String str2 = JOptionPane.showInputDialog(this, "输入你的目标数字(128到2048中的2的幂次)：");
            String str3 = JOptionPane.showInputDialog(this,"输入时间限制");
            Object[] options = {"Yes","No"};
            int result = JOptionPane.CLOSED_OPTION;
            result = JOptionPane.showOptionDialog(null, "是否需要道具模式?", "道具模式设置", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            System.out.println("size: "+str1);
            System.out.println("target: "+str2);
            System.out.println("Time: "+str3);
            int boardSize;
            int target;
            boolean isTimeLimit = (GameFrame.isInteger(str3) && Integer.parseInt(str3) > 0);
            System.out.println("isTimelimit: " + isTimeLimit);
            if (GameFrame.isInteger(str1) && GameFrame.isInteger(str2))
            {
                boardSize=Integer.parseInt(str1);
                target=Integer.parseInt(str2);
                if (boardSize >= 2 && boardSize <= 10 && target > 0 && GameFrame.isPowerOfTwo(target)){
                    if (isTimeLimit)ettGameFrame=new EttGameFrame(900,700,boardSize,target,null,"Tourist",true,Integer.parseInt(str3));
                    else ettGameFrame=new EttGameFrame(900,700,boardSize,target,null,"Tourist",false,0);
                    ettGameFrame.setModeFrame(this);
                    ettGameFrame.getGamePanel().setModeFrame(this);
                    this.controller=new GameController(ettGameFrame.getGamePanel(),ettGameFrame.getGamePanel().getModel(),ettGameFrame,this,this.initiaFrame);
                    ettGameFrame.setController(this.controller);
                    ettGameFrame.getGamePanel().setController(this.controller);
                    if (result == 0)
                    {
                        ettGameFrame.setisTool(true);
                        ettGameFrame.creatTool();
                    }
                    this.dispose();
                    GameFrame.StartGame(ettGameFrame);
                    JOptionPane.showMessageDialog(ettGameFrame,String.format("使用%d*%d，合成%d!",boardSize,boardSize,target));
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
        button.setBackground(new Color(175, 158, 137));
        button.setForeground(Color.WHITE);
        this.add(button);
        button.setFont(Font.creatFont("ttfFont/Jersey10-Regular.ttf",30f));
        return button;
    }
    private JLabel createLabel(String name, java.awt.Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        this.add(label);
        return label;
    }
    public static void OpenMode(ModeFrame modeFrame)
    {
        SwingUtilities.invokeLater(() -> {
            modeFrame.setVisible(true);
        });
    }

    public void setLoadFrame(LoadFrame loadFrame)
    {this.loadFrame = loadFrame;}

    public LoadFrame getloadframe()
    {
        return loadFrame;
    }
}
