/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import com.mycompany.atm.transaction.system.model.RoleItem;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class guiCreateUsers extends javax.swing.JPanel {
private HashMap<String, Integer> roleMap = new HashMap<>();
private Integer editingUserId = null;


    /**
     * Creates new form guiCreateUsers
     */
    public guiCreateUsers(int userid, boolean iskhe_) {
        initComponents();
         this.loudingrole();
         loadingUser();

         jTable1.getTableHeader().setFont(
    new java.awt.Font("Khmer OS Battambang", java.awt.Font.PLAIN, 16)
);
jTable1.getTableHeader().repaint();
      addButtonToTable();


         this.userid = userid;
         this.iskher_ = iskhe_;
              setKhmerFont(this);
                 if(this.iskher_) {
                iskher_ = false;

                        setKh();

                        } else {
                            iskher_ = true;
                            setEng();
                        }


    }

     private void setKhmerFont(java.awt.Component component) {
    java.awt.Font current = component.getFont();
    java.awt.Font khFont = new java.awt.Font("Khmer OS Battambang", current.getStyle(), current.getSize());
    component.setFont(khFont);

    if (component instanceof java.awt.Container) {
        for (java.awt.Component child : ((java.awt.Container) component).getComponents()) {
            setKhmerFont(child);
        }
    }

    }
     private void addButtonToTable() {

    // ===== EDIT BUTTON =====
    jTable1.getColumn("Edit").setCellRenderer(
            new ButtonRenderer("Edit")
    );

    jTable1.getColumn("Edit").setCellEditor(
            new ButtonEditor(new javax.swing.JCheckBox(), "Edit")
    );

    // ===== DELETE BUTTON =====
    jTable1.getColumn("Delete").setCellRenderer(
            new ButtonRenderer("Delete")
    );

    jTable1.getColumn("Delete").setCellEditor(
            new ButtonEditor(new javax.swing.JCheckBox(), "Delete")
    );
}



        // ================= BUTTON RENDERER ==================
class ButtonRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {

    public ButtonRenderer(String text) {
        setText(text);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        return this;
    }
}


// ================= BUTTON EDITOR ==================
class ButtonEditor extends javax.swing.DefaultCellEditor {

    protected javax.swing.JButton button;
    private String label;
    private boolean clicked;
    private int row;

    public ButtonEditor(javax.swing.JCheckBox checkBox, String text) {
        super(checkBox);

        button = new javax.swing.JButton();
        this.label = text;


        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {

        this.row = row;
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {

        if (clicked) {

            int id = (int) jTable1.getValueAt(row, 0);   // userid

            // ================== EDIT ==================
            if (label.equals("Edit")) {

                editingUserId = id;

                String username = jTable1.getValueAt(row, 1).toString();
                Boolean active = (Boolean) jTable1.getValueAt(row, 2);
                String role = jTable1.getValueAt(row, 3).toString();

                // push to form inputs
                txtUsername.setText(username);

                // password not from table
                txtPassword.setText("");
                txtComfirmps.setText("");

                choRole.setSelectedItem(role);
                chkActive.setSelected(active);

                JOptionPane.showMessageDialog(null, "Editing user id = " + id);
            }

            // ================== DELETE ==================
            else if (label.equals("Delete")) {

                int c = JOptionPane.showConfirmDialog(
                        null,
                        "Delete user id = " + id + " ?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (c == JOptionPane.YES_OPTION) {
                    String sql = """
                            	update public.users set active = false where users.userid = ?
                            """;

                    DBHelper.execute(sql, id);

                    JOptionPane.showMessageDialog(null, "Deleted!");

                    loadingUser();
                }
            }
        }

        clicked = false;
        return label;
    }
}


 public int userid;
    public boolean iskher_;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUserName = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblUserName1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        txtComfirmps = new javax.swing.JTextField();
        lblUserName2 = new javax.swing.JLabel();
        lblUserName3 = new javax.swing.JLabel();
        lblUserName4 = new javax.swing.JLabel();
        choRole = new javax.swing.JComboBox<>();
        chkActive = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblUserName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(220, 194, 154));
        lblUserName.setText("UserName : ");

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(220, 194, 154));
        txtUsername.setName(""); // NOI18N

        lblUserName1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUserName1.setForeground(new java.awt.Color(220, 194, 154));
        lblUserName1.setText("Password :");

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(220, 194, 154));
        txtPassword.setName(""); // NOI18N

        txtComfirmps.setBackground(new java.awt.Color(255, 255, 255));
        txtComfirmps.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtComfirmps.setForeground(new java.awt.Color(220, 194, 154));
        txtComfirmps.setName(""); // NOI18N

        lblUserName2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUserName2.setForeground(new java.awt.Color(220, 194, 154));
        lblUserName2.setText("Comfirm Password :");

        lblUserName3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUserName3.setForeground(new java.awt.Color(220, 194, 154));
        lblUserName3.setText("Role :");

        lblUserName4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUserName4.setForeground(new java.awt.Color(220, 194, 154));
        lblUserName4.setText("Active :");

        choRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        chkActive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkActive.setText("Yes");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "userid", "username", "active", "role", "Edit", "Delete"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancel.setText("CanCel");

        btnAdd1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd1.setText("Add");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblUserName1)
                        .addGap(108, 108, 108)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 204, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblUserName)
                                .addGap(95, 95, 95)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUserName2)
                                    .addComponent(lblUserName3)
                                    .addComponent(lblUserName4))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(choRole, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAdd1))
                                    .addComponent(txtComfirmps, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkActive))))
                        .addGap(62, 62, 62))))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(224, 224, 224))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName1)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName2)
                    .addComponent(txtComfirmps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName3)
                    .addComponent(choRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName4)
                    .addComponent(chkActive))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnCancel))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
  if (!validateCreateUser()) return;

    // INSERT
    if (editingUserId == null) {

        boolean result = insertUser();

        if (result) {
            JOptionPane.showMessageDialog(this, "User created successfully!");
        }
    }
    // UPDATE
    else {

        updateUser();
        JOptionPane.showMessageDialog(this, "User updated successfully!");
    }

    clearForm();
    loadingUser();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:

        guiRole role = new guiRole();
