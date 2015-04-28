/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.people;

import model.Place;
import model.objects.PersonObject;

/**
 *
 * @author Jens
 */
public class DudePersonObject extends PersonObject{
    
    public DudePersonObject(Place p, String personName, String text) {
        super(p, personName, text);
        setName(getName() + "dude");
        setOutputName("dude");
    }
    
    public DudePersonObject(Place p, String personName) {
        this(p, personName, "Hi, I'm " + personName);
    }

    public DudePersonObject(Place p) {
        this(p, "Dude");
    }
    
    
    
}
