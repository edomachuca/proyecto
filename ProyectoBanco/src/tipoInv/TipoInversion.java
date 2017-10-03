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
import Datos.IOLocal;

/**
 *
 * @author BSOD
 */
public class TipoInversion extends GestorDatos {

    private Lista datos;
    private ArrayList filtro;
    private String ini, fin;
    private String tip;
    private IOLocal archivo;

    public TipoInversion(String arch, String i, String f, String t) throws IOException, NoDato {
        try {
            archivo = new IOLocal(arch);
            datos = archivo.Lectura();
            ini = i;
            fin = f;
            tip = t;
            filtro = Filtro();
        } catch (NullPointerException e) {
            System.out.println("El archivo no existe.");
        }
    }

    public IOLocal getArchivo() {
        return archivo;
    }

    @Override
    public String[][] informe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList Filtro() {
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
        if (entrega.size() == 0 ) 
            entrega.add(def);
        if(filtroTipo[0].size()==0)
            filtroTipo[0].add(def);
        if(filtroTipo[1].size()==0)
            filtroTipo[1].add(def);
        if(filtroTipo[2].size()==0)
            filtroTipo[2].add(def);
        if(filtroTipo[3].size()==0)
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
