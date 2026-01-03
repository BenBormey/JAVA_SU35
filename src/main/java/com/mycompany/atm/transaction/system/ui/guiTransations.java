package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;
import java.io.FileWriter;

public class guiTransations extends JFrame {

    public int userId;

    private JTable tbTransation;
    private JComboBox<String> cboRange;
    private com.toedter.calendar.JDateChooser dtFrom;
    private com.toedter.calendar.JDateChooser dtTo;
    private JButton btnView;
    private JButton btnExcel;
    private JButton btnClose;

    public guiTransations() {
        initComponents();
        initTable();
        styleTable();
    }

    public guiTransations(int userId) {
        this.userId = userId;
        initComponents();
        initTable();
        styleTable();
        loadToday();
    }

    // ================== TABLE ====================
    private void initTable() {
        String[] headers = {"Date/Time", "Type", "Amount", "Balance After", "Note"};
        DefaultTableModel model = new DefaultTableModel(headers, 0);
        tbTransation.setModel(model);
    }

    private void styleTable() {
        tbTransation.setRowHeight(24);
        tbTransation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tbTransation.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
        );
    }

    // ================== LOAD DATA ====================
    private void loadTransactionsByDate(Date from, Date to) {
        try {

            java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
            java.sql.Date sqlTo   = new java.sql.Date(to.getTime());

            String sql = """
                SELECT created_at, tran_type, amount, balance_after, note
                FROM public.atm_transaction
                WHERE user_id = %d
                  AND created_at::date BETWEEN '%s' AND '%s'
                ORDER BY created_at DESC
            """.formatted(userId, sqlFrom, sqlTo);

            var rows = DBHelper.getValues(sql);

            DefaultTableModel model = (DefaultTableModel) tbTransation.getModel();
            model.setRowCount(0);

            for (var r : rows) {
                model.addRow(new Object[]{
                        r.get("created_at"),
                        r.get("tran_type"),
                        r.get("amount"),
                        r.get("balance_after"),
                        r.get("note")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ================== RANGE HELPERS ====================
    private void loadToday() {
        Date d = new Date();
        loadTransactionsByDate(d, d);
    }

    private void loadThisWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        loadTransactionsByDate(c.getTime(), new Date());
    }

    private void loadThisMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        loadTransactionsByDate(c.getTime(), new Date());
    }

    private void loadThisYear() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, 1);
        loadTransactionsByDate(c.getTime(), new Date());
    }

    // ================== VIEW BUTTON ====================
    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {

        String option = cboRange.getSelectedItem().toString();

        switch (option) {
            case "Today" -> loadToday();
            case "This Week" -> loadThisWeek();
            case "This Month" -> loadThisMonth();
            case "This Year" -> loadThisYear();
            case "Custom Range" -> {
                if (dtFrom.getDate() == null || dtTo.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Select From and To date");
                    return;
                }
                loadTransactionsByDate(dtFrom.getDate(), dtTo.getDate());
            }
        }
    }

    // ================== EXPORT CSV ====================
    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("transactions.csv"));
            chooser.showSaveDialog(this);

            FileWriter fw = new FileWriter(chooser.getSelectedFile());

            DefaultTableModel model = (DefaultTableModel) tbTransation.getModel();

            // header
            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");

            // data
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object v = model.getValueAt(r, c);
                    fw.write(v == null ? "" : v.toString().replace(",", " "));
                    if (c < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }

            fw.close();
            JOptionPane.showMessageDialog(this, "Exported successfully! You can open it with Excel.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ================== INIT COMPONENTS ====================
    private void initComponents() {

        JPanel topPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        JScrollPane scroll = new JScrollPane();

        tbTransation = new JTable();

        cboRange = new JComboBox<>(new String[]{
                "Today", "This Week", "This Month", "This Year", "Custom Range"
        });

        dtFrom = new com.toedter.calendar.JDateChooser();
        dtTo   = new com.toedter.calendar.JDateChooser();

        btnView  = new JButton("View");
        btnExcel = new JButton("Export Excel");
        btnClose = new JButton("Close");

        btnView.addActionListener(this::btnViewActionPerformed);
        btnExcel.addActionListener(this::btnExcelActionPerformed);

        // close THIS window only
        btnClose.addActionListener(e -> dispose());

        topPanel.add(new JLabel("Range:"));
        topPanel.add(cboRange);
        topPanel.add(new JLabel("From:"));
        topPanel.add(dtFrom);
        topPanel.add(new JLabel("To:"));
        topPanel.add(dtTo);
        topPanel.add(btnView);
        topPanel.add(btnExcel);
        topPanel.add(btnClose);

        scroll.setViewportView(tbTransation);

        setLayout(new java.awt.BorderLayout());
        add(topPanel, java.awt.BorderLayout.NORTH);
        add(scroll, java.awt.BorderLayout.CENTER);

        setTitle("Transaction Report");
        setSize(900, 600);
        setLocationRelativeTo(null); // center screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // ================== MAIN TEST ====================
    public static void main(String[] args) {
        new guiTransations(1).setVisible(true);
    }
}
