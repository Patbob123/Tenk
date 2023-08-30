import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Transition that will switch between worlds
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Transition extends Actor
{
    private int frame;
    private int animDelay, animSpeed;
    private int waitTime;
    
    private GreenfootImage[] transitionImages;
    private GreenfootImage image;
    private String state;
    
    private GreenfootSound transitionInSound = new GreenfootSound("transclose8.wav");
    private GreenfootSound transitionOutSound = new GreenfootSound("transopen8.wav");;
    
    private World world;
    
    /**
     * Sets images for animation
     * 
     * @param curWorld  The world the game will go to
     */
    public Transition(World curWorld){
        world = curWorld;
        
        animSpeed = 5;
        animDelay = animSpeed;
        waitTime = 60;
        
        transitionImages = new GreenfootImage[4];
        for (int i = 0; i < transitionImages.length; i++){
            transitionImages[i] = new GreenfootImage("transition" + (i+1) + ".png");
            transitionImages[i].scale(transitionImages[i].getWidth()*GameWorld.SCALING, transitionImages[i].getHeight()*GameWorld.SCALING);
        }
        
        state = "in";
        frame = 0;
        image = transitionImages[frame];
        setImage(image);
    }
    
    /**
     * Sets Greenfoot world to world
     * Add itself to the new world, then play animation in reverse
     */
    public void transitionTo(){
        Greenfoot.setWorld(world);
        world.addObject(this, getX(), getY());
    }
    /**
     * Act method
     * Plays animation
     */
    public void act()
    {
        playAnim();
    }
    /**
     * Plays animation based on what state of movement the transition is currently in
     */
    public void playAnim(){
        if(state.equals("wait")){ //wait means the the animation is not being played
            waitTime--;
            if(waitTime<=0){
                transitionTo();
                transitionOutSound.play();
                state = "out";
            }
            return;
        }
        animDelay = Math.max (animDelay - 1, 0);
        if (animDelay == 0){
            if(state.equals("in")){ //in means the animation is playing normally
                if(frame == 0){
                    transitionInSound.play();
                }
                if (frame < 3){
                    frame++;
                }else{
                    state = "wait";
                }
            }else if(state.equals("out")){ //in means the animation plays in reverse
                if (frame > 0){
                    frame--;
                }else{
                    getWorld().removeObject(this);
                    return;
                }
            }else{
                
            }
            
            animDelay = animSpeed;
        }
        setImage (transitionImages[frame]);
    }
}
