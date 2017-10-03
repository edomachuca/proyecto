/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.IOException;
import listas.Lista;
import listas.NoDato;

/**
 *
 * @author Mazhuka
 */
public interface IOD {
    public Lista Lectura() throws IOException, NoDato, NumberFormatException;
    public void Escritura(String[][] p, String[][] q);
}
