import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * GameWorld is where the game takes place
 * 
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class GameWorld extends World
{
    private GreenfootImage backgroundImage = new GreenfootImage("gamebackground.png");
    private GreenfootImage bossBackgroundImage = new GreenfootImage("bossbackground.png");
    private Font FONT = new Font("Impact", true, false , 20);
    private Label stats;
    private Player player;
    private int totalTicks; //keeps track of time
    
    private int spawnRate;
    private int spawnTick;
    
    private int maxLevel;
    private int maxWave;
    
    private int level;
    private int wave;
    private int enemyNum;
    
    private int enemyHp;
    
    private ArrayList<ArrayList<ArrayList<EnemySpawn>>> enemySpawns;
    private ArrayList<ArrayList<WallSpawn>> wallSpawns;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Wall> wallList;
    
    public static int SCALING = 2;
    public GameWorld()
    {    
        super(960, 544, 1); 
        spawnRate = 30;
        spawnTick = 0;
        
        maxWave = 3;
        maxLevel = 9;
        
        wave = 0;
        level = 0;
        enemyNum = 0;
        
        enemyHp = 2;
        
        totalTicks = 0;

        paintOrder();
        initObjects();
        
        refreshEnemyList();
        createRandomEnemySpawns();
        //createEnemySpawns();
        refreshWallList();
        createRandomWallSpawns();
        spawnWalls();
        
        
        bossBackgroundImage.scale(bossBackgroundImage.getWidth()*GameWorld.SCALING, bossBackgroundImage.getHeight()*GameWorld.SCALING);
        backgroundImage.scale(backgroundImage.getWidth()*GameWorld.SCALING, backgroundImage.getHeight()*GameWorld.SCALING);
        setBackground(backgroundImage);
        
        MusicManager.switchTrack(1);
    }
    
    /**
     * Creates all the objects at the start of the game (buttons, labels, ui, player)
     */
    private void initObjects(){
        Button b = new Button(pauseGame, "pause.png");
        addObject(b, 14*getWidth()/15, 2*getHeight()/17);
        b.init();
        
        stats = new Label(FONT, new Color(140, 58, 192), getWidth()/2, b.getImage().getHeight());
        addObject(stats, 4*getWidth()/15, 2*getHeight()/17);
        stats.setLabels(new String[]{"LEVEL:", "WAVE:", "SCORE:"});
        stats.init();
        
        addObject(new Cursor(), 2, 2);
        
        player = new Player(2,2,4);
        addObject(player, getWidth()/2, getHeight()/2);
        
        addObject(new HealthBar(player), 14*getWidth()/15, 15*getHeight()/17);
        addObject(new AmmoBar(player, this), 10*getWidth()/15, 15*getHeight()/17);
  
        
    }
    /**
     * Act method
     * Adds time
     * Update stats label
     * Spawn enemies on normal levels, and randomly spawn enemies on boss level
     */
    public void act(){
        totalTicks++; 
        stats.update(new String[]{String.valueOf(level+1), String.valueOf(wave+1), String.valueOf(player.getScore())});
        if(level != enemySpawns.size()){
            spawnEnemies();
        }
        else{
            spawnRate = 600;
            spawnRandomEnemies();
        }
    }
    /**
     * Sets the paint order of the classes on this world
     */
    private void paintOrder(){
        setPaintOrder(
            Flash.class, 
            Transition.class,
            UI.class, 
            Button.class, 
            Label.class, 
            Effects.class, 
            Cursor.class, 
            Player.class, 
            BossBody.class, 
            BossGun.class, 
            Enemy.class, 
            Projectile.class
        );

    }
    /**
     * Acessor for the player in the world
     */
    public Player getPlayer(){
        return player;
    }    
    /**
     * Gets the current list of enemies and spawns them in waves and levels
     * Each time all the enemies are eliminated, a new wave spawns
     * Every maxWave number of waves completed will proceed to the next level
     */
    private void spawnEnemies(){
        spawnTick++; 
        if(spawnTick%spawnRate == 0){ //Spawns when spawnTick reaches a multiple of spawnRate
            if(enemyNum != enemySpawns.get(level).get(wave).size()){ //If nothing to interrupt the spawn, spawn enemy
                EnemySpawn spawn = enemySpawns.get(level).get(wave).get(enemyNum);
                if (!checkSpawnHitbox(spawn.enemy.getImage().getWidth(), spawn.enemy.getImage().getHeight(), spawn.startX, spawn.startY)){
                    addObject(new Target(this, spawn.enemy, 1), spawn.startX, spawn.startY);
                }
            }
            enemyNum = Math.min(enemyNum+1, enemySpawns.get(level).get(wave).size());
            if((getObjects(Enemy.class).size()==0)
            &&(getObjects(Target.class).size()==0)
            &&(enemyNum == enemySpawns.get(level).get(wave).size())){ //If nothing is alive, spawn the next wave
                enemyNum = 0;
                wave = Math.min(wave+1, enemySpawns.get(level).size());
                if((getObjects(Enemy.class).size()==0)
                &&(getObjects(Target.class).size()==0)
                &&(wave == enemySpawns.get(level).size())){//If no waves left, go to next level
                    wave = 0;
                    level = Math.min(level+1, enemySpawns.size());
                    nextLevel.apply();
                    
                    removeObjects(getObjects(Bullet.class)); 
                    removeObjects(getObjects(Wall.class)); 
                    if(level == enemySpawns.size()){
                        player.setLocation(getWidth()/2, getHeight()/2);
                        spawnBoss();
                        MusicManager.switchTrack(3);
                    }else{
                        spawnWalls();
                    }
                }
            }
        }
        
    }
    /**
     * Spawns enemies at random coordinates every so often based off incrementing spawnTick and spawnRate
     * Used during the boss fight
     */
     private void spawnRandomEnemies(){
        spawnTick++;
        if(spawnTick%spawnRate == 0){//Every time spawnTick reaches at muliple of spawnRate, attempt to spawn something
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            Enemy enemy = enemyList.get(Greenfoot.getRandomNumber(enemyList.size()));
            if (!checkSpawnHitbox(enemy.getImage().getWidth(), enemy.getImage().getHeight(), x, y)){ //If there is nothing here, spawn the enemy
                addObject(new Target(this, enemy, 1), x, y);
                refreshEnemyList();
            }
        }
        
    }
    /**
     * Creates and positions the arms and body of the boss
     */
    private void spawnBoss(){
        setBackground(bossBackgroundImage);
        
        Enemy boss = new BossBody(50);
        Enemy bossGun1 = new BossGun(20, "left");
        Enemy bossGun2 = new BossGun(20, "right");
        bossGun1.getImage().mirrorHorizontally(); //Left image is mirrored
        
        int bossGun1X = getWidth()/2-bossGun1.getImage().getWidth()*6/5; //Calculate gun positions
        int bossGun2X = getWidth()/2+bossGun2.getImage().getWidth()*6/5;
        
        addObject(boss ,getWidth()/2,100);
        addObject(bossGun1,bossGun1X,120);
        addObject(bossGun2,bossGun2X,120);
        
    }
    /**
     * Gets the current list of obstacles and spawns them each level
     */
    private void spawnWalls(){
        for(WallSpawn spawn: wallSpawns.get(level)){
            if (!checkSpawnHitbox(spawn.wall.getImage().getWidth(), spawn.wall.getImage().getHeight(), spawn.startX, spawn.startY)){
                addObject(spawn.wall, spawn.startX, spawn.startY);
            }
        }
        
    }
    /**
     * Manual method for creating each enemy at each location
     * Customize waves and levels using this method
     */
    private void createEnemySpawns(){ //Creates all the enemies at each level and wave, should probably be loaded from a file
        enemySpawns = new ArrayList<>();
        
        ArrayList<ArrayList<EnemySpawn>> l1 = new ArrayList<>();
        
        ArrayList<EnemySpawn> l1w1 = new ArrayList<>();
        l1w1.add(new EnemySpawn(new NormalEnemy(2), 30*SCALING, 100*SCALING));
        l1w1.add(new EnemySpawn(new NormalEnemy(2), 35*SCALING, 200*SCALING));
        l1w1.add(new EnemySpawn(new NormalEnemy(2), 340*SCALING, 108*SCALING));
        l1.add(l1w1);
        
        ArrayList<EnemySpawn> l1w2 = new ArrayList<>();
        l1w2.add(new EnemySpawn(new NormalEnemy(2), 50*SCALING, 150*SCALING));
        l1w2.add(new EnemySpawn(new NormalEnemy(2), 02*SCALING, 150*SCALING));
        l1w2.add(new EnemySpawn(new NormalEnemy(2), 107*SCALING, 150*SCALING));
        l1.add(l1w2);
        
        
        ArrayList<ArrayList<EnemySpawn>> l2 = new ArrayList<>();
        
        ArrayList<EnemySpawn> l2w1 = new ArrayList<>();
        l2w1.add(new EnemySpawn(new NormalEnemy(2), 30*SCALING, 100*SCALING));
        l2w1.add(new EnemySpawn(new NormalEnemy(2), 35*SCALING, 200*SCALING));
        l2w1.add(new EnemySpawn(new NormalEnemy(2), 340*SCALING, 108*SCALING));
        l2.add(l2w1);
        
        ArrayList<EnemySpawn> l2w2 = new ArrayList<>();
        l2w2.add(new EnemySpawn(new NormalEnemy(2), 50*SCALING, 150*SCALING));
        l2w2.add(new EnemySpawn(new NormalEnemy(2), 02*SCALING, 150*SCALING));
        l2w2.add(new EnemySpawn(new NormalEnemy(2), 107*SCALING, 150*SCALING));
        l2.add(l2w2);
        
        
        enemySpawns.add(l1);
        enemySpawns.add(l2);
        
        

    }
    /**
     * Randomly generate waves and levels, for random enemies and positions
     */
    private void createRandomEnemySpawns(){
        enemySpawns = new ArrayList<>();
        
        for(int i = 0 ; i < maxLevel; i++){
            ArrayList<ArrayList<EnemySpawn>> curLevelWaves = new ArrayList<>(); //Creates the ArrayList for a level
            for(int j = 0 ; j < maxWave; j++){
                ArrayList<EnemySpawn> curWaveEnemies = new ArrayList<>(); //Creates the ArrayList for a wave
                for(int k = 0 ; k < (i/2)+1 ; k++){
                    int x = Greenfoot.getRandomNumber(getWidth());
                    int y = Greenfoot.getRandomNumber(getHeight());
                    Enemy enemy = enemyList.get(Greenfoot.getRandomNumber(i/enemyList.size()+1)); //Increasing enemy variety later on
                    curWaveEnemies.add(new EnemySpawn(enemy, x, y));
                    refreshEnemyList();
                }
                curLevelWaves.add(curWaveEnemies); //Adds the wave to the level list
            }
            enemySpawns.add(curLevelWaves); //Adds level to total list

        }
        
    }
    /**
     * Randomly generate waves and levels, for random obstacles and positions
     */
    private void createRandomWallSpawns(){
        wallSpawns = new ArrayList<>();
        
        for(int i = 0 ; i < maxLevel; i++){
            ArrayList<WallSpawn> curLevelWalls = new ArrayList<>(); //Creates the ArrayList for a level
            for(int j = 0 ; j < maxWave*2; j++){ //Adds the Wall to the ArrayList
                    int x = Greenfoot.getRandomNumber(getWidth());
                    int y = Greenfoot.getRandomNumber(getHeight());
                    Wall wall = wallList.get(Greenfoot.getRandomNumber(wallList.size()));
                    curLevelWalls.add(new WallSpawn(wall, x, y));
                    refreshWallList();
            }
            wallSpawns.add(curLevelWalls);
        }
        
        
    }
    /**
     * Loads up a list of enemies to be added to the list of enemies to be added to world
     */
    private void refreshEnemyList(){
        enemyList = new ArrayList<>();
        enemyList.add(new NormalEnemy(2));
        enemyList.add(new FastEnemy(2));
        enemyList.add(new FatEnemy(2));
    }
    /**
     * Loads up a list of obstacles to be added to the list of obstacles to be added to world
     */
    private void refreshWallList(){
        wallList = new ArrayList<>();
        wallList.add(new Gastank());
        wallList.add(new Tire());
    }
    /**
     * Manually create obstacle and their locations
     * Customize levels using this method
     */
    private void createWallSpawns(){ //Creates all the walls at each level, should probably be loaded from a file
        wallSpawns = new ArrayList<>();
        
        ArrayList<WallSpawn> l1 = new ArrayList<>();
        l1.add(new WallSpawn(new Tire(), 30*SCALING, 200*SCALING));
        l1.add(new WallSpawn(new Gastank(), 35*SCALING, 250*SCALING));
        l1.add(new WallSpawn(new Tire(), 340*SCALING, 108*SCALING));
        
        ArrayList<WallSpawn> l2 = new ArrayList<>();
        l2.add(new WallSpawn(new Tire(), 150*SCALING, 150*SCALING));
        l2.add(new WallSpawn(new Gastank(), 102*SCALING, 150*SCALING));
        l2.add(new WallSpawn(new Tire(), 207*SCALING, 150*SCALING));
        
        
        wallSpawns.add(l1);
        wallSpawns.add(l2);
        
        
    }
    
    /**
     * Creates a dummy actor to check if it intersects another object
     * 
     * @param width    Width of the dummy
     * @param height   Height of the dummy
     * @param x        Places dummy at x coordinate
     * @param y        Places dummy at y coordinate
     */
    private boolean checkSpawnHitbox(int width, int height, int x, int y){
        Tire dummy = new Tire(); //Creates the tire object with height and width, then add it to the world 
        GreenfootImage image = new GreenfootImage(width, height);
        dummy.setImage(image);
        addObject(dummy, x, y);
        
        List<Actor> colliding = dummy.getColliding(dummy.getImage().getWidth()+20); //Get the current colliding objects on the dummy
        removeObject(dummy); //Then remove the dummy from the world
        for(int i = 0 ; i < colliding.size(); i++){ // Check if the dummy collided with any of these classes
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
        
        return false;
    }
    
    /**
     * Creates a blank transition
     */
    public Function nextLevel = () -> addObject(new Transition(this), getWidth()/2, getHeight()/2);
    
    /**
     * Creates a transition that goes to the pauseWorld
     */
    public Function pauseGame = () -> addObject(new Transition(new PauseWorld(this)), getWidth()/2, getHeight()/2);
    
    
}
