/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.walls;

import model.Direction;
import model.objects.WallObject;

/**
 *
 * @author Jens
 */
public class WallBackWallObject extends WallObject{
    
    public WallBackWallObject() {
        super();
        setName(getName() + "wallback");
        setOutputName("wallback"); 
    }
    
    @Override
    public void talk(Direction myDirection) {
    }
    
}
