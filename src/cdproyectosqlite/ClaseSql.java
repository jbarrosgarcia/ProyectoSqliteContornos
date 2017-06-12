/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdproyectosqlite;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jose Barros
 */
public class ClaseSql {

    Connection cn = null;
    String url = "";
    Statement consulta;
    boolean conn;
    int total=0;

    public ClaseSql() {
        url = "base.db";
    }

    public ClaseSql(String url) {
        this.url = url;
    }

    public boolean conectar() {
        try {
            cn = DriverManager.getConnection("jdbc:sqlite:baseproductos.db");
            consulta = cn.createStatement();
            if (cn != null) {
                conn = true;
            }
        } catch (SQLException ex) {
            //System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
            conn = false;
        }
        return conn;
    }

    /**
     * Este metodo realiza insertar sencillas en una tabla Utilizo String...
     * para que se pueden realizar consultas con diferente numero de parametros
     * (campos)
     *
     * @param tabla El nombre de la tabla en la que se realiza la busqueda
     * @param campo Lista de los campos a buscar, separados por comas
     * @return
     */
    public int consultar(String tabla, String... campo) {
        try {
            String consultar = "select ";
            for (int i = 1; i <= campo.length; i++) {
                if (i == (campo.length - 1)) {
                    consultar = consultar + campo[i] + " from " + tabla;
                } else {
                    consultar = consultar + campo[i] + ", ";
                }
                total=total+i;
            }
            consulta = cn.createStatement();
            ResultSet rs = consulta.executeQuery(consultar);
            /*
             Aqui tenemos el problema de que no todas las tablas estan formadas por 
             strings. En este caso, si pillamos un int y puede hacer la conversion, la realiza
             correctamente, pero si no, pondria un 0. A la hora de crear la tabla ocurre
             lo mismo, le pasamos un numero como string a un campo declarado como integer
             y realiza la conversion al añadir el dato
             */
            while (rs.next()) {
                System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getInt(3));
            }
           
        } catch (SQLException e) {
            
        }
        return total;
    }

    public int inserta(String tabla, String... values) {
        try {
            String insertar = "insert into " + tabla + " values(";
            for (int i = 1; i <= values.length; i++) {
                if (i == (values.length - 1)) {
                    insertar = insertar + "'" + values[i] + "')";
                } else {
                    insertar = insertar + "'" + values[i] + "', ";
                }
                 total=total+i;
            }
            Statement st = cn.createStatement();
            st.executeUpdate(insertar);
        } catch (SQLException e) {
            
        }
        return total;
    }

    public void borrar(String id) {
        try {
            consulta= cn.createStatement();
            consulta.execute("DELETE from agenda where id ='" + id + "'");
            total = consulta.getUpdateCount();
            System.out.println("Registros borrados " + total);
        } catch (SQLException ex) {
            System.err.println("Error al borrar " + ex.getMessage());
}

    }

   public void modificar(String nombre, String apellido, String telefono, String id) {

        try {
            PreparedStatement modificacion = cn.prepareStatement("update agenda set nombre ='" + nombre + "',apellidos ='" + apellido + "', telefono ='" + telefono + "' where id=" + id);
            modificacion.execute();

            total = modificacion.getUpdateCount();
            System.out.println("Datos actualizados " + total);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar " + ex.getMessage());
        }

    }
}
