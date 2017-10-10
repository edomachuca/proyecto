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
import java.util.Date;
import proyectobanco.Fecha;

public class Inversion {

    private final int id_inv;
    private final String tipo_inv;
    private final int mon_inv;
    private final double tasa_int;
    private final double mon_rec;
    private final Fecha fec;
    private boolean inv_cerrada;
    private final int indexCliente;

    public Inversion(int index, int id, String tipo, int montoInicial, double tasa, String fi, String ff, String fc) {
        indexCliente = index;
        id_inv = id;
        tipo_inv = tipo;
        mon_inv = montoInicial;
        tasa_int = tasa;
        mon_rec = montoInicial * (1 + tasa);
        fec = new Fecha(fi, ff, fc);
        inv_cerrada = false;
    }

    private boolean InversionCerrada() {
        if (fec.getCierre() != null) {
            inv_cerrada = true;
        }
        return inv_cerrada;
    }

    public Date getCierre() {
        if (InversionCerrada()) {
            return fec.getCierre();
        }
        return null;
    }

    public int getMontoI() {
        return mon_inv;
    }

    public Fecha getFechas() {
        return fec;
    }

    public String getTipo() {
        return tipo_inv;
    }

}
