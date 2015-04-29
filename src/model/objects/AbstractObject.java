package model.objects;


import java.awt.Point;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Direction;
import model.objects.listeners.DirectionEvent;
import model.objects.listeners.DirectionListener;
import model.objects.listeners.TransportListener;
import view.GamePanel;


/**
 *
 * @author jemeersc
 */
public abstract class AbstractObject extends Object{
    
    // <editor-fold desc="Fields">
    
    protected boolean enterable = false;
    protected Direction currentDirection;
    protected Map<Direction, Icon> images;
    protected int squareWidth,squareHeight;
    protected String name;
    protected String outputName;
    protected Set<DirectionListener> directionlisteners;
    protected Set<TransportListener> transportlisteners;
    protected Set<Point> blockedTiles;

    // </editor-fold> ----------------------------------------------------------
    
    // <editor-fold desc="Constructors">

    /**
     * Returns a new AbstractObjact with the given size and name.
     * @param squareWidth width of the object
     * @param squareHeight height of the object
     * @param name name of the object
     */
    public AbstractObject(int squareWidth, int squareHeight, String name) {
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.name = name;
        currentDirection = Direction.SOUTH;     // so you see the front of the player
        
        images = new EnumMap<Direction, Icon>(Direction.class);
        directionlisteners = new HashSet<DirectionListener>();
        transportlisteners = new HashSet<TransportListener>();
        blockedTiles = new HashSet<Point>() {};
        
        // loads the images for the object
        loadImages();
    }
    
    /**
     * Creates a new AbstractObject with the given name.
     * @param name name of the object
     */
    public AbstractObject(String name){
        this(1,1, name);
    }
    
    /**
     * Creates a new AbstractObject with the given size.
     * @param squareWidth width of the object
     * @param squareHeight height of the object
     */
    public AbstractObject(int squareWidth, int squareHeight) {
        this(squareWidth,squareHeight, "");
    }

    /**
     * Creates a new AbstractObject
     */
    public AbstractObject() {
        this(1,1);
    }

    // </editor-fold> ----------------------------------------------------------
    
    // <editor-fold desc="Getters/Setters">

    /**
     * Returns whether this Object can be entered by other Objects.
     * @return enterable
     */
    public boolean isEnterable() {
        return enterable;
    }

    /**
     * Sets if this object can be entered by other objects.
     * @param enterable
     */
    public void setEnterable(boolean enterable) {
        this.enterable = enterable;
    }
    
    /**
     * Returns the Icon representing the image for this object.
     * The correct image is based on the current direction of the object.
     * @return image
     */
    public Icon getImage(){
          return images.get(currentDirection);  
    }
    
    /**
     * Sets the Direction of the object.
     * This triggers a DirectionEvent for all the DirectionListeners.
     * @param d new Direction of the object
     */
    public void changeDirection(Direction d){
        for(DirectionListener dl: directionlisteners){
            dl.directionChanged(new DirectionEvent(currentDirection, d, this));
        }
        currentDirection = d;
    }
    
    /**
     * Adds a new DirectionListener to this object.
     * @param dl DirectionListener
     */
    public void addDirectionListener(DirectionListener dl){
        directionlisteners.add(dl);
    }

    /**
     * Returns the name of this object.
     * @return name of this object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this object.
     * @param name name of this object
     */
    public void setName(String name) {
        this.name = name;
        loadImages();
    }

    /**
     * Returns the name of this object which will be used for output.
     * @return name used for output
     */
    public String getOutputName() {
        return outputName;
    }

    /**
     * Sets the name of this object which will be used for output.
     * @param outputName name used for output
     */
    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    /**
     * Returns a Set of Points representing the coverage of this object.
     * 
     * If an object is larger than 1 Tile,
     * this will represent all the tiles which also cover this object.
     * If this object is non-enterable, all the Tiles with the given Points
     * will also be non-enterable.
     * @return Set of Points 
     */
    public Set<Point> getBlockedTiles() {
        return blockedTiles;
    }
    
    /**
     * Sets the Tile on the given Point as blocked.
     * 
     * This adds the Tile of the given Point to the list of blocked tiles.
     * This Tile will be seen as a part of this object,
     * meaning that this object also covers that Tile.
     * @param r row number of the Tile to be blocked
     * @param c column number of the Tile to be blocked
     */
    public void setTilesAsBlocked(int r, int c){
        blockedTiles.add(new Point(c,r));
    }
    
    /**
     * Talks to the object in the given Direction.
     * 
     * This will trigger an event on the object in the given Direction.
     * @param myDirection the Direction to which to talk to
     */
    public abstract void talk(Direction myDirection);

    /**
     * Adds a GamePanel as TransportListener to this object 
     * @param parent TransportListener
     */
    public void addTransportListener(GamePanel parent) {
        transportlisteners.add(parent);
    }
    
    // </editor-fold> ----------------------------------------------------------
    
    // <editor-fold desc="Methods">

    /**
     * Respresents the action that is to be executed when anther objects steps on this object.
     * This will be overriden by the extending classes
     */
    public void stepOnto(){
        System.out.println("does stepOnto");
    }
    
    // </editor-fold> ----------------------------------------------------------
    
    // <editor-fold desc="Private methods">

    /**
     * Loads the correct images for all direction of this Object
     */
    private void loadImages(){
        String s = "South";     // for testing
        try{
            images.put(Direction.SOUTH, new ImageIcon("resources/" + name + "S.png")); s = "North";
            images.put(Direction.NORTH, new ImageIcon("resources/" + name + "N.png")); s = "East";
            images.put(Direction.EAST, new ImageIcon("resources/" + name + "E.png")); s = "West";
            images.put(Direction.WEST, new ImageIcon("resources/" + name + "W.png"));
//            images.put(Direction.SOUTH, new ImageIcon(AbstractObject.class.getResource("/resources/" + name + "S.png"))); s = "North";
//            images.put(Direction.NORTH, new ImageIcon(AbstractObject.class.getResource("/resources/" + name + "N.png"))); s = "East";
//            images.put(Direction.EAST, new ImageIcon(AbstractObject.class.getResource("/resources/" + name + "E.png"))); s = "West";
//            images.put(Direction.WEST, new ImageIcon(AbstractObject.class.getResource("/resources/" + name + "W.png")));
        }catch(Exception e){
            System.out.println("Error while opening " + s + " image of " + name);
        }
    }
    
    // </editor-fold> ----------------------------------------------------------

}
