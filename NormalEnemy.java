import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The default enemy, similar in stats to player
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class NormalEnemy extends Enemy
{
    /**
     * @param curHp     HP of the enemy
     */
    public NormalEnemy(int curHp){
        super(curHp*2, 2, "normalEnemy.png", 7, 10);

    }

}
