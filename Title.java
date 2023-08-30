import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A title image
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Title extends Actor
{
    GreenfootImage titleImage = new GreenfootImage("title.png");
    /**
     * Sets image
     */
    public Title()
    {
        titleImage.scale(titleImage.getWidth()*GameWorld.SCALING, titleImage.getHeight()*GameWorld.SCALING);
        setImage(titleImage);
    }
}
