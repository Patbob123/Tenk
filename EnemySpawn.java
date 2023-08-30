import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemy Spawn Encapsulation
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class EnemySpawn extends Spawn
{
    protected Enemy enemy;
    protected int startX;
    protected int startY;
    
    /**
     * Enemy Spawn set
     * 
     * @param curEnemy  The enemy part
     * @param curX      The x-coordinate
     * @param curY      The y-coordinate
     */
    public EnemySpawn(Enemy curEnemy, int curX, int curY){
        enemy = curEnemy;
        startX = curX;
        startY = curY;
    }
}
