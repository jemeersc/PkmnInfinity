/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.walls;

import model.Direction;
import model.objects.WallObject;
import view.GameFrame;

/**
 *
 * @author Jens
 */
public class WindowWallObject extends WallObject{

    public WindowWallObject() {
        super();
        setName(getName() + "window");
        setOutputName("window");
        setTilesAsBlocked(-1, 0);
    }

    @Override
    public void talk(Direction myDirection) {
        GameFrame.setText("The weather seems nice outside.");
    }
    
    
    
}
