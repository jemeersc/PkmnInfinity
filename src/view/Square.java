/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.Icon;
import model.objects.AbstractObject;
import model.objects.EmptyObject;

/**
 *
 * @author Jens
 */
public class Square {
    
    private Point p;
    private AbstractObject field, object;
    private boolean selected = false;

    public Square(Point p, AbstractObject o) {
        this.p = p;
        this.field = o;
        this.object = new EmptyObject();
    }
    
    public Square(Point p) {
        this(p, new EmptyObject());
    }

    public AbstractObject getField() {
        return field;
    }

    public void setField(AbstractObject o) {
        this.field = o;
    }

    public AbstractObject getObject() {
        return object;
    }

    public void setObject(AbstractObject object) {
        this.object = object;
    }

    public Point getPoint() {
        return p;
    }

    public void setPoint(Point p) {
        this.p = p;
    }
    
    public void drawObject(Component c, Graphics g){
        try{
          object.getImage().paintIcon(c, g, p.x+16/2-object.getImage().getIconWidth()/2, p.y+16-object.getImage().getIconHeight()); 
        }catch(Exception ex){
        }
        drawSelected(c, g);
    }
    public void drawField(Component c, Graphics g){
        try{
          field.getImage().paintIcon(c, g, p.x+16/2-field.getImage().getIconWidth()/2, p.y+16-field.getImage().getIconHeight());
        }catch(Exception ex){
            g.setColor(Color.white);
            g.fillRect(p.x, p.y, 16, 16);
        }
    }
    
    public void drawSelected(Component c, Graphics g){
        if(selected){
            g.setColor(Color.red);
            g.fillRect(p.x, p.y, 16, 16);
        }
    }
    
    public void drawImage(Component c, Graphics g){
        try{
          field.getImage().paintIcon(c, g, p.x+16/2-field.getImage().getIconWidth()/2, p.y+16-field.getImage().getIconHeight());
        }catch(Exception ex){
            g.setColor(Color.white);
            g.fillRect(p.x, p.y, 16, 16);
        }
        try{
          object.getImage().paintIcon(c, g, p.x+16/2-object.getImage().getIconWidth()/2, p.y+16-object.getImage().getIconHeight()); 
        }catch(Exception ex){
        }
        if(selected){
            g.setColor(Color.red);
            g.fillRect(p.x, p.y, 16, 16);
        }
    }
    
    public void select(){
        selected = true;
    }
    public void deselect(){
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
    
}
