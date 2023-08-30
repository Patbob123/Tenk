import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Display health on screen
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class HealthBar extends UI
{
    private GreenfootImage[] healthImages;
    private GreenfootImage image;
    
    private Player player;
    private int index;
    
    /**
     * Sets variables and all images for each amounts of HP
     * 
     * @param curPlayer The player this AmmoBar belongs to
     */
    public HealthBar(Player curPlayer){
        player = curPlayer;
        
        healthImages = new GreenfootImage[5];
        for (int i = 0; i < healthImages.length; i++){
            healthImages[i] = new GreenfootImage("healthbar" + i + ".png");
            healthImages[i].scale(healthImages[i].getWidth()*GameWorld.SCALING, healthImages[i].getHeight()*GameWorld.SCALING);
        }
        
        
    }
    /**
     * Act method
     * Gets player hp and sets image accordingly
     */
    public void act()
    {
        index = Math.min(player.hp, 4);
        image = healthImages[index];
        setImage(image);
    }
}
