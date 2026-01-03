package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class guiCustomerManegement extends javax.swing.JPanel {

    public int userid;
    public boolean iskher_;

    private HashMap<String,Integer> userMap = new HashMap<>();

    // ==== UI COMPONENTS ====
    private javax.swing.JTable tblCustomer;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;

    private javax.swing.JComboBox<String> cboUser;

    private javax.swing.JButton btnAdd;

    private javax.swing.JLabel lblTitle;

    public guiCustomerManegement(int userid , boolean iskhe_) {

        this.userid = userid;
        this.iskher_ = iskhe_;

        initComponents();

        loadingUserToCombo();
        loadingCustomer();
    }

    // =========================================================
    // ================== INIT COMPONENTS ======================
    // =========================================================
    private void initComponents() {

        setLayout(null);

        lblTitle = new javax.swing.JLabel("Customer Management");
        lblTitle.setBounds(20,10,400,40);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22));
        add(lblTitle);

        txtCustomerName = new javax.swing.JTextField();
        txtCustomerName.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Name"));
        txtCustomerName.setBounds(20,60,300,55);
        add(txtCustomerName);

        txtEmail = new javax.swing.JTextField();
        txtEmail.setBorder(javax.swing.BorderFactory.createTitledBorder("Email"));
        txtEmail.setBounds(340,60,300,55);
        add(txtEmail);

        txtPhone = new javax.swing.JTextField();
        txtPhone.setBorder(javax.swing.BorderFactory.createTitledBorder("Phone"));
        txtPhone.setBounds(20,130,300,55);
        add(txtPhone);

        cboUser = new javax.swing.JComboBox<>();
        cboUser.setBorder(javax.swing.BorderFactory.createTitledBorder("User"));
        cboUser.setBounds(340,130,300,55);
        add(cboUser);

        btnAdd = new javax.swing.JButton("Add Customer");
        btnAdd.setBounds(660,95,150,50);
        btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        add(btnAdd);

        // ===== TABLE =====
        tblCustomer = new javax.swing.JTable();

        tblCustomer.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"ID","Customer Name","User ID","Username"}
                )
        );

        jScrollPane1 = new javax.swing.JScrollPane(tblCustomer);
        jScrollPane1.setBounds(20,210,790,350);
        add(jScrollPane1);
    }

    // =========================================================
    // ================= LOAD CUSTOMER DATA ====================
    // =========================================================
    private void loadingCustomer(){

        String sql = """
            SELECT 
                c."ID",
                c."CustomerName",
                c."UserID",
                u.username
            FROM public."CUSTOMER" c
            LEFT JOIN public.users u 
                ON c."UserID" = u.userid
            ORDER BY c."ID"
        """;

        ArrayList<HashMap<String,Object>> rows = DBHelper.getValues(sql);

        DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();
        model.setRowCount(0);

        if(rows == null) return;

        for(HashMap<String,Object> r : rows){

            model.addRow(new Object[]{
                    r.get("ID"),
                    r.get("CustomerName"),
                    r.get("UserID"),
                    r.get("username")
            });
        }
    }

    // =========================================================
    // ================= LOAD USER COMBO =======================
    // =========================================================
    private void loadingUserToCombo(){

        cboUser.removeAllItems();
        userMap.clear();

        String sql = """
            SELECT userid, username
            FROM public.users
            WHERE active = true
            ORDER BY username
        """;

        ArrayList<HashMap<String,Object>> rows = DBHelper.getValues(sql);

        if(rows == null) return;

        for(HashMap<String,Object> r : rows){

            int id = ((Number) r.get("userid")).intValue();
            String name = r.get("username").toString();

            cboUser.addItem(name);
            userMap.put(name,id);
        }
    }

    // =========================================================
    // ================= INSERT CUSTOMER =======================
    // =========================================================
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt){

        String name = txtCustomerName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter customer name!");
            return;
        }

        if(cboUser.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this,"Select user!");
            return;
        }

        String username = cboUser.getSelectedItem().toString();
        int userId = userMap.get(username);

        String sql = """
            INSERT INTO public."CUSTOMER"
            ("CustomerName","UserID")
            VALUES (?,?)
        """;

        int x = DBHelper.execute(sql,name,userId);

        if(x>0){
            JOptionPane.showMessageDialog(this,"Customer added!");
            loadingCustomer();
        } else {
            JOptionPane.showMessageDialog(this,"Insert failed!");
        }
    }
}
