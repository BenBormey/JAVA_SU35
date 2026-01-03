
package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class guiCreateCard extends JFrame {

    private long currentUserId;

    JComboBox<String> cbAccount = new JComboBox<>();
    JComboBox<String> cbType = new JComboBox<>(new String[]{"VISA","MASTER","JCB"});
    JComboBox<String> cbStatus = new JComboBox<>(new String[]{"ACTIVE","BLOCKED","EXPIRED"});

    JTextField txtCardNo = new JTextField();
    JTextField txtPin = new JTextField();
    JTextField txtExpiry = new JTextField();
    JTextField txtCVV = new JTextField();
    JTextField txtLimit = new JTextField();
    JTextField txtDebt = new JTextField();

    public guiCreateCard(long currentUserId){

        this.currentUserId = currentUserId;

        setTitle("Create Card");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Create New Card", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Panel
        JPanel p = new JPanel(new GridLayout(10,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        add(p, BorderLayout.CENTER);

        addRow(p,"Account:", cbAccount);
        addRow(p,"Card Number:", txtCardNo);
        addRow(p,"Card Type:", cbType);
        addRow(p,"PIN Code:", txtPin);
        addRow(p,"Expiry (YYYY-MM-DD):", txtExpiry);
        addRow(p,"CVV:", txtCVV);
        addRow(p,"Credit Limit:", txtLimit);
        addRow(p,"Current Debt:", txtDebt);
        addRow(p,"Status:", cbStatus);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        p.add(btnSave);
        p.add(btnCancel);

        btnSave.addActionListener(e -> saveCard());
        btnCancel.addActionListener(e -> dispose());

        setVisible(true);

        loadAccounts();
    }

    // ---------- LOAD USER ACCOUNTS ----------
  private void loadAccounts(){

    cbAccount.removeAllItems();

    String sql = """
         SELECT a.id, a.account_no
                FROM account a
                INNER JOIN "CUSTOMER" c ON a.customer_id = c."ID"
                INNER JOIN users u ON u.userid = c."UserID"
        WHERE u.userid = ?
    """;

    var list = DBHelper.getValues(sql, currentUserId);

    for (var row : list){
        long id = ((Number)row.get("id")).longValue();
        String acc = row.get("account_no").toString();
        cbAccount.addItem(id + " - " + acc);
    }
}


    // ---------- SAVE CARD ----------
    private void saveCard(){

        try {

            String acc = cbAccount.getSelectedItem().toString();
            long accountId = Long.parseLong(acc.split(" - ")[0]);

            String sql = """
                INSERT INTO card
                (card_number, account_id, card_type, pin_code, expiry_date,
                 cvv, credit_limit, current_debt, status, created_at)
                VALUES (?,?,?,?,?,?,?,?,?, NOW())
            """;

            int row = DBHelper.execute(
                    sql,
                    txtCardNo.getText(),
                    accountId,
                    cbType.getSelectedItem().toString(),
                    txtPin.getText(),
                    Date.valueOf(txtExpiry.getText()),
                    txtCVV.getText(),
                    new BigDecimal(txtLimit.getText()),
                    new BigDecimal(txtDebt.getText()),
                    cbStatus.getSelectedItem().toString()
            );

            if(row > 0){
                JOptionPane.showMessageDialog(this,"🎉 Card Created Successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"❌ Failed to create card");
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(this,"ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addRow(JPanel p, String label, JComponent c){
        p.add(new JLabel(label));
        p.add(c);
    }
}
