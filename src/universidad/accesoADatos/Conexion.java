/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidad.accesoADatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class Conexion {
 
     private static String url = "jdbc:mariadb://localhost:3307/universidad";   
        private static String usuario = "root";
        private static String password = "";
        private static Connection connection;
    
        private Conexion() throws ClassNotFoundException {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        
        private Conexion(String url, String usuario, String password) throws ClassNotFoundException {
            this.url = url;
            this.usuario = usuario;
            this.password = password;
            Class.forName("org.mariadb.jdbc.Driver");
        }
        
          public static Connection getConexion() throws SQLException, ClassNotFoundException {
            if (connection == null) {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(url, usuario, password);
            }
            return connection;
        }
}
