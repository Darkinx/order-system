package com.darkin.electronicordersystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    /**
     * @see this query from stackoverflow about hashing and salting
     *  *  here: https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
     *
     */

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
}
