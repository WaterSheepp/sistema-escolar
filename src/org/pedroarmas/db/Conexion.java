package org.pedroarmas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    private String driver;
    private String url;
    private String user;
    private String pass;
    private String dbname;
    
    
    public Conexion() {
        String dbname = "DBKinal2019";
        String url = "jdbc:mysql://localhost:3306/" + dbname + "?useSSL=false&zeroDateTimeBehavior=convertToNull";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String pass = "admin";
        
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(url, user, pass);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            e.getMessage();
            
        }catch(InstantiationException e){
            e.printStackTrace();
            e.getMessage();
            
        }catch(IllegalAccessException e){
            e.printStackTrace();
            e.getMessage();
            
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    
    public static Conexion getInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
    
}
