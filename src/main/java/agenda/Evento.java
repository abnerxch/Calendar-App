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

    }
}
