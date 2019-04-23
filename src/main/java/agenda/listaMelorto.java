package agenda;
/*
El nodo dia esta contenido en una matriz ortogonal  o lista ortogonal
cada nodo tiene 4 punteros a otros nodos

Cada instancia de la matriz ortogonal conforma una lista doble enlazada

 */
public class listaMelorto {
    melOrto inicio=null;
    melOrto actual =null;
    melOrto enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre;

    public listaMelorto(){
        melOrto m=new melOrto();
        this.inicio=m;
        actual=inicio;
        enero=new melOrto();
        febrero=new melOrto();
        marzo=new melOrto();
        abril=new melOrto();
        mayo=new melOrto();
        junio=new melOrto();
        julio=new melOrto();
        agosto=new melOrto();
        septiembre=new melOrto();
        octubre=new melOrto();
        noviembre=new melOrto();
        diciembre=new melOrto();

        enero.setID(1);
        febrero.setID(2);
        marzo.setID(3);
        abril.setID(4);
        mayo.setID(5);
        junio.setID(6);
        julio.setID(7);
        agosto.setID(8);
        septiembre.setID(9);
        octubre.setID(10);
        noviembre.setID(11);
        diciembre.setID(12);

        this.insertar(enero);
        this.insertar(febrero);
        this.insertar(marzo);
        this.insertar(abril);
        this.insertar(mayo);
        this.insertar(junio);
        this.insertar(agosto);
        this.insertar(septiembre);
        this.insertar(octubre);
        this.insertar(noviembre);
        this.insertar(diciembre);

    }

    public boolean isEmpty(){
        boolean foo=false;
        if(inicio.getSiguiente()==null)
            foo=true;
        return foo;
    }

    public void insertar(melOrto foo){
        actual =inicio;
        melOrto tmp,t1;
        if(isEmpty()){
            actual.setSiguiente(foo);
            foo.setAnterior(actual);
        }
        else{
            tmp=elAnterior(foo);
            if(tmp==null){
                actual.setSiguiente(foo);
                foo.setAnterior(actual);
            }
            else{
                t1=tmp.getAnterior();
                tmp.setAnterior(foo);
                foo.setSiguiente(tmp);
                foo.setAnterior(t1);
                t1.setSiguiente(foo);
            }

        }

    }

    public melOrto getMes(int foo){
        actual = inicio;
        boolean bfoo=false;
        while ((actual!=null)&&(bfoo==false)){
            if(actual.getSiguiente().getID()==foo)
                bfoo=true;
            actual =actual.getSiguiente();

        }
        return actual;
    }

    public melOrto elAnterior(melOrto m){
        actual = inicio;
        int id=m.getID();
        boolean boolfoo=false;
        while((actual.getSiguiente()!=null)&&(boolfoo==false)){
            if(actual.getSiguiente().getID()>id)
                boolfoo=true;
            actual=actual.getSiguiente();
        }
        if(boolfoo)
            return actual;
        else
            return null;
    }
    public void print(){
        actual = inicio;
        while(actual!=null){
            System.out.println(actual.getID());
            actual=actual.getSiguiente();
        }
    }
}
