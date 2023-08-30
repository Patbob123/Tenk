import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ammo icon images
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Ammo extends UI
{
    private GreenfootImage ammoImage = new GreenfootImage("ammo.png");
    /**
     * Sets image
     */
    public Ammo(){
        ammoImage.scale(ammoImage.getWidth()*GameWorld.SCALING, ammoImage.getHeight()*GameWorld.SCALING);
        setImage(ammoImage);
    }
}