role.setVisible(true);

//        getCash.setLocationRelativeTo(this); // center relative to main form
//    getCash.setVisible(true);
//     this.dispose();

    role.setLocationRelativeTo(this);
    role.setVisible(true);




    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void updateUser() {

    String sql = """
        UPDATE users
        SET username=?, password=?, role_id=?, active=?
        WHERE userid=?
    """;

    int roleId = roleMap.get(choRole.getSelectedItem().toString());

    DBHelper.execute(
            sql,
            txtUsername.getText().trim(),
            txtPassword.getText().trim(),
            roleId,
            chkActive.isSelected(),
            editingUserId
    );

    editingUserId = null; // exit edit mode
}

    private boolean validateCreateUser() {

    String userName = txtUsername.getText().trim();
    String password = txtPassword.getText();//new String(txtPassword.getPassword());

    String confirm  = txtComfirmps.getText();


    if (userName.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Username is required!",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE
        );
        txtUsername.requestFocus();
        return false;
    }


    if (password.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Password is required!",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE
        );
        txtPassword.requestFocus();
        return false;
    }

    // 3. Confirm password empty
    if (confirm.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Confirm Password is required!",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE
        );
        txtComfirmps.requestFocus();
        return false;
    }

    if (!password.equals(confirm)) {
        JOptionPane.showMessageDialog(
            this,
            "Password and Confirm Password do not match!",
            "Validation Error",
            JOptionPane.ERROR_MESSAGE
        );
        txtComfirmps.requestFocus();
        return false;
    }


    if (choRole.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Please select a role!",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE
        );
        choRole.requestFocus();
        return false;
    }

    return true;
}
private void clearForm() {
    txtUsername.setText("");
    txtPassword.setText("");
    txtComfirmps.setText("");
    choRole.setSelectedIndex(-1);
    chkActive.setSelected(false);
    txtUsername.requestFocus();
}
private void loadingUser(){
        String sql = """
        SELECT u.userid,
               u.username,
               u.active,
               r.role_name
        FROM public.users u
        JOIN public.roles r ON u.role_id = r.role_id
        ORDER BY u.userid
    """;

       ArrayList<HashMap<String, Object>> rows = DBHelper.getValues(sql);
           DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // clear old data
    if (rows == null || rows.isEmpty()) {
        return;
    }
      for (HashMap<String, Object> row : rows) {

        Object userId   = row.get("userid");
        Object username = row.get("username");
        Object active   = row.get("active");
        Object roleName = row.get("role_name");

        model.addRow(new Object[]{
            userId,
            username,
            active,
            roleName
        });
    }
}
private boolean insertUser() {

    String userName = txtUsername.getText().trim();
    String password = txtPassword.getText().trim();

    if (choRole.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Please select a role!");
        return false;
    }

  String roleName = choRole.getSelectedItem().toString();
int roleId = roleMap.get(roleName);

    boolean active = chkActive.isSelected();



    String sql = """
        INSERT INTO users (username, password, role_id, active)
        VALUES (?, ?, ?, ?)
    """;

    int rows = DBHelper.execute(
        sql,
        userName,
        password, // later hash
        roleId,
        active
    );

    return rows > 0;
}

