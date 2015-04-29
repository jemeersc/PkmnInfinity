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
 * This represents a person-object
 * This can be an NPC as well as the player itself
 * @author jemeersc
 */
public abstract class PersonObject extends AbstractObject {
   
    // <editor-fold desc="Fields">

    protected String text;
    protected String personName;
    protected Place p;
    protected int r, c;
    
    // </editor-fold> ----------------------------------------------------------

    // <editor-fold desc="Constructors">

    /**
     * Creates a new PersonObject on the given place with the given name and the text it says when spoken to.
     * @param p the place on which the person walks
     * @param personName the name of the person
     * @param text the stuff the person has to say to the player when spoken to
     */  
    public PersonObject(Place p, String personName, String text) {
        super();
        this.p = p;
        setEnterable(false);    // nope, it's not that kind of game
        this.text = text;
        this.personName = personName;
        name = "people/";
        if (!(this instanceof PlayerPersonObject)) {
            p.addPersonToPlace(this);
        }
        addDirectionListener(p.getParent());
    }

    /**
     * Creates a new person-object on the given place with the given name
     * @param p the place on which the person walks
     * @param personName the name of the person
     */
    public PersonObject(Place p, String personName) {
        this(p, personName, "Hi, my name is " + personName);
    }

    /**
     * Creates a new person-object on the given place
     * @param p the place on which the person walks
     */
    public PersonObject(Place p) {
        this(p, "Random Person");
    }

    // </editor-fold> ----------------------------------------------------------

    // <editor-fold desc="Methods">
    
    /**
     * Safely moves the player to a new location
     * specified by the given column number (x-coordinate) and row number (y-coordinate),
     * and sets the direction to look to
     * @param c column number (x-coordinate) to move to
     * @param r row number (y-coordinate) to move to
     * @param d the direction to look to
     */
    public void movePlayer(int c, int r, Direction d){
        // assures the current place will be empty again
        p.setStaticObject(this.r, this.c, new EmptyObject());
        setColumn(c);
        setRow(r);
        p.setStaticObject(r, c, this);
        changeDirection(d);
    }
    
    /**
     * Safely moves the player to a new location
     * specified by the given column number (x-coordinate) and row number (y-coordinate)
     * @param c column number (x-coordinate) to move to
     * @param r row number (y-coordinate) to move to
     */
    public void movePlayer(int c, int r){
        movePlayer(c, r, currentDirection);
    }
    
    /**
     * Safely adds the player on the place with the correct coordinates 
     */   
    public void updatePlayer() {
        p.setStaticObject(r, c, this);
        // Safety for assuring the direction isn't changed
        changeDirection(currentDirection);
    }
   
    /**
     * Moves to the given direction.
     * This will only happen if the given direction is the same as the current one.
     * Otherwise the person will only turn to the new direction, without moving
     * @param d the direction in which the person should move
     */
    public void moveTo(Direction d) {

        if (d == currentDirection) {    // person want to move in current direction -> walk
            if (d == Direction.EAST) {
                if (p.isEnterable(r, c + 1)) {
                    p.setStaticObject(r, c, new EmptyObject());
                    c += 1;
                    
                    // the following try-catch constructions aren't very pretty -> better use a design pattern
                    try {   // Makes the field react if it should transport
                        ((TransportFieldObject) (p.getField(r, c))).stepOnto();
                    } catch (Exception ex) {
                    }
                    try {   // Makes the building react if it should be entered
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
        } else {    // if person wants to move in another direction -> turn first (don't walk yet)
            changeDirection(d);
        }
    }

    @Override
    public void talk(Direction myDirection) {
        // Lets the person orientate itself to the player, given that myDirection is the direction of the player
        changeDirection(Direction.getOpposite(myDirection));
        // Let the GameFrame print the speech
        GameFrame.setText(personName + ":\n" + text);
    }

    /**
     * Makes the person walk around
     * The direction is randomly chosen, and the action depends on which one it is:
     * - same as current direction -> move 1 tile towards that direction
     * - different direction from current one -> only change the direction the person is facing, don't move yet
     * There's a safety for stopping the person from making a 180 degree turnaround, this can easily be changed
     */
    public void walk() {
        Direction d = Direction.getRandomDirection();
        
        // Assures the person will not suddenly make a 180 degree spin (not everyone is Michael Jackson)
        if (Direction.getOpposite(d) == currentDirection) {
            d = currentDirection;
        }
        
        moveTo(d);                          // first move to check if direction is the same
        changeDirection(currentDirection);  // only now change direction of object
    }
    
    // </editor-fold> ----------------------------------------------------------

    // <editor-fold desc="Getters/Setters">

    /**
     * Gives the column number (x-coordinate) on which the person stands
     * @return the x-coordinate
     */
    public int getColumn() {
        return c;
    }

    /**
     * Gives the row number (y-coordinate) on which the person stands
     * @return the y-coordinate
     */
    public int getRow() {
        return r;
    }

    /**
     * Sets the column number (x-coordinate) on which the person stands
     * @param c the column number (x-coordinate) on which the person stands
     */
    public void setColumn(int c) {
        this.c = c;
        // safety for assuring the player won't change stance when teleporting
        changeDirection(currentDirection);
    }

    /**
     * Sets the row number (y-coordinate) on which the person stands
     * @param r the row number (y-coordinate) on which the person stands
     */
    public void setRow(int r) {
        this.r = r;
        // safety for assuring the player won't change stance when teleporting
        changeDirection(currentDirection);
    }

    /**
     * Gives the name of the person
     * @return name of the person
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Sets the name of the person
     * @param personName name of the person
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * Gives the text the person says hen spoken to
     * @return the text the person says hen spoken to
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text the person says hen spoken to
     * @param text the text the person says hen spoken to
     */
    public void setText(String text) {
        this.text = text;
    }

    // </editor-fold> ----------------------------------------------------------
 
}
