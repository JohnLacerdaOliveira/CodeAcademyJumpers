package DoodleJump;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.io.IOException;


public class gameScreen {

    public static final int PADDING = 10;
    public static final int HEIGHT = 900;
    public static final int WIDTH = 500;


    public static Picture backGround;
    public static Picture gameOver;


    //private static ArrayList<Text> texts = new ArrayList<>();

    public gameScreen() {
        backGround = new Picture(PADDING, PADDING, "resources/background.png");
        backGround.draw();

    }

    public static void gameOver(Score score){

        backGround.delete();

        gameOver = new Picture(PADDING, PADDING, "resources/gameOver.png");
        gameOver.draw();

        Text strScore = new Text(232, 110, "Score: " + score.getScore());
        strScore.grow(100, 25);
        strScore.setColor(Color.DARK_GRAY);
        strScore.draw();

        Text strScore2 = new Text(232, 109, "Score: " + score.getScore());
        strScore2.grow(100, 26);
        strScore2.setColor(Color.ORANGE);
        strScore2.draw();

        Text strMaxScore = new Text(220, 190, "Max Score: " + score.getMaxScore());
        strMaxScore.grow(200, 25);
        strMaxScore.setColor(Color.DARK_GRAY);
        strMaxScore.draw();

        Text strMaxScore2 = new Text(220, 189, "Max Score: " + score.getMaxScore());
        strMaxScore2.setColor(Color.ORANGE);
        strMaxScore2.grow(200, 26);
        strMaxScore2.draw();

        countdown();

    }

    public static void reset() throws IOException, InterruptedException {
        gameOver.delete();
        backGround.draw();
        Platform.hasReachScreenTop = false;
        Main.myGame = new GameEngine();

    }

    public static void countdown(){
        for (int i = 5; i >= 0; i--) {

            Text delayToStart = new Text(210, 769, "Game re-Starting in " + i+'s');
            delayToStart.setColor(Color.DARK_GRAY);
            delayToStart.grow(160, 50);
            delayToStart.draw();

            Text delayToStart2 = new Text(210, 768, "Game re-Starting in " + i+'s');
            delayToStart2.setColor(Color.ORANGE);
            delayToStart2.grow(160, 51);
            delayToStart2.draw();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            delayToStart.delete();
            delayToStart2.delete();

        }
    }

}

