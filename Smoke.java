import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Smoke particles for player and enemies
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Smoke extends Effects
{
    private int startX;
    private int startY;
    private int opacity;
    private int playSpeed;
    
    private int smokeSpread;
    
    private GreenfootImage smokeImage = new GreenfootImage("smoke.png");
    
    /**
     * Creates a Smoke particle object and set its image
     * 
     * @param curX      x-coordinate
     * @param curY      y-coordinate
     * @param curSpeed  Speed at which the particle moves up
     */
    public Smoke(int curX, int curY, int curSpeed){
        startX = curX;
        startY = curY;
        opacity = 100;
        playSpeed = curSpeed+1;
        smokeSpread = 5;
        
        
        smokeImage.scale(smokeImage.getWidth()*GameWorld.SCALING, smokeImage.getHeight()*GameWorld.SCALING);
        setImage(smokeImage);
    }
    
    /**
     * Creates a Smoke particle object and set its image at a certain size
     * 
     * @param curX              x-coordinate
     * @param curY              y-coordinate
     * @param curSpeed          Speed at which the particle moves up
     * @param curSmokeSpread    How far apart the particles are
     * @param size              The size of the particle
     */
    public Smoke(int curX, int curY, int curSpeed, int curSmokeSpread, int size){
        startX = curX;
        startY = curY;
        opacity = 100;
        playSpeed = curSpeed+1;
        smokeSpread = curSmokeSpread;
        
        
        smokeImage.scale(smokeImage.getWidth()*size*GameWorld.SCALING, smokeImage.getHeight()*size*GameWorld.SCALING);
        setImage(smokeImage);
    }
    
    /**
     * Adds a particle to world, offset depending on the smokeSpread
     * 
     * @param world     The world to add this to
     */
    public void spawn(World world){
        int offsetX = Greenfoot.getRandomNumber(smokeSpread*GameWorld.SCALING);  //Get random x within range
        if(Greenfoot.getRandomNumber(2)==0){
            offsetX = offsetX*-1;
        }
        int offsetY = Greenfoot.getRandomNumber(smokeSpread*GameWorld.SCALING); //Get random y within range
        if(Greenfoot.getRandomNumber(2)==0){
            offsetY = offsetY*-1;
        }
        
        world.addObject(this, startX+offsetX, startY+offsetY);
    }
    
    /**
     * Act method
     * Each act opacity decreases and position moves up, if transparent, remove itself
     */
    public void act()
    {
        setLocation(getX(), getY()-playSpeed);
        opacity--;
        if(opacity<=0||isAtEdge()){
            getWorld().removeObject(this);
            return;
        }
        getImage().setTransparency(opacity);
    }
}
