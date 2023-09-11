/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import universidad.accesoADatos.AlumnoData;
import universidad.accesoADatos.Conexion;
import universidad.accesoADatos.materiaData;
import universidad.entidades.Alumno;
import universidad.entidades.Materia;

/**
 *
 * @author Pablo
 */
public class Universidad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Alumno maria = new Alumno(3684694, "Barros", "maria", LocalDate.of(1998, Month.APRIL, 21), true);
        Materia mat = new Materia("Fisica 1", 2021, true);

        AlumnoData ad = new AlumnoData();
        materiaData matD = new materiaData();

//        ad.guardarAlumno(maria);
//        ad.modificarAlumno(juan);
//        ad.eliminarAlumno(2);     
//        for (Alumno alumno : ad.buscarAlumnoPorId(1)) {
//            System.out.println(alumno.getApellido());
//        }
//        for (Alumno lista : ad.buscarAlumnoPorDNI(3049298)) {
//            System.out.println(lista.getApellido());
//        }

//         matD.guardarMateria(mat);
        System.out.println(matD.buscarMateria(1));
        
        
    }

}
