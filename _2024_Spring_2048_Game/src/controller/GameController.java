package controller;

import model.GridNumber;
import view.*;
import util.Filer;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController{
    private GamePanel view;
    private GridNumber model;
    private InitiaFrame initiaFrame;
    private GameFrame gameFrame;
    private ModeFrame modeFrame;
    private LoadFrame loadFrame;
    private boolean register=false;
    private boolean login=false;
    private boolean exsit=false;
    private boolean read=false;
    public GameController(GamePanel view, GridNumber model, GameFrame gameFrame,ModeFrame modeFrame,InitiaFrame initiaFrame) {
        this.view = view;
        this.model = model;
        this.gameFrame=gameFrame;
        this.modeFrame=modeFrame;
        this.initiaFrame=initiaFrame;
    }
    public GameController(){}
    public void restartGame() {
        System.out.println("Do restart game here");
        view.refreshGame();
        view.setifOver(false);
    }
    public void endGame()
    {
        System.out.println("Game Over!");
        model.setisMove(false);
        view.setifOver(true);
    }
    public void saveGame()
    {
        Filer.SaveNumber(model.getNumbers(),view.getCOUNT(),view.getTarget());
    }
    public void CheckLogin(String username)
    {
        if(!Filer.CheckDirectory(username)){
            System.out.println("不存在该用户");
        }
        if(Filer.CheckDirectory(username)){
            setExsit(true);
            System.out.println("用户存在");
        }
    }
    public void LoginGame(String username,String password){
        if (Objects.equals(password, Filer.ReadPassword(username))){
            setLogin(true);
            System.out.println("密码正确成功登录");
        }
        else {
            System.out.println("密码错误登录失败");
        }
    }
    public void CheckRegister(String username){
        if(!Filer.CheckDirectory(username)){
            setRegister(true);
            System.out.println("这是未使用过的用户名");
        }
        if(Filer.CheckDirectory(username)){
            System.out.println("用户名已被占用");
        }
    }
    public void Register(String username,String password)throws IOException{
        Filer.WriteUsersInitial(username,password);
    }
    public void Revive()
    {
        System.out.println("Revive!");
        view.reviveGame();
        view.setifOver(false);
    }
    public void CheckRead(){
         if(Filer.CheckRead()){
             setRead(true);
             System.out.println("读取成功！");
         }
         else {
             System.out.println("读取失败！");
         }
    }
    public void ReadGame(){
          model.setNumbers(Filer.ReadArray());
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

    public boolean isRead() {
        return read;
    }

    public void setGameFrame(GameFrame gameFrame)
    {this.gameFrame = gameFrame;}

    public void setView(GamePanel gamePanel)
    {this.view = gamePanel;}

    public void setModel(GridNumber model)
    {
        this.model = model;
    }
    //todo: add other methods such as loadGame, saveGame...
}
