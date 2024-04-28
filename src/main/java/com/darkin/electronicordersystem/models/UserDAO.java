package com.darkin.electronicordersystem.models;

import com.darkin.electronicordersystem.DatabaseConnection;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class UserDAO {
    private DatabaseConnection dbUtil = new DatabaseConnection();

    //TODO: Separate any interface to the Database
    public boolean validateLogin(String username, String password){
        String stmt = "SELECT count(1) FROM account WHERE username='" + username + "' AND password='" + password + "'";

        try{
            CachedRowSet loginRs = dbUtil.selectQuery(stmt);
            while(loginRs.next()){
                if(loginRs.getInt(1) == 1){
                    return true;
                }
            }
        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
        return false;
    }
    public void registerUser(User user) throws SQLException, ClassNotFoundException{
        String stmt = "INSERT INTO account (username, password, fname, lname, address, email) VALUES ";

        stmt += String.format("('%s','%s','%s','%s','%s','%s')", user.getUsername(), user.getPassword(), user.getFname(), user.getLname(), user.getAddress(), user.getEmail());
        try {
            dbUtil.executeUpdate(stmt);
        }catch (SQLException e){
            System.err.println("Error Occurred while inserting: " + e);
            throw e;
        }
    }

    public User getUserInfo(String username, String password) throws SQLException, ClassNotFoundException{
        User user = null;
        try{
            String stmt = "SELECT * FROM account WHERE username='" + username + "' AND password='" + password + "'";

            CachedRowSet userRs = dbUtil.selectQuery(stmt);
            while(userRs.next()){
                user = new User();
                user.setId(userRs.getInt("id"));
                user.setUsername(userRs.getString("username"));
                user.setPassword(userRs.getString("password"));
                user.setFname(userRs.getString("fname"));
                user.setLname(userRs.getString("lname"));
                user.setAddress(userRs.getString("address"));
                user.setEmail(userRs.getString("email"));
            }

        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
        return user;

    }



}
