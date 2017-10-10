package estadistica;

import tipoInv.GestorDatos;

/**
 *
 * @author BSOD
 */
public class TendenciaCentral extends Calculos {

    public TendenciaCentral(GestorDatos g) {
        super(g);
    }

    @Override
    public String[][] informe(boolean mostrar) {
        String[][] d = datos;

        d[0][0] = "Esperanza: \t\t\t";
        super.Esperanza();

        d[1][0] = "Varianza: \t\t\t";
        d[4][0] = "Desviación estandar:\t\t";
        d[5][0] = "Coeficiente de variación: \t";
        super.Varianza();

        d[2][0] = "Mediana: \t\t\t";
        super.Mediana();

        d[3][0] = "Moda: \t\t\t\t";
        super.Moda();

        d[6][0] = "Media truncada: \t\t";
        super.MediaT();
        
        if(mostrar)
            ImprimirPorPantalla(d);

        return d;
    }

    public void ImprimirPorPantalla(String[][] d){
                System.out.println("Medidas de tendencia central:");

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                try {
                    System.out.print(d[i][j]);
                } catch (NullPointerException e) {
                    System.out.print("No Dato");
                } finally {
                    if(j==0)
                        System.out.print("|");
                }
            }
            System.out.println("");
        }
        System.out.println("");

    }
    



}
