package com.darkin.electronicordersystem.models;

import com.darkin.electronicordersystem.DatabaseConnection;
import com.darkin.electronicordersystem.utility.HashPassword;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO {
    private DatabaseConnection dbUtil = new DatabaseConnection();
    private final String TABLE = "account";


    public boolean validateLogin(String username, String password){
        String hashPassword = null, salt = null;
                String stmt = "SELECT `password`, `salt` FROM "+ TABLE +" WHERE username=?";
                String[]  userString = {username};
        try{
            CachedRowSet loginRs = dbUtil.selectQuery(stmt, userString);
            while(loginRs.next()){
                hashPassword = loginRs.getString("password");
                salt = loginRs.getString("salt");
            }
            Optional<String> processPassword = HashPassword.hashPassword(password, salt);
            if(processPassword.isPresent()){
                if (hashPassword.equals(processPassword.get())){
                    return true;
                }else {
                    return false;
                }
            }
            else
                return false;
        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
        return false;
    }
    public boolean validateRegister(String username, String email){
        String stmt = "SELECT count(1) FROM "+ TABLE +" WHERE username=? OR email=?";
        String[]  userString = {username, email};
        try{
            CachedRowSet registerRs = dbUtil.selectQuery(stmt, userString);
            while(registerRs.next()){
                int result = registerRs.getInt(1);
                System.out.println("Register result: " + result);
                if(result == 1){
                    System.out.println("Where here");
                    return true;
                }
            }
        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
        System.out.println("So its out");
        return false;
    }
    public void registerUser(User user) throws SQLException, ClassNotFoundException{
        String stmt = "INSERT INTO " + TABLE + " (username, password, fname, lname, address, email, salt) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?);";
        String salt = HashPassword.generateSalt();
        Optional<String> hashPassword = HashPassword.hashPassword(user.getPassword(), salt);
        user.setPassword(null);

        if(hashPassword.isPresent()){
            String[] userString = {user.getUsername(), hashPassword.get(), user.getFname(), user.getLname(), user.getAddress(), user.getEmail(), salt};
            //        stmt += String.format("('%s','%s','%s','%s','%s','%s')", user.getUsername(), user.getPassword(), user.getFname(), user.getLname(), user.getAddress(), user.getEmail());
            try {
                dbUtil.executeUpdate(stmt, userString);
            }catch (SQLException e){
                System.err.println("Error Occurred while inserting: " + e);
                throw e;
            }
        }
    }

    public User getUserInfo(String username) throws SQLException, ClassNotFoundException    {
        User user = null;
        String stmt = "SELECT * FROM "+ TABLE +" WHERE username=?";
        String[]  userString = {username};
        try{
            CachedRowSet userRs = dbUtil.selectQuery(stmt, userString);

            while(userRs.next()){
                user = new User();
                user.setId(userRs.getInt("id"));
                user.setUsername(userRs.getString("username"));
                user.setPassword(null);
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

    //TODO Update to have a way to update it based on what just changed
    public void updateUserInfo(User user) throws SQLException, ClassNotFoundException{
        String updateStmt = String.format("`username`='%s', `fname`='%s', `lname`='%s', `password`='%s', `address`='%s', `email`='%s'", user.getUsername(), user.getFname(), user.getLname(), user.getPassword(), user.getAddress(), user.getEmail());

        String stmt = "UPDATE `" + TABLE + "` SET " + updateStmt + "WHERE `id`=" + user.getId() +";";
        System.out.println("FIXME: on UpdateUSERINFO");
//        try {
//            dbUtil.executeUpdate(stmt);
//        }catch (SQLException e){
//            System.err.println("Error Occurred while updating new info: " + e);
//            throw e;
//        }
    }
}
