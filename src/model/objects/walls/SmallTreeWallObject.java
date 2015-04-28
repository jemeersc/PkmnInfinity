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
public class SmallTreeWallObject extends WallObject{

    public SmallTreeWallObject() {
        super();
        setName(getName() + "smalltree");
        setOutputName("smalltree");
    }

    @Override
    public void talk(Direction myDirection) {
    }
    
}
