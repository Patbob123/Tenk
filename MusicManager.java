import greenfoot.*;

/**
 * Class to manage all the music in the game
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class MusicManager  
{
    private static GreenfootSound introTheme;
    private static GreenfootSound mainTheme1;
    private static GreenfootSound mainTheme2;
    private static GreenfootSound bossTheme;
    private static GreenfootSound[] tracks;
    
    private static GreenfootSound curTrack;
    public MusicManager(){
        
    }
    /**
     * Initializes all the music at the start of each run
     */
    public static void initTracks(){
        introTheme = new GreenfootSound("darkpass.mp3");
        mainTheme1 = new GreenfootSound("battlepog.mp3");
        mainTheme2 = new GreenfootSound("happening.mp3");
        bossTheme = new GreenfootSound("dragoncastle.mp3");
        tracks = new GreenfootSound[]{introTheme, mainTheme1, mainTheme2, bossTheme};
    }
    /**
     * Switches which track is played
     * @param trackIndex    Index of the track in the tracks array
     */
    public static void switchTrack(int trackIndex)
    {
        if(curTrack!=null){
            curTrack.stop();
        }
        curTrack = tracks[trackIndex];
        curTrack.playLoop();
    }
    /**
     * Set volume for the current track
     */
    public static void setVolume(int volume)
    {
        curTrack.setVolume(volume);
    }
    /**
     * Stop the track
     */
    public static void stopTrack()
    {
        if(curTrack!=null){
            curTrack.stop();
        }
    }
}
