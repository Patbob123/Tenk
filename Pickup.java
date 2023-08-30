import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Parent class for powerups/pickups
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Pickup extends Actor
{   
    protected static int MAX_PICKUPS = 3;
    
    public Pickup(){

    }
    
    /**
     * Gets a random int, and add corresponding powerup to the world
     */
    public static void spawnRandomPickup(World world, int curX, int curY){
        int randomPickup = Greenfoot.getRandomNumber(MAX_PICKUPS);
        switch(randomPickup){
            case 0:
                BulletUP bulletUp = new BulletUP(1200);
                world.addObject(bulletUp, curX, curY);
                break;
            case 1:
                HealthUP healthUp = new HealthUP(2);
                world.addObject(healthUp, curX, curY);
                break;
            case 2:
                SpeedUP speedUp = new SpeedUP(1200);
                world.addObject(speedUp, curX, curY);
                break;
        }
    }
}

