/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.resources;

import hieubd.utils.DBHelper;
import hieubd.utils.MyConstants;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author CND
 */
public class ResourcesDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    private List<ResourcesDTO> list;

    public List<ResourcesDTO> getList() {
        return list;
    }

    private void closeDB() throws SQLException {
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

    public int getQuantity(int resourceID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT quantity "
                        + "FROM Resources "
                        + "WHERE resourceID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, resourceID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public int updateQuantity(int resourceID, int newQuantity) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE Resources "
                        + "SET quantity = ? "
                        + "WHERE resourceID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, newQuantity);
                stm.setInt(2, resourceID);
                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public void getByName(String name) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {

                String sql = "SELECT resourceID, name, color, quantity, date, categoryID "
                        + "FROM Resources "
                        + "WHERE name LIKE ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");

                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ResourcesDTO(rs.getInt("resourceID"),
                            rs.getInt("quantity"),
                            rs.getInt("categoryID"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getDate("date")));
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getOrderByName(int page, String name, int categoryID, Date date) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {

                String addSql = "";
                if (categoryID != 0 && date != null) {
                    addSql = "AND categoryID = ? AND date = ? ";
                } else if (categoryID != 0) {
                    addSql = "AND categoryID = ? ";
                } else if (date != null) {
                    addSql = "AND date = ? ";
                }

                String sql = "SELECT resourceID, name, color, quantity, date, categoryID "
                        + "FROM Resources "
                        + "WHERE name LIKE ? " + addSql
                        + "ORDER BY name "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                int row = (page - 1) * MyConstants.TOTAL_ITEM_IN_PAGE;
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                if (categoryID != 0 && date != null) {
                    stm.setInt(2, categoryID);
                    stm.setDate(3, date);
                    stm.setInt(4, row);
                    stm.setInt(5, MyConstants.TOTAL_ITEM_IN_PAGE);
                } else if (categoryID != 0) {
                    stm.setInt(2, categoryID);
                    stm.setInt(3, row);
                    stm.setInt(4, MyConstants.TOTAL_ITEM_IN_PAGE);
                } else if (date != null) {
                    stm.setDate(2, date);
                    stm.setInt(3, row);
                    stm.setInt(4, MyConstants.TOTAL_ITEM_IN_PAGE);
                } else {
                    stm.setInt(2, row);
                    stm.setInt(3, MyConstants.TOTAL_ITEM_IN_PAGE);
                }

                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ResourcesDTO(rs.getInt("resourceID"),
                            rs.getInt("quantity"),
                            rs.getInt("categoryID"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getDate("date")));
                }
            }
        } finally {
            closeDB();
        }
    }

    public int getCountOrderByName(String name, int categoryID, Date date) throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if (con != null) {

                String addSql = "";
                if (categoryID != 0 && date != null) {
                    addSql = "AND categoryID = ? AND date = ?";
                } else if (categoryID != 0) {
                    addSql = "AND categoryID = ?";
                } else if (date != null) {
                    addSql = "AND date = ?";
                }
                String sql = "SELECT COUNT(resourceID) "
                        + "FROM Resources "
                        + "WHERE name LIKE ?  " + addSql;
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                if (categoryID != 0 && date != null) {
                    stm.setInt(2, categoryID);
                    stm.setDate(3, date);
                } else if (categoryID != 0) {
                    stm.setInt(2, categoryID);
                } else if (date != null) {
                    stm.setDate(2, date);
                }
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
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
                String sql = "SELECT resourceID, name, color, quantity, date, categoryID "
                        + "FROM Resources ";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ResourcesDTO(rs.getInt("resourceID"),
                            rs.getInt("quantity"),
                            rs.getInt("categoryID"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getDate("date")));
                }
            }
        } finally {
            closeDB();
        }
    }
}
