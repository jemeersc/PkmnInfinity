/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import model.Place;
import model.objects.AbstractObject;
import model.objects.BuildingObject;
import model.objects.ObjectFactory;
import model.objects.PersonObject;
import model.objects.fields.TransportFieldObject;

/**
 *
 * @author Jens
 */
public class EditorPanel extends JPanel {

    private Square[][] squares;
    private int width, height;
    private String placename, filename;

    public EditorPanel(int width, int height) {
        constructMe(width, height);
    }

    public EditorPanel() {
        this(40, 40);
    }

    public void constructMe(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width * 16, height * 16));
        squares = new Square[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                squares[r][c] = new Square(new Point(c * 16, r * 16));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.setColor(Color.darkGray);
//        g.clearRect(0, 0, width, height);
        setBackground(Color.white);
        g.setColor(Color.gray);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Square p = squares[r][c];
                p.drawField(this, g);
//                g.setColor(Color.black);
//                g.drawRect(p.getPoint().x, p.getPoint().y, 16, 16);
            }
        }
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Square p = squares[r][c];
                p.drawObject(this, g);
                g.setColor(Color.black);
                g.drawRect(p.getPoint().x, p.getPoint().y, 16, 16);
            }
        }
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    
    
    public Square getSquare(int r, int c) {
        return squares[r][c];
    }

    public Point getPair(Point p) {
        Point res = null;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Square po = squares[r][c];
                Rectangle2D rec = new Rectangle(po.getPoint(), new Dimension(16, 16));
                if (rec.contains(p)) {
                    res = new Point(r, c);
                }
            }
        }
        return res;
    }

    public Square getSquare(Point p) {
//        Square res = null;
//        for(int r=0; r<height; r++){
//            for(int c=0; c<width; c++){
//                Square po = squares[r][c];
//                Rectangle2D rec = new Rectangle(po.getPoint(), new Dimension(16,16));
//                if (rec.contains(p)){
//                    res = squares[r][c];
//                }
//            }
//        }
//        return res;
        if (getPair(p) != null) {
            return squares[getPair(p).x][getPair(p).y];
        } else {
            return null;
        }
    }

    public void deselectAll() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                squares[r][c].deselect();
            }
        }
        revalidate();
        repaint();
    }

    public void select(Point begin, Point end) {
        int x0, x1, y0, y1;
        x0 = Math.min(getPair(begin).x, getPair(end).x);
        x1 = Math.max(getPair(begin).x, getPair(end).x);
        y0 = Math.min(getPair(begin).y, getPair(end).y);
        y1 = Math.max(getPair(begin).y, getPair(end).y);
        for (int r = x0; r < x1 + 1; r++) {
            for (int c = y0; c < y1 + 1; c++) {
                squares[r][c].select();
            }
        }
        revalidate();
        repaint();
    }

    public void setFieldObjects(AbstractObject o) {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (squares[r][c].isSelected()) {
                    squares[r][c].setField(o);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void setObject(AbstractObject o) {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (squares[r][c].isSelected()) {
                    squares[r][c].setObject(o);
                    if(o instanceof BuildingObject){
                        ((BuildingObject)o).setR(r);
                        ((BuildingObject)o).setC(c);
                    }
//                    for(Point pt: o.getBlockedTiles()){
//                        squares[r+pt.y][c+pt.x].getObject().setEnterable(false);
//                    }
                }
            }
        }
        revalidate();
        repaint();
    }

    public void saveContentDialog(Frame owner) {
        final JFileChooser chooser = new JFileChooser("./worlds");
//        chooser.setFileFilter(new FileNameExtensionFilter("World file", "world"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new ExtFilter(".world"));
        int buttonPressed = chooser.showSaveDialog(owner);
        if (buttonPressed == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath();
            if (!filename.endsWith(".world")) {
                filename += ".world";
            }
            saveContent(filename);
        }
    }

    public void saveContent(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            PrintWriter out = new PrintWriter(writer);
            out.println(width);
            out.println(height);
            out.println(filename);
            out.println(placename);
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    out.print(squares[r][c].getField().getOutputName() + "#");
                    out.print(squares[r][c].getObject().getOutputName());
                    if (squares[r][c].getObject() instanceof PersonObject) {
                        out.print("#" + ((PersonObject) (squares[r][c].getObject())).getPersonName() + "#");
                        out.println(((PersonObject) (squares[r][c].getObject())).getText());
                    } else if (squares[r][c].getObject() instanceof BuildingObject) {
                        out.print("#" + ((BuildingObject) (squares[r][c].getObject())).getLinkedPlace() + "#");
                        out.print(((BuildingObject) (squares[r][c].getObject())).getXpos() + "#");
                        out.println(((BuildingObject) (squares[r][c].getObject())).getYpos());
                    } else if (squares[r][c].getField() instanceof TransportFieldObject) {
                        out.print("#" + ((TransportFieldObject) (squares[r][c].getField())).getLinkedPlace() + "#");
                        out.print(((TransportFieldObject) (squares[r][c].getField())).getxPos() + "#");
                        out.println(((TransportFieldObject) (squares[r][c].getField())).getyPos());
                    } else {
                        out.println();
                    }

                }
            }
            writer.close();

        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openContentDialog(Frame owner) {
        JFileChooser chooser = new JFileChooser("./worlds");
//        chooser.setFileFilter(new FileNameExtensionFilter("World file", "world"));
        chooser.setFileFilter(new ExtFilter(".world"));
        int buttonPressed = chooser.showOpenDialog(owner);
        if (buttonPressed == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            filename = chooser.getSelectedFile().getAbsolutePath();
            openContent(filename);
        }
    }

    public void openContent(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader in = new BufferedReader(reader);
            width = Integer.parseInt(in.readLine());
            height = Integer.parseInt(in.readLine());
            filename = in.readLine();
            placename = in.readLine();
            constructMe(width, height);
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    String s[] = in.readLine().split("#");
                    squares[r][c].setField(ObjectFactory.createObject(s[0], Place.getUselessInstance()));
                    squares[r][c].setObject(ObjectFactory.createObject(s[1], Place.getUselessInstance()));
                    if (squares[r][c].getObject() instanceof PersonObject) {
                        try {
                            ((PersonObject) (squares[r][c].getObject())).setPersonName(s[2]);
                            ((PersonObject) (squares[r][c].getObject())).setText(s[3]);
                        } catch (Exception ex) {
                        }
                    }else if (squares[r][c].getObject() instanceof BuildingObject) {
                        try {
                            ((BuildingObject) (squares[r][c].getObject())).setLinkedPlace(s[2]);
                            ((BuildingObject) (squares[r][c].getObject())).setXpos(Integer.parseInt(s[3]));
                            ((BuildingObject) (squares[r][c].getObject())).setYpos(Integer.parseInt(s[4]));
                        } catch (Exception ex) {
                        }
                    }else if (squares[r][c].getField() instanceof TransportFieldObject) {
                        try {
                            ((TransportFieldObject) (squares[r][c].getField())).setLinkedPlace(s[2]);
                            ((TransportFieldObject) (squares[r][c].getField())).setxPos(Integer.parseInt(s[3]));
                            ((TransportFieldObject) (squares[r][c].getField())).setxPos(Integer.parseInt(s[4]));
                        } catch (Exception ex) {
                        }
                    }

                }
            }
            reader.close();
            revalidate();
            repaint();
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
