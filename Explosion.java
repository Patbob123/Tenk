import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Explosion that spawns if an enemy dies or a barrel explodes, customizable size
 * Deals damage to everything caught in the radius
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Explosion extends Effects
{
    private int startX; 
    private int startY;
    
    private int frame;
    private int animDelay, animSpeed;
    
    private GreenfootImage[] explosionImages;
    private GreenfootImage image;
    
    private GreenfootSound explosionSound = new GreenfootSound("explosion8.wav");
    
    /**
     * Explosion that spawns if an enemy dies or a barrel explodes, customizable size
     * Deals damage to everything caught in the radius
     * 
     * @param width     Width
     * @param height    Height
     * @param curX      x-coordinate
     * @param curY      y-coordinate
     */
    public Explosion(int width, int height, int curX, int curY){
        
        startX = curX;
        startY = curY;
        
        animSpeed = 5;
        animDelay = animSpeed;
        
        explosionImages = new GreenfootImage[4];
        for (int i = 0; i < explosionImages.length; i++){
            explosionImages[i] = new GreenfootImage("explosion" + (i+1) + ".png");
            explosionImages[i].scale(width, height);
        }
        
        frame = 0;
        image = explosionImages[frame];
        setImage(image);
    }
    /**
     * Adds itself to the world
     * Deals damage to everything caught in the radius
     * 
     * @param world         The world it will spawn in
     * @param causeActor    The owner of the explosion 
     */
    public void spawn(World world, Actor causeActor){
        explosionSound.play();
        world.addObject(this, startX, startY);
        checkDoDamage(causeActor); 
    }
    
    /**
     * Act method
     * Plays an animation
     */
    public void act()
    {
        playAnim();
    }
    /**
     * Do damage to every Enemy, Player or wall in the radius
     * 
     * @param causeActor    The owner of the explosion
     */
    private void checkDoDamage(Actor causeActor){
        List<Actor> actors = getIntersectingObjects(Actor.class);
        if(actors==null) return;
        for(int i = 0 ; i < actors.size(); i++){
            Actor a = actors.get(i);
            if(a==causeActor) continue;
            if(a.getClass().getSuperclass().getName().equals("Enemy")){
                    ((Enemy)a).takeDamage(4);
            }
            else if(a.getClass().getName().equals("Player")){
                    ((Player)a).takeDamage(3);
            }else if(a.getClass().getSuperclass().getName().equals("Wall")){
                   ((Wall)a).takeDamage();
            }
        }
        
    }
    /**
     * Plays a 4 frame animation
     */
    private void playAnim(){ 
        animDelay = Math.max (animDelay - 1, 0);
        if (animDelay == 0){
            frame++;
            if (frame > 3){
                getWorld().removeObject(this);
                return;
            }
            animDelay = animSpeed;
        }
        setImage (explosionImages[frame]);
    }
}
