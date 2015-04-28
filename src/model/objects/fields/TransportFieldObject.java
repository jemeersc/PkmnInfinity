/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.fields;

import model.objects.FieldObject;
import model.objects.listeners.TransportEvent;
import model.objects.listeners.TransportListener;

/**
 *
 * @author Jens
 */
public class TransportFieldObject extends FieldObject{
    
    private String linkedPlace;
    private int xPos, yPos;

    public TransportFieldObject() {
        super();
        setName(getName() + "transport");
        setOutputName("transport");
        linkTo("", 0, 0);
    }
    
    public void linkTo(String linkedPlace, int xPos, int yPos){
        this.linkedPlace = linkedPlace;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getLinkedPlace() {
        return linkedPlace;
    }

    public void setLinkedPlace(String linkedPlace) {
        this.linkedPlace = linkedPlace;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public void stepOnto() {
        super.stepOnto();
        for(TransportListener tl: transportlisteners){
            System.out.println("transportlistener");
            tl.transport(new TransportEvent(linkedPlace, xPos, yPos, this));
        }
    }
    
    
    
    
}
