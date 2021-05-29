/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.users;

import hieubd.utils.DBHelper;
import hieubd.utils.MyConstants;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author CND
 */
public class UsersDAO implements Serializable {

    List<UsersDTO> list;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public List<UsersDTO> getList() {
        return list;
    }

    private void closeDB() throws NamingException, SQLException {
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

    public UsersDTO checkLogin(String userID, String password) throws NamingException, SQLException {

        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT  email, password, name, phone, address, createDate, roleID, statusID "
                        + "FROM Users "
                        + "WHERE email = ? AND password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return new UsersDTO(rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDate("createDate"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"));
                }
            }
        } finally {
            closeDB();
        }

        return null;
    }

    public UsersDTO checkLoginGoogle(String email) throws NamingException, SQLException {

        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT  email, password, name, phone, address, createDate, roleID, statusID "
                        + "FROM Users "
                        + "WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return new UsersDTO(rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDate("createDate"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"));
                }

            }
        } finally {
            closeDB();
        }
        return null;
    }

    public int addNewUser(String email, String password, String name, String phone, String address) throws NamingException, SQLException {

        con = DBHelper.getConnect();
        try {
            if (con != null) {
                Date date = Date.valueOf(LocalDate.now());
                String sql = "INSERT INTO Users(email, password, name, phone, address, createDate, roleID, statusID) "
                        + "VALUES(?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, name);
                stm.setString(4, phone);
                stm.setString(5, address);
                stm.setDate(6, date);
                stm.setInt(7, MyConstants.ROLE_EMPLOYEE);
                stm.setInt(8, MyConstants.STATUS_NEW);

                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public int updatetatusActive(String email) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "UPDATE Users "
                        + "SET statusID = ? "
                        + "WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, MyConstants.STATUS_ACTIVE);
                stm.setString(2, email);
                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public void getAll() throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT email, password, name, phone, address, createDate, roleID, statusID "
                        + "FROM Users";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new UsersDTO(rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDate("createDate"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID")));
                }
            }
        } finally {
            closeDB();
        }
    }
}
