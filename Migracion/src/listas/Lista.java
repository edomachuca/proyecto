package listas;

/**
 *
 * @author BSOD
 * @param <C>
 */

public class Lista<C> implements ListaC {

    private Nodo<C> head;
    private Nodo<C> actual;
    private int largo;

    public Lista() {
        head = new Nodo(null, null);
        actual = head;
        largo = 0;
    }

    @Override
    public void clear() {
        head = new Nodo(null, null);
        actual = head;
        largo = 0;
    }

    @Override
    public void add(Object item) {
        actual.setNext(new Nodo(item, actual.getNext()));
        largo++;
    }

    @Override
    public Object remove() {
        Object entrega = actual.getNext().getDato();
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

    @Override
    public void setValue(Object obj) {
        Nodo aux = head;
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
    public Object currValue() {
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

}
