/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipoInv;

import Datos.IOContexto;
import Datos.IOLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import listas.Inversion;
import listas.Lista;
import listas.NoDato;

/**
 *
 * @author BSOD
 */
public class General extends GestorDatos {
    
    private Lista datos;
    private ArrayList filtro;
    private Date i, f;
    private String tip;
    private IOContexto archivo;
    private Inversion def;

    public General(String arch, String ini, String fin, String t) throws IOException, NoDato {
        try {
            archivo = new IOLocal(arch);
            datos = archivo.Lectura();
            i = ConvertirFecha(ini);
            f = ConvertirFecha(fin);
            tip = t;
            filtro = setFiltro();
            def = getDefault();
        } catch (NullPointerException e) {
            System.out.println("El archivo no existe.");
        }
    }

    @Override
    public String[][] informe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList Filtro() {
        return filtro;
    }

    @Override
    public IOContexto getArchivo() {
        return archivo;
    }

    private ArrayList setFiltro() {
        ArrayList entrega = FiltroFecha(datos, i, f);
        if (entrega.isEmpty() ) 
            entrega.add(def);
        quickSort(entrega, 0, entrega.size() - 1);
        return entrega;
    }
    
}
