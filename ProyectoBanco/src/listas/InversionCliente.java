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

//ESTA CLASE PUEDE SER BORRADA


import java.util.Date;
import proyectobanco.Fecha;

public class InversionCliente {
    private int id_inv;
    private String tipo_inv;
    private int mon_inv;
    private double tasa_int;
    private double mon_rec;
    private Fecha fec;
    private boolean inv_cerrada;


public InversionCliente(int id,String tipo,int montoInicial,double tasa,String fi,String ff,String fc){
    id_inv=id;
    tipo_inv=tipo;
    mon_inv=montoInicial;
    tasa_int=tasa;
    mon_rec=montoInicial*(1+tasa);
    fec=new Fecha(fi,ff,fc);
    inv_cerrada=false;
}

private boolean InversionCerrada(){
    if(fec.getCierre()!=null)
        inv_cerrada=true;
    return inv_cerrada;
}

public Date getCierre(){
    if(InversionCerrada())
        return fec.getCierre();
    return null;
}


}