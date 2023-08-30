import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Tires are just obstacles
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Tire extends Wall
{
    private int hp;
    
    /**
     * Sets hp
     * Makes a wall with this image url
     */
    public Tire(){
        super("tire.png");
        hp = 50;
    }
    /**
     * Decrease hp by 1 when any damage is taken
     */
    public void takeDamage(){
        hp = Math.max(hp-1, 0);
    }
    /**
     * Method for dummy tires to detect if anything is colliding with it
     * Used for checking if a position is availible to spawn enemies or walls
     */
    public List<Actor> getColliding(int range){
        return getObjectsInRange(range ,Actor.class);
    }
    /**
     * Act method
     * Just removes itself when hp reaches 0
     */
    public void act(){
        if(hp<=0){
            getWorld().removeObject(this);
        }
    }
}
