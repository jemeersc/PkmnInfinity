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
public class RockWallObject extends WallObject{

    public RockWallObject() {
        super();
        setName(getName() + "rock");
        setOutputName("rock");
    }

    @Override
    public void talk(Direction myDirection) {
    }
    
    
    
        
}
