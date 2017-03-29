package alexcrit.balls;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by о on 07.12.2016.
 */
public class EnemyCircle extends SimpleCircle {

    public static final int FROM_RADIUS = 10;
    public static final int TO_RADIUS = 110;
    private int dx;
    private int dy;

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random random = new Random();
        int x = random.nextInt(GameManager.getWidth());
        int y = random.nextInt(GameManager.getHeight());
        int dx = 1 + random.nextInt(10);
        int dy = 1 + random.nextInt(10);
        int radius = FROM_RADIUS + random.nextInt(TO_RADIUS - FROM_RADIUS);

        EnemyCircle enemyCircle = new EnemyCircle(x, y, radius, dx, dy);
        enemyCircle.setColor(Color.RED);
        return enemyCircle;
    }

    public void setEnemyOrFoodColorDependsOn(MainCircle mainCircle) {
        if(isSmallerThan(mainCircle)){
            setColor(Color.GREEN);
        } else{
            setColor(Color.RED);
        }
    }

    protected boolean isSmallerThan(SimpleCircle circle) {
        if (radius < circle.radius) return true;
        return false;
    }

    public void moveOneStep() {
        x+=dx;
        y+=dy;
        checkBounds();
    }

    private void checkBounds() {
        if(x > GameManager.getWidth() || x < 0){
            dx = -dx;
        }
        if(y > GameManager.getHeight() || y < 0){
            dy = -dy;
        }
    }
}
