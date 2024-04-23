package controller;

import model.GridNumber;
import view.GamePanel;


/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController {
    private GamePanel view;
    private GridNumber model;


    public GameController(GamePanel view, GridNumber model) {
        this.view = view;
        this.model = model;

    }
    public void restartGame() {
        System.out.println("Do restart game here");
    }

    //todo: add other methods such as loadGame, saveGame...

}
