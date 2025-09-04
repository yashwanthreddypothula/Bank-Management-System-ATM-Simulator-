package bank.atm;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame {
    String pin;

    MiniStatement(String pin) {
        this.pin = pin;

        setTitle("Mini Statement");
        setLayout(null);

        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 200, 20);
        add(bank);

        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);

        JTextArea mini = new JTextArea();
        mini.setBounds(20, 140, 400, 200);
        add(mini);

        JLabel balanceLbl = new JLabel();
        balanceLbl.setBounds(20, 370, 300, 20);
        add(balanceLbl);

        try {
            conn c = new conn();

            // Masked card number (last 4 digits only)
            ResultSet rs = c.s.executeQuery("select * from login where pin = '" + pin + "'");
            if (rs.next()) {
                String cardno = rs.getString("cardno");
                card.setText("Card Number: " + cardno.substring(0, 4) + "XXXXXXXX" + cardno.substring(12));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn c1 = new conn();
            int balance = 0;
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '" + pin + "'");

            while (rs.next()) {
                mini.append(rs.getString("date") + "  " + rs.getString("type") + "  " + rs.getString("amount") + "\n");

                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            balanceLbl.setText("Your Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(450, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String args[]) {
        new MiniStatement("").setVisible(true);
    }
}
