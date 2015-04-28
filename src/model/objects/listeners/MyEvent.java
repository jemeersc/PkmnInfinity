/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects.listeners;

import java.awt.AWTEvent;

/**
 *
 * @author Jens
 */
public class MyEvent extends AWTEvent{
    
    private static int my_id = RESERVED_ID_MAX+1;


    public MyEvent(Object source, int id) {
        super(source, id);
        my_id++;
    }
    
    public MyEvent(Object source) {
        super(source, my_id);
        my_id++;
    }
    
}
