import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A red target that indicates when and where an enemy will spawn
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Target extends Actor
{
    private int opacity;
    private double sizeMultiplier;
    private int speed;
    
    private int originalWidth;
    private int originalHeight;
    
    private Enemy enemy;
    private World world;
    private GreenfootImage targetImage = new GreenfootImage("target.png");
    
    /**
     * Sets variables for speed, size and opacity for the target
     * 
     * @param curWorld  The world to add this
     * @param curEnemy  The enemy this spawns
     * @param curSpeed  How fast this will shrink and appear
     */
    public Target(World curWorld, Enemy curEnemy, int curSpeed){
        world = curWorld;
        enemy = curEnemy;
        speed = curSpeed;
        opacity = 0;
        sizeMultiplier = 2;
        
        originalWidth = (int)(targetImage.getWidth()*GameWorld.SCALING);
        originalHeight = (int)(targetImage.getHeight()*GameWorld.SCALING);
        targetImage.scale(originalWidth, originalHeight);
        setImage(targetImage);
    }
    
    /**
     * Act method
     * Size multiplier ticks down, which shrinks the image size each act
     * Opacity increase each act 
     * Once size is its original size, spawn a Dust that will spawn an Enemy
     */
    public void act() 
    {
        if(sizeMultiplier==1){
            Dust d = new Dust(enemy, 1, getX(), getY());
            world.removeObject(this);
            d.spawn(world);
        }else{
            targetImage = new GreenfootImage("target.png");
            sizeMultiplier = Math.max(sizeMultiplier-1*0.01*speed, 1);
            int width = (int)(originalWidth*sizeMultiplier);
            int height = (int)(originalHeight*sizeMultiplier);
            targetImage.scale(width, height);
            
            opacity = Math.min(opacity+2*speed, 255);
            targetImage.setTransparency(opacity);
            
            
            setImage(targetImage);
        }
    }    
}
