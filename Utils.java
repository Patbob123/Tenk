import greenfoot.*;
import java.util.HashMap;
/**
 * Utilities class, duration is not fully implemented as of 2023-06-20
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Utils
{
    private static HashMap<String ,Integer> directionInts = new HashMap<String, Integer>(){{ //Hashmap that corresponds direction strings and ints
        put("left", 2);
        put("right", 0);
        put("up", 3);
        put("down", 1);
    }};
    private static HashMap<Integer ,String> intDirections = new HashMap<Integer, String>(){{//Hashmap that corresponds ints to direction strings
        put(2, "left");
        put(0, "right");
        put(3, "up");
        put(1, "down"); 
    }};
    
    /**
     * Method to rotate a given actor
     * 
     * @param actor     The actor
     * @param rotation  Degree of the rotation
     * @param duration  How long the rotation will take
     */
    public static void rotate(Actor actor, int rotation, int duration)
    {
        rotateHelper(actor, rotation/duration, duration, 0);
    }
    
    /**
     * Method to rotate a given actor
     * 
     * @param actor             The actor
     * @param interRotation     Degree of intermediate rotations
     * @param duration          How long the rotation will take
     * @param i                 Current intermediate rotation 
     */
    public static void rotateHelper(Actor actor, int interRotation, int duration, int i){
        if(i>=duration+1) return;
        actor.setRotation(i*interRotation);
        rotateHelper(actor, interRotation, duration, i+1);
    }
    
    /**
     * Accesor for directionInts
     */
    public static HashMap<String, Integer> getDirInts(){
        return directionInts;
    }
    /**
     * Accesor for intDirections
     */
    public static HashMap<Integer, String> getIntDirs(){
        return intDirections;
    }
    /**
     * Better version of Greenfoot.getRandomNumber that allows for a set range
     */
    public static int getRandomNumber(int start,int end)
    {
           int normal = Greenfoot.getRandomNumber(end-start+1);
           return normal+start;
    }
    
}
