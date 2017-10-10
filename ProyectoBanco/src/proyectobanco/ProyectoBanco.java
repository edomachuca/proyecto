package proyectobanco;

/**
 *
 * @author BSOD
 */

import estadistica.*;
import listas.NoDato;
import java.io.*;
import static java.lang.Thread.sleep;
import tipoInv.TipoInversion;
import tipoInv.GestorDatos;

public class ProyectoBanco {
    public static Menu menu;
    public static GestorDatos legacy;

    public static void main(String[] args) throws IOException, NoDato, InterruptedException, Exception {
        
        // DISPLAY DE MENU
        menu = new Menu();//Generamos el menú
        menu.setVisible(true);//Mostramos el menú

        //Iteración para esperar las respuestas ingresadas a través del menú
        do {
            sleep(1000);
            System.out.print(".");
        } while (!menu.getEstado());
        
        //Se comienza a procesar la solicitud
        System.out.println("\n Su consulta fué realizada para las siguientes fechas:" + "\n"
                + "\n   Fecha inicial: " + menu.getFechaInicio()
                + "\n   Fecha final:   " + menu.getFechaFin() + "\n"
                + "\n PROCESANDO SOLICITUD..." + "\n");

        menu.setVisible(false);//Ocultamos el menú
        //Se solicita el filtro de datos según los parámetros ingresados
        
        if(false){
            legacy = new TipoInversion("DatosSistemaLegacy.xlsx", menu.getFechaInicio(), menu.getFechaFin(), menu.getTipo());
        }else{
            legacy = new TipoInversion("Banco_peoplebank","","root","", menu.getFechaInicio(), menu.getFechaFin(), menu.getTipo());
        }
        
        Calculos Calculos;    
        //Se genera el informe con la tabla de frecuencias
        Calculos = new TablaHistograma(legacy);
        String[][] p = Calculos.informe(true);
        //Se genera el informe con las medidas de tendencia central
        Calculos = new TendenciaCentral(legacy);
        String[][] q = Calculos.informe(true);
        //Escribimos el informe en el archivo de salida (Excel)
        legacy.Escribir(p,"Tabla de frecuencias_"+menu.getTipo());
        legacy.Escribir(q, menu.getFechaInicio(), menu.getFechaFin(), menu.getTipo());
        //Se finaliza la aplicación (menú)
        menu.cambiarEstado();
        
        
        
        
        //Cambios 2
    }
}
