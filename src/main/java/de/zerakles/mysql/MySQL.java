package de.zerakles.mysql;

import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

    private String host;
    private String port;
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public MySQL(String host, String port, String database, String user, String password){
        this.database = database;
        this.port = port;
        this.password = password;
        this.user = user;
        this.host = host;
        connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://"
                        + host + ":" + port + "/" + database
                        + "?characterEncoding=latin1&useConfigs=maxPerformance", user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("MySQL Error");
            }
        }
    }

    private boolean isConnected(){
        if(connection == null){
            return false;
        }
        return true;
    }

    public void update(String qry){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            preparedStatement.execute();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ResultSet getResult(String qry){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            return preparedStatement.executeQuery();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

}
