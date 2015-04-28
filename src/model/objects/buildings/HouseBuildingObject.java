/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.buildings;

import model.Direction;
import model.objects.BuildingObject;

/**
 *
 * @author Jens
 */
public class HouseBuildingObject extends BuildingObject{

    public HouseBuildingObject(String linkedPlace, int xpos, int ypos) {
        super(linkedPlace, xpos, ypos);
        setName(getName() + "house");
        setOutputName("house");
        setTilesAsBlocked(-1, 0);
        setTilesAsBlocked(-2, 0);
        setTilesAsBlocked(0, -1);
        setTilesAsBlocked(-1, -1);
        setTilesAsBlocked(-2, -1);
        setTilesAsBlocked(0, 1);
        setTilesAsBlocked(-1, 1);
        setTilesAsBlocked(-2, 1);
        setTilesAsBlocked(0, 2);
        setTilesAsBlocked(-1, 2);
        setTilesAsBlocked(-2, 2);
        
    }

    public HouseBuildingObject() {
        this("", 0, 0);
    }
   

    @Override
    public void talk(Direction myDirection) {
    }
    
}
