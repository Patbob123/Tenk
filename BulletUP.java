import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Increase the damage of player bullets
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class BulletUP extends Pickup
{
    private int duration;
    private GreenfootImage bulletUpImage = new GreenfootImage("bulletUp.png");
    private GreenfootSound collectSound = new GreenfootSound("upgrades8.wav");
    /**
     * Sets image
     * 
     * @param curDuration   How long the powerup lasts
     */
    public BulletUP(int curDuration){
        duration = curDuration;
        
        bulletUpImage.scale(bulletUpImage.getWidth()*GameWorld.SCALING, bulletUpImage.getHeight()*GameWorld.SCALING);
        setImage(bulletUpImage);
    }
    
    /**
     * Act method
     * Gets collected if a player is at it
     */
    public void act()
    {
        Player player = (Player)getOneIntersectingObject(Player.class);
        if(player!=null){
            collect(player);
        }
    }
    
    /**
     * Applies the buff to player
     * Removes itself from the world
     */
    private void collect(Player player){
        player.bulletBuff = duration;
        collectSound.play();
        getWorld().removeObject(this);
    }
}


