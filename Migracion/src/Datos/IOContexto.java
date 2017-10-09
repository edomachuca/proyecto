/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import listas.NoDato;
import listas.Lista;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BSOD
 */

public abstract class IOContexto {

    protected String nomArchivo;
    protected List clientes;
    protected Lista inversiones;

    public IOContexto(String nombre) throws NoDato, IOException, NumberFormatException {
        nomArchivo = nombre;
        clientes = new ArrayList();
        inversiones = new Lista();
    }
    
     public IOContexto(String nombre,String dbname,String user, String pass) throws NoDato, IOException, NumberFormatException {
        nomArchivo = nombre;
        clientes = new ArrayList();
        inversiones = new Lista();
    }

    public abstract Lista Lectura() throws IOException, NoDato, NumberFormatException;
    
    public abstract void Escritura(String[][] p,String tipo);
    public abstract void Escritura(String[][] q) ;

      
}
