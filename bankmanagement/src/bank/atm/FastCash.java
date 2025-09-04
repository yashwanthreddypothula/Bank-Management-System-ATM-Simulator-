package bank.atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.ResultSet;

public class FastCash extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, back;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        JLabel text = new JLabel("SELECT WITHDRAWAL AMOUNT");
        text.setBounds(210, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        image.add(text);

        b1 = new JButton("Rs 100");
        b1.setBounds(170, 415, 150, 30);
        image.add(b1);

        b2 = new JButton("Rs 500");
        b2.setBounds(355, 415, 150, 30);
        image.add(b2);

        b3 = new JButton("Rs 1000");
        b3.setBounds(170, 450, 150, 30);
        image.add(b3);

        b4 = new JButton("Rs 2000");
        b4.setBounds(355, 450, 150, 30);
        image.add(b4);

        b5 = new JButton("Rs 5000");
        b5.setBounds(170, 485, 150, 30);
        image.add(b5);

        b6 = new JButton("Rs 10000");
        b6.setBounds(355, 485, 150, 30);
        image.add(b6);

        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        image.add(back);

        // Register listeners
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        back.addActionListener(this);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
        } else {
            String amount = ((JButton) ae.getSource()).getText().substring(3); // remove "Rs "
            Date date = new Date();

            try {
                conn c = new conn();

                // Calculate current balance
                int balance = 0;
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '" + pin + "'");
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }

                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                // Insert withdrawal entry
                String query = "insert into bank values('" + pin + "', '" + date + "', 'Withdraw', '" + amount + "')";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");

                setVisible(false);
                new Transactions(pin).setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new FastCash("").setVisible(true);
    }
}
