import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Increase the speed of player
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class SpeedUP extends Pickup
{
    private static int duration;
    
    private GreenfootImage speedUpImage = new GreenfootImage("speedUp.png");
    private GreenfootSound collectSound = new GreenfootSound("upgrades8.wav");
    /**
     * Sets image
     * 
     * @param curDuration   How long the powerup lasts
     */
    public SpeedUP(int curDuration){
        duration = curDuration;
        
        speedUpImage.scale(speedUpImage.getWidth()*GameWorld.SCALING, speedUpImage.getHeight()*GameWorld.SCALING);
        setImage(speedUpImage);
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
        player.speedBuff = duration;
        collectSound.play();
        getWorld().removeObject(this);
    }
}

