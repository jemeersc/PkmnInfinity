/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.objects.listeners.TransportEvent;
import model.objects.listeners.TransportListener;

/**
 *
 * @author Jens
 */
public abstract class BuildingObject extends AbstractObject{
    
    protected String linkedPlace;
    protected int xpos, ypos, r, c;
    
    public BuildingObject(String linkedPlace, int xpos, int ypos) {
        super("buildings/");
        this.linkedPlace = linkedPlace;
        this.xpos = xpos;
        this.ypos = ypos;
        setEnterable(true);
    }
    
    public BuildingObject(){
        this("", 0, 0);
    }

    @Override
    public void stepOnto() {
        super.stepOnto();
        for(TransportListener tl: transportlisteners){
            System.out.println("transportlistener");
            tl.transport(new TransportEvent(linkedPlace, xpos, ypos, this));
        }
    }

    public String getLinkedPlace() {
        return linkedPlace;
    }

    public void setLinkedPlace(String linkedPlace) {
        this.linkedPlace = linkedPlace;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
    
    
    

    
}
