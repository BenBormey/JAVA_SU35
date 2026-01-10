package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginForm extends JFrame {

    private boolean isKh = false;

    public LoginForm() {


        initComponents();
        setKhmerFont(this);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);

        // default language
        setEnglish();

        // set Khmer font if available
    }


    /** --------------------  LANGUAGE  -------------------- **/

    private void setKhmer() {
        lblUserName.setText("ឈ្មោះអ្នកប្រើ :");
        lblPassword.setText("ពាក្យសម្ងាត់ :");
        btnLogin.setText("ចូលប្រើ");
        btnLang.setText("English");
        lblHeader.setText("សូមស្វាគមន៍មកវិញ");
        lblLang.setText("ភាសា :");
        isKh = true;
    }

    private void setEnglish() {
        lblUserName.setText("Username :");
        lblPassword.setText("Password :");
        btnLogin.setText("Login");
        btnLang.setText("ខ្មែរ");
        lblHeader.setText("Welcome Back");
        lblLang.setText("Language :");
        isKh = false;
    }

    private void setKhmerFont(Component c) {
        try {
            Font khFont = new Font("Khmer OS Battambang", c.getFont().getStyle(), c.getFont().getSize());
            c.setFont(khFont);
        } catch (Exception ignored) {}

        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                setKhmerFont(child);
            }
        }
    }

    /** --------------------  LOGIN ACTION  -------------------- **/

    private void doLogin() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String sql = """
                SELECT u.userid, u.active, r.role_name
                FROM public.users u
                JOIN public.roles r ON u.role_id = r.role_id
                WHERE u.username = ? AND u.password = ?
                """;

        ArrayList<HashMap<String, Object>> result =
                DBHelper.getValues(sql, username, password);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        HashMap<String, Object> row = result.get(0);

        boolean active = Boolean.parseBoolean(row.get("active").toString());
        String role = row.get("role_name").toString();
        int userId = Integer.parseInt(row.get("userid").toString());

        if (!active) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your account is inactive. Please contact admin. ",
                    "Access denied",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful!");

        try {
            if (role.equalsIgnoreCase("ADMIN")) {
                AdminDeshbord admin = new AdminDeshbord(userId, isKh);
                admin.setVisible(true);
            } else {
                MainForm main = new MainForm(userId,isKh);
                main.pack();
                main.setLocationRelativeTo(null);
                main.setVisible(true);

            }
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot open system: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** -------------------- GENERATED UI -------------------- **/

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ATM Login");
        setSize(480, 420);
        setResizable(false);

        // Root panel
        JPanel root = new JPanel();
        root.setBackground(new Color(5, 18, 35));
        root.setLayout(new GridBagLayout());
        add(root);

        // Card panel
        JPanel card = new JPanel();
        card.setBackground(new Color(10, 31, 57));
        card.setPreferredSize(new Dimension(420, 360));
        card.setLayout(null); // manual nice layout
        root.add(card);

        // Language
        lblLang = new JLabel("Language :");
        lblLang.setForeground(new Color(220, 194, 154));
        lblLang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblLang.setBounds(30, 20, 100, 30);
        card.add(lblLang);

        btnLang = new JButton("ខ្មែរ");
        btnLang.setBounds(140, 20, 80, 30);
        btnLang.setBackground(new Color(5, 18, 35));
        btnLang.setForeground(new Color(220, 194, 154));
        btnLang.addActionListener(e -> {
            if (isKh) setEnglish();
            else setKhmer();
        });
        card.add(btnLang);

        // Title
        lblHeader = new JLabel("WELCOME BACK", SwingConstants.CENTER);
        lblHeader.setForeground(new Color(220, 194, 154));
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setBounds(50, 70, 320, 40);
        card.add(lblHeader);

        // Username label
        lblUserName = new JLabel("Username :");
        lblUserName.setForeground(new Color(220, 194, 154));
        lblUserName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblUserName.setBounds(50, 130, 120, 30);
        card.add(lblUserName);

        // Username box
        txtUsername = new JTextField();
        txtUsername.setBounds(180, 130, 200, 30);
        txtUsername.setBackground(new Color(5, 18, 35));
        txtUsername.setForeground(new Color(220, 194, 154));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(txtUsername);

        // Password label
        lblPassword = new JLabel("Password :");
        lblPassword.setForeground(new Color(220, 194, 154));
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblPassword.setBounds(50, 180, 120, 30);
        card.add(lblPassword);

        // Password box
        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 180, 200, 30);
        txtPassword.setBackground(new Color(5, 18, 35));
        txtPassword.setForeground(new Color(220, 194, 154));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(txtPassword);

        // Login button
        btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(140, 240, 140, 40);
        btnLogin.setBackground(new Color(220, 194, 154));
        btnLogin.setForeground(new Color(10, 31, 57));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogin.addActionListener(e -> doLogin());
        card.add(btnLogin);

        setLocationRelativeTo(null);
    }


    /** -------------------- MAIN -------------------- **/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }

    // Variables
    private JPanel jPanel1;
    private JLabel lblHeader, lblUserName, lblPassword, lblLang;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnLang;
}
