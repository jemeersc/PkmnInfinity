/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import model.objects.AbstractObject;
import model.objects.EmptyObject;

/**
 *
 * @author Jens
 */
public class Tile {

    /**
     * The width used for the tiles on which objects will occur.
     */
    public static final int TILE_WIDTH = 16;

    /**
     * The height used for the tiles on which object will occur.
     */
    public static final int TILE_HEIGHT = 16;
    private Point p;    // Point(x=width=column, y=height=row)
    private AbstractObject field, object;

    /**
     * Creates a new Tile-object on the given location and containing the given Object.
     * @param p the location of the tile
     * @param o the object the tile should contain
     */
    public Tile(Point p, AbstractObject o) {
        this.p = p;
        this.field = o;
        this.object = new EmptyObject();
    }

    /**
     * Creates a new Tile-object on the given location.
     * @param p Point
     */
    public Tile(Point p) {
        this(p, new EmptyObject());
    }

    /**
     * Returns the object that represents the field on this Tile.
     * @return the field object
     */
    public AbstractObject getField() {
        return field;
    }

    /**
     * Sets the object that represents the field on this Tile.
     * @param o the field object
     */
    public void setField(AbstractObject o) {
        this.field = o;
    }

    /**
     * Returns the object that this Tile contains.
     * @return the containing object
     */
    public AbstractObject getObject() {
        return object;
    }

    /**
     * Seets the object that this Tile contains.
     * @param object the containing object
     */
    public void setObject(AbstractObject object) {
        this.object = object;
    }

    /**
     * Returns the Point representing the location of the Tile.
     * @return Point representing the location
     */
    public Point getPoint() {
        return p;
    }

    /**
     * Sets the Point representing the location of the Tile.
     * @param p Point representing the location
     */
    public void setPoint(Point p) {
        this.p = p;
    }

    /**
     * Draws the field-image of the tile on the specified component
     * @param c the component to paint on
     * @param g the Graphics object to use to paint
     */
    public void drawField(Component c, Graphics g) {
        try {
            field.getImage().paintIcon(c, g, p.x + TILE_WIDTH / 2 - field.getImage().getIconWidth() / 2, p.y + TILE_HEIGHT - field.getImage().getIconHeight());
        } catch (Exception ex) {
            g.setColor(Color.white);
            g.fillRect(p.x, p.y, TILE_WIDTH, TILE_HEIGHT);
        }
    }

    /**
     * Draws the image of the object this tile contains on the specified component
     * @param c the component to paint upon
     * @param g the Graphics object to use to paint
     */
    public void drawObject(Component c, Graphics g) {
        try {
            if (!(object instanceof EmptyObject)) {
                object.getImage().paintIcon(c, g, p.x + TILE_WIDTH / 2 - object.getImage().getIconWidth() / 2, p.y + TILE_HEIGHT - object.getImage().getIconHeight());
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Tells whether the tile can be entered by the player
     * @return can the tile be entered?
     */
    public boolean isEnterable() {
        return field.isEnterable() && object.isEnterable();
    }
}
