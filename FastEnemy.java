import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fast little guy
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class FastEnemy extends Enemy
{
   
    /**
     * @param curHp     HP of the enemy
     */
    public FastEnemy(int curHp){
        
        super(curHp, 4, "fastEnemy.png", 15, 30);
    }
}
