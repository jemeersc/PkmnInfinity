/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.people;

import model.Direction;
import model.Place;
import model.objects.EmptyObject;
import model.objects.PersonObject;
import view.GameFrame;

/**
 *
 * @author Jens
 */
public class PlayerPersonObject extends PersonObject {

    

    public PlayerPersonObject(Place p) {
        super(p);
        setName(getName() + "hilbert");
        setOutputName("player");
        c = p.getPlayerTile().x;
        r = p.getPlayerTile().y;
        this.p.setStaticObject(r, c, this);
    }
    
    public void updatePlayer(){
        c = p.getPlayerTile().x;
        r = p.getPlayerTile().y;
        p.setStaticObject(r, c, this);
    }

    public Direction getDirection() {
        return currentDirection;
    }

    @Override
    public void moveTo(Direction d) {
        super.moveTo(d);
        GameFrame.setText("");
    }
    
    
    
    

    
}
