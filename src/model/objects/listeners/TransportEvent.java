/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.listeners;

import java.awt.AWTEvent;

/**
 *
 * @author Jens
 */
public class TransportEvent extends MyEvent{
    
    private static int my_id = RESERVED_ID_MAX+1;
    private String newPlace;
    private int xpos, ypos; 
    
    public TransportEvent(Object source, int id) {
        super(source, id);
    }
    
    public TransportEvent(Object source) {
        super(source);
    }

    public TransportEvent(String newPlace, Object source) {
        super(source);
        this.newPlace = newPlace;
    }
    
    public TransportEvent(String newPlace, int xpos, int ypos, Object source) {
        this(newPlace, source);
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public String getNewPlace() {
        return newPlace;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }
    
    
    
    
    
}
