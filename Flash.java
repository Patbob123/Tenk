import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A flash on the screen to add effect
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Flash extends UI
{
    private GreenfootImage flashImage;
    private int opacity;
    private int speed;
    
    /**
     * Sets opacity and speed variable
     * Sets image and fill the whole screen
     */
    public Flash(){
        opacity = 150;
        speed = 3;
        
        flashImage = new GreenfootImage(960, 544);
        flashImage.setColor(Color.RED);
        flashImage.setTransparency(opacity);
        flashImage.fill();
        setImage(flashImage);
    }
    /**
     * Act method
     * 
     * Opacity decreases until it reaches 0, then it removes itself
     */
    public void act(){
        opacity-=speed;
        flashImage.setTransparency(opacity);
        if(opacity<=0){
            getWorld().removeObject(this);
            return;
        }

    }
}
