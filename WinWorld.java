import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The world the player goes to when they win
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class WinWorld extends World
{

    private int explosionDelay = 600;
    private GreenfootImage endBgImage = new GreenfootImage("endBg.png");
    private GreenfootImage gameoverImage = new GreenfootImage("gameover.png");
    private Font FONT = new Font("Impact", true, false , 50);
    
    private Enemy boss;
    private Player player;
    
    private Button b;
    
    /**
     * Adds the boss to the world to be exploded
     * Sets background
     * 
     * @param curPlayer     Player's score will be accessed to be displayed
     * @param bossBody      The boss that will explode as a result of winning
     */
    public WinWorld(Player curPlayer, Enemy bossBody)
    {    
        super(960, 544, 1);  
        
        player = curPlayer;
        boss = bossBody;
        addObject(bossBody, bossBody.getX(), bossBody.getY());
        
        gameoverImage.scale(gameoverImage.getWidth()*GameWorld.SCALING, gameoverImage.getHeight()*GameWorld.SCALING);
        endBgImage.scale(endBgImage.getWidth()*GameWorld.SCALING, endBgImage.getHeight()*GameWorld.SCALING);
        
        setBackground(endBgImage);
        
        paintOrder();
        
        MusicManager.stopTrack();
    }
    
    /**
     * explosionDelay ticks down each act until it reaches 0
     * 
     */
    public void act(){
        explosionDelay = Math.max(explosionDelay-1, 1);
        int randNumber = Greenfoot.getRandomNumber(explosionDelay); //As explosionDelay decrease the likelihood of an explosion increase each act
        if((randNumber<=10)&&explosionDelay>2){
            
            int offsetX = Greenfoot.getRandomNumber(boss.getImage().getWidth()/2); //Gets random X within the boss
            if(Greenfoot.getRandomNumber(2)==0){
                offsetX = offsetX*-1;
            }
            int offsetY = Greenfoot.getRandomNumber(boss.getImage().getHeight()/2); //Gets random Y within the boss
            if(Greenfoot.getRandomNumber(2)==0){
                offsetY = offsetY*-1;
            }
            
            Explosion e = new Explosion(boss.getImage().getWidth(), boss.getImage().getHeight(), boss.getX() + offsetX, boss.getY() + offsetY);
            e.spawn(this, boss);
        }
        if(explosionDelay == 2){ //If explosionDelay reaches almost 0, remove the boss and create GUI elements
            removeObject(boss);
  
            setBackground(gameoverImage);
            b = new Button(startGame, "start.png");
            addObject(b, gameoverImage.getWidth()/2, (gameoverImage.getHeight()/2)+(GameWorld.SCALING*80));
            b.init();
            
            addLabels(new String[]{"YOU WIN","SCORE:"+player.getScore()});
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
            l.setLabels(new String[]{labels[i]});
            l.update(new String[]{""});
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
