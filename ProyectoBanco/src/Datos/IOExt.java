
//HOLA ESTOY PROBANDO

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.IOException;
import listas.Lista;
import listas.NoDato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import listas.Cliente;
import listas.Inversion;


/**
 *
 * @author Mazhuka
 */
public class IOExt extends IOContexto{
    private Connection conn;
    private final String driver="com.mysql.jdbc.Driver";
    
    public IOExt(String nombre,String dbName,String user, String pass) throws Exception{
        super(nombre);
        String path="jdbc:mysql://localhost/";
        conectar(path+dbName,user,pass);
        //INSERTAR CODIGO AQUI
        UsarDB(nombre);
        crearTabla("Estadisticas",""
                + " nro_con INT NOT NULL AUTO_INCREMENT,"
                + " Esperanza INT,"
                + " Varianza DOUBLE,"
                + " Mediana INT,"
                + " Moda INT,"
                + " Des_stand DOUBLE,"
                + " Coef_var DOUBLE,"
                + " Media_T DOUBLE,"
                + " Tipo_f varchar(3) NOT NULL,"
                + " Fecha_i varchar(10) NOT NULL,"
                + " fecha_f varchar(10) NOT NULL,"
                + " Primary KEY (nro_con)");
        
        crearTabla("TablaFrec",""
                + "nro_con INT NOT NULL,"
                + "nro_class INT,"
                + "L_inf INT,"
                + "L_sup INT,"
                + "Marca INT,"
                + "Obs INT,"
                + "frec Decimal(6,5),"
                + "prctj Decimal(6,4),"
                + "Obs_a INT,"
                + "frec_a Decimal(6,5),"
                + "prctj_a Decimal(6,5), "
                + "Primary KEY (nro_con)");
    }
    
    private void conectar(String url,String user, String pass ) throws Exception{
        try{
            Class.forName(driver).newInstance();
            conn=DriverManager.getConnection(url,user,pass);
            if(!conn.isClosed())
                System.out.println("Conexión realizada");
        }catch(SQLException e){
                System.out.println("Error en la Conexión");
    }
    }

    private void mostrarPropiedades() {
        java.sql.DatabaseMetaData dm = null;
        ResultSet result = null;
        try {
            if (conn != null) {
                dm = conn.getMetaData();
                
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: " + dm.getDriverName());
                System.out
                        .println("\tDriver Version: " + dm.getDriverVersion());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());
                System.out.println("\tNombre DB: "+dm.getDatabaseProductName());
                Statement select = conn.createStatement();
                result = select.executeQuery("SELECT * FROM Clientes");

                while (result.next()) {
                    System.out.println("id: " + result.getString(1) + "\n");
                    System.out.println("Nombre: " + result.getString(2) + "\n");
                    
                }
                result.close();
                result = null;
            } else {
                System.out.println("Error: No active Connection");
            }
        } catch (SQLException e) {
            System.out.println("Error dentro del proceso de Propiedades");
        }
        dm = null;
    }

    public void cerrar() {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
        }
    }
    
    private void UsarDB(String nombre){
        try{
        Statement st=conn.createStatement();
        st.executeUpdate("USE "+nombre);
        }catch(Exception e){
        Statement st;  
            try {
                st = conn.createStatement();
                st.executeUpdate("CREATE DATABASE "+nombre+";");
            } catch (SQLException ex) {}
        }
    }
    
    private void crearTabla(String nombre,String Att){
        try{
        Statement st=conn.createStatement();
        st.executeUpdate("CREATE TABLE "+nombre+" ("+Att+");");
        }catch(Exception e){} 
    }
    

    
    @Override
    public Lista Lectura() throws IOException, NoDato, NumberFormatException {
        ResultSet result;
        try {
            Statement select = conn.createStatement();
            result = select.executeQuery("SELECT * FROM inversiones INNER JOIN clientes ON inversiones.cod_cli = clientes.cod_cli");
            while (result.next()) {
                List<String> datosfila=migrarDato(result);
                agregarDato(datosfila);
            }
            result.close();
        }catch(Exception e){}
        
        return inversiones;
     }
    
    private List<String> migrarDato(ResultSet r){
        List<String> datosfila=new ArrayList();
        int i=1;
        try{
            while (r.getString(i) != null) {
                datosfila.add(r.getString(i));
                i++;
            }
        }catch(Exception e){
            return datosfila;
        }
        return datosfila;
    }
    
    private void agregarDato(List<String> df) {
        Cliente c = new Cliente(df.get(9), df.get(10));
        if (!clientes.contains(c)) {
            clientes.add(c);
        }
        inversiones.add(new Inversion(clientes.indexOf(c), (int) Double.parseDouble(df.get(0)), df.get(2), (int) Double.parseDouble(df.get(3)), Double.parseDouble(df.get(4)), df.get(6), df.get(7), df.get(8)));
    }
     
    private void insertarDato(String table, String Atts, String dats) {
    try {
        Statement insertar = conn.createStatement();
        insertar.executeUpdate("INSERT INTO " + table + " (" + Atts + ") VALUES(" + dats + ")");
    } catch (Exception e) {
        //System.out.println("error Insertar: TABLA: "+table+", objeto: "+dats);
    }

    }
    
    @Override
    public void Escritura(String[][] p, String tipo) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Escritura(String[][] q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
