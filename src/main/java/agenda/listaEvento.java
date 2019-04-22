package agenda;


import javax.swing.JOptionPane;
public class listaEvento {
    Evento inicio=null;
    Evento actual=null;

    public listaEvento(){
        Evento e3= new Evento(" ", " "," ",6, false);
        inicio = e3;
        System.out.println(inicio.getPriori());
        actual=inicio;
    }

    public boolean isEmpty(){
        boolean foo=false;
        if(this.inicio.getSiguiente()==null){
            foo=true;
        }
        else
            foo=false;
        return foo;
    }

    public void addEvento(Evento foo){
        Evento tmp=null, t1=null;
        if(isEmpty())
        {
            actual=inicio;
            actual.setSiguiente(foo);
            foo.setAnterior(actual);
            System.out.println("First Insert");

        }
        else{
            tmp=getEventoAnterior(foo);
            if(tmp==null){
                actual.setSiguiente(foo);
                foo.setAnterior(actual);
            }
            else {
                t1 = tmp.getAnterior();
                tmp.setAnterior(foo);
                foo.setSiguiente(tmp);
                foo.setAnterior(t1);
                t1.setSiguiente(foo);
            }
        }

    }

    public Evento getEventoAnterior(Evento foo){
        int intFoo=foo.getPriori();
        actual=inicio;
        boolean boolFoo=false;
        System.out.println("entra");
        while((actual.getSiguiente()!=null)&&(boolFoo==false)){
            if(actual.getSiguiente().getPriori()<intFoo){
                boolFoo=true;
            }
            actual=actual.getSiguiente();

        }
        if(boolFoo)
            return actual;
        else
            return null;

    }

    public void print(){
        actual =inicio;
        while(actual!=null){
            System.out.println(actual.getPriori());
            actual=actual.getSiguiente();
        }
    }

    public void vaciarEventos(){
        this.inicio.setSiguiente(null);
    }

}
