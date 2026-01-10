package com.mycompany.atm.transaction.system.ui;

import com.mycompany.atm.transaction.system.DB.DBHelper;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class guiRole extends JFrame {

    private Integer editingRoleId = null;

    JTextField txtRoleName = new JTextField();
    JTextField txtDesc = new JTextField();
    JCheckBox chkActive = new JCheckBox("Active");

    JTable table;
    DefaultTableModel model;

    JButton btnSave = new JButton("Save");
    JButton btnClear = new JButton("Clear");
    JButton btnDelete = new JButton("Delete");

    public guiRole() {
        setTitle("Role Management");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP FORM =====
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Role Name:"));
        form.add(txtRoleName);

        form.add(new JLabel("Description:"));
        form.add(txtDesc);

        form.add(new JLabel("Status:"));
        form.add(chkActive);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnClear);
        btnPanel.add(btnDelete);

        form.add(btnPanel);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Role Name", "Description", "Active"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== EVENTS =====
        btnSave.addActionListener(e -> saveRole());
        btnClear.addActionListener(e -> clearForm());
        btnDelete.addActionListener(e -> deleteRole());

        table.getSelectionModel().addListSelectionListener(e -> loadSelectedRole());

        loadRoles();
    }

    // ================= LOAD ROLES =================
    private void loadRoles() {
        model.setRowCount(0);

        String sql = """
            SELECT role_id, role_name, description, active
            FROM public.roles
            ORDER BY role_id
        """;

        ArrayList<HashMap<String, Object>> rows = DBHelper.getValues(sql);

        for (HashMap<String, Object> row : rows) {
            model.addRow(new Object[]{
                    row.get("role_id"),
                    row.get("role_name"),
                    row.get("description"),
                    row.get("active")
            });
        }
    }

    // ================= SAVE / UPDATE =================
    private void saveRole() {

        String roleName = txtRoleName.getText().trim();

        if (roleName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Role name required!");
            return;
        }

        if (editingRoleId == null) {
            insertRole();
            JOptionPane.showMessageDialog(this, "Role added!");
        } else {
            updateRole();
            JOptionPane.showMessageDialog(this, "Role updated!");
        }

        clearForm();
        loadRoles();
    }

    private void insertRole() {
        String sql = """
            INSERT INTO roles (role_name, description, active)
            VALUES (?, ?, ?)
        """;

        DBHelper.execute(
                sql,
                txtRoleName.getText(),
                txtDesc.getText(),
                chkActive.isSelected()
        );
    }

    private void updateRole() {
        String sql = """
            UPDATE roles
            SET role_name=?, description=?, active=?
            WHERE role_id=?
        """;

        DBHelper.execute(
                sql,
                txtRoleName.getText(),
                txtDesc.getText(),
                chkActive.isSelected(),
                editingRoleId
        );
    }

    // ================= DELETE =================
    private void deleteRole() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a role first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        int c = JOptionPane.showConfirmDialog(
                this,
                "Delete this role?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (c == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM roles WHERE role_id=?";
            DBHelper.execute(sql, id);
            JOptionPane.showMessageDialog(this, "Deleted!");

            clearForm();
            loadRoles();
        }
    }

    // ================= LOAD SELECTED =================
    private void loadSelectedRole() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        editingRoleId = (int) model.getValueAt(row, 0);
        txtRoleName.setText(model.getValueAt(row, 1).toString());
        txtDesc.setText(model.getValueAt(row, 2).toString());
        chkActive.setSelected((Boolean) model.getValueAt(row, 3));
    }

    // ================= CLEAR =================
    private void clearForm() {
        editingRoleId = null;
        txtRoleName.setText("");
        txtDesc.setText("");
        chkActive.setSelected(false);
        table.clearSelection();
    }
}
