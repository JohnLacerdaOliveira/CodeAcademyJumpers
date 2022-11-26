package DoodleJump;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Platform {


    private static final int PLATFORM_WIDTH = 70;
    public static final int PLATFORM_HEIGHT = 10;
    public static final int PLATFORM_NUMBER = 13;

    private final Picture platformPicture;
    public int[] platformHitBox = new int[PLATFORM_WIDTH];


    private int startY;
    public static int platformY = -PLATFORM_HEIGHT;
    
    private int jumpHight = 250;
    private int plataformYForce = 2;

    public static boolean hasReachScreenTop;




    public Platform(int startX, int startY) {

        this.startY = startY;

        platformPicture = new Picture(startX, startY, "platformPicture.png");
        platformPicture.draw();

        platformHitBox(startX);

    }


    public static Platform createPlatform() {
        int X = (int) (Math.random() * (gameScreen.WIDTH - PLATFORM_WIDTH)) + gameScreen.PADDING;


        if (!hasReachScreenTop) {
            platformY += (gameScreen.HEIGHT / PLATFORM_NUMBER);
        } else {
            platformY = PLATFORM_HEIGHT;
        }

        if (platformY > gameScreen.HEIGHT- 2*(gameScreen.HEIGHT / PLATFORM_NUMBER)) {
            hasReachScreenTop = true;
        }

        return new Platform(X, platformY);
    }

    //Platform Array creation creation...
    public static Platform[] platformArray() {
        Platform[] tempPlatformsArr = new Platform[PLATFORM_NUMBER];

        for (int i = 0; i < PLATFORM_NUMBER; i++) {
            tempPlatformsArr[i] = createPlatform();

        }
        return tempPlatformsArr;
    }

    public void showPlatform() {

        platformPicture.draw();
    }

    public int getPlatformYForce() {
        return plataformYForce;
    }



    public void platformHitBox(int starX) {
        for (int i = 0; i < PLATFORM_WIDTH; i++) {
            platformHitBox[i] = starX + i;
        }
    }


    public int getStartY() {
        return startY;
    }

    public void setStartY(int y) {
        this.startY = y;
    }


    public int getJumpHigth() {
        return jumpHight;
    }

    public int[] getPlatformHitBox() {
        return platformHitBox;
    }

    public void moveDown() {
        this.platformPicture.translate(0, 1);
    }

    public void delete(){
        this.platformPicture.delete();
    }
}



