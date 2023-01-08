package CodeAcademyJumpers;


import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;


import java.io.IOException;

import static CodeAcademyJumpers.Platform.PLATFORM_HEIGHT;


public class GameEngine implements KeyboardHandler {

    private final Picture myScreenCharacter;
    private Platform[] platforms;

    private static final int CHARACTER_SIDE_SPEED = 8;
    private static final int CHARACTER_SIZE = 40;

    private Line gameOverLine;

    private int GAMEOVER_LINE = 750;


    private int CHARACTER_X_START = 175;
    private int CHARACTER_Y_START = 340;

    private int hitBoxXOne;
    private int hitBoxYOne;
    private int hitBoxXTwo;
    private int hitBoxYTwo;

    private int speedDirection;
    private int threadTimer = 10;

    private boolean gameOver;

    Score score;


    public GameEngine() throws IOException, InterruptedException {

        score = new Score();
        this.hitBoxYOne = CHARACTER_Y_START + CHARACTER_SIZE + 10;
        this.hitBoxXOne = CHARACTER_X_START + CHARACTER_SIZE - 32;
        this.hitBoxYTwo = CHARACTER_Y_START + CHARACTER_SIZE + 10;
        this.hitBoxXTwo = CHARACTER_X_START + CHARACTER_SIZE - 1;

        int max = 7;
        int min = 1;

        //Choose random player image...
        int randomNum = (int) Math.ceil(Math.random() * (max - min));
        myScreenCharacter = new Picture(CHARACTER_X_START, CHARACTER_Y_START, randomNum + ".png");
        myScreenCharacter.draw();

        initKeyboard();

        start();

    }


    public void start() throws IOException, InterruptedException {
        gameOverLine=new Line(0,GAMEOVER_LINE,gameScreen.WIDTH,GAMEOVER_LINE);
        gameOverLine.setColor(Color.RED);
        gameOverLine.draw();

        this.platforms = Platform.platformArray();

        while (!gameOver) {
            move();
            gameOverLine.translate(-1,0);
            gameOverLine.draw();
        }
        gameOver();
    }

    public void gameOver() throws IOException, InterruptedException {

        myScreenCharacter.delete();

        for (Platform platform : platforms) {
            platform.delete();
        }
        gameScreen.gameOver(score);
           Platform.platformY = -PLATFORM_HEIGHT;
        score.updateFile();

        gameScreen.reset();
    }


    public void move() throws InterruptedException {
        movePlatforms();
        moveUp();


        while (speedDirection == 0 && !gameOver) {
            moveDown();

        }

    }

    private void initKeyboard() {
        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent rightPressed = new KeyboardEvent();
        rightPressed.setKey(KeyboardEvent.KEY_D);
        rightPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent leftPressed = new KeyboardEvent();
        leftPressed.setKey(KeyboardEvent.KEY_A);
        leftPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);


        keyboard.addEventListener(rightPressed);
        keyboard.addEventListener(leftPressed);



    }

    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_D:
                if (CHARACTER_X_START + CHARACTER_SIZE >= gameScreen.WIDTH || this.gameOver) {
                    return;
                }

                CHARACTER_X_START += CHARACTER_SIDE_SPEED;
                myScreenCharacter.translate(CHARACTER_SIDE_SPEED, 0);
                hitBoxXOne = hitBoxXOne + CHARACTER_SIDE_SPEED;
                hitBoxXTwo = hitBoxXTwo + CHARACTER_SIDE_SPEED;
                break;

            case KeyboardEvent.KEY_A:
                if (CHARACTER_X_START - gameScreen.PADDING - 5 <= 0 || this.gameOver) {
                    return;
                }

                CHARACTER_X_START -= CHARACTER_SIDE_SPEED;
                myScreenCharacter.translate(-CHARACTER_SIDE_SPEED, 0);
                hitBoxXOne = hitBoxXOne - CHARACTER_SIDE_SPEED;
                hitBoxXTwo = hitBoxXTwo - CHARACTER_SIDE_SPEED;
                break;


        }

    }


    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    public void moveDown() throws InterruptedException {

        threadTimer = 5;

        Thread.sleep(threadTimer);
        hitBoxYOne++;
        hitBoxYTwo++;
        CHARACTER_Y_START++;
        myScreenCharacter.translate(0, 1);

        platformCollision();

        if (hitBoxYOne >= GAMEOVER_LINE || hitBoxYTwo >= GAMEOVER_LINE) {
            this.gameOver = true;

        }

    }

    public void moveUp() throws InterruptedException {
        while (speedDirection > 0) {

            Thread.sleep(threadTimer);
            hitBoxYOne--;
            hitBoxYTwo--;
            CHARACTER_Y_START--;
            myScreenCharacter.translate(0, -1);
            speedDirection--;


        }
    }


    public void platformCollision() {
        for (Platform platform : this.platforms) {

            int[] hitBox = platform.getPlatformHitBox();

            for (int pixel : hitBox) {
                if (hitBoxYOne == platform.getStartY() && hitBoxXOne == pixel ||
                        hitBoxYTwo == platform.getStartY() && hitBoxXTwo == pixel) {

                    threadTimer = platform.getPlatformYForce();
                    speedDirection = platform.getJumpHigth();
                }
            }
        }
    }


    public void movePlatforms() {
        if ((CHARACTER_Y_START - speedDirection) < 0) {
            while ((CHARACTER_Y_START - speedDirection) < 0 || speedDirection > 0) {

                if (speedDirection > 0) {

                    for (int i = 0; i < this.platforms.length; i++) {
                        Platform platform = this.platforms[i];
                        if (platform == null) {
                            continue;
                        }

                        platform.setStartY(platform.getStartY() + 1);
                        platform.moveDown();

                        if (platform.getStartY() >= gameScreen.HEIGHT - PLATFORM_HEIGHT) {
                            platform.delete();

                            score.upScore();

                            this.platforms[i] = Platform.createPlatform();
                            this.platforms[i].showPlatform();

                        }
                    }
                }

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }

                speedDirection--;
            }
        }
    }


}