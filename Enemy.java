import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The enemy class contains how the enemy shoots, collides, moves, drops powerups and dies
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public abstract class Enemy extends Actor
{
    protected int maxHp;
    protected int hp;
    protected int speed;
    protected int normalSpeed;
    protected String direction;
    protected int trackOffset;
    
    protected String movementMode;
    protected int movingTime;
    protected int maxMovementTime;
    protected int hitFilterTime;
    
    protected int spawnChance;
    
    protected GreenfootImage enemyImage;
    
    /**
     * Initialize variables to keep track of movement and sets the image
     * 
     * @param curHp             HP of the enemy
     * @param curSpeed          Speed of the enemy
     * @param enemyType         The image file that the enemy will use
     * @param curTrackOffset    Where the move tracks will spawn relative to enemy
     * @param curSpawnChance    How often does the enemy drop a powerup
     */
    public Enemy(int curHp, int curSpeed, String enemyType, int curTrackOffset, int curSpawnChance){
        maxHp = curHp;
        hp = curHp;
        speed = curSpeed;
        normalSpeed = curSpeed;
        direction = "right";
        hitFilterTime = 1;
        
        movementMode = "";
        movingTime = 0;
        maxMovementTime = 60;
        trackOffset = curTrackOffset;
        
        spawnChance = curSpawnChance;
        
        enemyImage = new GreenfootImage(enemyType);
        enemyImage.scale(enemyImage.getWidth()*GameWorld.SCALING, enemyImage.getHeight()*GameWorld.SCALING);
        setImage(enemyImage);
    }
    
    /**
     * Act method
     * Decide how the enemy will move
     * Play effects
     */
    public void act() 
    {
        decideMovement();
        playEffects();
    }    
    
    /**
     * Randomly decides if enemy wants to chase after player, stay still, or move randomly
     */
    protected void decideMovement(){
       Player player = ((GameWorld)getWorld()).getPlayer();
        if(movementMode.equals("towardsPlayer")){
            if(Math.abs(player.getX()-getX())<player.getImage().getWidth()/3
            ||Math.abs(player.getY()-getY())<player.getImage().getHeight()/3){ //if enemy is at player, enemy stops
                speed = 0;
            }
            
        }
        
        if(movingTime<=0){ //changes movement pattern if at player, or if current movement pattern is over
            int movementModeInt = Greenfoot.getRandomNumber(5); //Gives a random movement pattern int
            changeMovement(movementModeInt);
            shootBullet();
        }else{
            movingTime--;
            move(speed);
            if(collide()){ //If enemy hits the player, move back
                move(-speed); 
            }
            createTracks(); //Spawn tracks
        }
    }
    
    /**
     * Setup the enemy direction based off current movementModeInt (normally random)
     * 
     * @param movementModeInt   The int that determines how the enemy moves
     */
    protected void changeMovement(int movementModeInt){
        
        switch(movementModeInt){
            case 1:
                movementMode = "random";
                int randomDirection = Greenfoot.getRandomNumber(4);
                direction = Utils.getIntDirs().get(randomDirection);
                setupMovement();
                break;
            case 2:
                speed = 0;
                break;
                
            default:
                movementMode = "towardsPlayer";
                int xyDirection = Greenfoot.getRandomNumber(2); //Either turns towards a player's x or a player's y
                if(xyDirection == 0) {//x
                    direction = getX() - ((GameWorld)getWorld()).getPlayer().getX() > 0 ? "left": "right";
                }else if(xyDirection == 1){ //y
                    direction = getY() - ((GameWorld)getWorld()).getPlayer().getY() > 0 ? "up": "down";
                }
                setupMovement();
                break;
                
        }
        movingTime = Utils.getRandomNumber(maxMovementTime, maxMovementTime+200); //random range of time for current moving pattern
        
    }
    /**
     * Resets speed and rotates the enemy 
     */
    private void setupMovement(){
        
        speed = normalSpeed;
        Utils.rotate(this, Utils.getDirInts().get(direction)*90, 10);
    }
    /**
     * Gets all classes colliding with this, and returns true of any of them are player, enemy or wall
     */
    private boolean collide(){
        List<Actor> colliding = getObjectsInRange(getImage().getWidth()+10,Actor.class);
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
        List<Enemy> bossColliding = getIntersectingObjects(Enemy.class); //Additional check for boss due to boss's inaccurate hitbox
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
     * Method to avoid spawning in other objects, pushes the enemy out instead
     */
    protected void avoidCollision(){
        while(collide()){
            int randomDirection = Greenfoot.getRandomNumber(2);
            if(randomDirection == 0){
                move(-1); 
            }else{
                move(1); 
            }
            
        }
    }
    /**
     * Spawn tracks
     */
    private void createTracks(){
        Tracks track = new Tracks(getX(), getY(), direction, getImage().getWidth(), trackOffset);
        track.spawn(getWorld());
    }
    /**
     * Spawns a bullet at current direction
     */
    private void shootBullet(){
        Bullet bullet = new Bullet(getX(), getY(), direction, 4, 1, "bullet.png", this);
        bullet.spawn(getWorld());
    }
    /**
     * Takes damage, dies when hp falls below 0 and sets transparency for hit effect
     * 
     * @param dmg   amount of dmg
     */
    protected void takeDamage(int dmg){
        hp-=dmg;
        getImage().setTransparency(150);
        hitFilterTime = 6;
        if(hp<=0){
            die();
        }

    }
    /**
     * Spawn an explosion, have a chance to spawn a powerup and removes the enemy from the world
     */
    protected void die(){
        if(getWorld()==null) return;
        
        Explosion e = new Explosion(enemyImage.getWidth(), enemyImage.getHeight(), getX(), getY());
        spawnPickup();
        
        
        GameWorld world = (GameWorld)getWorld();
        world.getPlayer().addScore();
        world.removeObject(this);
        
        e.spawn(world, this);
    }
    /**
     * Rolls a random number, if it's below the spawnChance of the powerup, spawn a random powerup
     */
    private void spawnPickup(){
        int randNumber = Greenfoot.getRandomNumber(100)+1;
        if(randNumber <= spawnChance){
            Pickup.spawnRandomPickup(getWorld(), getX(), getY());
        }
    }
    /**
     * Plays smoke and hit effects
     */
    protected void playEffects(){
        if(hp <= maxHp / 2){
            Smoke smoke = new Smoke(getX(), getY(), speed);
            smoke.spawn(getWorld());
        }
        
        hitFilterTime--;
        if(hitFilterTime == 0){
            getImage().setTransparency(255);
        }
    }
            
}


