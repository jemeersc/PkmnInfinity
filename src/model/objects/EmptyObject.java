/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.Direction;
import view.GameFrame;

/**
 * This represents an AbstractObject which is empty.
 * This object does not hold other objects, and can be entered.
 * @author jemeersc
 */
public class EmptyObject extends AbstractObject{

    /**
     * Creates a new EmptyObject.
     * 
     * This represents an AbstractObject which is empty.
     * This object does not hold other objects, and can be entered.
     */
    public EmptyObject() {
        super();
        setOutputName("empty");
        setName("empty");
        setEnterable(true);
    }

    @Override
    public void talk(Direction myDirection) {
        //GameFrame.setText("");    //testing
    }
    
    
}
