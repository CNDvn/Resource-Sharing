/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.categories;

import hieubd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
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
public class CategoriesDAO implements Serializable{

    List<CategoriesDTO> list;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public List<CategoriesDTO> getList() {
        return list;
    }

    
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

    public void getAll() throws NamingException, SQLException {
        con = DBHelper.getConnect();
        try {
            if(con != null){
                String sql = "SELECT categoryID, name "
                        + "FROM Categories ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    if(list == null){
                        list = new ArrayList<>();
                    }
                    list.add(new CategoriesDTO(rs.getInt("categoryID"), rs.getString("name")));
                }
            }
        } finally {
            closeDB();
        }
    }
}
