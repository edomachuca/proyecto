/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import listas.Cliente;
import listas.Inversion;
import listas.Lista;
import listas.NoDato;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mazhuka
 */
public class IOLocal extends IOContexto {
    
    private XSSFWorkbook libroOut;
    
    public IOLocal(String nombre) throws NoDato, IOException {
        super(nombre);
        libroOut = new XSSFWorkbook();
    }

    @Override
    public Lista Lectura() throws IOException, NoDato, NumberFormatException {
        FileInputStream file = new FileInputStream(nomArchivo);
        XSSFWorkbook libro = new XSSFWorkbook(file);
        XSSFSheet hoja = libro.getSheetAt(0);
        Iterator rows = hoja.rowIterator();
        int j = 0;
        while (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            List<String> df = new ArrayList();
            for (int i = 0; i < 10; i++) {
                XSSFCell cell = null;
                if (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                }
                try {
                    df.add(agregarCelda(cell));
                } catch (NoDato e) {
                    df.add(null);
                }

            }
            if (j != 0) {
                agregarDato(df);
            }
            j++;
        }
        if (file != null) {
            file.close();
        }
        return inversiones;
    }

    private void agregarDato(List<String> df) {
        Cliente c = new Cliente(df.get(1), df.get(2));
        if (!clientes.contains(c)) {
            clientes.add(c);
        }
        inversiones.add(new Inversion(clientes.indexOf(c), (int) Double.parseDouble(df.get(0)), df.get(3), (int) Double.parseDouble(df.get(4)), Double.parseDouble(df.get(5)), df.get(7), df.get(8), df.get(9)));
    }

    private String agregarCelda(XSSFCell cell) throws NoDato {
        if (cell == null) {
            throw new NoDato();
        }
        return cell.toString();
    }

    @Override
    public void Escritura(String[][] p,String nombreHoja) {
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
    
    @Override
    public void Escritura(String[][] q) {
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
