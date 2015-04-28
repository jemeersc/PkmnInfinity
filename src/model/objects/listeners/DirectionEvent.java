/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.listeners;

import java.awt.AWTEvent;
import model.Direction;
import model.objects.AbstractObject;

/**
 *
 * @author Jens
 */
public class DirectionEvent extends MyEvent{
    
    private AbstractObject my_source;
    private Direction previousDirection, newDirection;

    public DirectionEvent(Object source, int id) {
        super(source, id);
    }
    
    public DirectionEvent(Object source) {
        super(source);
    }

    public DirectionEvent(Direction previousDirection, Direction newDirection, Object source) {
        super(source);
        this.previousDirection = previousDirection;
        this.newDirection = newDirection;
    }

    public Direction getNewDirection() {
        return newDirection;
    }

    public Direction getPreviousDirection() {
        return previousDirection;
    }
    
    
    
    
    
    
}
