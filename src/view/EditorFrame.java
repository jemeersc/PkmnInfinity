/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;
import model.Place;
import model.objects.AbstractObject;
import model.objects.BuildingObject;
import model.objects.walls.BigTreeWallObject;
import model.objects.walls.BushWallObject;
import model.objects.fields.CaveFieldObject;
import model.objects.EmptyObject;
import model.objects.PersonObject;
import model.objects.buildings.HouseBuildingObject;
import model.objects.fields.FloorFieldObject;
import model.objects.fields.GrassFieldObject;
import model.objects.fields.TransportFieldObject;
import model.objects.people.DudePersonObject;
import model.objects.people.SilverPersonObject;
import model.objects.walls.RockWallObject;
import model.objects.walls.SmallTreeWallObject;
import model.objects.walls.WallBackWallObject;
import model.objects.walls.WallWallObject;
import model.objects.walls.WindowWallObject;

/**
 *
 * @author Jens
 */
public class EditorFrame extends JFrame implements MouseInputListener {

    private EditorPanel ep;
    private JPanel content;
    private Point beginSelection, endSelection;
    private JToolBar toolbar;
    private AbstractObject buttonObject;

    public EditorFrame() {
        setTitle("World Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel main = new JPanel(new BorderLayout());
        content = new JPanel();
        content.setPreferredSize(new Dimension(700, 700));
        content.setBackground(Color.darkGray);

        toolbar = new JToolBar(JToolBar.VERTICAL);
        main.add(toolbar, BorderLayout.WEST);
        addFieldButtonToToolbar(new GrassFieldObject());
        addFieldButtonToToolbar(new CaveFieldObject());
        addFieldButtonToToolbar(new FloorFieldObject());
        addFieldButtonToToolbar(new TransportFieldObject());
        addObjectButtonToToolbar(new EmptyObject());
        addObjectButtonToToolbar(new BushWallObject());
        addObjectButtonToToolbar(new SmallTreeWallObject());
        addObjectButtonToToolbar(new BigTreeWallObject());
        addObjectButtonToToolbar(new RockWallObject());
        addObjectButtonToToolbar(new WallWallObject());
        addObjectButtonToToolbar(new WindowWallObject());
        addObjectButtonToToolbar(new WallBackWallObject());
        addObjectButtonToToolbar(new HouseBuildingObject());
        addObjectButtonToToolbar(new SilverPersonObject(Place.getUselessInstance()));
        addObjectButtonToToolbar(new DudePersonObject(Place.getUselessInstance()));
//        addObjectButtonToToolbar(new PlayerPersonObject());

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New...");
        newItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newItemDialog();
                setTitle("World Editor - " + ep.getPlacename());
            }
        });
        file.add(newItem);

        JMenuItem saveItem = new JMenuItem("Save...");
        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveItemDialog();
            }
        });
        file.add(saveItem);

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openItemDialog();
                setTitle("World Editor - " + ep.getPlacename());
            }
        });
        file.add(openItem);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        file.addSeparator();
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        JMenuItem namedialog = new JMenuItem("Name...");
        namedialog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(content, "Choose a name for your place.", "Choose a name", JOptionPane.QUESTION_MESSAGE);
                ep.setPlacename(s);
                setTitle("World Editor - " + ep.getPlacename());
            }
        });
        edit.add(namedialog);
        
        JMenu test = new JMenu("Test");
        JMenuItem testItem = new JMenuItem("Test...");
        testItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ep.saveContent("test");
                GameFrame testGame = new GameFrame("test");

            }
        });
        test.add(testItem);

        menu.add(file);
        menu.add(edit);
        menu.add(test);


        newContent(40, 40, "NewPlace");
        main.add(content, BorderLayout.CENTER);
        setContentPane(main);
        setJMenuBar(menu);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void newContent(int width, int height, String placename) {
        ep = new EditorPanel(width, height);
        ep.setPlacename(placename);
        ep.addMouseListener(this);
        ep.addMouseMotionListener(this);
        content.removeAll();
        content.add(ep, BorderLayout.CENTER);
        ep.setLocation(content.getWidth() / 2 - ep.getWidth() / 2, content.getHeight() / 2 - ep.getHeight() / 2);
        content.revalidate();
        content.repaint();
    }

    private void newItemDialog() {
        int w = 40, h = 40;
        NewItemDialog dial = new NewItemDialog(this);
        if (dial.showDialog() == JOptionPane.OK_OPTION) {
            System.out.println("OkOption");
            w = dial.getW();
            h = dial.getH();
            newContent(w, h, dial.getPlacename());
        } else {
            System.out.println("CancelOption");
        }
    }

    private void saveItemDialog() {
        ep.saveContentDialog(this);
    }

    private void openItemDialog() {
        ep.openContentDialog(this);
    }

    private void addFieldButtonToToolbar(final AbstractObject o) {
        JButton btn = new JButton(o.getImage());
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ep.setFieldObjects(o);
            }
        });
        toolbar.add(btn);
    }

    private void addObjectButtonToToolbar(final AbstractObject o) {
        JButton btn = new JButton(o.getImage());
        buttonObject = o;
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (o instanceof PersonObject) {
                    NewPersonDialog dial = new NewPersonDialog(null);
                    if (dial.showDialog() == JOptionPane.OK_OPTION) {
                        PersonObject po = (PersonObject) o;
                        po.setPersonName(dial.getPersonName());
                        po.setText(dial.getText());
                        ep.setObject(po);
                    }

                } else if (o instanceof BuildingObject) {
                    int xpos = 9, ypos = 9;
                    JFileChooser chooser = new JFileChooser("./worlds");
                    if (chooser.showSaveDialog(content) == JFileChooser.APPROVE_OPTION) {
                        File f = chooser.getSelectedFile();
                        String filename = f.getAbsolutePath();
                        if (!filename.endsWith(".world")) {
                            filename += ".world";
                        }
                        ep.setObject(o);
                        String backup = ep.getFilename();
                        ep.saveContent(backup);
                        ep.openContent("building.world");
                        for (int r = 0; r < ep.getHeight(); r++) {
                            for (int c = 0; c < ep.getWidth(); c++) {
                                try {
                                    if (ep.getSquare(r, c).getField() instanceof TransportFieldObject) {
                                        TransportFieldObject tr = ((TransportFieldObject) (ep.getSquare(r, c).getField()));
                                        tr.setLinkedPlace(backup);
                                        xpos = ((BuildingObject) o).getC();
                                        tr.setxPos(xpos);
                                        ypos = ((BuildingObject) o).getR() + 1;
                                        tr.setyPos(ypos);
                                        xpos = c; ypos = r-1;
                                    }
                                } catch (Exception ex) {
                                }
                            }
                        }
                        ep.saveContent(filename);
                        ep.openContent(backup);
                        BuildingObject b = (BuildingObject)o;
                        b = (BuildingObject)(ep.getSquare(b.getR(), b.getC()).getObject());
                        b.setLinkedPlace(filename);
                        b.setXpos(xpos);
                        b.setYpos(ypos);
                    }

                } else {
                    ep.setObject(o);
                }
            }
        });
        toolbar.add(btn);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        newPerson(buttonObject);
//        if (o instanceof PersonObject) {
//            NewPersonDialog dial = new NewPersonDialog(this);
//            if (dial.showDialog() == JOptionPane.OK_OPTION) {
//                PersonObject po = (PersonObject) o;
//                po.setPersonName(dial.getPersonName());
//                po.setText(dial.getText());
//                ep.setObject(po);
//            }
//
//        } else {
//            ep.setObject(o);
//        }
//    }
    public void newPerson(AbstractObject o) {
        if (o instanceof PersonObject) {
            NewPersonDialog dial = new NewPersonDialog(this);
            if (dial.showDialog() == JOptionPane.OK_OPTION) {
                PersonObject po = (PersonObject) o;
                po.setPersonName(dial.getPersonName());
                po.setText(dial.getText());
                ep.setObject(po);
            }

        } else {
            ep.setObject(o);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        beginSelection = e.getPoint();
        select(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        select(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        select(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void select(MouseEvent e) {
        if (!e.isControlDown()) {
            ep.deselectAll();
        }
        endSelection = e.getPoint();
        try {
            ep.select(beginSelection, endSelection);
        } catch (Exception ex) {
        }
        revalidate();
        repaint();
    }
}
