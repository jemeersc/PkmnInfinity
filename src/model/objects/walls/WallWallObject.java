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
public class WallWallObject extends WallObject{

    public WallWallObject() {
        super();
        setName(getName() + "wall");
        setOutputName("wall"); 
        setTilesAsBlocked(-1, 0);
    }
    
    @Override
    public void talk(Direction myDirection) {
    }
    
}
