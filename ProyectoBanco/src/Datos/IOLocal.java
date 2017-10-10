/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import listas.NoDato;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author BSOD
 */
public final class IOLocal extends IOContexto {

    public IOLocal(String nombre) throws NoDato, IOException {
        super(nombre);
        Lectura();
    }

    @Override
    // LECTURA 
    public void Lectura() throws IOException, NoDato, NumberFormatException {
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
                agregarDatoL(df);
            }
            j++;
        }
        file.close();
    }

    private String agregarCelda(XSSFCell cell) throws NoDato {
        if (cell == null) {
            throw new NoDato();
        }
        return cell.toString();
    }
    
    // ESCRITURA
    @Override
    public void Escritura(String[][] p,String nombreHoja) {
        EscribirLibro(p,nombreHoja);
    }
    
    @Override
    public void Escritura(String[][] q, String fi, String ff, String tipo) {
        EscribirLibro(q);
    }
    // FIN ESCRITURA
    
}
