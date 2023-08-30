import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Heal particles for player
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Heal extends Effects
{
    private int startX;
    private int startY;
    private int opacity;
    private int playSpeed;
    
    private int healSpread; //How far the particles are from eachother
    
    private GreenfootImage healImage = new GreenfootImage("heal.png");
    
    /**
     * Creates a Heal particle object and set its image
     * 
     * @param curX      x-coordinate
     * @param curY      y-coordinate
     * @param curSpeed  Speed at which the particle moves up
     */
    public Heal(int curX, int curY, int curSpeed){
        startX = curX;
        startY = curY;
        opacity = 100;
        playSpeed = curSpeed+1;
        healSpread = 10;
        
        
        healImage.scale(healImage.getWidth()*GameWorld.SCALING, healImage.getHeight()*GameWorld.SCALING);
        setImage(healImage);
    }
    
    /**
     * Adds a particle to world, offset depending on the healSpread
     * 
     * @param world     The world to add this to
     */
    public void spawn(World world){
        int offsetX = Greenfoot.getRandomNumber(healSpread*GameWorld.SCALING); 
        if(Greenfoot.getRandomNumber(2)==0){ //Get random x within range
            offsetX = offsetX*-1;
        }
        int offsetY = Greenfoot.getRandomNumber(healSpread*GameWorld.SCALING); 
        if(Greenfoot.getRandomNumber(2)==0){ //Get random y within range
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
