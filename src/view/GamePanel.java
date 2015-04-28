/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Direction;
import model.Place;
import model.Tile;
import model.objects.BuildingObject;
import model.objects.ObjectFactory;
import model.objects.PersonObject;
import model.objects.fields.TransportFieldObject;
import model.objects.listeners.DirectionEvent;
import model.objects.listeners.DirectionListener;
import model.objects.listeners.TransportEvent;
import model.objects.listeners.TransportListener;
import model.objects.people.PlayerPersonObject;

/**
 *
 * @author Jens
 */
public class GamePanel extends JPanel implements KeyListener, DirectionListener, TransportListener{

    private Place p;
    private PlayerPersonObject player = null;
    private int width, height;

    public GamePanel(int width, int height) {
        constructMe(width, height);
        addKeyListener(this);
    }

    public void constructMe(int width, int height) {
        this.width = width;
        this.height = height;
        p = new Place(width, height, this);
        player = new PlayerPersonObject(p);
        setPreferredSize(new Dimension(width * Tile.TILE_WIDTH, (height - 1) * Tile.TILE_HEIGHT + Tile.TILE_WIDTH));
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int x = 0, y = 0;
        setBackground(Color.white);
        //Field
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
//                    p.getField(r, c).getImage().paintIcon(this, g2, x, y);
                    p.getSquare(r, c).drawField(this, g);
                } catch (Exception e) {
                }

                x += Tile.TILE_WIDTH;
            }
            x = 0;
            y += Tile.TILE_HEIGHT;
        }
        //Static Object
        x = 0;
        y = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                try {
//                    p.getStaticObject(r, c).getImage().paintIcon(this, g2, x, y);
                    p.getSquare(r, c).drawObject(this, g);
                } catch (Exception e) {
                }
                x += Tile.TILE_WIDTH;
            }
            x = 0;
            y += Tile.TILE_HEIGHT;
        }
