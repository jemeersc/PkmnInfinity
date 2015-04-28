/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Jens
 */
public class NewPersonDialog extends JDialog implements FocusListener, ActionListener {

    private JTextField txtName;
    private JTextArea txtText;
    private String personName, text;
    private int answer;

    public NewPersonDialog(Frame owner) {
        super(owner);
        setModal(true);
        JPanel content = new JPanel(new BorderLayout());
        
        JPanel top = new JPanel(new BorderLayout());
        
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        txtName.addFocusListener(this);
        txtName.setText("name");
        namePanel.add(txtName);
        top.add(namePanel, BorderLayout.NORTH);
        
        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Text:"));
        txtText = new JTextArea(30,20);
        txtText.addFocusListener(this);
        txtText.setText("text");
        textPanel.add(txtText);
        top.add(textPanel, BorderLayout.CENTER);
        
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
        JTextComponent txt = (JTextComponent) e.getSource();
        if(txt == txtName){
                personName = txt.getText();
            }else{
                text = txt.getText();
            }
//        int input;
//        try {
//            input = Integer.parseInt(txt.getText());
//            if(input < 10 || input > 40)
//                throw new Exception();
//            if(txt == txtName){
//                w = input;
//            }else{
//                h = input;
//            }
//        }catch (Exception ex) {
//            txt.setText("40");
//            txt.selectAll();
//        }
    }

    public String getPersonName() {
        return personName;
    }

    public String getText() {
        return text;
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

