
//HOLA ESTOY PROBANDO

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.IOException;
import listas.NoDato;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author BSOD
 */
public final class IOExt extends IOContexto{
    private Connection conn;
    private final String driver="com.mysql.jdbc.Driver";
    private final String Est="Estadisticas";
    private final String TabF="TablaFrec";
    private int nro_con;
    
    // CONSTRUCTOR
    public IOExt(String nombre,String dbName,String user, String pass) throws Exception{
        super(nombre);
        String path="jdbc:mysql://localhost/";
        conectar(path+dbName,user,pass);
        //INSERTAR CODIGO AQUI
        UsarDB(nombre);
        crearTabla(Est,""
                + " nro_con INT NOT NULL AUTO_INCREMENT,"
                + " Esperanza INT,"
                + " Varianza DOUBLE,"
                + " Mediana INT,"
                + " Moda INT,"
                + " Des_stand DOUBLE,"
                + " Coef_var DOUBLE,"
                + " Media_T DOUBLE,"
                + " Tipo_f varchar(3) NOT NULL,"
                + " Fecha_i varchar(11) NOT NULL,"
                + " fecha_f varchar(11) NOT NULL,"
                + " Primary KEY (nro_con)");
        
        crearTabla(TabF,""
                + "nro_con INT NOT NULL,"
                + "nro_class INT,"
                + "L_inf INT,"
                + "L_sup INT,"
                + "Marca INT,"
                + "Obs INT,"
                + "frec Decimal(6,5),"
                + "prctj Decimal(6,3),"
                + "Obs_a INT,"
                + "frec_a Decimal(6,5),"
                + "prctj_a Decimal(6,3), "
                + "Primary KEY (nro_con,nro_class)");
        
        setNroConsulta();
        Lectura();
    }
    
    //METODOS PARA EL CONSTRUCTOR
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

    private void setNroConsulta(){
        ResultSet result;
        try{
            Statement select = conn.createStatement();
            result = select.executeQuery("SELECT * FROM "+Est+" WHERE nro_con=(SELECT max(nro_con) FROM "+Est+" )");
            nro_con=1;
            if(result.next()){
                nro_con=result.getInt(1)+1;
            }
        }catch(Exception e){
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
    
    // LECTURA 
    @Override
    public void Lectura() throws IOException, NoDato, NumberFormatException {
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
     }
    
    private List<String> migrarDato(ResultSet r){
        List<String> datosfila=new ArrayList();
        try{
            int largo=r.getMetaData().getColumnCount()+1;
            for(int i=1;i<largo;i++) {
                try{
                datosfila.add(r.getString(i));              
                }catch(Exception e){
                datosfila.add(null); 
                }
            }
        }catch(Exception e){}
        return datosfila;
    }
    
    // ESCRITURA
    // ESCRITURA DE TABLA
    @Override
    public void Escritura(String[][] p, String nombreHoja) {
        EscribirLibro(p,nombreHoja);
        String Atts="nro_con,nro_class,L_inf,L_sup,Marca,Obs,frec,prctj,Obs_a,frec_a,prctj_a";
        for(int i=1;i<p.length;i++){
            String dats="'"+nro_con+"'";
            for(int j=0;j<p[0].length;j++){
                dats+=", '"+p[i][j].trim()+"'";
            }
            insertarDato(TabF,Atts,dats);
        }

    }

    // ESCRITURA DE TENDENCIA
    @Override
    public void Escritura(String[][] q, String fi, String ff, String tipo) {
        EscribirLibro(q);
        String Atts="Esperanza,Varianza ,Mediana , Moda , Des_stand , Coef_var , Media_T , Tipo_f , Fecha_i, fecha_f ";
        for(int i=1;i<q[0].length;i++){
            String dats="";
            for(int j=0;j<q.length;j++){
                dats+="'"+q[j][i].trim()+"',";
            }
            dats+="'"+tipo+"','"+fi+"','"+ff+"'";
            insertarDato(Est,Atts,dats);
        }
        
        cerrar();
    }
    
    // METODOS PARA LA ESCRITURA
    private void insertarDato(String table, String Atts, String dats) {
        try {
            Statement insertar = conn.createStatement();
            insertar.executeUpdate("INSERT INTO " + table + " (" + Atts + ") VALUES(" + dats + ")");
            
            //System.out.println("INSERT INTO " + table + " (" + Atts + ") VALUES(" + dats + ")");
        } catch (Exception e) {
            //System.out.println("error INSERT INTO " + table + " (" + Atts + ") VALUES(" + dats + ")");
        }
    }
    
    // CIERRE DE CONEXION.
    public void cerrar() {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
        }
    }
    
}
