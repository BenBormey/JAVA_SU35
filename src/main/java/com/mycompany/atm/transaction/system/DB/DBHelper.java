package com.mycompany.atm.transaction.system.DB;

import com.mycompany.atm.transaction.system.model.transation;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class DBHelper {

    // 🔹 SELECT → return list<Map> (no params, raw SQL)
    public static ArrayList<HashMap<String, Object>> getValues(String sql) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        try (Connection con = dbcontextion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int col = meta.getColumnCount();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= col; i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                list.add(row);
            }

        } catch (Exception e) {
            System.out.println("❌ getValues Error: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 SELECT → return list<Map> (with params, PreparedStatement)
    public static ArrayList<HashMap<String, Object>> getValues(String sql, Object... params) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        try (Connection con = dbcontextion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int col = meta.getColumnCount();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= col; i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                list.add(row);
            }

} catch (Exception e) {
    JOptionPane.showMessageDialog(
        null,
        "❌ getValues(params) Error: " + e.getMessage()
    );
    e.printStackTrace();
}


        return list;
    }

    // 🔹 INSERT / UPDATE / DELETE (no params, raw SQL)
    public static int execute(String sql) {
        try (Connection con = dbcontextion.getConnection();
             Statement stmt = con.createStatement()) {

            return stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("❌ execute Error: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    // 🔹 INSERT / UPDATE / DELETE (with params, PreparedStatement)
    public static int execute(String sql, Object... params) {
        try (Connection con = dbcontextion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            int rows = ps.executeUpdate();
            return rows;

        } catch (SQLException e) {

            String msg = "SQL ERROR\n"
                    + "Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState() + "\n"
                    + "Error Code: " + e.getErrorCode();

            JOptionPane.showMessageDialog(null, msg, "DB ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return 0;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "❌ Database Error:\n" + e.getMessage(),
                    "DB ERROR",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return 0;
        }
    }
    public static transation fromRow(HashMap<String, Object> row) {
    transation t = new transation();

    t.setId(((Number) row.get("id")).longValue());
    t.setAccountId(((Number) row.get("account_id")).longValue());
    t.setUserId(((Number) row.get("user_id")).intValue());
    t.setTranType(row.get("tran_type").toString());
    t.setAmount(new BigDecimal(row.get("amount").toString()));
    t.setKh((Boolean) row.get("is_kh"));
    t.setBalanceBefore(new BigDecimal(row.get("balance_before").toString()));
    t.setBalanceAfter(new BigDecimal(row.get("balance_after").toString()));
    t.setNote(row.get("note") == null ? "" : row.get("note").toString());
    // created_at you can parse later if you want

    return t;
}


    // 🔹 SELECT return single value (no params)
    public static Object getSingleValue(String sql) {
        try (Connection con = dbcontextion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next())
                return rs.getObject(1);

        } catch (Exception e) {
            System.out.println("❌ getSingleValue Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 SELECT return single value (with params)
    public static Object executeScalar(String sql, Object... params) {
        try (Connection con = dbcontextion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }

        } catch (Exception e) {
            System.out.println("❌ executeScalar Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
