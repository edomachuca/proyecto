package listas;

/**
 *
 * @author BSOD
 */

// ESTA CLASE TAMBIEN PEUDE SER BORRADA

public class ListaCliente implements ListaC {

    private NodoCliente head;
    private NodoCliente actual;
    private int largo;

    public ListaCliente() {
        head = new NodoCliente(null, null);
        actual = head;
        largo = 0;
    }

    @Override
    public void clear() {
        head = new NodoCliente(null, null);
        actual = head;
        largo = 0;
    }

    public void add(Cliente item) {
        actual.setNext(new NodoCliente(item, actual.getNext()));
        largo++;
    }

    @Override
    public Cliente remove() {
        Cliente entrega = actual.getNext().getDato();
        actual.setNext(actual.getNext().getNext());
        largo--;
        return entrega;
    }

    @Override
    public void setFirst() {
        actual = head;
    }

    @Override
    public void next() {
        actual = actual.getNext();
    }

    @Override
    public int length() {
        return largo;
    }

    public void setValue(Cliente obj) {
        NodoCliente aux = head;
        boolean modificado = false;
        while (!modificado && aux.getNext() != null) {
            if (aux.getNext().getDato().equals(obj)) {
                actual = aux;
                modificado = true;
            } else {
                aux = aux.getNext();
            }
        }
    }

    @Override
    public Cliente currValue() {
        if (actual.getNext() == null) {
            return null;
        }
        return actual.getNext().getDato();
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    @Override
    public boolean eol() {
        return (actual.getNext() == null);
    }

    public boolean exist(Cliente c) {
        NodoCliente aux = head;
        while (aux.getNext() != null) {
            if (aux.getNext().getDato().getCod().equals(c.getCod())) {
                return true;
            }
            aux = aux.getNext();
        }
        return false;
    }

    public void addInv(InversionCliente inv) {
        actual.getNext().getDato().agregarInversion(inv);
    }

    @Override
    public void add(Object item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
