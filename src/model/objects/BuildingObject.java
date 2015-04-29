/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.objects.listeners.TransportEvent;
import model.objects.listeners.TransportListener;

/**
 * This represents a building
 * The door is the location
 * @author jemeersc
 */
public abstract class BuildingObject extends AbstractObject{

    protected String linkedPlace;
    protected int xpos,ypos,r,c;
    
    /**
     * Creates a new building-object
     * @param linkedPlace the place which will be used as the inside of the building
     * @param xpos the x-coordinate of the building door
     * @param ypos the y-coordinate of the building door
     */
    public BuildingObject(String linkedPlace, int xpos, int ypos) {
        super("buildings/");
        this.linkedPlace = linkedPlace;
        this.xpos = xpos;
        this.ypos = ypos;
        setEnterable(true);
    }
    
    /**
     * Creates a new building-object
     */
    public BuildingObject(){
        this("", 0, 0);
    }

    @Override
    public void stepOnto() {
        super.stepOnto();
        for(TransportListener tl: transportlisteners){
            System.out.println("transportlistener");    //testing
            tl.transport(new TransportEvent(linkedPlace, xpos, ypos, this));
        }
    }

    /**
     * Gives the name of the place that represents the inside of the building
     * @return the name of the place that represents the inside of the building
     */
    public String getLinkedPlace() {
        return linkedPlace;
    }

    /**
     * Sets the name of the place that represents the inside of the building
     * @param linkedPlace the name of the place that represents the inside of the building
     */
    public void setLinkedPlace(String linkedPlace) {
        this.linkedPlace = linkedPlace;
    }

    /**
     * Gives the x-coordinate of the building door
     * @return x-coordinate of the building door
     */
    public int getXpos() {
        return xpos;
    }

    /**
     * Sets the x-coordinate of the building door 
     * @param xpos x-coordinate of the building door
     */
    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    /**
     * Gives the y-coordinate of the building door
     * @return y-coordinate of the building door
     */
    public int getYpos() {
        return ypos;
    }

    /**
     * Gives the y-coordinate of the building door
     * @param ypos y-coordinate of the building door
     */
    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    /**
     * No clue
     * @return no clue
     */
    public int getC() {
        return c;
    }

    /**
     * No clue
     * @param c no clue
     */
    public void setC(int c) {
        this.c = c;
    }

    /**
     * No clue
     * @return no clue
     */
    public int getR() {
        return r;
    }

    /**
     * No clue
     * @param r no clue
     */
    public void setR(int r) {
        this.r = r;
    }
        
}
