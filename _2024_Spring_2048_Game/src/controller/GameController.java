package controller;

import model.GridNumber;
import view.GameFrame;
import view.GamePanel;
import view.ModeFrame;

/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController{
    private GamePanel view;
    private GridNumber model;
    private GameFrame gameFrame;
    private ModeFrame modeFrame;
    public GameController(GamePanel view, GridNumber model, GameFrame gameFrame,ModeFrame modeFrame) {
        this.view = view;
        this.model = model;
        this.gameFrame=gameFrame;
        this.modeFrame=modeFrame;
    }
    public void restartGame() {
        System.out.println("Do restart game here");
        view.refreshGame();
        /*
         * We can also use the method view.refreshGame();
         * */
//        view.refreshGame();
    }
    //todo: add other methods such as loadGame, saveGame...
}
