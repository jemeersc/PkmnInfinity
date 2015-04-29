/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.Direction;

/**
 * This object represents a field object.
 * This field is used as underground for other objcet.
 * This object can be entered.
 * @author jemeersc
 */
public abstract class FieldObject extends AbstractObject{

    /**
     * Creates a new FieldObject.
     * 
     * This object is enterable.
     */
    public FieldObject() {
        super("fields/");
        setEnterable(true);
    }
 
    @Override
    public void talk(Direction myDirection){
        // Nothing here but us kittens
    }
    
    
    
    
    
}
