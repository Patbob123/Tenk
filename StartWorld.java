import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * WELCOME TO TENK GAME
 * =====================
 * Play as a tank that needs to eliminate the threat of the Spider Troopers through a span of [10] levels.
 * Move using WASD or Arrow keys to move the character, SPACE to shoot
 * Collect 3 unique powerups with special effects 
 * Explode or get exploded by the gas barrels randomly placed around the map
 * Defeat waves of enemies that increase in number and variety to proceed to the next level
 * Defeat the final boss with 3 different attack patterns to earn victory
 * 
 * Start with a four health, four ammo
 * 
 * Music credits:
 * "Battle of Pogs" - Komiku
 * "Dragon Castle" - Makai Symphony
 * "My Dark Passenger" - Darren Curtis
 * 
 * KNOWN ISSUES:
 *  -Button is jittery if the cursor is at the bottom due it mouse rapidly leaving and entering the button boundries
 *  -Tires are meant to be indestructible, but JUST IN CASE the player is trapped between four tires, it can be destroyed after 50 shots (dmg does not matter)
 *  -The boss can be "cheesed" if the player moves to either side of the screen, this is actually intended since the player cannot directly reach the boss this way
 * 
 * 
 * @author Dawson
 * @version 2023-06-20
 */

/**
 * The start of the game
 * Contains a button which loads into the GameWorld
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class StartWorld extends World
{
    private GreenfootImage startBgImage = new GreenfootImage("startBg.png");
    
    /**
     * Sets background
     * Adds a button
     */
    public StartWorld()
    {    
        super(960, 544, 1); 
        
        startBgImage.scale(startBgImage.getWidth()*GameWorld.SCALING, startBgImage.getHeight()*GameWorld.SCALING);
        setBackground(startBgImage);
        
        
        addObject(new Title(), getWidth()/2, getHeight()/2-GameWorld.SCALING*40);
        
        Button b = new Button(startGame, "start.png");
        addObject(b, getWidth()/2, getHeight()/2+GameWorld.SCALING*60);
        b.init();
        
        paintOrder();
        MusicManager.initTracks();
        MusicManager.switchTrack(0);
    }   
    
    /**
     * Sets the paint order of the classes
     * 
     */
    private void paintOrder(){
        setPaintOrder(Transition.class, Button.class);

    }
    
    /**
     * Adds a transition to go to the GameWorld
     * 
     */
    public Function startGame = () -> addObject(new Transition(new GameWorld()), getWidth()/2, getHeight()/2);

    
}
