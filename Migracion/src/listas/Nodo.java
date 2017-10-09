/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listas;

/**
 *
 * @author BSOD
 * @param <C>;
 */
public class Nodo<C> {

    private C dato;
    private Nodo<C> next;

    public Nodo(C c, Nodo<C> n) {
        dato = c;
        next = n;
    }

    public Nodo getNext() {
        return next;
    }

    public C getDato() {
        return dato;
    }

    public void setNext(Nodo sn) {
        next = sn;
    }

}
