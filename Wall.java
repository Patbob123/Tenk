import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Walls are immovable objects around the map, can be destroyed
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public abstract class Wall extends Actor
{
    protected GreenfootImage wallImage;
    
    /**
     * Sets image
     * 
     * @param wallType  The name of the image file of the wall
     */
    public Wall(String wallType){
        
        wallImage = new GreenfootImage(wallType);
        wallImage.scale(wallImage.getWidth()*GameWorld.SCALING, wallImage.getHeight()*GameWorld.SCALING);
        setImage(wallImage);
    }
    /**
     * Default method for a wall taking damage
     * Dies when any damage is taken
     */
    public void takeDamage(){
        die();
    }  
    /**
     * Default method for a wall dying
     * Removes from world
     */
    public void die(){
        getWorld().removeObject(this);
    }
}
