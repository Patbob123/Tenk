import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Label class that contain text to display
 * 
 * @author Dawson
 * @version 2023-06-20
 */
public class Label extends Actor
{
    private Font font;
    private Color color;
    private int width;
    private int height;
    private String[] labels;
    
    private GreenfootImage image;
    /**
     * Sets variables and image
     * 
     * @param curFont   Font
     * @param curColor  Color  
     * @param curWidth  Width
     * @param curHeight Height
     */
    public Label(Font curFont, Color curColor, int curWidth, int curHeight){
        font = curFont;
        color = curColor;
        width = curWidth;
        height = curHeight;
        image = new GreenfootImage(curWidth, curHeight);
    }
    /**
     * Sets color, font and image
     */
    public void init(){
        image.setFont(font);
        image.setColor(color);
        setImage(image);    
    }
    /**
     * Sets labels
     * 
     * @param curLabels The labels that will displayed alongside a value
     */
    protected void setLabels(String[] curLabels){
        labels = curLabels;
    }
    
    /**
     * Updates the label
     * 
     * @param value The values that will displayed alongside a label
     */
    protected void update(String[] value){
        image = new GreenfootImage(width, height);
        image.setFont(font);
        image.setColor(color);
        setImage(image); 
        String text = "";
        for(int i = 0 ; i < value.length; i++){
            text+=labels[i]+" "+value[i]+"  ";
        }
        image.drawString(text, font.getSize(), font.getSize());
        
    }
}
