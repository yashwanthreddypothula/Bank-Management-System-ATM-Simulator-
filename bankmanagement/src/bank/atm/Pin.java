package bank.atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener {
    JPasswordField pin, repin;
    JButton change, back;
    String pinnumber;

    Pin(String pinnumber) {
        this.pinnumber = pinnumber;

        setTitle("Change PIN");
        setLayout(null);

        JLabel text = new JLabel("CHANGE YOUR PIN");
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(90, 40, 400, 30);
        add(text);

        JLabel pintext = new JLabel("New PIN:");
        pintext.setBounds(40, 100, 100, 30);
        add(pintext);

        pin = new JPasswordField();
        pin.setBounds(150, 100, 150, 30);
        add(pin);

        JLabel repintext = new JLabel("Re-Enter New PIN:");
        repintext.setBounds(40, 150, 150, 30);
        add(repintext);

        repin = new JPasswordField();
        repin.setBounds(150, 150, 150, 30);
        add(repin);

        change = new JButton("Change");
        change.setBounds(40, 220, 120, 30);
        change.addActionListener(this);
        add(change);

        back = new JButton("Back");
        back.setBounds(180, 220, 120, 30);
        back.addActionListener(this);
        add(back);

        setSize(400, 300);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == change) {
            try {
                String npin = pin.getText();
                String rpin = repin.getText();

                if (!npin.equals(rpin)) {
                    JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                    return;
                }

                if (npin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter new PIN");
                    return;
                }
                if (rpin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please re-enter new PIN");
                    return;
                }

                conn c = new conn();
                // Update PIN in all related tables
                String q1 = "update bank set pin = '" + rpin + "' where pin = '" + pinnumber + "'";
                String q2 = "update login set pin = '" + rpin + "' where pin = '" + pinnumber + "'";
                String q3 = "update signupthree set pin = '" + rpin + "' where pin = '" + pinnumber + "'";

                c.s.executeUpdate(q1);
                c.s.executeUpdate(q2);
                c.s.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");

                setVisible(false);
                new Transactions(rpin).setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

    public static void main(String args[]) {
        new Pin("").setVisible(true);
    }
}