//        p.draw(this, g);
    }

    public void openContent(JFrame f) {
        stopMusic();
        JFileChooser chooser = new JFileChooser("./worlds");
//        chooser.setFileFilter(new FileNameExtensionFilter("World file", "world"));
        chooser.setFileFilter(new ExtFilter(".world"));
        int buttonPressed = chooser.showOpenDialog(f);
        if (buttonPressed == JFileChooser.APPROVE_OPTION) {
            File fi = chooser.getSelectedFile();
//            String filename = chooser.getName(f) + ".world";
            try {
                FileReader reader = new FileReader(fi);
                BufferedReader in = new BufferedReader(reader);
                width = Integer.parseInt(in.readLine());
                height = Integer.parseInt(in.readLine());
                constructMe(width, height);
                p.setOutputName(in.readLine());
                p.setGameName(in.readLine());
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        String s[] = in.readLine().split("#");
                        p.setField(r, c, ObjectFactory.createObject(s[0], p));
                        p.setStaticObject(r, c, ObjectFactory.createObject(s[1], p));
                        p.getStaticObject(r, c).addTransportListener(this);
                        if (p.getStaticObject(r, c) instanceof PersonObject) {
//                            ((PersonObject) (p.getStaticObject(r, c))).setPersonName(s[2]);
//                            ((PersonObject) (p.getStaticObject(r, c))).setText(s[3]);
                            try {
                                PersonObject person = (PersonObject) p.getStaticObject(r, c);
                                person.setColumn(c);
                                person.setRow(r);
                                person.setPersonName(s[2]);
                                person.setText(s[3]);
                                person.updatePlayer();
                            } catch (Exception ex) {
                            }
                        } else if (p.getStaticObject(r, c) instanceof BuildingObject) {
                            try {
                                ((BuildingObject) (p.getStaticObject(r, c))).setLinkedPlace(s[2]);
                                ((BuildingObject) (p.getStaticObject(r, c))).setXpos(Integer.parseInt(s[3]));
                                ((BuildingObject) (p.getStaticObject(r, c))).setYpos(Integer.parseInt(s[4]));
                            } catch (Exception ex) {
                            }
                        } else if (p.getField(r, c) instanceof TransportFieldObject) {
                            try {
                                ((TransportFieldObject) (p.getField(r, c))).setLinkedPlace(s[2]);
                                ((TransportFieldObject) (p.getField(r, c))).setxPos(Integer.parseInt(s[3]));
                                ((TransportFieldObject) (p.getField(r, c))).setxPos(Integer.parseInt(s[4]));
                            } catch (Exception ex) {
                            }
                        }

                    }
                }
                player.updatePlayer();
                GameFrame.setText(p.getGameName());
                reader.close();
            } catch (FileNotFoundException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            p.startMusic();
        }
        revalidate();
        repaint();
    }

    public void openSpecificContent(String path) {
        try {
            stopMusic();
            FileReader reader = new FileReader(path);
            BufferedReader in = new BufferedReader(reader);
            width = Integer.parseInt(in.readLine());
            height = Integer.parseInt(in.readLine());
            constructMe(width, height);
            p.setOutputName(in.readLine());
            p.setGameName(in.readLine());
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    String s[] = in.readLine().split("#");
                    p.setField(r, c, ObjectFactory.createObject(s[0], p));
                    p.setStaticObject(r, c, ObjectFactory.createObject(s[1], p));
                    p.getField(r, c).addTransportListener(this);
                    p.getStaticObject(r, c).addTransportListener(this);
                    if (p.getStaticObject(r, c) instanceof PersonObject) {
//                        ((PersonObject) (p.getStaticObject(r, c))).setPersonName(s[2]);
//                        ((PersonObject) (p.getStaticObject(r, c))).setText(s[3]);
                        try {
                            PersonObject person = (PersonObject) p.getStaticObject(r, c);
                            person.setColumn(c);
                            person.setRow(r);
                            person.setPersonName(s[2]);
                            person.setText(s[3]);
                            person.updatePlayer();
                        } catch (Exception ex) {
                        }
                    } else if (p.getStaticObject(r, c) instanceof BuildingObject) {
                        try {
                            ((BuildingObject) (p.getStaticObject(r, c))).setLinkedPlace(s[2]);
                            ((BuildingObject) (p.getStaticObject(r, c))).setXpos(Integer.parseInt(s[3]));
                            ((BuildingObject) (p.getStaticObject(r, c))).setYpos(Integer.parseInt(s[4]));
                        } catch (Exception ex) {
                        }
                    }
                    if (p.getField(r, c) instanceof TransportFieldObject) {
                        try {
                            ((TransportFieldObject) (p.getField(r, c))).setLinkedPlace(s[2]);
                            ((TransportFieldObject) (p.getField(r, c))).setxPos(Integer.parseInt(s[3]));
                            ((TransportFieldObject) (p.getField(r, c))).setxPos(Integer.parseInt(s[4]));
                            System.out.println(s[2]);
                        } catch (Exception ex) {
                        }
                    }

                }
            }
            player.updatePlayer();
            GameFrame.setText(p.getGameName());
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("file not found: " + path);
        } catch (IOException ex) {
            System.out.println("io");
        }
        p.startMusic();
        invalidate();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction d = null;
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q) {
            d = Direction.WEST;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            d = Direction.EAST;
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z) {
            d = Direction.NORTH;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            d = Direction.SOUTH;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E) {
            try {
                p.getObjectFromDirection(player.getRow(), player.getColumn(), player.getDirection()).talk(player.getDirection());
            } catch (NullPointerException ex) {
                System.out.println("talking to myself");
            }
        }
        if (d != null) {
            player.moveTo(d);
            revalidate();
            repaint();
        }
//        System.out.println("keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void directionChanged(DirectionEvent e) {
        revalidate();
        repaint();
    }

    void stopMusic() {
        p.stopMusic();
    }

    void startMusic() {
        p.startMusic();
    }

    @Override
    public void transport(TransportEvent e) {
        System.out.println("doet transport");
        Direction d = player.getDirection();
        openSpecificContent(e.getNewPlace());
        player.updateColumn(e.getXpos());
        player.updateRow(e.getYpos());
        player.changeDirection(d);
        GameFrame.setText(p.getGameName());
        invalidate();
        repaint();
    }
}
