package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class DashBoardPanel_ extends javax.swing.JPanel {

    private JLabel lblTotalCustomer;
    private JLabel lblTotalAccount;
    private JLabel lblTotalTransaction;
    private JLabel lblExchange;

    private JTable gvtransition;

    public DashBoardPanel_() {
        initComponents();

        // load data immediately
        TotalCustomer();
        TotalAccount();
        TotalTransation();
        Exchange();
        loadRecentTransaction();
    }

    private void initComponents() {

        setBackground(Color.WHITE);
        setLayout(null);

        // ---------- TITLE ----------
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setBounds(20, 15, 400, 40);
        add(lblTitle);

        // ---------- CARD 1 ----------
        JPanel card1 = createCardPanel("Total Customers");
        card1.setBounds(20, 80, 260, 100);
        lblTotalCustomer = createCardValue(card1);
        add(card1);

        // ---------- CARD 2 ----------
        JPanel card2 = createCardPanel("Total Accounts");
        card2.setBounds(300, 80, 260, 100);
        lblTotalAccount = createCardValue(card2);
        add(card2);

        // ---------- CARD 3 ----------
        JPanel card3 = createCardPanel("Total Transactions");
        card3.setBounds(580, 80, 260, 100);
        lblTotalTransaction = createCardValue(card3);
        add(card3);

        // ---------- CARD 4 ----------
        JPanel card4 = createCardPanel("Exchange Rate (KHR/USD)");
        card4.setBounds(860, 80, 260, 100);
        lblExchange = createCardValue(card4);
        add(card4);

        // ---------- TABLE TITLE ----------
        JLabel lblRecent = new JLabel("Recent Transactions");
        lblRecent.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblRecent.setBounds(20, 210, 400, 35);
        add(lblRecent);

        // ---------- TABLE ----------
        gvtransition = new JTable();
        gvtransition.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gvtransition.setModel(new DefaultTableModel(
                new Object [][] {},
                new String [] {"No", "Date", "Type", "Amount", "Status"}
        ));

        JScrollPane scrollPane = new JScrollPane(gvtransition);
        scrollPane.setBounds(20, 250, 1100, 350);
        add(scrollPane);
    }

    // ---------------- CARD PANEL STYLE ----------------
    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setLayout(null);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitle.setBounds(15, 10, 230, 25);
        panel.add(lblTitle);

        return panel;
    }

    private JLabel createCardValue(JPanel panel) {
        JLabel lbl = new JLabel("0");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbl.setBounds(15, 40, 230, 50);
        panel.add(lbl);
        return lbl;
    }

    // ---------------- LOAD COUNTS ----------------

    public void TotalCustomer() {
        Object v = DBHelper.getSingleValue("SELECT COUNT(*) FROM \"CUSTOMER\"");
        lblTotalCustomer.setText(String.valueOf(v));
    }

    public void TotalAccount() {
        Object v = DBHelper.getSingleValue("SELECT COUNT(*) FROM  public.account");
        lblTotalAccount.setText(String.valueOf(v));
    }

    public void TotalTransation() {
        Object v = DBHelper.getSingleValue("SELECT COUNT(*) FROM public.atm_transaction");
        lblTotalTransaction.setText(String.valueOf(v));
    }

    public void Exchange() {
        Object v = DBHelper.getSingleValue(
                "SELECT * FROM currency_rate"
        );
        lblExchange.setText(String.valueOf(v));
    }

    // ---------------- LOAD RECENT TABLE ----------------
    public void loadRecentTransaction() {

        String sql =
                "SELECT id, created_at, tran_type, amount, is_kh " +
                        "FROM transaction ORDER BY created_at DESC LIMIT 10";

        var rows = DBHelper.getValues(sql);

        DefaultTableModel model = (DefaultTableModel) gvtransition.getModel();
        model.setRowCount(0);

        int no = 1;

        for (var r : rows) {

            model.addRow(new Object[]{
                    no++,
                    r.get("created_at"),
                    r.get("tran_type"),
                    r.get("amount"),
                    r.get("is_kh")
            });
        }
    }
}
