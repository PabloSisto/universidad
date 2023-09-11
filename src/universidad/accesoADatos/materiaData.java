/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidad.accesoADatos;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidad.entidades.Materia;

/**
 *
 * @author Pablo
 */
public class materiaData {

    private void mensaje(String mensaje) {
        JOptionPane.showConfirmDialog(null, mensaje);
    }

    private Connection connection;

    public materiaData() {
        try {
            connection = Conexion.getConexion();
        } catch (SQLException ex) {
            mensaje("Error de conexion");
        } catch (ClassNotFoundException ex) {
            mensaje("Error en cargar los drivers");
        }
    }

    public void guardarMateria(Materia materia) {

        String sql = "INSERT INTO materia(nombre, anio, estado) VALUES (?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setBoolean(3, materia.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                materia.setIdMateria(1);
                mensaje("La materia" + materia.getNombre() + "se cargo exitosamente");
            }

        } catch (SQLException e) {
            mensaje("La materia no se pudo cargar ");
        }
    }

    public Materia buscarMateria1(int id) {

        Materia materia = null;

        String sql = "SELECT nombre, anio, estado FROM materia WHERE idMateria = ? AND estado = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ps.setInt(1, id);

            if (rs.next()) {
                materia = new Materia();
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            mensaje("Error al acceder a la tabla Materia " + e.getMessage());
        }
        return materia;
    }

    public Materia buscarMateria(int id) {
        Materia materia = null;
        String sql = "SELECT idMateria, nombre, anio, estado FROM materia WHERE idMateria =? AND estado = 1";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(rs.getBoolean("estado"));

            } else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");

            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + ex.getMessage());
        }

        return materia;
    }
    
    public void modificarMateria(Materia materia) {
        
        String sql = "UPDATE materia SET idMateria=?,nombre= ?, anio = ?, estado = ? WHERE 1";
        
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, materia.getIdMateria());
            ps.setString(2, materia.getNombre());
            ps.setInt(3, materia.getAnio());
            ps.setBoolean(4, materia.isEstado());
            
            int exito = ps.executeUpdate();
            
            if (exito ==1) {
                mensaje("La materia se modifico exitosamente");
            }else{
                mensaje("La materia no existe");
            }
            
        } catch (SQLException e) {
            mensaje("No se pudo modificar la materia " + e.getMessage());
        }
    }
    
    String sqlString;
    
    

}
