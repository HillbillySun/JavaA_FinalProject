package controller;
import model.GridNumber;
import view.GamePanel;
import view.GameFrame;

/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController {
    private GamePanel view;
    private GridNumber model;
    private GameFrame gameFrame;
    public GameController(GamePanel view, GridNumber model, GameFrame gameFrame) {
        this.view = view;
        this.model = model;
        this.gameFrame=gameFrame;
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
