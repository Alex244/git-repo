package alexcrit.balls;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by о on 06.12.2016.
 */
public class GameManager {

    public static final int MAX_CIRCLES = 10;

    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> circles;
    private CanvasView canvasView;
    private static int width;
    private static int height;

    public GameManager(CanvasView canvasView, int w, int h){
        this.canvasView = canvasView;
        width = w;
        height = h;
        initMainCircle();
        initEnemyCircles();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public void onDraw() {
        canvasView.drawCircle(mainCircle);
        for(EnemyCircle circle : circles){
            canvasView.drawCircle(circle);
        }
    }


    private void initMainCircle() {
        mainCircle = new MainCircle(width / 2, height / 2);
    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleWhenTouch(x, y);
        checkCollision();
        moveCircles();

    }

    private void checkCollision() {
        SimpleCircle circleForDel = null;
        for(EnemyCircle circle : circles){
            if(mainCircle.isIntersect(circle)){
                if(circle.isSmallerThan(mainCircle)){
                    mainCircle.growRadius(circle);
                    circleForDel = circle;
                    calculateAndSetCirclesColor();
                    break;
                } else{
                    gameEnd("YOU LOSE!");
                    return;
                }

            }
        }
        if(circleForDel != null){
            circles.remove(circleForDel);
        }
        if(circles.isEmpty()){
            gameEnd("YOU WIN!");
        }
    }

    private void gameEnd(String message) {
        canvasView.showMessage(message);
        mainCircle.initRasius();
        initEnemyCircles();
        canvasView.redraw();
    }

    private void moveCircles() {
        for(EnemyCircle circle : circles){
            circle.moveOneStep();
        }
    }

    private void initEnemyCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        circles = new ArrayList<EnemyCircle>();
        EnemyCircle enemyCircle;
        for(int i=0; i < MAX_CIRCLES; i++){
            do {
                enemyCircle = EnemyCircle.getRandomCircle();
            } while(enemyCircle.isIntersect(mainCircleArea));

            circles.add(enemyCircle);
        }
        calculateAndSetCirclesColor();
    }

    private void calculateAndSetCirclesColor() {
        for (EnemyCircle enemyCircle: circles){
            enemyCircle.setEnemyOrFoodColorDependsOn(mainCircle);
        }
    }
}
