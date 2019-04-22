package agenda;

public class Evento {
    String hora;
    String nombre;
    String descripcion;
    int prioridad;
    boolean mail;
    Evento sig;
    Evento ant;
    public Evento(String time, String nombre,String descripcion, int p, boolean mail){
       this.hora=time;
       this.nombre=nombre;
       this.prioridad=p;
       this.mail=mail;
       this.sig=null;
       this.ant=null;
       this.descripcion=descripcion;
    }

    public Evento(String time, String nombre,int p, boolean mail, Evento s, Evento a ){
        this.hora=time;
        this.nombre=nombre;
        this.prioridad=p;
        this.mail=mail;
        this.sig=s;
        this.ant=a;

    }

    public void setSiguiente(Evento e){
        this.sig=e;
    }

    public void setAnterior(Evento e){
        this.ant=e;
    }
    public Evento getSiguiente(){
        return this.sig;
    }

    public Evento getAnterior(){
        return this.ant;
    }
    public void setPriori(int i){
        this.prioridad=i;
    }
    public void setHora(String foo){
        this.hora=foo;
    }

    public int getPriori(){
        return this.prioridad;
    }

    public void setMail(boolean foo){
        this.mail=foo;
    }


}
