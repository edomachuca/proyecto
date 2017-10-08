/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.FileOutputStream;
import listas.NoDato;
import listas.Lista;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author BSOD
 */

public abstract class IOContexto {

    protected String nomArchivo;
    protected List clientes;
    protected Lista inversiones;
    private XSSFWorkbook libroOut;

    public IOContexto(String nombre) throws NoDato, IOException, NumberFormatException {
        nomArchivo = nombre;
        clientes = new ArrayList();
        inversiones = new Lista();
        libroOut = new XSSFWorkbook();
    }
    
     public IOContexto(String nombre,String dbname,String user, String pass) throws NoDato, IOException, NumberFormatException {
        nomArchivo = nombre;
        clientes = new ArrayList();
        inversiones = new Lista();
        libroOut = new XSSFWorkbook();
    }

    public abstract Lista Lectura() throws IOException, NoDato, NumberFormatException;
    
    public abstract void Escritura(String[][] p,String tipo);
    
    public abstract void Escritura(String[][] q) ;
    
    protected void EscribirLibro(String[][] p,String nombreHoja){
        XSSFSheet hoja = libroOut.createSheet(nombreHoja);
        for (int i = 0; i < p.length; i++) {
            XSSFRow fila = hoja.createRow(i);
            for (int j = 0; j < p[0].length; j++) {
                XSSFCell celda = fila.createCell(j);
                if (i == 0 || j == 1) {
                    celda.setCellValue(p[i][j]);
                }
                if (i != 0 && (j == 0 || j == 2 || j == 3 || j == 6)) {
                    celda.setCellValue(Integer.parseInt(p[i][j].trim()));
                }
                if (i != 0 && (j == 4 || j == 5 || j == 7 || j == 8)) {
                    celda.setCellValue(Double.parseDouble(p[i][j].trim()));
                }
                hoja.autoSizeColumn(j);
            }
        }
    }
    protected void EscribirLibro(String[][] q){
            XSSFSheet hoja1 = libroOut.createSheet("Medidas de tendencia central");
        for (int i = 0; i < q.length; i++) {
            XSSFRow fila = hoja1.createRow(i);
            for (int j = 0; j < q[0].length; j++) {
                XSSFCell celda = fila.createCell(j);
                if (j == 0) {
                    celda.setCellValue(q[i][j]);
                }
                if (j == 1) {
                    celda.setCellValue(Double.parseDouble(q[i][j].trim()));
                }
                hoja1.autoSizeColumn(j);//formato
            }
        }
        crearLibro(libroOut);

    }
    
    private void crearLibro(XSSFWorkbook libroEntrada){
         
        java.util.Date fecha = new Date();
        String[] fechaV = fecha.toString().split(" ");
        String[] fechaVH = fechaV[3].split(":");
        String nfecha = fechaV[2] + "_" + fechaV[1] + "_" + fechaV[5] + "_" + fechaVH[0] + "_" + fechaVH[1] + "_" + fechaVH[2];
        try {
            FileOutputStream elFichero = new FileOutputStream("Informe_Estadístico_" + nfecha + ".xlsx");
            libroEntrada.write(elFichero);
            elFichero.close();
        } catch (Exception e) {
        }
    }
}
