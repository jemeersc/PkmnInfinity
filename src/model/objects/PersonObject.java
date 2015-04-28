/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.Direction;
import model.Place;
import model.objects.fields.TransportFieldObject;
import model.objects.people.PlayerPersonObject;
import view.GameFrame;

/**
 *
 * @author Jens
 */
public abstract class PersonObject extends AbstractObject {
   
    // <editor-fold desc="Fields">

    protected String text;
    protected String personName;
    protected Place p;
    protected int r, c;
    
    // </editor-fold> ----------------------------------------------------------

    // <editor-fold desc="Constructors">

    public PersonObject(Place p, String personName, String text) {
        super();
        this.p = p;
        setEnterable(false);
        this.text = text;
        this.personName = personName;
        name = "people/";
        if (!(this instanceof PlayerPersonObject)) {
            p.addPersonToPlace(this);
        }
        addDirectionListener(p.getParent());
    }

    public PersonObject(Place p, String personName) {
        this(p, personName, "Hi, my name is " + personName);
    }

    public PersonObject(Place p) {
        this(p, "Random Person");
    }

    // </editor-fold> ----------------------------------------------------------

    // <editor-fold desc="Methods">

    // </editor-fold> ----------------------------------------------------------
    
    public void updatePlayer() {
        p.setStaticObject(r, c, this);
        changeDirection(currentDirection);
    }

    public int getColumn() {
        return c;
    }

    public int getRow() {
        return r;
    }

    public void updateColumn(int c) {
        p.setStaticObject(this.r, this.c, new EmptyObject());
        this.c = c;
//        changeDirection(currentDirection);
        updatePlayer();
    }

    public void updateRow(int r) {
        p.setStaticObject(this.r, this.c, new EmptyObject());
        this.r = r;
//        changeDirection(currentDirection);
        updatePlayer();
    }
    
    public void setColumn(int c) {
        this.c = c;
        changeDirection(currentDirection);
    }

    public void setRow(int r) {
        this.r = r;
        changeDirection(currentDirection);
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void moveTo(Direction d) {

        if (d == currentDirection) {
            if (d == Direction.EAST) {
                if (p.isEnterable(r, c + 1)) {
                    p.setStaticObject(r, c, new EmptyObject());
                    c += 1;
                    try {
                        ((TransportFieldObject) (p.getField(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    try {
                        ((BuildingObject) (p.getStaticObject(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    p.setStaticObject(r, c, this);
                }
            } else if (d == Direction.NORTH) {
                if (p.isEnterable(r - 1, c)) {
                    p.setStaticObject(r, c, new EmptyObject());
                    r -= 1;
                    try {
                        ((TransportFieldObject) (p.getField(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    try {
                        ((BuildingObject) (p.getStaticObject(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    p.setStaticObject(r, c, this);
                }
            } else if (d == Direction.SOUTH) {
                if (p.isEnterable(r + 1, c)) {
                    p.setStaticObject(r, c, new EmptyObject());
                    r += 1;
                    try {
                        ((TransportFieldObject) (p.getField(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    try {
                        ((BuildingObject) (p.getStaticObject(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    p.setStaticObject(r, c, this);
                }
            } else if (d == Direction.WEST) {
                if (p.isEnterable(r, c - 1)) {
                    p.setStaticObject(r, c, new EmptyObject());
                    c -= 1;
                    try {
                        ((TransportFieldObject) (p.getField(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    try {
                        ((BuildingObject) (p.getStaticObject(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    p.setStaticObject(r, c, this);
                }
            }
        } else {
            changeDirection(d);
        }
    }

    public void talk(Direction myDirection) {
        changeDirection(Direction.getOpposite(myDirection));
//        ((GameFrame)(p.getParent().getParent().getParent().getParent().getParent().getParent())).setText(text);
        GameFrame.setText(personName + ":\n" + text);
    }

    public void walk() {
        Direction d = Direction.getRandomDirection();
        if (Direction.getOpposite(d) == currentDirection) {
            d = currentDirection;
        }
        moveTo(d);
        changeDirection(currentDirection);
    }
}
