package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class guiAddAccount extends javax.swing.JFrame {

    public int CurrentId;   // UserID

    public guiAddAccount(int CurrenId) {
        initComponents();
        this.CurrentId = CurrenId;

        // auto generate account number when open
        generateAccountNo();
    }

    public guiAddAccount() {
        initComponents();
        generateAccountNo();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        txtAccountNo = new javax.swing.JTextField();
        txtBalance = new javax.swing.JTextField();

        cboType = new javax.swing.JComboBox<>();
        cboCurrency = new javax.swing.JComboBox<>();

        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Account No :");
        jLabel2.setText("Account Type :");
        jLabel3.setText("Currency :");
        jLabel4.setText("Initial Balance :");

        txtAccountNo.setEnabled(false); // ❌ user cannot type, auto generate

        cboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "SAVING",
                "CURRENT"
        }));

        cboCurrency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "KHR",
                "USD"
        }));

        btnSave.setText("Save");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> dispose());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(txtAccountNo)
                                        .addComponent(txtBalance)
                                        .addComponent(cboType, 0, 300, Short.MAX_VALUE)
                                        .addComponent(cboCurrency, 0, 300, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))

                                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        pack();
        setLocationRelativeTo(null);
    }

    // 🔥 AUTO ACCOUNT NO GENERATOR
    private void generateAccountNo() {

        // get last account
        String sql = """
            SELECT account_no
            FROM account
            ORDER BY id DESC
            LIMIT 1
            """;

        var list = DBHelper.getValues(sql);

        String branch = "001";
        String bank = "111";
        int next = 1;

        if (!list.isEmpty()) {

            String lastAcc = list.get(0).get("account_no").toString(); // 001-111-0002

            // get last 4 digits
            String[] parts = lastAcc.split("-");
            String lastRun = parts[2]; // 0002

            next = Integer.parseInt(lastRun) + 1;
        }

        String running = String.format("%04d", next);

        String newAcc = branch + "-" + bank + "-" + running;

        txtAccountNo.setText(newAcc);
    }

    // load customer ID using UserID
    private Integer loadCustomerIdByUser() {

        String sql = """
            SELECT "ID"
            FROM public."CUSTOMER"
            WHERE "UserID" = ?
            """;

        var list = DBHelper.getValues(sql, this.CurrentId);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer not found for this user");
            return null;
        }

        return ((Number) list.get(0).get("ID")).intValue();
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {

        if (txtBalance.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Balance required");
            return;
        }
        String exit = """
                
                			select * from account where customer_id = 9
                """;
        var exited = DBHelper.getValues(exit);
        if (!exited.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot save your account");
            return;
        }

        Integer custId = loadCustomerIdByUser();
        if (custId == null) return;

        String accountNo = txtAccountNo.getText();
        String type = cboType.getSelectedItem().toString();
        String currency = cboCurrency.getSelectedItem().toString();
        BigDecimal balance = new BigDecimal(txtBalance.getText());

        boolean isKh = currency.equals("KHR");

        String sql = """
            INSERT INTO account
            (account_no, account_type, balance, is_kh, customer_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        int row = DBHelper.execute(
                sql,
                accountNo,
                type,
                balance,
                isKh,
                custId
        );

        if (row > 0) {
            JOptionPane.showMessageDialog(this, "Account created successfully");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Create failed");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new guiAddAccount().setVisible(true));
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtAccountNo;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JComboBox<String> cboType;
    private javax.swing.JComboBox<String> cboCurrency;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnCancel;
}
