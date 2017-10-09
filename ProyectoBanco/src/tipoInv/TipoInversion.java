/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipoInv;

import listas.NoDato;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import listas.Inversion;
import listas.Lista;
import Datos.IOContexto;
import Datos.IOLocal;
import java.util.Iterator;

/**
 *
 * @author BSOD
 */
public class TipoInversion extends GestorDatos {

    private Lista datos;
    private ArrayList filtro;
    private Date i,f;
    private String tip;
    private IOContexto archivo;
    private Inversion def;

    public TipoInversion(String arch, String ini, String fin, String t) throws IOException, NoDato {
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
    public IOContexto getArchivo() {
        return archivo;
    }

    @Override
    public String[][] informe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList Filtro() {
        return filtro;
    }
    
    private ArrayList setFiltro() {
        ArrayList x = FiltroFecha(datos, i, f);
        ArrayList[] filtroTipo = new ArrayList[4];
        filtroTipo[0] = new ArrayList();
        filtroTipo[1] = new ArrayList();
        filtroTipo[2] = new ArrayList();
        filtroTipo[3] = new ArrayList();
        
        Iterator iterador = x.listIterator(); 
        while( iterador.hasNext() ) {
            switch (((Inversion) iterador.next()).getTipo()) {
                        case "DF":
                            filtroTipo[0].add(iterador.next());
                            break;
                        case "FM":
                            filtroTipo[1].add(iterador.next());
                            break;
                        case "FI":
                            filtroTipo[2].add(iterador.next());
                            break;
                        case "BO":
                            filtroTipo[3].add(iterador.next());
                            break;
                        default:
                            throw new AssertionError();
            }
        } 
        
        if(filtroTipo[0].isEmpty())
            filtroTipo[0].add(def);
        if(filtroTipo[1].isEmpty())
            filtroTipo[1].add(def);
        if(filtroTipo[2].isEmpty())
            filtroTipo[2].add(def);
        if(filtroTipo[3].isEmpty())
            filtroTipo[3].add(def);
        
        switch (tip) {
            case "DF":
                quickSort(filtroTipo[0], 0, filtroTipo[0].size() - 1);
                return filtroTipo[0];
            case "FM":
                quickSort(filtroTipo[1], 0, filtroTipo[1].size() - 1);
                return filtroTipo[1];
            case "FI":
                quickSort(filtroTipo[2], 0, filtroTipo[2].size() - 1);
                return filtroTipo[2];
            case "BO":
                quickSort(filtroTipo[3], 0, filtroTipo[3].size() - 1);
                return filtroTipo[3];
            default:
                throw new AssertionError();
        }
    }

}
