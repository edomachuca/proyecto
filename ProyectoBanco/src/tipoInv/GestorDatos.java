package tipoInv;

import listas.Inversion;
import java.util.*;
import Datos.IOContexto;
import Datos.IOExt;
import Datos.IOLocal;
import java.io.IOException;
import listas.Lista;
import listas.NoDato;
/**
 *
 * @author BSOD
 */
public abstract class GestorDatos {
    
    protected IOContexto archivo;
    protected Lista datos;
    
    // CONSTRUCTORES 
    public GestorDatos(String arch) throws NoDato, IOException{
        try{
            archivo = new IOLocal(arch);
            datos = archivo.getLectura();
        }catch(Exception e){
            System.out.println("El Archivo no existe");
        }
    }
    
    public GestorDatos(String nombre,String dbName,String user, String pass) throws NoDato, IOException{
        try{
            archivo = new IOExt(nombre,dbName,user,pass);
            datos = archivo.getLectura();
        }catch(Exception e){
            System.out.println("El Archivo no existe");
        }
    }

    // FILTRO RESPECTIVO
    public abstract ArrayList Filtro();
    
    // LLAMADO A METODOS DE ESCRITURA
    public abstract void Escribir(String[][] p, String nombrehoja);
    public abstract void Escribir(String[][] q, String fi, String ff, String tipo);

    // ORDENAMIENTO DE DATOS: INVERSION MENOR A MAYOR
    protected void quickSort(ArrayList e, int izq, int der) {
        Inversion piv = (Inversion) e.get(izq);
        int pivote = Get(e, izq);
        int i = izq;
        int j = der;
        Inversion auxIntercambio;
        while (i < j) {
            while (Get(e, i) <= pivote && i < j) {
                i++;
            }
            while (Get(e, j) > pivote) {
                j--;
            }
            if (i < j) {
                auxIntercambio = (Inversion) e.get(i);
                e.set(i, e.get(j));
                e.set(j, auxIntercambio);
            }
        }
        e.set(izq, e.get(j));
        e.set(j, piv);
        if (izq < j - 1) {
            quickSort(e, izq, j - 1);
        }
        if (j + 1 < der) {
            quickSort(e, j + 1, der);
        }
    }

    // METODOS PROPIOS DE FUNCIONAMIENTO
    protected int Get(ArrayList e, int i) {
        return ((Inversion) e.get(i)).getMontoI();
    }

    protected Date ConvertirFecha(String fecha) {
        if (fecha == null) {
            return null;
        }
        String[] nuevaF = fecha.split("-");
        switch (nuevaF[1]) {
            case "ene": nuevaF[1] = 0 + ""; break;
            case "feb": nuevaF[1] = 1 + ""; break;
            case "mar": nuevaF[1] = 2 + ""; break;
            case "abr": nuevaF[1] = 3 + ""; break;
            case "may": nuevaF[1] = 4 + ""; break;
            case "jun": nuevaF[1] = 5 + ""; break;
            case "jul": nuevaF[1] = 6 + ""; break;
            case "ago": nuevaF[1] = 7 + ""; break;
            case "sep": nuevaF[1] = 8 + ""; break;
            case "oct": nuevaF[1] = 9 + ""; break;
            case "nov": nuevaF[1] = 10 + ""; break;
            case "dic": nuevaF[1] = 11 + ""; break;
            case "jan": nuevaF[1] = 0 + ""; break;
            case "Jan": nuevaF[1] = 0 + ""; break;
            case "Feb": nuevaF[1] = 1 + ""; break;
            case "Mar": nuevaF[1] = 2 + ""; break;
            case "Apr": nuevaF[1] = 3 + "";  break;
            case "May": nuevaF[1] = 4 + "";  break;
            case "Jun": nuevaF[1] = 5 + ""; break;
            case "Jul": nuevaF[1] = 6 + ""; break;
            case "Agu": nuevaF[1] = 7 + ""; break;
            case "Aug": nuevaF[1] = 7 + ""; break;
            case "Sep": nuevaF[1] = 8 + ""; break;
            case "Oct": nuevaF[1] = 9 + ""; break;
            case "Nov": nuevaF[1] = 10 + ""; break;
            case "Dic": nuevaF[1] = 11 + ""; break;
            case "Dec": nuevaF[1] = 11 + ""; break;
        }
        Date nfecha = new Date(Integer.parseInt(nuevaF[2]) - 1900, Integer.parseInt(nuevaF[1]), Integer.parseInt(nuevaF[0]));
        return nfecha;
    }
}