private void loudingrole() {

    choRole.removeAllItems();
    roleMap.clear();

    String sql = """
        SELECT role_id, role_name
        FROM public.roles
        WHERE active = true
        ORDER BY role_name
    """;

    ArrayList<HashMap<String, Object>> roles = DBHelper.getValues(sql);

    if (roles == null || roles.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No roles found!");
        return;
    }

    for (HashMap<String, Object> row : roles) {

        int roleId = ((Number) row.get("role_id")).intValue();
        String roleName = row.get("role_name").toString();

        choRole.addItem(roleName);          // ✅ String only
        roleMap.put(roleName, roleId);      // ✅ store id
    }
}
public void setEng() {

    // 🔹 Form labels
    lblUserName.setText("User Name :");
    lblUserName1.setText("Password :");
    lblUserName2.setText("Confirm Password :");
    lblUserName3.setText("Role :");
    lblUserName4.setText("Active :");

    // 🔹 Inputs / checkbox
    chkActive.setText("Yes");

    // 🔹 Buttons
    btnAdd.setText("Add");
    btnCancel.setText("Cancel");

    // 🔹 Table headers
    jTable1.getColumnModel().getColumn(0).setHeaderValue("User ID");
    jTable1.getColumnModel().getColumn(1).setHeaderValue("Username");
    jTable1.getColumnModel().getColumn(2).setHeaderValue("Active");
    jTable1.getColumnModel().getColumn(3).setHeaderValue("Role");

    jTable1.getTableHeader().repaint();
}

public void setKh() {

    // 🔹 Form labels
    lblUserName.setText("ឈ្មោះអ្នកប្រើ :");
    lblUserName1.setText("ពាក្យសម្ងាត់ :");
    lblUserName2.setText("បញ្ជាក់ពាក្យសម្ងាត់ :");
    lblUserName3.setText("តួនាទី :");
    lblUserName4.setText("សកម្ម :");

    // 🔹 Inputs / checkbox
    chkActive.setText("បាទ/ចាស");

    // 🔹 Buttons
    btnAdd.setText("បន្ថែម");
    btnCancel.setText("បោះបង់");

    // 🔹 Table headers
    jTable1.getColumnModel().getColumn(0).setHeaderValue("លេខអ្នកប្រើ");
    jTable1.getColumnModel().getColumn(1).setHeaderValue("ឈ្មោះអ្នកប្រើ");
    jTable1.getColumnModel().getColumn(2).setHeaderValue("សកម្ម");
    jTable1.getColumnModel().getColumn(3).setHeaderValue("តួនាទី");

    jTable1.getTableHeader().repaint();
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnCancel;
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JComboBox<String> choRole;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lblUserName2;
    private javax.swing.JLabel lblUserName3;
    private javax.swing.JLabel lblUserName4;
    private javax.swing.JTextField txtComfirmps;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
