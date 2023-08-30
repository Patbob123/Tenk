import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cursor purely for aesthetic purposes
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Cursor extends Actor
{
    private GreenfootImage cursorImage = new GreenfootImage("cursor.png");
    
    /**
     * Sets cursor image and size
     */
    public Cursor(){
        cursorImage.scale(cursorImage.getWidth()*GameWorld.SCALING, cursorImage.getHeight()*GameWorld.SCALING);
        setImage(cursorImage);
    }
    
    /**
     * Each act ensures the cursor follows the mouse
     */
    public void act()
    {
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            setLocation(mouse.getX(), mouse.getY());
        }
        
    }
}
