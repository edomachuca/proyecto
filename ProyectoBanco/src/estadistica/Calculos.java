
package estadistica;

/**
 *
 * @author BSOD
 */
import java.util.*;
import tipoInv.*;

public abstract class Calculos extends GestorDatos {

    private GestorDatos gDatos;

    private double Varianza;
    private double Esperanza;
    private double Mediana;
    private double Moda;
    private double desviacion;
    private double coefVar;
    private double MediaT;
    private final int k;
    private String[][] histograma;
    private String[][] datos;
    private String[][] tabla;
    private ArrayList lista;

    public Calculos(GestorDatos g) {
        gDatos = g;
        datos = new String[7][2];
        if (1+ (int)(3.3*Math.log(gDatos.Filtro().size())) > 15) {
            k = 15;
        } else {
            k = 1+ (int)(3.3*Math.log(gDatos.Filtro().size()));
        }
        histograma = new String[k][gDatos.Filtro().size()];
        tabla = new String[k + 1][9];
        lista = gDatos.Filtro();
    }
    
    protected void Esperanza() {
        double suma = 0;
        for (int i = 0; i < lista.size(); i++) {
            suma += Get(lista, i);
        }
        Esperanza = suma / lista.size();
        datos[0][1] = Esperanza + "";
    }

    protected void Moda(){
        int mayor=0;
        int lugar=0;
        for(int i=0;i<lista.size()-1;i++){
            int count=0;
            while(Get(lista,i)==Get(lista,i+1)){
                count++;
                i++;
           }
            if(count>mayor){
                lugar=i;
                mayor=count;        
            }  
        }
        if(mayor==1){
            Moda=-1;
        }else{
            Moda=Get(lista,lugar);
        }
        datos[3][1] = Moda + "";
    }
    
    protected void MediaT(){
        int Tot=lista.size();
        int margen=(int)(Tot*0.05);
        double suma=0;
        for(int i=margen+1;i<(Tot-margen);i++){
            suma+=Get(lista,i);
        }      
        MediaT = suma/(Tot-2*margen);
        datos[6][1] = MediaT + "";
     }
 
    protected void Mediana() {
        if (lista.size() % 2 == 0) {
            Mediana = Get(lista, lista.size() / 2) + Get(lista, lista.size() / 2 - 1);
            datos[2][1] = Mediana + "";
            return;
        }
        Mediana = Get(lista, (int) lista.size() / 2);
        datos[2][1] = Mediana + "";
    }

    protected double Varianza() {
        double preVarianza = 0.0;
        for (int x = 0; x < lista.size(); x++) {
            preVarianza += (Get(lista, x) - Esperanza) * (Get(lista, x) - Esperanza);
        }
        Varianza = preVarianza / (lista.size());
        datos[1][1] = Varianza + "";
        desviacion = Math.sqrt(Varianza);
        datos[4][1] = desviacion + "";
        coefVar = desviacion / Esperanza;
        datos[5][1] = coefVar + "";
        return Varianza;
    }

    protected GestorDatos getgDatos() {
        return gDatos;
    }

    protected int getK() {
        return k;
    }

    protected String[][] getHistograma() {
        return histograma;
    }

    protected String[][] getDatos() {
        return datos;
    }

    protected String[][] getTabla() {
        return tabla;
    }

}
