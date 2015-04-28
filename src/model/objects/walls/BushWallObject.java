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
public class BushWallObject extends WallObject{

    public BushWallObject() {
        super();
        setName(getName() + "bush");
        setOutputName("bush");
    }

    @Override
    public void talk(Direction myDirection) {
    }
    
    
}
