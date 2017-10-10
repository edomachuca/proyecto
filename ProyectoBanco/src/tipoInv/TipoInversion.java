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


/**
 *
 * @author BSOD
 */
public class TipoInversion extends GestorDatos {
    
    private final ArrayList filtro;
    private final String ini; 
    private final String fin;
    private final String tip;

    // CONSTRUCTORES 
    public TipoInversion(String arch, String i, String f, String t) throws NoDato, IOException{
        super(arch);
        ini = i;
        fin = f;
        tip = t;
        filtro = setFiltro(); 
    }

    public TipoInversion(String nombre,String dbName,String user, String pass, String i, String f, String t) throws IOException, NoDato, Exception {
            super(nombre,dbName,user,pass);
            ini = i;
            fin = f;
            tip = t;
            filtro = setFiltro();
    }

    
    // SALIDA DE DATOS
    @Override
    public void Escribir(String[][] p, String nombrehoja){
        archivo.Escritura(p, nombrehoja);
    }
    
    @Override
    public void Escribir(String[][] q, String fi, String ff, String tipo){
        archivo.Escritura(q, fi, ff, tipo);
    }

    // GETTER FILTRO
    @Override
    public ArrayList Filtro() {
        return filtro;
    }
    
    // BUILDER DEL FILTRO DE DATOS POR INVERSION
    private ArrayList setFiltro() {
        Date i = ConvertirFecha(ini);
        Date f = ConvertirFecha(fin);
        ArrayList entrega = new ArrayList();
        ArrayList[] filtroTipo = new ArrayList[4];
        filtroTipo[0] = new ArrayList();
        filtroTipo[1] = new ArrayList();
        filtroTipo[2] = new ArrayList();
        filtroTipo[3] = new ArrayList();

        datos.setFirst();
        while (!datos.eol()) {
            if (((Inversion) datos.currValue()).getFechas().getInicio().compareTo(i)>=0 && ((Inversion) datos.currValue()).getFechas().getInicio().compareTo(f)<=0) {
                if (tip.equals("GR")) {
                    entrega.add(datos.currValue());
                } else {
                    switch (((Inversion) datos.currValue()).getTipo()) {
                        case "DF":
                            filtroTipo[0].add(datos.currValue());
                            break;
                        case "FM":
                            filtroTipo[1].add(datos.currValue());
                            break;
                        case "FI":
                            filtroTipo[2].add(datos.currValue());
                            break;
                        case "BO":
                            filtroTipo[3].add(datos.currValue());
                            break;
                        default:
                            throw new AssertionError();
                    }
                }

            }
            datos.next();
        }
        Inversion def=new Inversion(0, 0, "GR", 0, 0, "00-ene-0000","00-ene-0000", "00-ene-0000");
        if (entrega.isEmpty() ) 
            entrega.add(def);
        if(filtroTipo[0].isEmpty())
            filtroTipo[0].add(def);
        if(filtroTipo[1].isEmpty())
            filtroTipo[1].add(def);
        if(filtroTipo[2].isEmpty())
            filtroTipo[2].add(def);
        if(filtroTipo[3].isEmpty())
            filtroTipo[3].add(def);
        
        if (tip.equals("GR")) {
            quickSort(entrega, 0, entrega.size() - 1);
            return entrega;
        } else {
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

}
