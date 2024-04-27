package com.darkin.electronicordersystem;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static Connection databaseLink;

    /**
     * @see this query from stackoverflow about hashing and salting
     *  *  here: https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
     *
     */

    //TODO: Have a secure hashing of password and username
    //TODO: Way to create a database, tables, and initial setup of the connection by its own.
    //      - Used when the system is first installed

    public Connection getConnection(){//trying to connect to the database
        String databaseName = "test";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            System.err.println("Error: " + e);
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }

    private static void dbDisconnect() throws SQLException {
        try {
            if (databaseLink != null && !databaseLink.isClosed()) {
                databaseLink.close();
            }
        } catch (Exception e){
            throw e;
        }
    }

    public CachedRowSet selectQuery(String stmtQuery) throws SQLException{
        CachedRowSet crs = null;
        ResultSet rs = null;
        try {
            databaseLink = getConnection();
            Statement stmt = databaseLink.createStatement();
            rs = stmt.executeQuery(stmtQuery);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);


        }catch (SQLException e){
            System.err.println("Problem executing: "+ e);
            throw e;
        }finally {
            if (rs != null) {
                //Close resultSet
                rs.close();
            }

            dbDisconnect();
        }
        return crs;
    }

    public void executeUpdate(String stmtUpdate) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        try{
            databaseLink = getConnection();
            stmt = databaseLink.createStatement();
            stmt.executeUpdate(stmtUpdate);

        }catch (SQLException e){
            System.err.println("Problem occurred: " + e);
            throw e;
        }finally {
            if(stmtUpdate != null ){
                stmt.close();
            }
            dbDisconnect();
        }
    }
}
