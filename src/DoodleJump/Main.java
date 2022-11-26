package DoodleJump;

import java.io.IOException;

public class Main {

    public static GameEngine myGame;
    public static gameScreen myGameScreen;


    public static void main(String[]args) throws IOException, InterruptedException {

        myGameScreen = new gameScreen();
        myGame = new GameEngine();


    }

}
