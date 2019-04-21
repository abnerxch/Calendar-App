package agenda;

public class listaApunte {
    apunte inicio = null;
    apunte actual = null;

    public listaApunte(){
        inicio=new apunte("","");

    }

    public boolean esVacia(){
        boolean foo;
        if(inicio.getAnt()==inicio&&inicio.getSig()==inicio){
            foo =true;
        }
        else
            foo=false;
        return foo;
    }
    public void agrega(apunte foo){
        if(esVacia()){
            inicio.setSig(inicio);
            foo.setAnt(inicio);
            foo.setSig(inicio);
            foo.setSig(inicio);
            inicio.setAnt(foo);
        }
        else{
            apunte tmp=inicio.getAnt();
            inicio.setAnt(foo);
            foo.setSig(inicio);
            foo.setAnt(tmp);
            tmp.setSig(foo);
        }

    }

    public apunte buska(apunte foo){
        String sfoo=foo.apunte;
        actual=inicio;
        while(!(actual.getSig().apunte.equals(sfoo))){
            actual=actual.getSig();
        }
        return actual;

    }
    public void delte(apunte foo){

    }

}
