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

}
