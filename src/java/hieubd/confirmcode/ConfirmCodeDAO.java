/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.confirmcode;

import hieubd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.naming.NamingException;

/**
 *
 * @author CND
 */
public class ConfirmCodeDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public int addNewCode(String email) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "INSERT INTO ConfirmCode(email, code, currentDate) VALUES(?,?,?)";
                int confirmCode = 0;
                Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
                do {
                    confirmCode = (int) Math.round(Math.random() * 1000000);
                } while (confirmCode == 0);

                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setInt(2, confirmCode);
                stm.setTimestamp(3, dateTime);

                if (stm.executeUpdate() > 0) {
                    return confirmCode;
                }
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public int updateCode(String email) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "UPDATE ConfirmCode "
                        + "SET code = ?, currentDate = ? "
                        + "WHERE email = ?";
                int confirmCode = 0;
                Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
                do {
                    confirmCode = (int) Math.round(Math.random() * 1000000);
                } while (confirmCode == 0);

                stm = con.prepareStatement(sql);
                stm.setInt(1, confirmCode);
                stm.setTimestamp(2, dateTime);
                stm.setString(3, email);

                if (stm.executeUpdate() > 0) {
                    return confirmCode;
                }
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public int resetCode(String email) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "UPDATE ConfirmCode "
                        + "SET code = ?, currentDate = ? "
                        + "WHERE email = ?";
                Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());

                stm = con.prepareStatement(sql);
                stm.setInt(1, 0);
                stm.setTimestamp(2, dateTime);
                stm.setString(3, email);

                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public ConfirmCodeDTO getCode(String email) throws NamingException, SQLException {

        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT email, code, currentDate "
                        + "FROM ConfirmCode "
                        + "WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return new ConfirmCodeDTO(rs.getString("email"), rs.getInt("code"), rs.getTimestamp("currentDate"));
                }
            }
        } finally {
            closeDB();
        }

        return null;
    }
}
