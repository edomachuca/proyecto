/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listas;

/**
 *
 * @author BSOD
 */

public class Cliente {

    private final String cod_cli; //key
    private final String nom_cli;

    public Cliente(String cod, String nom) {
        cod_cli = cod;
        nom_cli = nom;
    }

    public String getCod() {
        return cod_cli;
    }

    public String getNom() {
        return nom_cli;
    }



}
