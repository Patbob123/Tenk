import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;
/**
 * Bullet interactions with enemies and players are computed here
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Bullet extends Projectile
{
    private int spawnOffset = 10;
    
    private int startX;
    private int startY;
    private int rotation;
    private String direction;

    private int speed;
    private int dmg;
    private Actor owner;
    
    private GreenfootImage bulletImage;
    
    private GreenfootSound shootSound = new GreenfootSound("shoot8.wav");
    /**
     * Initialize variables to keep track of movement and sets the image for normal bullets
     * 
     * @param curX              x-coordinate
     * @param curY              y-coodinate
     * @param curDirection      The direction the bullet will go
     * @param curSpeed          Speed
     * @param curDmg            Damage
     * @param imgFileName       The name of the image file
     * @param curOwner          The owner of the bullet
     */
    public Bullet(int curX ,int curY, String curDirection, int curSpeed, int curDmg, String imgFileName, Actor curOwner){
        startX = curX;
        startY = curY;
        direction = curDirection;
        rotation = Utils.getDirInts().get(curDirection)*90;
        speed = curSpeed;
        dmg = curDmg;
        owner = curOwner;
        
        bulletImage = new GreenfootImage(imgFileName);
        bulletImage.scale(bulletImage.getWidth()*GameWorld.SCALING, bulletImage.getHeight()*GameWorld.SCALING);
        setImage(bulletImage);
    }
    /**
     * Initialize bullet for boss bullets
     * 
     * @param curX              x-coordinate
     * @param curY              y-coodinate
     * @param curRotation       The rotation of the bullet
     * @param curSpeed          Speed
     * @param curDmg            Damage
     * @param curOwner          The owner of the bullet
     */
    public Bullet(int curX ,int curY, int curRotation, int curSpeed, int curDmg, Actor curOwner){
        startX = curX;
        startY = curY;
        rotation = curRotation;
        direction = "";
        speed = curSpeed;
        dmg = curDmg;
        owner = curOwner;
        
        bulletImage = new GreenfootImage("bossbullet.png");
        bulletImage.scale(bulletImage.getWidth()*GameWorld.SCALING, bulletImage.getHeight()*GameWorld.SCALING);
        setImage(bulletImage);
    }
    
    /**
     * Sets rotation and adds bullet to the world
     */
    public void spawn(World world){
        int offsetX = 0; 
        int offsetY = 0;
        switch (direction){
            case "left":
                offsetX = -spawnOffset;
                break;
            case "right":
                offsetX = spawnOffset;
                break;
            case "down":
                offsetY = spawnOffset;
                break;
            case "up":
                offsetY = -spawnOffset;
                break;
        }
        setRotation(rotation);
        
        shootSound.setVolume(80);
        shootSound.play();
        
        world.addObject(this, startX+offsetX, startY+offsetY);
    }
    
    /**
     * Act method
     * Moves and do damage
     * Removes this when reached the edge of the world
     */
    public void act() 
    {
        checkDoDamage();
        move(speed);
        if(this.getWorld()==null) return;
        
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
        
    }  
    /**
     * Gets a list of actors colliding with this and do damage to any valid targets
     */
    public void checkDoDamage(){
        List<Actor> actors = getIntersectingObjects(Actor.class);
        
        if(actors==null) return;
        
        for(int i = 0 ; i < actors.size(); i++){
            Actor a = actors.get(i);
            if(a==owner)continue;
            if(owner.getClass().getName().equals("BossGun")
                &&(a.getClass().getName().equals("BossBody")
                    ||a.getClass().getName().equals("BossGun"))) continue;
                
            if(a.getClass().getSuperclass().getName().equals("Enemy")){
                ((Enemy)a).takeDamage(dmg);
                getWorld().removeObject(this);
                return;
            }
            else if(a.getClass().getName().equals("Player")){
                    ((Player)a).takeDamage(dmg);
                    getWorld().removeObject(this);
                    return;
            }else if(a.getClass().getSuperclass().getName().equals("Wall")){
                   ((Wall)a).takeDamage();
                   getWorld().removeObject(this);
                   return;
            }
            
        }
    }
}
