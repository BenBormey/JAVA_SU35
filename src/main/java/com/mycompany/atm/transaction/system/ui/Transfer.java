package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

public class Transfer extends javax.swing.JFrame {

    public int currentUserId;

    public Transfer(int currenctUserId) {
        initComponents();
        this.currentUserId = currenctUserId;
        this.loadAccountsToCombo();
    }

    public Transfer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboAccount = new javax.swing.JComboBox<>();
        txtAccountTo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("From Account :");

        cboAccount.setBackground(new java.awt.Color(204, 204, 204));
        cboAccount.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cboAccount.setForeground(new java.awt.Color(0, 0, 0));
        cboAccount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtAccountTo.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("To Account :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Amount :");

        txtAmount.setBackground(new java.awt.Color(204, 204, 204));

        btnOk.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnOk.setText("Okay");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnOk)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(cboAccount, 0, 416, Short.MAX_VALUE)
                                                        .addComponent(txtAccountTo)
                                                        .addComponent(jLabel3)
                                                        .addComponent(txtAmount))))
                                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAccountTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOk)
                                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {

        if (txtAmount.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Enter Amount");
            return;
        }

        if (txtAccountTo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Enter Account No");
            return;
        }

        Object selAccount = cboAccount.getSelectedItem();
        if (selAccount == null) {
            JOptionPane.showMessageDialog(this, "Please choose Pay From Account");
            return;
        }

        BigDecimal amount = new BigDecimal(txtAmount.getText());
        String toAccountNo = txtAccountTo.getText();

        String accountInfo = selAccount.toString();
        String[] parts = accountInfo.split(" - ");
        String accountNo = parts[0].trim();

        String sqlAcc = """
            SELECT a.id, a.balance, a.is_kh
            FROM account a
            JOIN public."CUSTOMER" c ON a.customer_id = c."ID"
            WHERE a.account_no = ?
              AND c."UserID" = ?
            """;

        var accList = DBHelper.getValues(sqlAcc, accountNo, this.currentUserId);

        if (accList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sender account not found!");
            return;
        }

        var acc = accList.get(0);
        long accountIdDb = ((Number) acc.get("id")).longValue();
        BigDecimal balanceBefore = new BigDecimal(acc.get("balance").toString());
        boolean isKh = (Boolean) acc.get("is_kh");

        if (balanceBefore.compareTo(amount) < 0) {
            JOptionPane.showMessageDialog(this, "Not enough balance!");
            return;
        }

        String sqlToAcc = """
            SELECT id, is_kh
            FROM account
            WHERE account_no = ?
            """;

        var toAccInfo = DBHelper.getValues(sqlToAcc, toAccountNo);

        if (toAccInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Receiver account not found!");
            return;
        }

        long toAccountId = ((Number) toAccInfo.get(0).get("id")).longValue();
        boolean toIsKh = (Boolean) toAccInfo.get(0).get("is_kh");

        // currency conversion
        BigDecimal rate = new BigDecimal("4100");

        BigDecimal amountSender = amount;
        BigDecimal amountReceiver = amount;

        if (isKh && !toIsKh) {           // KHR -> USD
            amountReceiver = amount.divide(rate, 2, RoundingMode.HALF_UP);
        }

        if (!isKh && toIsKh) {           // USD -> KHR
            amountReceiver = amount.multiply(rate);
        }

        BigDecimal balanceAfter = balanceBefore.subtract(amountSender);

        String sqlUpdateSender = """
            UPDATE account
            SET balance = balance - ?
            WHERE id = ?
            """;

        DBHelper.execute(sqlUpdateSender, amountSender, accountIdDb);

        String sqlUpdateReceiver = """
            UPDATE account
            SET balance = balance + ?
            WHERE id = ?
            """;

        DBHelper.execute(sqlUpdateReceiver, amountReceiver, toAccountId);

        JOptionPane.showMessageDialog(this, "Transfer success!");
    }

    private void loadAccountsToCombo() {

        String sql = """
            SELECT a.account_no, a.account_type, a.balance, a.is_kh
            FROM account a
            JOIN public."CUSTOMER" c ON a.customer_id = c."ID"
            WHERE c."UserID" = %d
            """.formatted(currentUserId);

        var list = DBHelper.getValues(sql);
        cboAccount.removeAllItems();

        for (var row : list) {
            String no = row.get("account_no").toString();
            String type = row.get("account_type").toString();
            double bal = ((Number) row.get("balance")).doubleValue();
            boolean isKh = (Boolean) row.get("is_kh");

            String symbol = isKh ? "៛" : "$";
            String text = "%s - %s (%,.2f %s)".formatted(no, type, bal, symbol);

            cboAccount.addItem(text);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transfer().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cboAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAccountTo;
    private javax.swing.JTextField txtAmount;
}
