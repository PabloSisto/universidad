/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidad.accesoADatos;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.runtime.ListAdapter;
import universidad.entidades.Alumno;

/**
 *
 * @author Pablo
 */
public class AlumnoData {

    private void mensaje(String mensaje) {
        JOptionPane.showConfirmDialog(null, mensaje);
    }

    private Connection connection;

    public AlumnoData() {
        try {
            connection = Conexion.getConexion();
        } catch (SQLException e) {
            mensaje("Error de conexion");
        } catch (ClassNotFoundException ex) {
            mensaje("Error al cargar los drivers");
        }
    }

    public void guardarAlumno(Alumno alumno) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setBoolean(5, alumno.isEstado());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt(1));
                mensaje("El alumno " + alumno.getApellido() + " se guardo correctamente ");
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            mensaje("Error a crear alumno ");
        }

    }

    public void modificarAlumno(Alumno alumno) {
        //Para modificar crea un Alumno nuevo y le pasa el idAlumno a modificar
        //luego en el metodo le paso el nombre del alumno nuevo
        String sql = "UPDATE alumno SET dni = ?, apellido = ?,"
                + "nombre = ?, fechaNacimiento = ?  WHERE idAlumno = ? ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setInt(5, alumno.getIdAlumno());
            int exito = ps.executeUpdate();

            if (exito == 1) {
                mensaje("El alumno se actualizo correctamente");
            }
        } catch (SQLException e) {
            mensaje("Error al modificar alumno");
        }
    }

    public ArrayList<Alumno> listarAlumnos() {

        ArrayList<Alumno> lista = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT idAlumno, dni, apellido, nombre, fechaNacimiento, estado FROM alumno")) {
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    int idAlumno = rs.getInt("idAlumno");
                    int dni = rs.getInt("dni");
                    String apellido = rs.getString("apellido");
                    String nombre = rs.getString("nombre");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();
                    boolean estado = rs.getBoolean("estado");

                }
            } catch (SQLException e) {
                mensaje("Error en obtener lista de alumnos " + e.getMessage());
            }

        } catch (SQLException e) {
            mensaje("Error en obtener lista de alumnos " + e.getMessage());
        }
        return lista;
    }

    public List<Alumno> buscarAlumnoPorId(int id) {
        //En el campo sql despues del SELECT van los campos que quiero traer
        // despues colocarlos en alum en el while

        List<Alumno> alu = new ArrayList<>();

        String sql = "SELECT apellido FROM alumno WHERE idAlumno = " + id;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                Alumno alum = new Alumno(rs.getString("apellido"));
                alu.add(alum);
            }

        } catch (SQLException e) {
            mensaje("No se pudo cargar" + e.getMessage());
        }
        return alu;
    }

    public List<Alumno> buscarAlumnoPorDNI(int dni) {
        //En el campo sql despues del SELECT van los campos que quiero traer
        // despues colocarlos en alum en el while

        List<Alumno> alu = new ArrayList<>();

        String sql = "SELECT apellido FROM alumno WHERE dni = " + dni;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                Alumno alum = new Alumno(rs.getString("apellido"));
                alu.add(alum);
            }

        } catch (SQLException e) {
            mensaje("No se pudo cargar" + e.getMessage());
        }
        return alu;
    }

    public void eliminarAlumno(int id) {

        int valor = 0;

//        String id = "SELECT * FROM alumno WHERE idAlumno LIKE '" + id + "'";
        String sql = "UPDATE alumno SET  estado = false WHERE idAlumno = ? ";

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM alumno WHERE idAlumno LIKE '" + id + "'"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                valor = rs.getInt("idAlumno");
                try (PreparedStatement pt = connection.prepareStatement(sql)) {
                    pt.setInt(1, valor);
                    pt.executeUpdate();

                } catch (Exception e) {
                    printStackTrace();
                }
            }
        } catch (Exception e) {
            printStackTrace();
        }

    }

}
