import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Button class that runs its Function object's function when clicked by the mouse
 * 
 * @author Dawson
 * @version 2023-06-20
 */

public class Button extends Actor
{
    private int startX;
    private int startY;
    
    private Function action;
    private GreenfootImage buttonImage = new GreenfootImage("button.png");//Default button image
    
    /**
     * @param buttonAction  The Function object that will run
     * @param buttonFile    The image of the button's directory
     */
    public Button(Function buttonAction, String buttonFile){
                
        buttonImage = new GreenfootImage(buttonFile);
        buttonImage.scale(buttonImage.getWidth()*GameWorld.SCALING, buttonImage.getHeight()*GameWorld.SCALING);
        setImage(buttonImage);
        action = buttonAction;
    }
    
    /**
     * Act method
     * For clicking and hovering
     */
    public void act()
    {
        if(Greenfoot.mouseClicked(this)){
            runAction();
        }
        detectHover();
    }
    
    /**
     * Mutator for X and Y
     */
    protected void init(){
        startX = getX();
        startY = getY();
    }
    
    /**
     * Running the Function object's function
     */
    public void runAction() {
        action.apply();
    }
    
    /**
     * Checks if the mouse is hovering over the button, if so, move the button up for a visual effect
     * KNOWN ISSUE: if the mouse is on the bottom of the button, then it will start jittering due to the mouse rapidly entering and leaving the button
     */
    private void detectHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            List hovering = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Button.class);
            if(hovering.contains(this)){
                setLocation(startX, startY-GameWorld.SCALING); //Moves the button up
            }else{
                setLocation(startX, startY); //Moves the button back to starting position
            }
        }
    }
}
