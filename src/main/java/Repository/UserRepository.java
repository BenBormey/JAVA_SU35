package Repository;

import com.mycompany.atm.transaction.system.DB.dbcontextion;
import interfaces.IUserRepository;
import java.sql.*;

public class UserRepository implements IUserRepository {

    @Override
    public boolean checkLogin(String username, String password) {
        String sql = """
               SELECT 
                    "UserID", 
                    "UserName", 
                    "Password", 
                    "Active", 
                    "Role"
                FROM public."User"
                WHERE "UserName" = ? 
                  AND "Password" = ? 
                  AND "Active" = TRUE
                LIMIT 1;
            """;

        try (Connection con = dbcontextion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2,password);
 

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
