import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The container that will store Ammo and display it on screen
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class AmmoBar extends UI
{
    private GreenfootImage[] healthImages;
    private GreenfootImage image;
    
    private World world;
    private Player player;
    private int ammoCount;
    
    /**
     * Sets variables
     * 
     * @param curPlayer The player this AmmoBar belongs to
     * @param curWorld  What world to display to
     */
    public AmmoBar(Player curPlayer, World curWorld){
        player = curPlayer;
        ammoCount = player.ammoCount;
        world = curWorld;
        getImage().setTransparency(0);
    }
    /**
     * Act method
     * Gets player ammoCount
     * Adds and removes Ammo from this to match ammoCount
     */
    public void act()
    {
        ammoCount = player.ammoCount;
        world.removeObjects(world.getObjects(Ammo.class));
        for(int i = 0 ; i < ammoCount; i++){
            world.addObject(
                new Ammo(), 
                13*world.getWidth()/15-i*getImage().getWidth()*2, 
                15*world.getHeight()/17);
        }
    }
}
