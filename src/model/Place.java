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
    public static final int MAX_WIDTH = 40;
    public static final int MAX_HEIGHT = 40;
    public static final int MIN_WIDTH = 8;
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
    public Place(int width, int height, GamePanel par) {
        this.parent = par;
        game_name = "New Place";
        output_name = "game";
        people = new Vector<PersonObject>();
        makeFields(width, height);
        try {
            music();
        } catch (Exception ex) {
            System.out.println("fout in muziek");
        }
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
//        setTimer();
        startMusic();
    }

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
    
    
    // <editor-fold desc="Getters/Setters">
    
    public String getGameName() {
        return game_name;
    }

    public void setGameName(String game_name) {
        this.game_name = game_name;
    }

    public String getOutputName() {
        return output_name;
    }

    public void setOutputName(String output_name) {
        this.output_name = output_name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public AbstractObject getField(int row, int column) {
        return squares[row][column].getField();
    }

    public void setField(int row, int column, AbstractObject o) {
        squares[row][column].setField(o);
    }

    public AbstractObject getStaticObject(int r, int c) {
        return squares[r][c].getObject();
    }

    public void setStaticObject(int r, int c, AbstractObject staticObject) {
        boolean blocked = false;
        if (!squares[r][c].getObject().isEnterable() && !(squares[r][c].getObject() instanceof PersonObject)) {
            blocked = true;
        }
        squares[r][c].setObject(staticObject);
        for (Point pt : staticObject.getBlockedTiles()) {
            squares[r + pt.y][c + pt.x].getObject().setEnterable(false);
        }
        if (blocked) {
            squares[r][c].getObject().setEnterable(false);
        }

    }

    public Point getPlayerTile() {
        return getFreeTile();
    }

    public Tile getSquare(int r, int c) {
        return squares[r][c];
    }

    public GamePanel getParent() {
        return parent;
    }

    // </editor-fold> ----------------------------------------------------------
    
    
    public static Place getUselessInstance() {
        return new Place(true);
    }

    public void makeFields(int width, int height) {
        if (width < MAX_WIDTH && width >= MIN_WIDTH) {
            this.width = width;
        } else {
            this.width = MIN_WIDTH;
        }
        if (height < MAX_HEIGHT && height >= MIN_HEIGHT) {
            this.height = height;
        } else {
            this.height = MIN_HEIGHT;
        }
        squares = new Tile[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                squares[r][c] = new Tile(new Point(c * Tile.TILE_WIDTH, r * Tile.TILE_HEIGHT));
                squares[r][c].getField().addTransportListener(parent);
            }
        }
    }

    public AbstractObject getObjectFromDirection(int r, int c, Direction d) {
        try {
            System.out.println("(" + d.getYpos() + ", " + d.getXpos() + ")");
            return squares[r + d.getYpos()][c + d.getXpos()].getObject();
        } catch (Exception ex) {
            System.out.println(d);
            return null;
        }

    }

    public Point getFreeTile() {
        Point p = new Point(0, 0);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (isEnterable(r, c)) {
                    p = new Point(c, r);
                }
            }
        }
        return p;
    }

    public boolean isEnterable(int r, int c) {
        try {
            return squares[r][c].isEnterable();
        } catch (Exception ex) {
            return false;
        }
    }

    public void draw(Component comp, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int x = 0, y = 0;
        //Field
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
                    squares[r][c].drawField(comp, g);
                } catch (Exception e) {
                }

                x += 16;
            }
            x = 0;
            y += 12;
        }
        //Static Object
        x = 0;
        y = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
                    squares[r][c].drawObject(comp, g);
                } catch (Exception e) {
                }
                x += 16;
            }
            x = 0;
            y += 12;
        }
    }

    public void addPersonToPlace(PersonObject person) {
        people.addElement(person);
        person.addDirectionListener(parent);
        try {
            if (!t.isRunning()) {
                t.start();
            }
        } catch (Exception ex) {
        }
    }

    private void setTimer() {
        t.stop();
        t.setDelay(randgen.nextInt(10000) + 1000);
        t.start();
    }

    public void music() {

        File f = new File("bgm/bgm.wav");
        AudioInputStream aip = null;
        try {
            aip = AudioSystem.getAudioInputStream(f);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("geen goede audio");
        } catch (IOException ex) {
            System.out.println("file niet gevonden");
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
                System.out.println("kan stream niet lezen");
            }

        } catch (LineUnavailableException ex) {
            System.out.println("Line Unavailable");
        }
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void startMusic() {
        music.loop(Clip.LOOP_CONTINUOUSLY);
        music.start();
    }

    public void stopMusic() {
        music.stop();
    }

}
