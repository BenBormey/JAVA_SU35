package com.mycompany.atm.transaction.system.DB;

import com.mycompany.atm.transaction.system.model.Card;
import java.sql.*;
import java.math.BigDecimal;

public class CardDAO {

    public static int createCard(Card c) throws Exception {

        String sql = """
            INSERT INTO card
            (card_number, account_id, card_type, pin_code, expiry_date,
             cvv, credit_limit, current_debt, status, created_at)
            VALUES (?,?,?,?,?,?,?,?,?, NOW())
        """;

        try (Connection con = dbcontextion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCardNumber());
            ps.setLong(2, c.getAccountId());
            ps.setString(3, c.getCardType());
            ps.setString(4, c.getPinCode());
            ps.setDate(5, new java.sql.Date(c.getExpiryDate().getTime()));
            ps.setString(6, c.getCvv());
            ps.setBigDecimal(7, c.getCreditLimit());
            ps.setBigDecimal(8, c.getCurrentDebt());
            ps.setString(9, c.getStatus());

            return ps.executeUpdate();
        }
    }
}
