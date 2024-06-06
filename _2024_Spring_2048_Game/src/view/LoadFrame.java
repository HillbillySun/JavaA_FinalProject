package view;

import controller.GameController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadFrame extends JFrame {
    private JButton registerButton;
    private JButton loginButton;
    private JButton TouristBtn;
    private GameController controller;
    private InitiaFrame initiaFrame;
    private JLabel welcomeLabel;
    private JLabel designerLabel;
    private JLabel hintLabel;
    protected boolean isTour;

    private JPanel backgroundPanel;


    public LoadFrame(int width, int height) {
        Font buttonfont = util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf",40f);
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("用户名:");
        JTextField nameField = new JTextField(18);
        JLabel passwordLabel = new JLabel("密码:");
        JTextField passwordField = new JPasswordField(18);
        this.controller = new GameController();
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        setTitle("2048 Game");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        registerButton = createButton("Sign Up", new Point(100, 450), 300, 90);
        loginButton = createButton("Log In", new Point(300, 300), 300, 90);
        TouristBtn = createButton("Tourist", new Point(500, 450), 300, 90);
        registerButton.setFont(buttonfont);
        loginButton.setFont(buttonfont);
        TouristBtn.setFont(buttonfont);
        registerButton.setFont(buttonfont);
        welcomeLabel = createLabel("Welcome to 2048", util.Font.creatFont("src/ttfFont/Jersey10-Regular.ttf",125f),new Point(75,70),800,100);
        designerLabel = createLabel("Designed by BillSun and lv05327", util.Font.creatFont("src/ttfFont/DancingScript-VariableFont_wght.ttf",35f),new Point(220,200),500,50);
        hintLabel = createLabel("Hint: Only 'Log in' and 'Tourist' can enter the game!", util.Font.creatFont("src/ttfFont/DancingScript-VariableFont_wght.ttf",25f),new Point(210,580),500,40);
        hintLabel.setForeground(Color.RED);
        JPanel bkgPanel = new JPanel();
        bkgPanel.setBackground(new Color(255, 237, 211));
        this.backgroundPanel = bkgPanel;
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);
        backgroundPanel.add(registerButton);
        backgroundPanel.add(loginButton);
        backgroundPanel.add(TouristBtn);
        backgroundPanel.add(welcomeLabel);
        backgroundPanel.add(designerLabel);
        backgroundPanel.add(hintLabel);
        this.registerButton.addActionListener(e ->
        {
            String name = JOptionPane.showInputDialog(this, "输入你想要的用户名");
            controller.CheckRegister(name);
            if (controller.isRegister()) {
                String password = JOptionPane.showInputDialog(this, "设置你的密码");
                System.out.println("用户名: " + name);
                System.out.println("密码: " + password);
                try {
                    controller.Register(name, password);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(LoadFrame.this, "注册成功！");
            } else {
                JOptionPane.showMessageDialog(LoadFrame.this, "抱歉，该用户名已被占用");
            }
        });
        this.loginButton.addActionListener(e ->
                {
                    int result = JOptionPane.showConfirmDialog(null, panel, "登录", JOptionPane.OK_CANCEL_OPTION);
                    String name = null;
                    String password = null;
                    if (result == JOptionPane.OK_OPTION) {
                        name = nameField.getText();
                        password = passwordField.getText();
                        System.out.println("用户名: " + name);
                        System.out.println("密码: " + password);
                    }
                    controller.CheckLogin(name);
                    if (controller.isExsit()) {
                        controller.LoginGame(name, password);
                        if (controller.isLogin()) {
                            isTour = false;
                            JOptionPane.showMessageDialog(LoadFrame.this, "登录成功！");
                            InitiaFrame initiaFrame = new InitiaFrame(600, 800);
                            initiaFrame.setLoadFrame(this);
                            User.CurrentUser = name;
                            InitiaFrame.OpenInitial(initiaFrame);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(LoadFrame.this, "密码错误，登录失败！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoadFrame.this, "用户名不存在，登录失败！");
                    }
                }
        );
        this.TouristBtn.addActionListener(e -> {
            isTour = true;
            ModeFrame modeFrame = new ModeFrame(900, 700, null);
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
                new LoadFrame(900, 700).setVisible(true);
            }
        });
    }

    private JLabel createLabel(String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        this.add(label);
        return label;
    }

    public boolean getisTour() {
        return isTour;
    }
}