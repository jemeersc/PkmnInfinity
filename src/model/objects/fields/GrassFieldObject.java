/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.fields;

import java.util.Random;
import model.objects.FieldObject;

/**
 *
 * @author Jens
 */
public class GrassFieldObject extends FieldObject{

    public GrassFieldObject() {
        super();
        Random rndgen = new Random();
        int rnd = rndgen.nextInt(5);
        setName(getName() + "grass" + rnd);
        setOutputName("grass");
    }
    
    
    
}
