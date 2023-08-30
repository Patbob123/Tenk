import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The core of the boss, required to kill inorder to win the game
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class BossBody extends Enemy
{
   
    private int frame;
    private int animDelay, animSpeed;
    
    private GreenfootImage[] bossBodyImages;
    private GreenfootImage image;
    
    /**
     * Creates images for boss animation
     * 
     * @param curHp     HP for the boss
     */
    public BossBody(int curHp){
        super(curHp, 0, "bossbody1.png", 0, 100);
        
        animSpeed = 5;
        animDelay = animSpeed;
        
        bossBodyImages = new GreenfootImage[4];
        for (int i = 0; i < bossBodyImages.length; i++){
            bossBodyImages[i] = new GreenfootImage("bossbody" + (i+1) + ".png");
            bossBodyImages[i].scale(bossBodyImages[i].getWidth()*GameWorld.SCALING, bossBodyImages[i].getHeight()*GameWorld.SCALING);
        }
        
        frame = 0;
        
        Utils.rotate(this, Utils.getDirInts().get("down")*90, 10);
    }
    /**
     *  Act method to play effects and animation
     */
    public void act(){
        playAnim();
        playEffects();
        
    }
    
    /**
     *  Plays a 4 frame animation
     */
    private void playAnim(){
        animDelay = Math.max (animDelay - 1, 0);
            if (animDelay == 0){
                frame++;
                if (frame > 3){
                    frame = 0;
                }
                animDelay = animSpeed;
            }
            int opacity = getImage().getTransparency();
            bossBodyImages[frame].setTransparency(opacity);
            setImage (bossBodyImages[frame]);
    }
    /**
     *  Plays smoke or hit effects
     */
    protected void playEffects(){
        if(hp <= maxHp / 2){
            Smoke smoke = new Smoke(getX(), getY()-getImage().getHeight()/2, speed, getImage().getHeight()/4, 2);
            smoke.spawn(getWorld());
        }
        
        hitFilterTime--;
        if(hitFilterTime == 0){
            getImage().setTransparency(255);
        }
    }
    /**
     *  Removes itself from the world, and add a transition to WinWorld
     */
    protected void die(){
        getWorld().addObject(new Transition(new WinWorld(((GameWorld)getWorld()).getPlayer(), this)), getWorld().getWidth()/2, getWorld().getHeight()/2);
    }
}
