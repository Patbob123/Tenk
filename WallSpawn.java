import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Wall Spawn Encapsulation
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class WallSpawn extends Spawn
{
    protected Wall wall;
    protected int startX;
    protected int startY;
    
    /**
     * Wall Spawn set
     * 
     * @param curWall   The wall part
     * @param curX      The x-coordinate
     * @param curY      The y-coordinate
     */
    public WallSpawn(Wall curWall, int curX, int curY){
        wall = curWall;
        startX = curX;
        startY = curY;
    }
}
