package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Report extends JPanel {

    private JTable table;
    private JComboBox<String> cboType;
    private JTextField txtFrom, txtTo;
    private JTextField txtCustomerId, txtAccountId;
    private JLabel lblTotalAmount, lblTotalCount;

    public Report() {
        initComponents();
    }

    private void initComponents() {

        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("ATM Transaction Report");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setBounds(20, 10, 400, 40);
        add(lblTitle);

        // ----------- FILTER PANEL -------------
        JLabel lblFrom = new JLabel("From:");
        lblFrom.setBounds(20, 70, 50, 30);
        add(lblFrom);

        txtFrom = new JTextField(LocalDate.now().minusMonths(1).toString());
        txtFrom.setBounds(70, 70, 120, 30);
        add(txtFrom);

        JLabel lblTo = new JLabel("To:");
        lblTo.setBounds(200, 70, 30, 30);
        add(lblTo);

        txtTo = new JTextField(LocalDate.now().toString());
        txtTo.setBounds(230, 70, 120, 30);
        add(txtTo);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(370, 70, 50, 30);
        add(lblType);

        cboType = new JComboBox<>(new String[]{"All", "Deposit", "Withdraw", "Transfer"});
        cboType.setBounds(420, 70, 120, 30);
        add(cboType);

        JLabel lblCustomer = new JLabel("Customer ID:");
        lblCustomer.setBounds(560, 70, 100, 30);
        add(lblCustomer);

        txtCustomerId = new JTextField();
        txtCustomerId.setBounds(660, 70, 80, 30);
        add(txtCustomerId);

        JLabel lblAccount = new JLabel("Account ID:");
        lblAccount.setBounds(760, 70, 90, 30);
        add(lblAccount);

        txtAccountId = new JTextField();
        txtAccountId.setBounds(850, 70, 100, 30);
        add(txtAccountId);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(970, 70, 100, 30);
        btnSearch.addActionListener(e -> loadReport());
        add(btnSearch);

        // ----------- TABLE --------------
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID",
                        "Date",
                        "Customer Name",
                        "Customer ID",
                        "Account ID",
                        "Type",
                        "Amount",
                        "Currency"
                }
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 120, 1050, 430);
        add(scroll);

        // ----------- SUMMARY --------------
        JLabel lblTotalText = new JLabel("Total Amount:");
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalText.setBounds(20, 560, 150, 30);
        add(lblTotalText);

        lblTotalAmount = new JLabel("0.00");
        lblTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalAmount.setBounds(150, 560, 200, 30);
        add(lblTotalAmount);

        JLabel lblCountText = new JLabel("Total Transactions:");
        lblCountText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCountText.setBounds(400, 560, 200, 30);
        add(lblCountText);

        lblTotalCount = new JLabel("0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalCount.setBounds(600, 560, 200, 30);
        add(lblTotalCount);

        // ---------- BUTTON: EXPORT EXCEL ----------
        JButton btnExport = new JButton("Export Excel");
        btnExport.setBounds(830, 560, 120, 30);
        btnExport.addActionListener(e -> exportToExcel());
        add(btnExport);

        // ---------- BUTTON: CLOSE ----------
        JButton btnClose = new JButton("Close");
        btnClose.setBounds(960, 560, 100, 30);
        btnClose.addActionListener(e -> closeReport());
        add(btnClose);

        loadReport();
    }

    // ================= LOAD REPORT ===================
    private void loadReport() {

        String from = txtFrom.getText().trim();
        String to = txtTo.getText().trim();
        String type = cboType.getSelectedItem().toString();
        String customer = txtCustomerId.getText().trim();
        String account = txtAccountId.getText().trim();

        String sql = """
            SELECT 
                a.id,
                a.created_at,
                ac.customer_id,
                c."CustomerName",
                a.account_id,
                a.tran_type,
                a.amount,
                a.is_kh
            FROM atm_transaction AS a
            INNER JOIN account AS ac 
                ON a.account_id = ac.id
            INNER JOIN "CUSTOMER" AS c 
                ON ac.customer_id = c."ID"
            WHERE a.created_at::date BETWEEN ?::date AND ?::date
        """;

        ArrayList<Object> params = new ArrayList<>();
        params.add(from);
        params.add(to);

        if (!type.equals("All")) {
            sql += " AND a.tran_type = ?";
            params.add(type.toLowerCase());
        }

        if (!customer.isEmpty()) {
            sql += " AND ac.customer_id = ?";
            params.add(Integer.parseInt(customer));
        }

        if (!account.isEmpty()) {
            sql += " AND a.account_id = ?";
            params.add(Integer.parseInt(account));
        }

        sql += " ORDER BY a.created_at DESC";

        ArrayList<HashMap<String, Object>> rows =
                DBHelper.getValues(sql, params.toArray());

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        double totalAmount = 0;
        int count = 0;

        for (var r : rows) {

            double amount = Double.parseDouble(r.get("amount").toString());
            totalAmount += amount;
            count++;

            model.addRow(new Object[]{
                    r.get("id"),
                    r.get("created_at"),
                    r.get("CustomerName"),
                    r.get("customer_id"),
                    r.get("account_id"),
                    r.get("tran_type"),
                    amount,
                    (Boolean) r.get("is_kh") ? "KHR" : "USD"
            });
        }

        lblTotalAmount.setText(String.valueOf(totalAmount));
        lblTotalCount.setText(String.valueOf(count));
    }

    // ================= CLOSE BUTTON ===================
    private void closeReport() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    // ================= EXPORT TO EXCEL ===================
    private void exportToExcel() {

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Report");

            chooser.setSelectedFile(new java.io.File("report_" + LocalDate.now() + ".csv"));

            int result = chooser.showSaveDialog(this);

            if (result != JFileChooser.APPROVE_OPTION) return;

            FileWriter writer = new FileWriter(chooser.getSelectedFile());

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // headers
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write(model.getColumnName(i) + ",");
            }
            writer.write("\n");

            // rows
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object value = model.getValueAt(r, c);
                    writer.write((value == null ? "" : value.toString()) + ",");
                }
                writer.write("\n");
            }

            writer.flush();
            writer.close();

            JOptionPane.showMessageDialog(this,
                    "Export successful!\nOpen file in Microsoft Excel.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Export failed: " + ex.getMessage());
        }
    }
}
