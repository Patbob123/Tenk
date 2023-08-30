import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The guns of the boss, has 3-4 different shooting patterns
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class BossGun extends Enemy
{
    private int shootCD;
    private int baseShootCD;
    private String leftOrRightGun;
    
    private int shotgunPellets;
    
    private int frame;
    private int animDelay, animSpeed;
    
    private String shootingMode;
    private int shootingTime;
    private int maxShootingTime;

    private GreenfootImage bossGunOffImage;
    private GreenfootImage bossGunOnImage;
    
    /**
     * @param curHp         HP for the gun
     * @param leftOrRight   String to determine which gun this is, will dictate shooting pattern
     */
    public BossGun(int curHp, String leftOrRight){
        super(curHp, 0, "bossgun1.png", 0, 100);
        
        baseShootCD = 6;
        shootCD = baseShootCD;
        leftOrRightGun = leftOrRight;
        
        shotgunPellets = 6;
        
        shootingMode = "";
        maxShootingTime = 180;
        shootingTime = maxShootingTime;
        
        animSpeed = 2;
        animDelay = animSpeed;
        
        bossGunOffImage = new GreenfootImage("bossgun1.png");
        bossGunOnImage = new GreenfootImage("bossgun2.png");
        
        bossGunOffImage.scale(bossGunOffImage.getWidth()*GameWorld.SCALING, bossGunOffImage.getHeight()*GameWorld.SCALING);
        bossGunOnImage.scale(bossGunOnImage.getWidth()*GameWorld.SCALING, bossGunOnImage.getHeight()*GameWorld.SCALING);
        
        if(leftOrRight.equals("left")){ //Mirror the two images if the gun is left side
            bossGunOffImage.mirrorHorizontally();
            bossGunOnImage.mirrorHorizontally();
        }
        
    }
    
    /**
     * Act method
     * Decides a shooting pattern
     * Plays effects
     */
    public void act(){
        decideShooting();
        playEffects();
    }
    /**
     * shootCD ticks down
     * shootingTime ticks down, if it is 0, the current shooting pattern is over, and another one is randomly chosen
     */
    protected void decideShooting(){
        shootCD = Math.max (shootCD - 1, 0);
        if(shootingTime<=0){
            shootingTime = maxShootingTime;
            int shootingModeInt = Greenfoot.getRandomNumber(6); //half of the time(3/6), guns will shoot, other times it won't
            changeShootingPattern(shootingModeInt);
            
                         
        }else{
            shootInPattern();
            shootingTime--;

        }
    }
    /**
     * Changes its current shooting mode based on the int given
     * 
     * @param shootingModeInt   The int given to determine shooting mode
     */
    protected void changeShootingPattern(int shootingModeInt){
        if(shootingModeInt>2){
            setImage(bossGunOffImage); //Turn the gun off when the gun is not shooting, set to different image
        }else{        
            setImage(bossGunOnImage);  //Turn the gun on when the gun is shooting, set to different image
        }
        switch(shootingModeInt){
            case 0:
                shootingMode = "random";
                break;
            case 1:
                shootingMode = "shotgun";
                break;
            case 2:
                shootingMode = "screenswipe";
                break;
            default:   
                shootingMode = "";
        }
    }
    /**
     * Based on current shootingMode, run the functions that will determine where and when bullets will shot
     */
    protected void shootInPattern(){
        switch(shootingMode){
            case "random":
                randomFlurry();
                break;
            case "shotgun":
                shotgunFlurry();
                break;
            case "screenswipe":
                screenSwipeFlurry();
                break;
            
        }
    }
    /**
     * Randomly shoots/rotates a bullet within a 140 degree angle
     */
    protected void randomFlurry(){
        int rotation = Greenfoot.getRandomNumber(140)+20;
        if(shootCD == 0){
            shootCD = baseShootCD*3;
            shootBullet(rotation);
        }
        
    }
    /**
     * Shoots shotgunPellets amounts of bullets in a spread simultaneously
     */    
    protected void shotgunFlurry(){
        if(shootCD == 0){
            shootCD = baseShootCD*8;
            for(int i = 0 ; i < shotgunPellets ; i++){
                int rotation = (180/shotgunPellets)*i;
                shootBullet(rotation);
                
            }
        }
    }
    /**
     * Depending on the side of the gun, shoot and slightly rotate each bullet closer to the centre
     */    
    protected void screenSwipeFlurry(){
        if(shootCD == 0){
            shootCD = baseShootCD*2;
            int rotation = 0;
            if(leftOrRightGun.equals("right")){
                rotation = (360-shootingTime*2);
            }else if(leftOrRightGun.equals("left")){
                rotation = (shootingTime*2);

            }
            
            shootBullet(rotation);
        }
    }
    /**
     *  Spawns bullet object
     *  @param rotation     The direction the bullet faces
     */    
    private void shootBullet(int rotation){
        
        Bullet bullet = new Bullet(getX(), getY()+getImage().getHeight()/2, rotation, 3 , 1, this);
        bullet.spawn(getWorld());
    }
    /**
     *  Plays effects for smoke and hit effects
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
}
