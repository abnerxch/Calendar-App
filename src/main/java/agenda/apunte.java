package agenda;

//Lista doble enlazada circular
public class apunte {
    private apunte sig;
    private apunte ant;
    String apunte;
    String nombre;
    apunte (String nombre, String apunte){
        this.apunte = apunte;
        this.nombre = nombre;
        sig = this;
        ant = this;
    }
    public void setSig(apunte foo){
        this.sig=foo;
    }
    public void setAnt(apunte foo){
        this.ant=foo;
    }
    public apunte getSig(){
        return this.sig;
    }
    public apunte getAnt(){
        return this.ant;
    }
}
