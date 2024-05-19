package view;

import controller.GameController;
import model.LoadModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadFrame extends JFrame {
    private JButton registerButton;
    private JButton loginButton;
    private JButton TouristBtn;
    private GameController controller;
    private InitiaFrame initiaFrame;

    protected boolean isTour = true;

    private JPanel backgroundPanel;

    public LoadFrame(int width, int height) {
        setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        registerButton = createButton("Sign Up", new Point(150, 35), 300, 90);
        loginButton = createButton("Log In", new Point(150, 155), 300, 90);
        TouristBtn = createButton("Tourist",new Point(150,275),300,90);
        JPanel bkgPanel = new JPanel();
        bkgPanel.setBackground(new Color(255, 237, 211));
        this.backgroundPanel = bkgPanel;
        backgroundPanel.setLayout(null); // 使用绝对布局
        this.setContentPane(backgroundPanel);
        backgroundPanel.add(registerButton);
        backgroundPanel.add(loginButton);
        backgroundPanel.add(TouristBtn);
        this.registerButton.addActionListener(e ->
        {
            String str1 = JOptionPane.showInputDialog(this, "输入你的用户名");
            String str2 = JOptionPane.showInputDialog(this, "输入你的密码");
            System.out.println("用户名: " + str1);
            System.out.println("密码: " + str2);
            boolean success = LoadModel.register(str1, str2);
            if (success) {
                JOptionPane.showMessageDialog(LoadFrame.this, "注册成功！");
                InitiaFrame initiaFrame = new InitiaFrame(600, 500);
                initiaFrame.setLoadFrame(this);
                InitiaFrame.OpenInitial(initiaFrame);
                isTour = false;
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(LoadFrame.this, "已经存在，请更换用户名和密码！");
            }
        });
        this.loginButton.addActionListener(e ->
                {
                    String str1 = JOptionPane.showInputDialog(this, "输入你的用户名");
                    String str2 = JOptionPane.showInputDialog(this, "输入你的密码");
                    System.out.println("用户名: " + str1);
                    System.out.println("密码: " + str2);
                    boolean success = LoadModel.login(str1, str2);
                    if (success) {
                        JOptionPane.showMessageDialog(LoadFrame.this, "登录成功！");
                        InitiaFrame initiaFrame = new InitiaFrame(600, 500);
                        initiaFrame.setLoadFrame(this);
                        InitiaFrame.OpenInitial(initiaFrame);
                        isTour = false;
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoadFrame.this, "用户名或密码错误，登录失败！");
                    }
                }
        );
        this.TouristBtn.addActionListener(e -> {
            ModeFrame modeFrame = new ModeFrame(900,700,null);
            modeFrame.setLoadFrame(this);
            ModeFrame.OpenMode(modeFrame);
            this.dispose();
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

    public static void OpenLoad() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoadFrame(600, 500).setVisible(true);
            }
        });
    }

    public boolean getisTour()
    {
        return isTour;
    }
}