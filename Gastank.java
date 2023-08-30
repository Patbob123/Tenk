import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Gastanks are explosive obstacles
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Gastank extends Wall
{
    private int explodeSize = 6;
    /**
     * Makes a wall with this image url
     */
    public Gastank(){
        super("gastank.png");
    }
    /**
     * Die when any damage is taken
     */
    public void takeDamage(){
        die();
    }
    /**
     * Spawns a huge explosion scaling off explodeSize
     * Then remove itself from the world
     */
    public void die(){
            
        if (getWorld() == null) return;
        Explosion e = new Explosion(
            getImage().getHeight()*explodeSize, 
            getImage().getHeight()*explodeSize, 
            getX(), 
            getY());
        
        World world = getWorld();
        getWorld().removeObject(this);
        
        e.spawn(world, this);
          
    }
}
