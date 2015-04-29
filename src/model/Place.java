package model;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import model.objects.AbstractObject;
import model.objects.PersonObject;
import view.GamePanel;

/**
 *
 * @author Jens
 */
public class Place {

    // <editor-fold desc="Constants">

    /**
     * The maximum width of a single tile
     */
    public static final int MAX_WIDTH = 40;

    /**
     * The maximum height of a single tile
     */
    public static final int MAX_HEIGHT = 40;

    /**
     * The minimum width of a single tile
     */
    public static final int MIN_WIDTH = 8;

    /**
     * The minimum height of a single tile
     */
    public static final int MIN_HEIGHT = 8;

    // </editor-fold> ----------------------------------------------------------
    
    
    // <editor-fold desc="Fields">
    private String game_name, output_name;
    private int width, height;
    private Tile squares[][];
    private Vector<PersonObject> people;
    private GamePanel parent;
    private Timer t;
    private Random randgen;
    private Clip music;

    // </editor-fold> ----------------------------------------------------------
    
    
    // <editor-fold desc="Constructors">

    /**
     * Constructs a new Place-object with the given width and height on the given GamePanel
     * @param width the width of the place
     * @param height the height of the place
     * @param par the parent GamePanel for the newly created place
     */
        public Place(int width, int height, GamePanel par) {
        this.parent = par;
        game_name = "New Place";
        output_name = "game";
        people = new Vector<PersonObject>();
        
        // initialize the tiles of the place
        makeFields(width, height);
        
        // initialize the music
        try {
            music();
        } catch (Exception ex) {
            System.out.println("Error while loading music:\n" + ex.getMessage());
        }
        
        // initialize the handling of th NPC's
        wakePeople();
     
//        setTimer();
        
        // start the music
        startMusic();
    }

    /**
     * Constructs a new Place-object on the given GamePanel with the smallest possible width and height
     * @param parent the parent GamePanel for the newly created place 
     */
    public Place(GamePanel parent) {
        this(MIN_WIDTH, MIN_HEIGHT, parent);
    }

    private Place(boolean fake_constructor) {
        people = new Vector<PersonObject>();
        t = null;
        randgen = null;
        parent = null;
        squares = null;
    }

    // </editor-fold> ----------------------------------------------------------
        
