package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class guiTransationAdmin extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<String> cbFrom, cbTo, cbType;
    private JTextField txtAmount;
    private JButton btnSubmit;

    public guiTransationAdmin() {
        setLayout(null);
        initTable();
        initForm();
        loadAccounts();
        loadAccountCombo();
    }

    // ================= TABLE =================
    private void initTable() {
        model = new DefaultTableModel();
        model.addColumn("Account No");
        model.addColumn("Type");
        model.addColumn("Balance");
        model.addColumn("KH");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 520, 200);
        add(sp);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                cbFrom.setSelectedItem(model.getValueAt(row, 0).toString());
            }
        });
    }

    // ================= FORM =================
    private void initForm() {
        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(20, 240, 80, 25);
        add(lblType);

        cbType = new JComboBox<>(new String[]{"DEPOSIT", "WITHDRAW", "TRANSFER"});
        cbType.setBounds(100, 240, 200, 25);
        add(cbType);

        JLabel lblFrom = new JLabel("From:");
        lblFrom.setBounds(20, 280, 80, 25);
        add(lblFrom);

        cbFrom = new JComboBox<>();
        cbFrom.setBounds(100, 280, 200, 25);
        add(cbFrom);

        JLabel lblTo = new JLabel("To:");
        lblTo.setBounds(20, 320, 80, 25);
        add(lblTo);

        cbTo = new JComboBox<>();
        cbTo.setBounds(100, 320, 200, 25);
        add(cbTo);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(20, 360, 80, 25);
        add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(100, 360, 200, 25);
        add(txtAmount);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(100, 400, 200, 30);
        add(btnSubmit);

        cbType.addActionListener(e -> toggleTo());
        toggleTo();

        btnSubmit.addActionListener(e -> process());
    }

    private void toggleTo() {
        String type = cbType.getSelectedItem().toString();
        cbTo.setEnabled(type.equals("TRANSFER"));
    }

    // ================= LOAD TABLE =================
    private void loadAccounts() {
        model.setRowCount(0);

        String sql = """
            SELECT a.account_no AS account_no,
                   a.account_type AS account_type,
                   a.balance AS balance,
                   a.is_kh AS is_kh
            FROM account a
            JOIN public."CUSTOMER" c
                 ON a.customer_id = c."ID"
        """;

        ArrayList<HashMap<String, Object>> list = DBHelper.getValues(sql);

        for (var row : list) {
            model.addRow(new Object[]{
                    row.get("account_no"),
                    row.get("account_type"),
                    row.get("balance"),
                    row.get("is_kh")
            });
        }
    }

    // ================= LOAD COMBO =================
    private void loadAccountCombo() {
        cbFrom.removeAllItems();
        cbTo.removeAllItems();

        String sql = """
            SELECT a.account_no
            FROM account a
            JOIN public."CUSTOMER" c
                 ON a.customer_id = c."ID"
        """;

        var list = DBHelper.getValues(sql);

        for (var row : list) {
            String accNo = row.get("account_no").toString();
            cbFrom.addItem(accNo);
            cbTo.addItem(accNo);
        }
    }

    // ================= PROCESS =================
    private void process() {
        try {
            String type = cbType.getSelectedItem().toString();
            String from = cbFrom.getSelectedItem().toString();
            BigDecimal amount = new BigDecimal(txtAmount.getText());

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be > 0");
                return;
            }

            if (type.equals("DEPOSIT")) {
                deposit(from, amount);
            } else if (type.equals("WITHDRAW")) {
                withdraw(from, amount);
            } else {
                String to = cbTo.getSelectedItem().toString();
                if (from.equals(to)) {
                    JOptionPane.showMessageDialog(this, "Cannot transfer to same account!");
                    return;
                }
                transfer(from, to, amount);
            }

            loadAccounts();
            JOptionPane.showMessageDialog(this, "Success!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // ================= LOGIC =================
    private BigDecimal getBalance(String accNo) {
        String sql = "SELECT balance FROM account WHERE account_no = ?";
        Object val = DBHelper.executeScalar(sql, accNo);
        return new BigDecimal(val.toString());
    }

    private void deposit(String accNo, BigDecimal amount) {
        String sql = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
        DBHelper.execute(sql, amount, accNo);
    }

    private void withdraw(String accNo, BigDecimal amount) {
        BigDecimal bal = getBalance(accNo);

        if (bal.compareTo(amount) < 0) {
            JOptionPane.showMessageDialog(this, "Insufficient balance!");
            return;
        }

        String sql = "UPDATE account SET balance = balance - ? WHERE account_no = ?";
        DBHelper.execute(sql, amount, accNo);
    }

    private void transfer(String from, String to, BigDecimal amount) {
        BigDecimal bal = getBalance(from);

        if (bal.compareTo(amount) < 0) {
            JOptionPane.showMessageDialog(this, "Insufficient balance!");
            return;
        }

        String sql1 = "UPDATE account SET balance = balance - ? WHERE account_no = ?";
        String sql2 = "UPDATE account SET balance = balance + ? WHERE account_no = ?";

        DBHelper.execute(sql1, amount, from);
        DBHelper.execute(sql2, amount, to);
    }
}
