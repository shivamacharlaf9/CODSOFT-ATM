import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM_GUI extends JFrame {
    private double balance = 0.0;
    private boolean authenticated = false;

    private JTextField amountField;
    private JTextArea displayArea;
    private JPasswordField pinField;

    private JButton depositButton;
    private JButton withdrawButton;
    private JButton checkButton;
    private JButton logoutButton;

    public ATM_GUI() {
        setTitle("ATM Interface");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel headerLabel = new JLabel("ATM Interface", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(headerLabel, gbc);

        // PIN Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Enter PIN:"), gbc);

        // PIN Field
        gbc.gridx = 1;
        pinField = new JPasswordField(10);
        add(pinField, gbc);

        // Login Button
        gbc.gridx = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setToolTipText("Click to login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginButton, gbc);

        // Amount Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Amount:"), gbc);

        // Amount Field
        gbc.gridx = 1;
        amountField = new JTextField(10);
        add(amountField, gbc);

        // Deposit Button
        gbc.gridy = 3;
        gbc.gridx = 1;
        depositButton = new JButton("Deposit");
        depositButton.setToolTipText("Click to deposit money");
        depositButton.setEnabled(false); // Disabled until logged in
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        add(depositButton, gbc);

        // Withdraw Button
        gbc.gridx = 2;
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setToolTipText("Click to withdraw money");
        withdrawButton.setEnabled(false); // Disabled until logged in
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        add(withdrawButton, gbc);

        // Check Balance Button
        gbc.gridy = 4;
        gbc.gridx = 1;
        checkButton = new JButton("Check Balance");
        checkButton.setToolTipText("Click to check balance");
        checkButton.setEnabled(false); // Disabled until logged in
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        add(checkButton, gbc);

        // Logout Button
        gbc.gridx = 2;
        logoutButton = new JButton("Logout");
        logoutButton.setToolTipText("Click to logout");
        logoutButton.setEnabled(false); // Disabled until logged in
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        add(logoutButton, gbc);

        // Display Area
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        displayArea = new JTextArea(15, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setBackground(Color.WHITE);
        displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, gbc);
    }

    private void login() {
        String pin = new String(pinField.getPassword());
        if (pin.equals("1234")) {
            authenticated = true;
            depositButton.setEnabled(true);
            withdrawButton.setEnabled(true);
            checkButton.setEnabled(true);
            logoutButton.setEnabled(true);
            displayArea.append("Login successful!\n");
        } else {
            displayArea.append("Invalid PIN.\n");
            JOptionPane.showMessageDialog(this, "Invalid PIN. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deposit() {
        if (authenticated) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    balance += amount;
                    displayArea.append("Deposited $" + amount + "\n");
                    amountField.setText(""); // Clear amount field
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please login first.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void withdraw() {
        if (authenticated) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    displayArea.append("Withdrew $" + amount + "\n");
                    amountField.setText(""); // Clear amount field
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds or invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please login first.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void checkBalance() {
        if (authenticated) {
            displayArea.append("Current balance: $" + balance + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Please login first.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void logout() {
        authenticated = false;
        depositButton.setEnabled(false);
        withdrawButton.setEnabled(false);
        checkButton.setEnabled(false);
        logoutButton.setEnabled(false);
        displayArea.append("Logged out successfully.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATM_GUI().setVisible(true);
            }
        });
    }
}