    private void wakePeople(){   
        randgen = new Random();
        int maxtime = 10000;
        if (!people.isEmpty()) {
            maxtime = 10000 / people.size();
        }
        t = new Timer(randgen.nextInt(maxtime) + 1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    people.elementAt(randgen.nextInt(people.size())).walk();
                } catch (Exception ex) {
                }
                parent.revalidate();
                parent.repaint();
                setTimer();
            }
        });
        
    }

    
    // <editor-fold desc="Getters/Setters">
    
    /**
     * Gives the name of the place to be shown in-game
     * @return a string representing the name of the place to be shown in-game
     */
    public String getGameName() {
        return game_name;
    }

    /**
     * Sets the name of the place to be shown in-game
     * @param game_name the name of the place to be shown in-game
     */
    public void setGameName(String game_name) {
        this.game_name = game_name;
    }

    /**
     * Don't remember the function anymore
     * @return
     */
    public String getOutputName() {
        return output_name;
    }

    /**
     * Don't remember the function anymore
     * @param output_name
     */
    public void setOutputName(String output_name) {
        this.output_name = output_name;
    }

    /**
     * Gives the height of the playing field of the place
     * @return the height of the playing field of the place
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the playing field of the place
     * @param height the height of the playing field of the place
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gives the width of the playing field of the place
     * @return the width of the playing field of the place
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the playing field of the place
     * @param width the width of the playing field of the place
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gives the tile on the specified location
     * @param row the row number of the tile to be given
     * @param column the column number of the tile to be given
     * @return the tile in form of an AbstractObject
     */
    public AbstractObject getField(int row, int column) {
        return squares[row][column].getField();
    }

    /**
     * Sets the tile on the specified location
     * @param row the row number of the tile to set
     * @param column the column number of the tile to set
     * @param o the tile to be set
     */
    public void setField(int row, int column, AbstractObject o) {
        squares[row][column].setField(o);
    }

    /**
     * Gives the object placed on the tile with the specified location
     * @param r the row number of the tile
     * @param c the column number of the tile
     * @return the object placed on the tile
     */
    public AbstractObject getStaticObject(int r, int c) {
        return squares[r][c].getObject();
    }

    /**
     * Sets the object to be placed on the tile with the specified location
     * @param r the row number of the tile
     * @param c the column number of the tile
     * @param staticObject the object to be set on the tile
     */
    public void setStaticObject(int r, int c, AbstractObject staticObject) {
        boolean blocked = false;
        // sets the tile as blocked from the player if the tile can't be entered and it's not because of an NPC (which can move eventually)
        if (!squares[r][c].getObject().isEnterable() && !(squares[r][c].getObject() instanceof PersonObject)) {
            blocked = true;
        }
        squares[r][c].setObject(staticObject);
        
        // if object is bigger than 1 tile, sets the other tiles underneath the object as blocked
        for (Point pt : staticObject.getBlockedTiles()) {
            squares[r + pt.y][c + pt.x].getObject().setEnterable(false);
        }
        
        if (blocked) {
            squares[r][c].getObject().setEnterable(false);
        }
    }

    /**
     * Gets the tile on which the player starts when the place is entered.
     * This is temporarily incomplete and for now returns the first free tile in the place
     * @return the tile on which the player starts
     */
    public Point getPlayerTile() {
        return getFreeTile();
    }

    /**
     * Gives the full tile on the specified location
     * @param r the row number of the tile
     * @param c the column number of the tile
     * @return the full tile
     */
    public Tile getSquare(int r, int c) {
        return squares[r][c];
    }

    /**
     * Gives the parent GamePanel-object
     * @return the parent Gamepanel
     */
    public GamePanel getParent() {
        return parent;
    }

    // </editor-fold> ----------------------------------------------------------
    
    /**
     * Gives an empty instance.
     * This is usefull for methods which require a Place-object before it is initialized
     * @return
     */
    public static Place getUselessInstance() {
        return new Place(true);
    }

    /**
     * Initializes the field of the place
     * @param width the width of the playing field
     * @param height the hieght of the playing field
     */
    public void makeFields(int width, int height) {
        // set the dimension of the field
        if (width < MAX_WIDTH && width >= MIN_WIDTH) {
            this.width = width;
        } else if(width < MIN_WIDTH) {
            this.width = MIN_WIDTH;
        }else{
            this.width = MAX_WIDTH;
        }
        if (height < MAX_HEIGHT && height >= MIN_HEIGHT) {
            this.height = height;
        } else if(height < MIN_HEIGHT) {
            this.height = MIN_HEIGHT;
        }else{
            this.height = MAX_HEIGHT;
        }
        
        // initialize the tiles on the field
        squares = new Tile[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                squares[r][c] = new Tile(new Point(c * Tile.TILE_WIDTH, r * Tile.TILE_HEIGHT));
                squares[r][c].getField().addTransportListener(parent);
            }
        }
    }

    /**
     * Gives the object on the tile next to the specified tile, in the specified direction.
     * <p>
     * Example: when the direction is SOUTH en the location is (2,4),
     * this method gives the object on the tile below the tile -> (3,4)
     * @param r the row number of the given tile
     * @param c the columb number of the given tile
     * @param d the direction of the next tile
     * @return the object on the next tile in the given direction
     */
    public AbstractObject getObjectFromDirection(int r, int c, Direction d) {
        try {
            System.out.println("(" + d.getYpos() + ", " + d.getXpos() + ")");
            return squares[r + d.getYpos()][c + d.getXpos()].getObject();
        } catch (Exception ex) {
            System.out.println(d);
            return null;
        }

    }

    /**
     * Gives the first free (non-blocked) tile on the playing field
     * @return the first free tile
     */
    public Point getFreeTile() {
        Point p = new Point(0, 0);
        boolean found = false;
        int r = 0, c = 0;
        while(!found && r < height){
            c = 0;
            while(!found && c < width){
                found = isEnterable(r, c);
                c++;
            }
            r++;
        }
        return new Point(c-1, r-1);
    }

    /**
     * Gives whether the tile on the given location can be entered by the player
     * @param r the row number of the tile
     * @param c the column number of the tile
     * @return can the tile be entered?
     */
    public boolean isEnterable(int r, int c) {
        try {
            return squares[r][c].isEnterable();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Draws the playing field of the place
     * @param comp the component on which to paint the place
     * @param g the Graphics object to draw upon
     */
    public void draw(Component comp, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //int x = 0, y = 0;     // previously used for testing
        
        //---Field
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
                    squares[r][c].drawField(comp, g);
                } catch (Exception e) {
                }

                //x += 16;  // previously used for testing
            }
            //x = 0;      // previously used for testing
            //y += 12;    // previously used for testing
        }
        
        //---Static Object
        //x = 0;      // previously used for testing
        //y = 0;      // previously used for testing
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
                    squares[r][c].drawObject(comp, g);
                } catch (Exception e) {
                }
                //x += 16;    // previously used for testing
            }
            //x = 0;      // previously used for testing
            //y += 12;    // previously used for testing
        }
    }

    /**
     * Adds the person to the place and make it active.
     * @param person the person to be added
     */
    public void addPersonToPlace(PersonObject person) {
        // adds the person the the array of people in this place
        people.addElement(person);
        person.addDirectionListener(parent);
        // starts the timer if it isn't already running (because of other people)
        try {
            if (!t.isRunning()) {
                t.start();
            }
        } catch (Exception ex) {
        }
    }

    // Initializes the timer and starts it
    private void setTimer() {
        t.stop();
        t.setDelay(randgen.nextInt(10000) + 1000);
        t.start();
    }

    /**
     * Initialize the music
     */
    public void music() {

        File f = new File("bgm/bgm.wav");
        AudioInputStream aip = null;
        try {
            aip = AudioSystem.getAudioInputStream(f);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("This audio type is not supported.");
        } catch (IOException ex) {
            System.out.println("File " + f.getName() + " not found.");
        }
        AudioFormat format = aip.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line Not Supported");
        }
        // Obtain and open the line.
        try {
            music = (Clip) AudioSystem.getLine(info);
            try {
                //            line.open(format);
                music.open(aip);
            } catch (IOException ex) {
                System.out.println("Can't read the stream");
            }

        } catch (LineUnavailableException ex) {
            System.out.println("Line Unavailable");
        }
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Starts the music
     */
    public void startMusic() {
        music.loop(Clip.LOOP_CONTINUOUSLY);
        music.start();
    }

    /**
     * Stops the music
     */
    public void stopMusic() {
        music.stop();
    }

}
