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
     * Creates a new Tile-objecct with the given Point and Object.
     * @param p Point
     * @param o AbstractObject
     */
    public Tile(Point p, AbstractObject o) {
        this.p = p;
        this.field = o;
        this.object = new EmptyObject();
    }

    /**
     * Creates a new Tile-object with the given Point.
     * @param p Point
     */
    public Tile(Point p) {
        this(p, new EmptyObject());
    }

    /**
     * Returns the object that represents the field on this Tile.
     * @return AbstractObject
     */
    public AbstractObject getField() {
        return field;
    }

    /**
     * Sets the object that represents the field on this Tile.
     * @param o AbstractObject
     */
    public void setField(AbstractObject o) {
        this.field = o;
    }

    /**
     * Returns the object that occurs on this Tile.
     * @return AbstractObject
     */
    public AbstractObject getObject() {
        return object;
    }

    /**
     * Seets the object that occurs on this Tile.
     * @param object
     */
    public void setObject(AbstractObject object) {
        this.object = object;
    }

    /**
     * Returns the Point representing the location of the Tile.
     * @return Point
     */
    public Point getPoint() {
        return p;
    }

    /**
     * Sets the Point representing the location of the Tile.
     * @param p
     */
    public void setPoint(Point p) {
        this.p = p;
    }

    /**
     * Draws the Tile on 
     * @param c
     * @param g
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
     *
     * @param c
     * @param g
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
     *
     * @return
     */
    public boolean isEnterable() {
        return field.isEnterable() && object.isEnterable();
    }
}
