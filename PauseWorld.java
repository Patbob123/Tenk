import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Press ESC to be sent to the pause world
 * Shows brief instructions on key bindings
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class PauseWorld extends World
{

    private World nextWorld;
    
    private GreenfootImage pauseBackgroundImage = new GreenfootImage("pausebackground.png");
    private Font FONT = new Font("Impact", true, false , 30);
    private Button b;
    
    /**
     * The world the game goes to when paused
     * 
     * @param   curWorld the world to go back to later
     */
    public PauseWorld(World curWorld)
    {    
        super(960, 544, 1); 
        nextWorld = curWorld;
        
        b = new Button(unpauseGame, "pause.png");
        addObject(b, 14*getWidth()/15, 2*getHeight()/17);
        b.init();
        
        addHowToLabels(new String[]{"[W,A,S,D] - MOVE", "[ARROW KEYS] - MOVE", "[SPACE] - SHOOT", "COLLECT POWERUPS", "STOP DYING"});
        
        pauseBackgroundImage.scale(pauseBackgroundImage.getWidth()*GameWorld.SCALING, pauseBackgroundImage.getHeight()*GameWorld.SCALING);
        setBackground(pauseBackgroundImage);
    }
    
    /**
     * Checks for the ESC key, unpause if it is pressed
     */
    public void act(){
        if(("escape").equals(Greenfoot.getKey())){
            Function unpause = unpauseGame;
            unpauseGame.apply();
        }
    }
    
    /**
     * Adds all the instruction labels on the pause screen
     * 
     * @param howTos    List of labels to add onto the object Label
     */
    private void addHowToLabels(String[] howTos){
        for(int i = 0 ; i < howTos.length; i++){
            Label l = new Label(FONT, new Color(253, 250, 79), getWidth()/3, b.getImage().getHeight()); //Height set as button height
            addObject(l, 2*(i+2)*(getWidth())/14, (2)*(i+2)*(getHeight())/17);
            l.setLabels(new String[]{howTos[i]});
            l.update(new String[]{""});
        }
    }
    
    /**
     * Adds a transition to go back to the original world(Only GameWorld as of 2023-06-20)
     */
    public Function unpauseGame = () -> addObject(new Transition(nextWorld), getWidth()/2, getHeight()/2);
}
