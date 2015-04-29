/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

/**
 * Represents an AbstractObject that is a wall.
 * This object can not be entered.
 * @author jemeersc
 */
public abstract class WallObject extends AbstractObject{

    /**
     * Creates a new WallObject.
     */
    public WallObject() {
        super("walls/");
        setEnterable(false);
    }
    
    
}
