package com.mycompany.atm.transaction.system.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper {

    // 🔹 SELECT → return list<Map>
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
        }

        return list;
    }

    // 🔹 INSERT / UPDATE / DELETE
    public static int execute(String sql) {
        try (Connection con = dbcontextion.getConnection();
             Statement stmt = con.createStatement()) {

            return stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("❌ execute Error: " + e.getMessage());
            return 0;
        }
    }

    // 🔹 SELECT return single value
    public static Object getSingleValue(String sql) {
        try (Connection con = dbcontextion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next())
                return rs.getObject(1);

        } catch (Exception e) {
            System.out.println("❌ getSingleValue Error: " + e.getMessage());
        }
        return null;
    }
}
