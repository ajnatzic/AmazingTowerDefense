package adt;

import java.awt.*;
/*Thoughts so far
 *
 * Need to make the enemy targeted wait to take damage until projectile gets there
 * Need to check to make sure that the projectile doesn't appear past the enemy
 *          -Check if it passed the enemy, or check the distance its traveling vs the distance to the enemy
 * spawnFrame might not be needed based on this implementation (updating the position should take care of the issue of knowing
 *      how far the projectile is
 * need to add a projectile graphic, add something in the panel to draw projectiles, and draw them facing the right way
 *      (assuming something like an arrow or bullet, circular or pixels won't need this)
 *
 * might curve as a fast enemy moves away from a tower? is this a problem or not?
 * this implementation only works from a tower to an enemy, might have to change from Tower and Enemy to
 *      sourcePoint and destPoint ---WILL CHANGE SOON---
 *
 */
public class Projectile {
    private Tower fromTower;
    private Enemy toEnemy;
    private long spawnFrame;
    private int projectileSpeed;
    private Point currPosition;

    public Projectile(Tower t, Enemy e, long frame, int speed){
        fromTower = t;
        toEnemy = e;
        spawnFrame = frame;
        projectileSpeed = speed;
        currPosition = t.position();
    }

    public void updatePosition(long frame){
        int deltaX;
        int deltaY;
        double angle;

        Point relativeTarget = new Point();
        relativeTarget.x = toEnemy.position().x - fromTower.position().x;
        relativeTarget.y = toEnemy.position().y - fromTower.position().y;

        angle = Math.atan(((double) (relativeTarget.y) / (relativeTarget.x)));
        deltaX = (int) (projectileSpeed * Math.cos(angle));
        deltaY = (int) (projectileSpeed * Math.sin(angle));

        currPosition.x += deltaX;
        currPosition.y += deltaY;
    }
}
