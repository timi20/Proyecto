/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class AccesoBase {

    static BaseDeDatos  bd = new BaseDeDatos();

    public static String[] consultaNombrePresi() {
        String[] nombres = null;
        ResultSet rs;
        PreparedStatement update;
        bd.abrirConexion();
        try {

            update = bd.getConexion().prepareStatement("Select nombre,apellido from persona where categoria='Presidente'");

            rs = update.executeQuery();
            int i = 0;
            rs.last();
            nombres = new String[rs.getRow()];
            rs.beforeFirst();
            while (rs.next()) {
                String nombre = rs.getString(1) + " " + rs.getString(2);
                nombres[i] = nombre;
                i++;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return nombres;
    }

    public static String[] consultaNombreDirector() {
        String[] nombres = null;
        ResultSet rs;
        PreparedStatement update;
        bd.abrirConexion();
        try {

            update = bd.getConexion().prepareStatement("Select nombre,apellido from persona where categoria='Director Comercial'");

            rs = update.executeQuery();
            int i = 0;
            rs.last();
            nombres = new String[rs.getRow()];
            rs.beforeFirst();
            while (rs.next()) {
                String nombre = rs.getString(1) + " " + rs.getString(2);
                nombres[i] = nombre;
                i++;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return nombres;
    }
    
    public static String[] consultaEquipos() {
        String[] nombres = null;
        ResultSet rs;
        PreparedStatement update;
        bd.abrirConexion();
        try {

            update = bd.getConexion().prepareStatement("Select nombre from equipo");

            rs = update.executeQuery();
            int i = 0;
            rs.last();
            nombres = new String[rs.getRow()];
            rs.beforeFirst();
            while (rs.next()) {
                String nombre = rs.getString(1);
                nombres[i] = nombre;
                i++;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return nombres;
    }
    
    public static int obtenerPrecio(String nombre){
        CallableStatement cs;
        ResultSet rs;
        int precio = 0;
        bd.abrirConexion();
        try {
            cs = bd.getConexion().prepareCall("{call buscarPrecio(?)}");
            cs.setString(1, nombre);

            rs = cs.executeQuery();
            while (rs.next()) {
                precio = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return precio;
    }
    
    public static void modificarDirector(String nombre,int id) {
        CallableStatement cs;
        bd.abrirConexion();
        try {
            cs = bd.getConexion().prepareCall("{call modificarDirector(?,?)}");
            cs.setString(1, nombre);
            cs.setInt(2, id);

            cs.execute();
            
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
    }

    public static int obtenerIdPersona(String nombre, String apellido) {
        CallableStatement cs;
        ResultSet rs;
        int id = 0;
        bd.abrirConexion();
        try {
            cs = bd.getConexion().prepareCall("{call buscarId(?,?)}");
            cs.setString(1, nombre);
            cs.setString(2, apellido);

            rs = cs.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return id;
    }
    
    public static int obtenerIdEquipo(String nombre) {
        CallableStatement cs;
        ResultSet rs;
        int id = 0;
        bd.abrirConexion();
        try {
            cs = bd.getConexion().prepareCall("{call buscarIdEquipo(?)}");
            cs.setString(1, nombre);

            rs = cs.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());

        }
        bd.cerrarConexion();
        return id;
    }

    public static String obtenerCategoria(String nombre, String contrasenia) {
        CallableStatement cs;
        ResultSet rs = null;
        String categoria = "";
        bd.abrirConexion();

        try {
            cs = bd.getConexion().prepareCall("{call obtenerCategoria(?,?)}");
            cs.setString(1, nombre);
            cs.setString(2, contrasenia);
            rs = cs.executeQuery();
            while (rs.next()) {
                categoria = rs.getString(1);
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        bd.cerrarConexion();

        return categoria;
    }

    public static void crearPersona(Persona p) {
        CallableStatement cs;
        bd.abrirConexion();
        try {

            cs = bd.getConexion().prepareCall("{call insertarPersona(?,?,?,?)}");
            cs.setString(1, p.getNombre());
            cs.setString(2, p.getApellido());
            cs.setString(3, p.getCategoria());
            cs.setString(4, p.getContrasenia());

            cs.execute();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        bd.cerrarConexion();
    }

    public static void crearEquipo(Equipo e) {
        CallableStatement cs;
        bd.abrirConexion();
        try {

            cs = bd.getConexion().prepareCall("{call insertarEquipo(?,?,?,?,?)}");
            cs.setString(1, e.getNombre());
            cs.setString(2, e.getTelefono());
            cs.setInt(3, e.getPresidente());
            cs.setInt(4, e.getDirectorMarketing());
            cs.setInt(5, e.getPrecio());

            cs.execute();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        bd.cerrarConexion();
    }
    
    public static void eliminarEquipo(String nombre) {
        CallableStatement cs;
        bd.abrirConexion();
        try {

            cs = bd.getConexion().prepareCall("{call eliminarEquipo(?)}");
            cs.setString(1, nombre);

            cs.execute();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        bd.cerrarConexion();
    }
    
    public static void crearEVenta(Venta v) {
        CallableStatement cs;
        bd.abrirConexion();
        try {

            cs = bd.getConexion().prepareCall("{call crearVenta(?,?)}");
            cs.setInt(1, v.getId_equipo());
            cs.setDate(2, v.getFecha());

            cs.execute();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        bd.cerrarConexion();
    }
}
