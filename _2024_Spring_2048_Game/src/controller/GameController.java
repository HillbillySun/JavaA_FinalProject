package controller;

import model.GridNumber;
import view.*;
import util.Filer;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController {
    private GamePanel view;
    private GridNumber model;
    private InitiaFrame initiaFrame;
    private GameFrame gameFrame;
    private ModeFrame modeFrame;
    private LoadFrame loadFrame;
    private boolean register = false;
    private boolean login = false;
    private boolean exsit = false;
    private boolean read = false;
    private boolean safe = true;

    public GameController(GamePanel view, GridNumber model, GameFrame gameFrame, ModeFrame modeFrame, InitiaFrame initiaFrame) {
        this.view = view;
        this.model = model;
        this.gameFrame = gameFrame;
        this.modeFrame = modeFrame;
        this.initiaFrame = initiaFrame;
    }

    public GameController() {
    }

    public void restartGame() {
        if (view.getisOver() && !gameFrame.getsiplay()) {
            gameFrame.getAudioClip().stop();
            gameFrame.getAudioClip().close();
            gameFrame.setIsplay(true);
        }
        System.out.println("Do restart game here");
        view.removeHistory();
        view.refreshGame();
        view.setifOver(false);
    }

    public void endGame() {
        if (!gameFrame.getsiplay()) {
            gameFrame.getAudioClip().stop();
            gameFrame.getAudioClip().close();
            gameFrame.setIsplay(true);
        }
        try {
            gameFrame.getLimitTimer().cancel();
        } catch (NullPointerException ignored) {
        }
        try {
            gameFrame.getSaveTimer().cancel();
        } catch (NullPointerException ignored) {
        }
        System.out.println("Game Over!");
        model.setisMove(false);
        view.setifOver(true);
        view.removeHistory();
    }

    public void saveGame() {
        int a=0;
        if(model.getisHard()){
            a=1;
        }
        System.out.println("isOver = " + view.getisOver());
        if (!view.getisOver()) {
            Filer.SaveNumber(model.getNumbers(), view.getSteps(), view.getTarget(), view.getPoints(), view.getCOUNT(),a);
            Timer timer = new Timer();
            gameFrame.getSaveLabel().setVisible(true);
            TimerTask twinkle = new TimerTask() {
                @Override
                public void run() {
                    gameFrame.getSaveLabel().setVisible(false);
                }
            };
            timer.schedule(twinkle, 1500);
        } else {
            JOptionPane.showMessageDialog(null, "游戏已结束，不可存档");
        }
    }

    public void CheckLogin(String username) {
        if (!Filer.CheckDirectory(username)) {
            System.out.println("不存在该用户");
        }
        if (Filer.CheckDirectory(username)) {
            setExsit(true);
            System.out.println("用户存在");
        }
    }

    public void LoginGame(String username, String password) {
        if (Objects.equals(password, Filer.ReadPassword(username))) {
            setLogin(true);
            System.out.println("密码正确成功登录");
        } else {
            System.out.println("密码错误登录失败");
        }
    }

    public void CheckRegister(String username) {
        if (!Filer.CheckDirectory(username)) {
            setRegister(true);
            System.out.println("这是未使用过的用户名");
        }
        if (Filer.CheckDirectory(username)) {
            System.out.println("用户名已被占用");
        }
    }

    public void Register(String username, String password) throws IOException {
        Filer.WriteUsersInitial(username, password);
    }

    public void Revive() {
        System.out.println("Revive!");
        view.reviveGame();
        view.setifOver(false);
        if (!gameFrame.getsiplay()) {
            gameFrame.setIsplay(true);
            gameFrame.playmusic("Music/Yellow Magic Orchestra - ファイアークラッカー.wav");
        }
    }

    public void CheckRead() {
        if (Filer.CheckRead()) {
            setRead(true);
            System.out.println("读取成功！");
        } else {
            System.out.println("读取失败！");
        }
    }

    public void CheckSafety() {
        if(Filer.ReadHard()==0){
        if (Filer.ReadTarget() != 1024 && Filer.ReadTarget() != 2048) {
            safe = false;
        }
        System.out.println("fisrt safe = "+safe);
        if (Filer.ReadPoint() % 2 != 0 && Filer.ReadPoint() !=1 ) {
            safe = false;
        }
        System.out.println("second safe = "+safe);
        int[][] a = Filer.ReadArray();
        for (int i = 0; i < Filer.ReadCount(); i++) {
            for (int j = 0; j < Filer.ReadCount(); j++) {
                if (a[i][j] != 0) {
                    double logBase2 = Math.log(a[i][j]) / Math.log(2);
                    if (logBase2 != (int) logBase2 || a[i][j] == 1) {
                        safe = false;
                    }
                }
            }
        }
        System.out.println("third safe = "+safe);
        }
        if(Filer.ReadHard()==1){
            if (Filer.ReadTarget() != 1024 && Filer.ReadTarget() != 2048) {
                safe = false;
            }
            System.out.println("fisrt safe = "+safe);
            if (Filer.ReadPoint() % 2 != 0 && Filer.ReadPoint() !=1 ) {
                safe = false;
            }
            System.out.println("second safe = "+safe);
            int[][] a = Filer.ReadArray();
            for (int i = 0; i < Filer.ReadCount(); i++) {
                for (int j = 0; j < Filer.ReadCount(); j++) {
                    if (a[i][j] != 0) {
                        double logBase2 = Math.log(a[i][j]) / Math.log(2);
                        if (logBase2 != (int) logBase2) {
                            safe = false;
                        }
                    }
                }
            }
            System.out.println("third safe = "+safe);
        }

    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isRegister() {
        return register;
    }

    public void setExsit(boolean exsit) {
        this.exsit = exsit;
    }

    public boolean isExsit() {
        return exsit;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isSafe() {
        return safe;
    }

    public boolean isRead() {
        return read;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void setView(GamePanel gamePanel) {
        this.view = gamePanel;
    }

    public void setModel(GridNumber model) {
        this.model = model;
    }
    //todo: add other methods such as loadGame, saveGame...
}
