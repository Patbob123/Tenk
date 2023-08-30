import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The world the player goes to when they lose
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class LoseWorld extends World
{

    private int explosionDelay = 120;
    private GreenfootImage endBgImage = new GreenfootImage("endBg.png");
    private GreenfootImage gameoverImage = new GreenfootImage("gameover.png");
    private Font FONT = new Font("Impact", true, false , 50);
    
    private Player player;
    private Button b;
    
    /**
     * Adds the player into the world then sets background
     * 
     * @param   curPlayer this is the player that will explode as a result of losing
     */
    public LoseWorld(Player curPlayer)
    {    
        super(960, 544, 1);  
        
        player = curPlayer;
        addObject(player, player.getX(), player.getY());
        
        gameoverImage.scale(gameoverImage.getWidth()*GameWorld.SCALING, gameoverImage.getHeight()*GameWorld.SCALING);
        endBgImage.scale(endBgImage.getWidth()*GameWorld.SCALING, endBgImage.getHeight()*GameWorld.SCALING);
        
        setBackground(endBgImage);
        
        paintOrder();
        
        MusicManager.stopTrack();
    }
    /**
     * The time until the explosion ticks down
     * Once it reaches 0, an explosion is spawned at the player and player is removed
     * GUI elements are added in after
     */
    public void act(){
        explosionDelay--;
        if(explosionDelay==0){
            Explosion e = new Explosion(player.getImage().getWidth()*2, player.getImage().getHeight()*2, player.getX(), player.getY());
            removeObject(player);
            e.spawn(this, player);
            
            setBackground(gameoverImage);
            b = new Button(startGame, "start.png");
            addObject(b, gameoverImage.getWidth()/2, (gameoverImage.getHeight()/2)+(GameWorld.SCALING*80));
            b.init();
            
            addLabels(new String[]{"YOU LOSE","SCORE:"+player.getScore()});
        }
        
        
    }
    /**
     * Set labels to add to the current world
     * 
     * @param labels    a list of labels to add to the Label object - each label corresponds with a value
     */
    private void addLabels(String[] labels){
        for(int i = 0 ; i < labels.length; i++){
            Label l = new Label(FONT, new Color(253, 250, 79), getWidth()/3, b.getImage().getHeight()); //Height set as button height
            addObject(l, getWidth()/2, (2)*(i+3)*(getHeight())/17);
            l.setLabels(new String[]{labels[i]}); //Input labels
            l.update(new String[]{""}); //Input value, none in this case
        }
    }
    
    /**
     * Sets paint order of the classes
     */
    private void paintOrder(){
        setPaintOrder(Effects.class, Cursor.class, Player.class, Enemy.class, Projectile.class);
    }
    
    /**
     * Adds a transition object that goes back to a new GameWorld, ie restarting the game
     */
    public Function startGame = () -> addObject(new Transition(new GameWorld()), getWidth()/2, getHeight()/2);
}
