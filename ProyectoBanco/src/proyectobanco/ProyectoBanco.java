package proyectobanco;

/**
 *
 * @author BSOD
 */
import Datos.*;
import estadistica.*;
import listas.NoDato;
import java.io.*;
import static java.lang.Thread.sleep;
import tipoInv.TipoInversion;
import tipoInv.GestorDatos;

public class ProyectoBanco {

    public static void main(String[] args) throws IOException, NoDato, InterruptedException, Exception {
        
        
        
        IOExt conn=new IOExt("","root","");
        
        //Cambios
        
        
        Menu x = new Menu();//Generamos el menú
        x.setVisible(true);//Mostramos el menú

        do {//Iteración para esperar las respuestas ingresadas a través del menú
            sleep(1000);
            System.out.print(".");
        } while (!x.getEstado());
        //Se comienza a procesar la solicitud
        System.out.println("\n Su consulta fué realizada para las siguientes fechas:" + "\n"
                + "\n   Fecha inicial: " + x.getFechaInicio()
                + "\n   Fecha final:   " + x.getFechaFin() + "\n"
                + "\n PROCESANDO SOLICITUD..." + "\n");

        x.setVisible(false);//Ocultamos el menú
        //Se solicita el filtro de datos según los parámetros ingresados
        GestorDatos legacy = new TipoInversion("DatosSistemaLegacy.xlsx", x.getFechaInicio(), x.getFechaFin(), x.getTipo());
        //Se genera el informe con la tabla de frecuencias
        legacy = new TablaHistograma(legacy);
        String[][] p = legacy.informe();
        //Se genera el informe con las medidas de tendencia central
        legacy = new TendenciaCentral(legacy);
        String[][] q = legacy.informe();
        //Escribimos el informe en el archivo de salida (Excel)
        legacy.getArchivo().Escritura(p, q);
        //Se finaliza la aplicación (menú)
        x.cambiarEstado();
        
        
        
        
        //Cambios 2
    }
}
