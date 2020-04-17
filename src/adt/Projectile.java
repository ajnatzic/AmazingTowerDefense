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
 */

/**
 * An unused class
 */
public class Projectile {
    private Point source;
    private Point target;
    private int projectileSpeed;
    private Point currPosition;

    public Projectile(Point from, Point to, int speed){
        source = from;
        target = to;
        projectileSpeed = speed;
        currPosition = new Point(source.x, source.y);
    }
    private static double distance(Point point1, Point point2){
        return Math.pow(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2), 0.5);
    }

    public void updatePosition(){
        int deltaX;
        int deltaY;
        double angle;

        Point relativeTarget = new Point();
        relativeTarget.x = target.x - source.x;
        relativeTarget.y = target.y - source.y;

        angle = Math.atan(((double) (relativeTarget.y) / (relativeTarget.x)));
        double distanceToTarget = distance(currPosition, target);
        if(projectileSpeed <= distanceToTarget) {
            deltaX = (int) (projectileSpeed * Math.cos(angle));
            deltaY = (int) (projectileSpeed * Math.sin(angle));
        }else{
            deltaX = (int) (projectileSpeed * Math.cos(angle));
            deltaY = (int) (projectileSpeed * Math.sin(angle));
        }

        currPosition.x += deltaX;
        currPosition.y += deltaY;
    }
}