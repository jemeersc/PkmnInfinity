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
public class SilverPersonObject extends PersonObject{
    
    public SilverPersonObject(Place p, String personName, String text) {
        super(p, personName, text);
        setName(getName() + "silver");
        setOutputName("silver");
    }
    
    public SilverPersonObject(Place p, String personName) {
        this(p, personName, "Hi, I'm " + personName);
    }

    public SilverPersonObject(Place p) {
        this(p, "Silver");
    }
    
    
    
}
