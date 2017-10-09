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
public interface ListaC {

    public void clear();

    public void add(Object item);

    public Object remove();

    public void setFirst();

    public void next();

    public int length();

    public void setValue(Object obj);

    public Object currValue();

    public boolean isEmpty();

    public boolean eol();
}
