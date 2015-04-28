/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Place;

/**
 *
 * @author Jens
 */
public class NewItemDialog extends JDialog implements FocusListener, ActionListener {

    private JTextField txtWidth, txtHeight, txtName;
    private int w, h;
    private int answer;

    public NewItemDialog(Frame owner) {
        super(owner);
        setModal(true);
        JPanel content = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(3, 2));
        top.add(new JLabel("Name:"));
        txtName = new JTextField(15);
        txtName.setText("New Place");
        top.add(txtName);
        top.add(new JLabel("Width:"));
        txtWidth = new JTextField(3);
        txtWidth.addFocusListener(this);
        txtWidth.setText("40");
        top.add(txtWidth);
        top.add(new JLabel("Height:"));
        txtHeight = new JTextField(3);
        txtHeight.addFocusListener(this);
        txtHeight.setText("40");
        top.add(txtHeight);
        content.add(top, BorderLayout.NORTH);
        JPanel buttons = new JPanel();
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        JButton btnOk = new JButton("Ok");
        btnOk.setSize(btnCancel.getSize());
        btnOk.addActionListener(this);
        buttons.add(btnOk);
        buttons.add(btnCancel);
        content.add(buttons, BorderLayout.EAST);
        setContentPane(content);
        setVisible(false);
        pack();
        setLocationRelativeTo(owner);
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField txt = (JTextField) e.getSource();
        int input;
        try {
            input = Integer.parseInt(txt.getText());
            if(input < Place.MIN_HEIGHT || input > Place.MAX_HEIGHT)
                throw new Exception();
            if(txt == txtWidth){
                w = input;
            }else{
                h = input;
            }
        }catch (Exception ex) {
            txt.setText("40");
            txt.selectAll();
        }
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public String getPlacename() {
        return txtName.getText();
    }
    
    
    public int showDialog(){
        setVisible(true);
        return answer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        if("Cancel".equals(btn.getText())){
            answer = JOptionPane.CANCEL_OPTION;
        }else{
            answer = JOptionPane.OK_OPTION;
        }
        dispose();
    }
    
}

