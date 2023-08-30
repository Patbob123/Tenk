import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Restores player health
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class HealthUP extends Pickup
{
    private static int amount;
    
    private GreenfootImage healthUpImage = new GreenfootImage("healthUp.png");
    private GreenfootSound collectSound = new GreenfootSound("upgrades8.wav");
    /**
     * Sets image
     * 
     * @param hpAmount   How much HP this heals
     */
    public HealthUP(int hpAmount){
        amount = hpAmount;
        
        healthUpImage.scale(healthUpImage.getWidth()*GameWorld.SCALING, healthUpImage.getHeight()*GameWorld.SCALING);
        setImage(healthUpImage);
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
     * Adds to player HP
     * Removes itself from the world
     */
    private void collect(Player player){
        player.hp = Math.min(player.maxHp, player.hp+amount);
        for(int i = 0; i < 20; i++){
            Heal heal = new Heal(player.getX(), player.getY(), player.speed-1);
            heal.spawn(getWorld());
        }
        collectSound.play();
        getWorld().removeObject(this);
    }
}

