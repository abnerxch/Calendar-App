package agenda;
/*
La lista de eventos esta contenida en el nodo dia
 */

public class dia {

    String nombre;
    int fecha;
    String fest;

    int fila;
    int columna;
    listaEvento ev;
    dia lcs=null;
    dia lca=null;

    dia lfs=null;
    dia lfa= null;

    public dia (String nombre, int fecha, String fest, int col, int fila){
        this.nombre=nombre;
        this.fecha=fecha;
        this.fest=fest;

        this.fila=fila;
        this.columna=col;

        this.lcs=null;
        this.lca=null;
        this.lfs=null;
        this.lfa=null;
        ev=new listaEvento();

    }

    public dia(String nombre, int fecha, String fest, int col, int fila, dia lcs, dia lca, dia lfs, dia lfa ){


        this.nombre = nombre;
        this.fecha=fecha;
        this.fest = fest;

        this.fila=fila;
        this.columna=col;

        this.lcs=lcs;
        this.lca=lca;
        this.lfs=lfs;
        this.lfa=lfa;
    }

    public int getColumna(){
        return this.columna;
    }
    public int getFila(){
        return this.fila;
    }

    public dia getLfs(){
        return this.lfs;
    }
    public dia getLfa(){
        return this.lfa;
    }
    public dia getLca(){
      return this.lca;
    }
    public dia getLcs(){
        return this.lcs;
    }
    public void setLca(dia n){
        this.lca=n;
    }
    public void setLcs(dia n){
        this.lcs=n;
    }
    public void setLfa(dia n){
        this.lfa=n;
    }
    public void setLfs(dia n){
        this.lfs =n;
    }
    public void setFest(String f){
        this.fest=f;
    }
    public void setFecha(int f){
        this.fecha=f;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void nuevoEvento(String hora,String nombre,String d,int prioridad,boolean mail){

        this.ev.addEvento(new Evento(hora,nombre,d,prioridad,mail));

    }
    public listaEvento getLista(){
        return this.ev;
    }

}
