/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author Jens
 */
public class GameFrame extends JFrame implements WindowListener{

    private GamePanel ep;
    private JPanel content;
    private static JTextArea text;
//    private JFrame f;

    public GameFrame() {

        setTitle("World");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(this);

        JPanel main = new JPanel(new BorderLayout());
        content = new JPanel();
        content.setPreferredSize(new Dimension(700, 700));
        content.setBackground(Color.darkGray);

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openItemDialog();
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

        menu.add(file);

        text = new JTextArea(3,30);
        text.setMinimumSize(new Dimension(100,100));
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFocusable(false);
        System.out.println(text.getFont().getSize());
        try {
            text.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/berlin.ttf")).deriveFont((float)14));
            System.out.println(text.getFont().getSize());
   
        } catch (Exception ex) {}
        JScrollPane txtPanel = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        main.add(txtPanel, BorderLayout.SOUTH);
        
        newContent(40, 40);
        ep.openSpecificContent("worlds/start.world");
        main.add(content, BorderLayout.CENTER);
        setContentPane(main);
        setJMenuBar(menu);
        pack();
        addKeyListener(ep);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public GameFrame(String path) {
        this();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ep.openSpecificContent(path);
    }
    
    

    private void newContent(int width, int height) {
        ep = new GamePanel(width, height);
        content.removeAll();
        content.add(ep, BorderLayout.CENTER);
        ep.setLocation(content.getWidth() / 2 - ep.getWidth() / 2, content.getHeight() / 2 - ep.getHeight() / 2);
        content.revalidate();
        content.repaint();
    }

    private void openItemDialog() {
        ep.openContent(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        ep.startMusic();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ep.stopMusic();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    public static void setText(String text){
        GameFrame.text.setText(text);
        System.out.println(text);
    }
}
