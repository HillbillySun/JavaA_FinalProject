package controller;

import model.GridNumber;
import view.GameFrame;
import view.GamePanel;
import view.InitiaFrame;
import view.ModeFrame;

/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController{
    private GamePanel view;
    private GridNumber model;
    private InitiaFrame initiaFrame;
    private GameFrame gameFrame;
    private ModeFrame modeFrame;
    public GameController(GamePanel view, GridNumber model, GameFrame gameFrame,ModeFrame modeFrame,InitiaFrame initiaFrame) {
        this.view = view;
        this.model = model;
        this.gameFrame=gameFrame;
        this.modeFrame=modeFrame;
        this.initiaFrame=initiaFrame;
    }
    public void restartGame() {
        System.out.println("Do restart game here");
        view.refreshGame();
        view.setifOver(false);
        /*
         * We can also use the method view.refreshGame();
         * */
//        view.refreshGame();
    }
    public void endGame()
    {
        System.out.println("Game Over!");
        model.setisMove(false);
        view.setifOver(true);
    }
    public void saveGame()
    {
        //todo: edit the save method
    }
    public void loadGame()
    {
        //todo: edit the load method
    }
    public void Revive()
    {
        System.out.println("Revive!");
        view.reviveGame();
        view.setifOver(false);
    }

    //todo: add other methods such as loadGame, saveGame...
}
