import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The tracks that will be left behind when a player or enemy moves
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Tracks extends Actor
{
    private int startX;
    private int startY;
    private String direction;
    private int tankWidth;
    private int trackOffset;
    
    private int opacity;
    
    private GreenfootImage trackImage = new GreenfootImage("tracks.png");
    
    /**
     * Sets variables for position, direction, size and opacity for the tracks
     * 
     * @param curX              x-coordinate
     * @param curY              y-coordinate
     * @param curDirection      The direction this faces
     * @param curTankWidth      Width the tank that is spawning these tracks
     * @param curTrackOffset    Where the tracks will spawn relative to the tank
     */
    public Tracks(int curX ,int curY, String curDirection, int curTankWidth, int curTrackOffset){
        startX = curX;
        startY = curY;
        direction = curDirection;
        tankWidth = curTankWidth;
        trackOffset = curTrackOffset;
        opacity = 100;
        
        
        
        trackImage.scale(trackImage.getWidth()*GameWorld.SCALING, trackImage.getHeight()*GameWorld.SCALING);
        setImage(trackImage);
    }
    /**
     * Spawns 2 tracks object on each side of the tank
     * 
     * @param world     The world this will spawn in
     */
    public void spawn(World world){
        int offsetX1 = 0; 
        int offsetY1 = 0;
        int offsetX2 = 0; 
        int offsetY2 = 0;
        switch (direction){
            case "left":
                offsetY1 = tankWidth/2-trackOffset;
                offsetY2 = -tankWidth/2+trackOffset;
                break;
            case "right":
                offsetY1 = tankWidth/2-trackOffset;
                offsetY2 = -tankWidth/2+trackOffset;
                break;
            case "down":
                offsetX1 = -tankWidth/2+trackOffset;
                offsetX2 = tankWidth/2-trackOffset;
                break;
            case "up":
                offsetX1 = -tankWidth/2+trackOffset;
                offsetX2 = tankWidth/2-trackOffset;
                break;
        }
        
        Tracks dupTrack = new Tracks(0,0, direction, 0, 0); //Track for the other side
        
        rotate();
        dupTrack.rotate();
        
        world.addObject(this, startX+offsetX1, startY+offsetY1);
        world.addObject(dupTrack, startX+offsetX2, startY+offsetY2);
    }
    
    /**
     * Act method 
     * Decrease opacity each act until 0, then removes itself
     */
    public void act()
    {
        opacity--;
        if(opacity<=0){
            getWorld().removeObject(this);
            return;
        }
        getImage().setTransparency(opacity);
    }
    /**
     * Rotate the track image
     */
    private void rotate(){
        setRotation(Utils.getDirInts().get(direction)*90);
    }
}
