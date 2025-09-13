/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DBUtils;

public class DAO {

    private static final String LOGIN = "SELECT * FROM tblUsers WHERE userID = ? AND [password] = ?";
    private static final String SEARCH = "SELECT [id], [name], [description], [price], [size], [status] FROM tblProduct WHERE [description] LIKE ?";
    private static final String DELETE = "UPDATE tblProduct SET [status] = 0 WHERE id = ?";

    public UserDTO login(String username, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, username);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    user = new UserDTO(username, name, roleID, password);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public List<ProductDTO> getListProduct(String search) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String size = rs.getString("size");
                    double price = Double.parseDouble(rs.getString("price"));
                    boolean status = rs.getBoolean("status");
                    
                    if(status) {
                        list.add(new ProductDTO(id, name, description, price, size, status));
                    }                  
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }


    public boolean delete(String id) throws SQLException {
       boolean check = false;
       Connection conn = null;
       PreparedStatement ptm = null;
       try {
            conn = DBUtils.getConnection();
            if(conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, id);
                check = ptm.executeUpdate() > 0;
            }
       } catch(Exception e) {
           e.getStackTrace();
       } finally {
           if(ptm != null) ptm.close();
           if(conn != null) conn.close();
       }
       return false;
    }

}
