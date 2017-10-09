
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
        crearDB(nombre);
        crearTabla("Inversiones","id_inv INT NOT NULL, mon_inv INT, tasa_int DOUBLE, mon_rec INT, Primary KEY (id_inv)");
        crearTabla("Clientes","cod_cli INT NOT NULL, nom_cli TEXT, Primary KEY (cod_cli)");
        crearTabla("Actividad","id_inv INT NOT NULL, cod_cli INT NOT NULL, fec_inv DATE, fec_fin DATE, fec_cier DATE, Primary KEY (id_inv)");
        
        insertarDato("Inversiones","id_inv, mon_inv, tasa_int, mon_rec","'2015','555555','4.4','999999'");
        insertarDato("Clientes","cod_cli, nom_cli","123, 'juanito'");
        
        mostrarPropiedades();
        //
        cerrarConexion();
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
        java.sql.ResultSet result = null;
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

    private void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
        }
    }
    
    private void crearDB(String nombre){
        try{
        Statement st=conn.createStatement();
            try{
                st.executeUpdate("DROP DATABASE "+nombre);
            }catch(SQLException e){
                //System.out.println("La BD no Existe");
            }
            st.executeUpdate("CREATE DATABASE "+ nombre);
            st.executeUpdate("USE "+nombre);
            //st.executeUpdate("CREATE TABLE tabla (id INT NOT NULL AUTO_INCREMENT, nombre Text, Primary KEY (id));");
        }catch(Exception e){
            System.out.println("ERROR en Crear");}
        
    }
    
    private void crearTabla(String nombre,String Att){
        try{
        Statement st=conn.createStatement();
            try{
                st.executeUpdate("DROP TABLE "+nombre);
            }catch(SQLException e){
                //System.out.println("La BD no Existe");
            }
            st.executeUpdate("CREATE TABLE "+nombre+" ("+Att+");");
        }catch(Exception e){
            System.out.println("ERROR en Crear");}
        
    }
    
    private void insertarDato(String table, String Atts,String dats){
        try{
            Statement insertar=conn.createStatement();
            insertar.executeUpdate("INSERT INTO "+table+" ("+Atts+") VALUES("+dats+")");
        }catch(Exception e){
            System.out.println("error Insertar: TABLA: "+table+", objeto: "+dats);}
       
    }
    
    @Override
    public Lista Lectura() throws IOException, NoDato, NumberFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
