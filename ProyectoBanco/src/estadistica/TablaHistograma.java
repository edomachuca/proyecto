package estadistica;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import Datos.IOContexto;
import tipoInv.GestorDatos;

/**
 *
 * @author BSOD
 */
public class TablaHistograma extends Calculos {

    public TablaHistograma(GestorDatos g) {
        super(g);
    }

    @Override
    public String[][] informe(boolean mostrar) {
        ArrayList n = Filtro();
        String[][] tabla = super.getTabla();
        String[][] histograma = super.getHistograma();
        int k = super.getK();
        int[] largos = new int[10];
        
        tabla[0][0] = "N° clase";
        largos[0] = tabla[0][0].length();
        tabla[0][1] = "Límite inferior";
        largos[1] = tabla[0][1].length();
        tabla[0][2] = "Límite superior";
        largos[2] = tabla[0][2].length();
        tabla[0][3] = "Marca de clase";
        largos[3] = tabla[0][3].length();
        tabla[0][4] = "N° observaciones";
        largos[4] = tabla[0][4].length();
        tabla[0][5] = "Frecuencia relativa";
        largos[5] = tabla[0][5].length();
        tabla[0][6] = "Porcentaje relativo";
        largos[6] = tabla[0][6].length();
        tabla[0][7] = "N° Obs. acumuladas";
        largos[7] = tabla[0][7].length();
        tabla[0][8] = "Frec. relativa acumulada";
        largos[8] = tabla[0][8].length();
        tabla[0][9] = "Porc. relativo acumulado";
        largos[9] = tabla[0][9].length();

        int mayor = Get(n, n.size() - 1);
        int menor = Get(n, 0);
        int largo = mayor - menor;
        int rango = largo / k;
        double count = 0;
        for (int c = 0; c < histograma.length; c++) { //k=15
            double men = menor + (rango * c);
            double may = menor + (rango * (c + 1));
            double parcial = 0;
            boolean seguir = true;
            if (c == k - 1) {
                may += k;
            }
            tabla[c + 1][0] = c + 1 + "";
            tabla[c + 1][1] = (int) men +""; 
            tabla[c + 1][2] = (int) may + "";
            tabla[c + 1][3] = (int) (men + may) / 2 + "";
            for (int i = (int) count; i < n.size() && seguir; i++) {
                if (men <= Get(n, i) && Get(n, i) < may) {
                    parcial++;
                    count++;
                } else {
                    seguir = false;
                }
            }
            tabla[c + 1][4] = (int) parcial + "";
            tabla[c + 1][5] = truncar(parcial / n.size()) + "";
            tabla[c + 1][6] = truncar(parcial / n.size() * 100) + "";
            tabla[c + 1][7] = (int) count + "";
            tabla[c + 1][8] = truncar(count / n.size()) + "";
            tabla[c + 1][9] = truncar(count / n.size() * 100) + "";
        }
        if(mostrar)
            ImprimirPorPantalla(tabla,largos);
        return tabla;
    }

    @Override
    public ArrayList Filtro() {
        return super.getgDatos().Filtro();
    }

    @Override
    public IOContexto getArchivo() {
        return super.getgDatos().getArchivo();
    }

    private String truncar(double d) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00000", simbolos); //entregaba un string del tipo 0,00000 (la coma no dejaba realizar el parseo al escribir en excel
        return df.format(d);
    }
    
    private void ImprimirPorPantalla(String[][] tabla,int[] largos){
        String esp = "                                        ";
        System.out.println("Tabla de frecuencias:");
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[0].length; j++) {
                try {
                    int dif = 0;
                    if (tabla[i][j].length() < largos[j]) {
                        dif = largos[j] - tabla[i][j].length();
                    }
                    System.out.print(tabla[i][j] + esp.substring(0, dif));
                } catch (NullPointerException e) {
                    System.out.print("No Dato");
                } finally {
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

}
