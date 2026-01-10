package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import javax.swing.*;

/**
 * guiCreateCard handles the creation of VISA/MASTER/JCB cards linked to specific accounts.
 * Supports both Khmer and English UI.
 */
public class guiCreateCard extends JFrame {

    private long currentUserId;
    private boolean iskh;

    // UI Components
    private JLabel lblTitle, lblAccount, lblCardNo, lblType, lblPin, lblExpiry, lblCvv, lblLimit, lblDebt, lblStatus;
    private JButton btnSave, btnCancel;

    private JComboBox<String> cbAccount = new JComboBox<>();
    private JComboBox<String> cbType = new JComboBox<>(new String[]{"VISA", "MASTER", "JCB"});
    private JComboBox<String> cbStatus = new JComboBox<>(new String[]{"ACTIVE", "BLOCKED", "EXPIRED"});

    private JTextField txtCardNo = new JTextField();
    private JTextField txtPin = new JTextField();
    private JTextField txtExpiry = new JTextField();
    private JTextField txtCVV = new JTextField();
    private JTextField txtLimit = new JTextField();
    private JTextField txtDebt = new JTextField();

    public guiCreateCard(long currentUserId, boolean iskh) {
        this.currentUserId = currentUserId;
        this.iskh = iskh;

        // Form Setup
        setTitle(iskh ? "ប្រព័ន្ធបង្កើតកាត" : "Card Creation System");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        lblTitle = new JLabel("", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Khmer OS Battambang", Font.BOLD, 22));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(10, 31, 57));
        lblTitle.setForeground(new Color(220, 194, 154));
        lblTitle.setPreferredSize(new Dimension(500, 60));
        add(lblTitle, BorderLayout.NORTH);

        // Body Panel
        JPanel p = new JPanel(new GridLayout(10, 2, 10, 10));
        p.setBackground(new Color(10, 31, 57));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(p, BorderLayout.CENTER);

        // Initialize and Style Labels
        lblAccount = createStyledLabel();
        lblCardNo = createStyledLabel();
        lblType = createStyledLabel();
        lblPin = createStyledLabel();
        lblExpiry = createStyledLabel();
        lblCvv = createStyledLabel();
        lblLimit = createStyledLabel();
        lblDebt = createStyledLabel();
        lblStatus = createStyledLabel();

        // Style TextFields
        styleComponent(txtCardNo);
        styleComponent(txtPin);
        styleComponent(txtExpiry);
        styleComponent(txtCVV);
        styleComponent(txtLimit);
        styleComponent(txtDebt);

        // Add to Panel
        p.add(lblAccount); p.add(cbAccount);
        p.add(lblCardNo);  p.add(txtCardNo);
        p.add(lblType);    p.add(cbType);
        p.add(lblPin);     p.add(txtPin);
        p.add(lblExpiry);  p.add(txtExpiry);
        p.add(lblCvv);     p.add(txtCVV);
        p.add(lblLimit);   p.add(txtLimit);
        p.add(lblDebt);    p.add(txtDebt);
        p.add(lblStatus);  p.add(cbStatus);

        // Buttons
        btnSave = new JButton();
        btnCancel = new JButton();
        btnSave.setBackground(new Color(220, 194, 154));
        btnCancel.setBackground(new Color(21, 42, 66));
        btnCancel.setForeground(Color.WHITE);

        p.add(btnSave);
        p.add(btnCancel);

        // Language Logic
        if (this.iskh) getkh(); else geteng();

        // Listeners
        btnSave.addActionListener(e -> saveCard());
        btnCancel.addActionListener(e -> goBack());

        loadAccounts();
    }

    private void getkh() {
        lblTitle.setText("បង្កើតកាតថ្មី");
        lblAccount.setText("ជ្រើសរើសគណនី:");
        lblCardNo.setText("លេខកាត:");
        lblType.setText("ប្រភេទកាត:");
        lblPin.setText("លេខកូដសម្ងាត់:");
        lblExpiry.setText("ថ្ងៃផុតកំណត់ (YYYY-MM-DD):");
        lblCvv.setText("លេខ CVV:");
        lblLimit.setText("កម្រិតឥណទាន:");
        lblDebt.setText("បំណុលបច្ចុប្បន្ន:");
        lblStatus.setText("ស្ថានភាព:");
        btnSave.setText("រក្សាទុក");
        btnCancel.setText("បោះបង់");
    }

    private void geteng() {
        lblTitle.setText("Create New Card");
        lblAccount.setText("Select Account:");
        lblCardNo.setText("Card Number:");
        lblType.setText("Card Type:");
        lblPin.setText("PIN Code:");
        lblExpiry.setText("Expiry (YYYY-MM-DD):");
        lblCvv.setText("CVV:");
        lblLimit.setText("Credit Limit:");
        lblDebt.setText("Current Debt:");
        lblStatus.setText("Status:");
        btnSave.setText("Save Card");
        btnCancel.setText("Cancel");
    }

    private void loadAccounts() {
        cbAccount.removeAllItems();
        String sql = """
             SELECT a.id, a.account_no
             FROM account a
             INNER JOIN "CUSTOMER" c ON a.customer_id = c."ID"
             WHERE c."UserID" = ?
        """;
        var list = DBHelper.getValues(sql, currentUserId);
        for (var row : list) {
            cbAccount.addItem(row.get("id") + " - " + row.get("account_no"));
        }
    }

    private void saveCard() {
        try {
            if (cbAccount.getSelectedItem() == null) {
                showMsg(iskh ? "សូមជ្រើសរើសគណនី" : "Please select an account");
                return;
            }

            long accountId = Long.parseLong(cbAccount.getSelectedItem().toString().split(" - ")[0]);
            Date expiry = Date.valueOf(txtExpiry.getText()); // Validates YYYY-MM-DD
            BigDecimal limit = new BigDecimal(txtLimit.getText());
            BigDecimal debt = new BigDecimal(txtDebt.getText());

            String sql = """
                INSERT INTO card (card_number, account_id, card_type, pin_code, expiry_date, cvv, credit_limit, current_debt, status, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
            """;

            int rows = DBHelper.execute(sql, txtCardNo.getText(), accountId, cbType.getSelectedItem(),
                    txtPin.getText(), expiry, txtCVV.getText(), limit, debt, cbStatus.getSelectedItem());

            if (rows > 0) {
                showMsg(iskh ? "កាតត្រូវបានបង្កើតដោយជោគជ័យ!" : "Card Created Successfully!");
                goBack();
            }
        } catch (Exception e) {
            showMsg("Error: " + e.getMessage());
        }
    }

    private void goBack() {
        MainForm main = new MainForm((int) currentUserId, iskh);
        main.setVisible(true);
        this.dispose();
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private JLabel createStyledLabel() {
        JLabel l = new JLabel();
        l.setForeground(new Color(220, 194, 154));
        l.setFont(new Font("Khmer OS Battambang", Font.PLAIN, 14));
        return l;
    }

    private void styleComponent(JComponent c) {
        c.setBackground(new Color(21, 42, 66));
        c.setForeground(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(new Color(220, 194, 154)));
    }
}