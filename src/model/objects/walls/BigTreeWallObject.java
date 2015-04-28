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
public class BigTreeWallObject extends WallObject{
    public BigTreeWallObject() {
        super();
        setName(getName() + "bigtree");
        setOutputName("bigtree");
        setTilesAsBlocked(-1, 0);
        setTilesAsBlocked(-1, -1);
        setTilesAsBlocked(0, -1);
    }

    @Override
    public void talk(Direction myDirection) {
        GameFrame.setText("I'm a talking tree, please hug me!");
    }
}
