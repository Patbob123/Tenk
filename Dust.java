import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dust effect when enemy spawns
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Dust extends Effects
{
    private int startX; 
    private int startY;
    
    private int opacity;
    private int speed;
    
    private int originalWidth;
    private int originalHeight;
    
    private Enemy enemy;
    private GreenfootImage dustImage = new GreenfootImage("dust.png");
    
    /**
     * Creates Dust and sets its opacity
     * 
     * @param curEnemy  Enemy added alongside the dust
     * @param curSpeed  How fast the dust dissipates
     * @param curX      X coordinate
     * @param curY      Y coordinate
     */
    public Dust(Enemy curEnemy, int curSpeed, int curX, int curY){
        enemy = curEnemy;
        startX = curX;
        startY = curY;
        
        speed = curSpeed;
        opacity = 255;
        
        dustImage.scale(enemy.getImage().getWidth()*2, enemy.getImage().getWidth()*2);
        setImage(dustImage);
    }
    
    /**
     * Creates Dust and sets its opacity
     */
    protected void spawn(World world){
        world.addObject(this, startX, startY);
        world.addObject(enemy,startX,startY);
        enemy.avoidCollision();
    }
    /**
     * Act method
     * Decrease opacity every act, removes itself when fully transparent
     */
    public void act() 
    {
        opacity = Math.max(opacity-2*speed, 0);
        dustImage.setTransparency(opacity);
        setImage(dustImage);
        if(opacity == 0){
            getWorld().removeObject(this);
        }
    }    
}
