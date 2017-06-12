/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdproyectosqlite;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Jose Barros
 */
public class CDProyectoSqlite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ClaseSql miDB = new ClaseSql();
        miDB.conectar();
        try {
            Statement st = miDB.cn.createStatement();
            st.executeUpdate("drop table prueba");
            st.executeUpdate("create table prueba(nombre string, apellido varchar, id integer)");
            miDB.inserta("prueba", "Paco", "Garcia", "71dhfgkjdgfgdf");
        } catch (SQLException ex) {
            Logger.getLogger(ClaseSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        miDB.consultar("prueba", "nombre", "apellido", "id");
    }
}
