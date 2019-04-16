package agenda;

import java.io.File;

public class Melorto {

    int id;
    dia actual=null;
    dia inicio=null;
    melOrto sig,ant;
    String nombre;
    /**
     *
     */
    public melOrto(){

        dia d=new dia("",0,"", 0, 0);
        inicio=d;

        actual=inicio;

        this.sig=null;
        this.ant=null;
    }

    public void setID(int foo){
        this.id=foo;
        this.setNombre(foo);

    }


    private void setNombre(int foo){
        switch(foo){
            case 1:this.nombre="enero";
                break;
            case 2: this.nombre="febrero";
                break;
            case 3: this.nombre="marzo";
                break;
            case 4: this.nombre="abril";
                break;
            case 5: this.nombre="mayo";
                break;
            case 6: this.nombre="junio";
                break;
            case 7: this.nombre="julio";
                break;
            case 8: this.nombre="agosto";
                break;
            case 9: this.nombre="septiembre";
                break;
            case 10: this.nombre="octubre";
                break;
            case 11: this.nombre="septiembre";
                break;
            case 12: this.nombre="diciembre";
                break;


        }
    }

    public String getNombre(){
        String s;
        int foo=this.getID();
        switch(foo){
            case 1: s=this.nombre;
                break;
            case 2: s= this.nombre;
                break;
            case 3: s= this.nombre;
                break;
            case 4:     s= this.nombre;
                break;
            case 5: s=this.nombre;
                break;
            case 6:     s= this.nombre;
                break;
            case 7: s= this.nombre;
                break;
            case 8: s= this.nombre;
                break;
            case 9: s=this.nombre;
                break;
            case 10: s= this.nombre;
                break;
            case 11: s= this.nombre;
                break;
            case 12: s= this.nombre;
                break;
            default: s= null;break;
        }

        return s;
    }

    public void setSiguiente(melOrto foo){
        this.sig=foo;
    }
    public void setAnterior(melOrto foo){
        this.ant=foo;
    }

    public melOrto getAnterior(){
        return this.ant;
    }

    public melOrto getSiguiente(){
        return this.sig;
    }
    public int getID(){
        return this.id;
    }
/**
 *
 * @return
 */

public boolean isVacia(){ //verifica si la matriz esta vacia
    if((this.inicio.lfs==null)&&(this.inicio.lcs==null))
        return true;
    else
        return false;
}
    public void gotoStart(){
        actual=inicio;

    }





}
