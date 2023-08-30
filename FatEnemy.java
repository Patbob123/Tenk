import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Big fat guy
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class FatEnemy extends Enemy
{
    /**
     * @param curHp     HP of the enemy
     */
    public FatEnemy(int curHp){
        super(curHp*5, 1, "fatEnemy.png", 7, 50);
    } 
}
