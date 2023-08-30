import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
/**
 * The player class contains how the player shoots, collides, moves, powerup and dies
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Player extends Actor
{
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int x;
    private int y;
    protected int maxHp;
    protected int hp;
    protected int speed;
    private String direction;
    
    private int shootCD;
    private int maxShootCD;
    
    protected int ammoCount;
    private int maxAmmoCount;
    
    protected int speedBuff;
    protected int bulletBuff;
    
    private int playerTrackOffset = 10;
    
    private int score;
    
    private HashMap<String, String> dirInputs = new HashMap<String, String>(){{
        put("a", "left");
        put("d", "right");
        put("w", "up");
        put("s", "down");
    }};
    private String[] validInputs = new String[]{"a","s", "w", "d", "left", "right", "up", "down"};
    
    private ArrayList<String> keysPressed = new ArrayList<String>();
    
    private int frame;
    private int animDelay, animSpeed;
    
    private int hitFilterTime;
    
    private GreenfootImage playerImage = new GreenfootImage("player.png");
    private GreenfootImage[] playerShootImages;
    
    
    /**
     * Initialize variables to keep track of movement, shooting, animations and sets the image
     * 
     * @param curX          x-coordinate
     * @param curY          y-coordinate
     * @param curHp         HP of player
     */
    public Player(int curX, int curY, int curHp){
        x = curX;
        y = curY;
        maxHp = curHp;
        hp = curHp;
        speed = 3;
        direction = "right";
        
        maxShootCD = 30;
        shootCD = 0;
        maxAmmoCount = 4;
        ammoCount = maxAmmoCount;
        
        speedBuff = 0;
        bulletBuff = 0;
        
        score = 0;

        playerImage.scale(playerImage.getWidth()*GameWorld.SCALING, playerImage.getHeight()*GameWorld.SCALING);
        setImage(playerImage);
        
        animSpeed = 5;
        animDelay = animSpeed;
        
        playerShootImages = new GreenfootImage[4];
        for (int i = 0; i < playerShootImages.length; i++){
            playerShootImages[i] = new GreenfootImage("playerShoot" + (i+1) + ".png");
            playerShootImages[i].scale(playerImage.getWidth(), playerImage.getHeight());
        }
        
        
        
        frame = 0;

        hitFilterTime = 1;
    }
    /**
     * Acessor for player HP
     */
    private int getHp(){
        return hp;
    }
    /**
     * Act method
     * Detect inputs
     * Play animations and effects
     * Uses buffs
     */
    public void act()
    {
       decreaseBuffs();
               
       inputKeys();
       playAnim();
       playEffects();
    }
    /**
     * Detects what keys are inputted
     */
    private void inputKeys(){
        inputDirKeys();
        inputShootKey();
        
    }
    /**
     * Detects and handles shooting inputs
     */
    private void inputShootKey(){
        shootCD = Math.max (shootCD - 1, 0);
        if(shootCD == 0){//If cooldown is equal to zero, add ammo count
            shootCD = maxShootCD;
            ammoCount = Math.min(ammoCount+1, maxAmmoCount);
        }
            
        
        String curKey = Greenfoot.getKey();
        if(curKey == null) return;
        
        switch(curKey){
            case "space":
                shootBullet();
                break;
            case "escape": //The only reason why this is here is because if getKey() is ran in GameWorld, getKey() here would be null since act in world runs before act of player, so it's easier to just leave it here
                if(getWorld().getClass().getName().equals("GameWorld")){
                    Function pause = ((GameWorld)getWorld()).pauseGame;
                    pause.apply();
                }
            break;
        }
    }
    /**
     * Detects and handles moving inputs
     */
    private void inputDirKeys(){
        for(int i = 0; i < validInputs.length; i++){ //Adds or removes the list of keys currently pressed down
            if(Greenfoot.isKeyDown(validInputs[i])){
                if(!keysPressed.contains(validInputs[i])){
                    keysPressed.add(validInputs[i]);
                }
            }else{
                if(keysPressed.contains(validInputs[i])){
                    keysPressed.remove(validInputs[i]);
                }
            }
        }
        String curKey = "";
        
        if(keysPressed.size()>0) curKey = keysPressed.get(keysPressed.size()-1);
        if(curKey==null) return;
        if(dirInputs.get(curKey)!=null) curKey = dirInputs.get(curKey); //Gets the direction String according to the input

        switch(curKey){
            case "left":
            case "right":
            case "up":
            case "down":
                direction = curKey;
                Utils.rotate(this, Utils.getDirInts().get(curKey)*90, 10);
                int curSpeed = (speedBuff>0)?speed+1:speed;
                move(curSpeed);
                if(collide()){ //If colliding if anything, move back
                    move(-curSpeed);
                } 
                break;
                         
        }
       
        createTracks();
    }
    /**
     * Spawns bullet and subtracts current ammo count
     * Bullet depends on the buff
     */
    private void shootBullet(){
        if(ammoCount > 0){
            frame = 0;
            shootCD = maxShootCD;
            ammoCount--;
            if(bulletBuff>0){
                Bullet bullet = new Bullet(getX(), getY(), direction, 10,  2, "bigBullet.png", this);
                bullet.spawn(getWorld());
            }else{
                Bullet bullet = new Bullet(getX(), getY(), direction, 10, 1, "bullet.png", this);
                bullet.spawn(getWorld());
            }
            
        }
    }
    /**
     * Gets all classes colliding with this, and returns true of any of them are player, enemy or wall
     */
    private boolean collide(){
        List<Actor> colliding = getObjectsInRange(getImage().getHeight(),Actor.class);
        //System.out.println(colliding);
        for(int i = 0 ; i < colliding.size(); i++){
            if(colliding.get(i).getClass().getName().equals("Player")){
                return true;
            }
            if(colliding.get(i).getClass().getSuperclass().getName().equals("Enemy")){
                return true;
            }
            if(colliding.get(i).getClass().getSuperclass().getName().equals("Wall")){
                return true;
            }
        }
        List<Enemy> bossColliding = getIntersectingObjects(Enemy.class);
        for(int i = 0 ; i < bossColliding.size(); i++){
            if(bossColliding.get(i).getClass().getName().equals("BossBody")){
                return true;
            }
            if(bossColliding.get(i).getClass().getName().equals("BossGun")){
                return true;
            }
        }
        
        return false;
    }
    /**
     * Spawn tracks
     */
    private void createTracks(){
        Tracks track = new Tracks(getX(), getY(), direction, getImage().getWidth(), playerTrackOffset);
        track.spawn(getWorld());
    }
    /**
     * Takes damage, dies when hp falls below 0 and sets transparency for hit effect
     * 
     * @param dmg   amount of dmg
     */
    public void takeDamage(int dmg){
        hp = Math.max(hp-dmg, 0);
        
        getWorld().addObject(new Flash(), getWorld().getWidth()/2, getWorld().getHeight()/2);
        
        getImage().setTransparency(150);
        hitFilterTime = 6;
        if(hp<=0){
            die();
        }
    }
    /**
     * Removes this from GameWorld, then adds transition to go to LoseWorld, which adds this back into LoseWorld
     */
    private void die(){
        getWorld().addObject(new Transition(new LoseWorld(this)), getWorld().getWidth()/2, getWorld().getHeight()/2);
    }
    /**
     * Ticks down on buffs until they reach 0
     */
    private void decreaseBuffs(){
        speedBuff = Math.max(0, speedBuff-1);
        bulletBuff = Math.max(0, bulletBuff-1);
    }
    /**
     * Plays a 4 frame shoot animation
     */
    private void playAnim(){
        
        
        if(maxShootCD - shootCD < animSpeed*4){
            animDelay = Math.max (animDelay - 1, 0);
            if (animDelay == 0){
                frame++;
                if (frame >= 3){
                    setImage(playerImage);
                    return;
                }
                animDelay = animSpeed;
            }
            setImage (playerShootImages[frame]);
        }
        
    }
    /**
     * Plays smoke and hit effects
     */
    private void playEffects(){
        if(hp <= maxHp / 2){
            Smoke smokeEffect = new Smoke(getX(), getY(), speed);
            smokeEffect.spawn(getWorld());
        }
        
        if(speedBuff>0&&Greenfoot.getRandomNumber(10)==1){
            Speed speedEffect = new Speed(getX(), getY(), speed);
            speedEffect.spawn(getWorld());
        }
        
        hitFilterTime--;
        if(hitFilterTime <= 0){
            getImage().setTransparency(255);
        }
    }
    /**
     * Mutator method for score
     */
    protected void addScore(){
        score++;
    }
    /**
     * Accessor method for score
     */
    protected int getScore(){
        return score;
    }
}
