/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.requests;

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
public class RequestsDAO implements Serializable {

    List<RequestsDTO> list;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public List<RequestsDTO> getList() {
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

    public int addRequest(String email, int resourceID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "INSERT INTO Requests(email, resourceID, date, statusID) VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setInt(2, resourceID);
                Date date = Date.valueOf(LocalDate.now());
                stm.setDate(3, date);
                stm.setInt(4, MyConstants.STATUS_NEW);
                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }

    public void getOrderByDate(int page, String name, Date date, String UserEmail) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String addSql = "";
                if (date != null) {
                    addSql = " AND Requests.date = ? ";
                }
                String sql = "SELECT Requests.requestID, email, Requests.resourceID, Requests.date, statusID "
                        + " FROM Requests, Resources "
                        + "WHERE Requests.resourceID = Resources.resourceID "
                        + "AND Resources.name LIKE ? "
                        + "AND email = ? "
                        + "AND (Requests.statusID = ? OR Requests.statusID = ?) " + addSql
                        + " ORDER BY Requests.date DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                int row = (page - 1) * MyConstants.TOTAL_ITEM_IN_PAGE;
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                stm.setString(2, UserEmail);
                stm.setInt(3, MyConstants.STATUS_NEW);
                stm.setInt(4, MyConstants.STATUS_ACCEPT);
                if (date != null) {
                    stm.setDate(5, date);
                    stm.setInt(6, row);
                    stm.setInt(7, MyConstants.TOTAL_ITEM_IN_PAGE);
                } else {
                    stm.setInt(5, row);
                    stm.setInt(6, MyConstants.TOTAL_ITEM_IN_PAGE);
                }

                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new RequestsDTO(rs.getInt("requestID"),
                            rs.getInt("resourceID"),
                            rs.getInt("statusID"),
                            rs.getString("email"),
                            rs.getDate("date")));
                }
            }
        } finally {
            closeDB();
        }
    }

    public int getCount(int page, String name, Date date, String UserEmail) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String addSql = "";
                if (date != null) {
                    addSql = " AND Requests.date = ? ";
                }
                String sql = "SELECT COUNT(Requests.requestID) "
                        + " FROM Requests, Resources "
                        + "WHERE Requests.resourceID = Resources.resourceID "
                        + "AND Resources.name LIKE ? "
                        + "AND email = ? "
                        + "AND (Requests.statusID = ? OR Requests.statusID = ?) " + addSql;
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                stm.setString(2, UserEmail);
                stm.setInt(3, MyConstants.STATUS_NEW);
                stm.setInt(4, MyConstants.STATUS_ACCEPT);
                if (date != null) {
                    stm.setDate(5, date);
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

    public void getByContent(int page, String content) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT Requests.requestID, Requests.email, Requests.resourceID, Requests.date, Requests.statusID "
                        + " FROM Requests, Resources, Users "
                        + "WHERE Requests.resourceID = Resources.resourceID "
                        + "AND (Requests.statusID = ? OR Requests.statusID = ?) "
                        + "AND Requests.email = Users.email "
                        + "AND (Resources.name LIKE ? OR Requests.date LIKE ? OR Users.name LIKE ?)"
                        + " ORDER BY Requests.requestID DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                int row = (page - 1) * MyConstants.TOTAL_ITEM_IN_PAGE;
                stm = con.prepareStatement(sql);
                stm.setInt(1, MyConstants.STATUS_NEW);
                stm.setInt(2, MyConstants.STATUS_ACCEPT);
                stm.setString(3, "%" + content + "%");
                stm.setString(4, "%" + content + "%");
                stm.setString(5, "%" + content + "%");
                stm.setInt(6, row);
                stm.setInt(7, MyConstants.TOTAL_ITEM_IN_PAGE);

                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new RequestsDTO(rs.getInt("requestID"),
                            rs.getInt("resourceID"),
                            rs.getInt("statusID"),
                            rs.getString("email"),
                            rs.getDate("date")));
                }
            }
        } finally {
            closeDB();
        }
    }

    public int getCountByContent(int page, String content) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(Requests.requestID) "
                        + " FROM Requests, Resources, Users "
                        + "WHERE Requests.resourceID = Resources.resourceID "
                        + "AND (Requests.statusID = ? OR Requests.statusID = ?) "
                        + "AND Requests.email = Users.email "
                        + "AND (Resources.name LIKE ? OR Requests.date LIKE ? OR Users.name LIKE ?)";

                int row = (page - 1) * MyConstants.TOTAL_ITEM_IN_PAGE;
                stm = con.prepareStatement(sql);
                stm.setInt(1, MyConstants.STATUS_NEW);
                stm.setInt(2, MyConstants.STATUS_ACCEPT);
                stm.setString(3, "%" + content + "%");
                stm.setString(4, "%" + content + "%");
                stm.setString(5, "%" + content + "%");

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

    public int updateStatus(int requesID, Integer oldStatus, int newStatus) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {

                String addSql = "";
                if (oldStatus != null) {
                    addSql = " AND statusID = ? ";
                }

                String sql = "UPDATE Requests "
                        + "SET statusID = ? "
                        + "WHERE requestID = ? " + addSql;
                stm = con.prepareStatement(sql);
                stm.setInt(1, newStatus);
                stm.setInt(2, requesID);
                if (oldStatus != null) {
                    stm.setInt(3, oldStatus);
                }
                return stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
        return 0;
    }
}
