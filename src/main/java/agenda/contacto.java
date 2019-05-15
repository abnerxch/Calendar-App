package agenda;
//Contactos almacenados en un arbol ALV
public class contacto {

    public int indice;
    public int fe;

    public String dato;
    public contacto izq;
    public contacto der;
    String nombre;
    String apellido;
    String apodo;
    String clasificacion;
    String telefono;
    String celular;
    String direccion;
    String email;
    public contacto(){
        //izq = new nodoAVL();
        //der = new nodoAVL();
        indice=0;
        fe=0;
        dato="";
        izq=null;
        der=null;
        nombre="";
        apellido="";
        apodo="";
        clasificacion="";
        telefono="";
        celular="";
        direccion="";
        email="";
    }

    public contacto(String dato,String nombre, String apellido, String apodo,String direc, String clasi,String tel, String cel, String mail ){

        this.dato=dato;
        this.fe=0;
        this.izq=null;
        this.der=null;
        this.nombre=nombre;
        this.apellido=apellido;
        this.apodo=apodo;
        this.clasificacion=clasi;
        this.telefono=tel;
        this.celular=cel;
        this.direccion=direc;
        this.email=mail;



    }
    public String getDato(){
        return this.apodo;
    }
    public void setIzq(contacto iz){
        this.izq=iz;

    }

    public void setDer(contacto der){
        this.der=der;

    }

    public contacto getIzq(){
        return this.izq;

    }

    public contacto getDer(){
        return this.der;
    }

    public void setIndice(int indice){
        this.indice=indice;


    }
    public int getIndice(){
        return this.indice;

    }

    public void setFe(int fe){
        this.fe=fe;

    }

    public int getFe(){
        return this.fe;

    }

}
