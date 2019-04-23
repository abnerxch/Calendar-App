package agenda;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Stack;

public class Arbol {
    public FileOutputStream Output;
    public PrintStream fiel;

    public Stack recorridos = new Stack();
    //public string reccorridos
    public contacto raiz;
    boolean comp;

    public Arbol(){
        this.raiz = null;
        this.comp = false;

    }
    //Busqueda de contacto
    contacto auxBusqueda;
    public contacto Search(contacto raiz, String apodo){
        if(raiz==null){
            auxBusqueda= null;
        }
        else if(apodo.equals(raiz.apodo)) {
            auxBusqueda= raiz;
        }else if(apodo.compareTo(raiz.apodo)<0){
            Search(raiz.izq,apodo);
        }else if (apodo.compareTo(raiz.apodo)>0){
            Search(raiz.der,apodo);
        }
        return auxBusqueda;
    }
    //Editar  Nodo
    public void Edition(contacto raiz, String nombre, String apellido, String apodonimo, String clasificacion, String tel, String cel, String dir, String mail){
        if(raiz==null){
            auxBusqueda= null;
        }
        else if(apodonimo.equals(raiz.apodo)) {
            raiz.nombre=(nombre);
            raiz.apellido=(apellido);
            raiz.clasificacion=(clasificacion);
            raiz.telefono=(tel);
            raiz.celular=(cel);
            raiz.direccion=(dir);
            raiz.email=(mail);
        }else if(apodonimo.compareTo(raiz.apodo)<0){
            Search(raiz.izq,apodonimo);
        }else if (apodonimo.compareTo(raiz.apodo)>0){
            Search(raiz.der,apodonimo);
        }
    }



    //Recorridos del AVL
    public void pre(contacto raiz){
        if(raiz!=null){
            this.recorridos.push(raiz);
            pre(raiz.izq);
            pre(raiz.der);
        }

    }


    public void in(contacto raiz){
        if (raiz != null){
            in(raiz.izq);
            this.recorridos.push(raiz);
            in(raiz.der);

        }else{

        }

    }

    public void post (contacto raiz){
        if (raiz != null){
            post (raiz.izq);
            post (raiz.der);
            this.recorridos.push(raiz);
        }

    }
    //Area de Insercion de Nodos
    public void InsertContact (contacto d){

        if ((!exist (d.apodo,raiz))){
            contacto new_contact = d;
            raiz=InsertBalace(raiz,new_contact);//Llamada a metodo para ingresar el nodo ya balanceado
        }

    }

    protected contacto InsertBalace(contacto raiz, contacto insertado){
        contacto aux;
        contacto info = insertado;
        if (vacio(raiz)){
            raiz= info;//mete a la raiz el nuevo nodo
            comp=true;
        }
        else
        if (insertado.apodo.compareTo( raiz.apodo)<0){
            raiz.izq=InsertBalace(raiz.izq,insertado);
            if (comp)
                switch(raiz.fe){
                    case 1:{
                        raiz.fe= 0;
                        comp=false;
                    }
                    break;
                    case 0:
                        raiz.fe= -1;
                        break;
                    case -1:{
                        aux=raiz.izq;
                        if (aux.fe== -1){
                            raiz = Rotacion_SII(raiz,aux);
                        }
                        else{
                            raiz = Rotacion_DID(raiz,aux);
                        }
                        comp = false;
                    }
                    break;
                }
        }
        else{
            if (insertado.apodo.compareTo(raiz.apodo)>0){
                raiz.der=InsertBalace(raiz.der, insertado);
                if (comp)
                    switch(raiz.fe){
                        case -1:
                            raiz.fe=0;
                            comp=false;
                            break;
                        case 0:
                            raiz.fe=1;
                            break;
                        case 1:{
                            aux=raiz.der;
                            if (aux.fe==1){
                                raiz = Rotacion_SDD(raiz,aux);
                            }
                            else{
                                raiz = Rotacion_DDI(raiz,aux);
                            }
                            comp = false;
                        }
                        break;
                    }

            }
            else{
                comp = false;
            }
        }
        return raiz;
    }

    //Area de Rotaciones
    protected contacto Rotacion_SDD(contacto c1, contacto c2){
        c1.der = c2.izq;
        c2.izq = c1;
        if (c2.fe==1) {
            c1.fe=0;
            c2.fe=0;
        }
        else{
            c1.fe = 1;
            c2.fe = -1;
        }
        c1= c2;
        return c1;
    }

    protected contacto Rotacion_SII(contacto c1, contacto c2){
        c1.izq = c2.der;
        c2.der = c1;
        if (c2.fe==-1){
            c1.fe=0;
            c2.fe=0;
        }
        else{
            c1.fe=-1;
            c2.fe=1;
        }
        c1=c2;
        return c1;
    }

    protected contacto Rotacion_DDI(contacto c1, contacto c2){
        contacto aux;
        aux = c2.izq;
        c1.der = aux.izq;
        aux.izq=c1;
        c2.izq=aux.der;
        aux.der=c2;
        if (aux.fe==1){
            c1.fe=-1;
        }
        else{
            c1.fe=0;
        }
        if (aux.fe==-1)
            c2.fe=1;
        else
            c2.fe=0;
        aux.fe=0;
        c1=aux;
        return c1;
    }


    protected contacto Rotacion_DID(contacto c1, contacto c2){
        contacto aux;
        aux=c2.der;
        c1.izq=aux.der;
        aux.der=c1;
        c2.der=aux.izq;
        aux.izq=c2;
        if (aux.fe==1)
            c2.fe=-1;
        else
            c2.fe=0;
        if (aux.fe==-1)
            c1.fe=1;
        else
            c1.fe=0;
        aux.fe=0;
        c1=aux;
        return c1;
    }

    protected boolean exist(String apodo, contacto busqueda){
        contacto Aux = busqueda;
        boolean respuesta = false;
        while (Aux != null){
            if (apodo.equals(Aux.apodo)){
                respuesta = true;
                Aux = null;
            }
            else{
                if (apodo.compareTo(Aux.apodo)>0)
                    Aux = Aux.der;
                else{
                    Aux = Aux.izq;
                    if (Aux == null)
                        respuesta = false;
                }
            }
        }
        return respuesta;
    }

    protected boolean vacio(contacto peticion){
        return (peticion == null);
    }


    public contacto getRaiz(){
        return this.raiz;
    }
    public String preorden(contacto raiz1){
        String m="";
        if(raiz1!=null){
            m=m+raiz1.apodo+", ";
            m=m+preorden(raiz1.izq);
            m=m+preorden(raiz1.der);
        }
        return m;
    }

    public contacto buscarContacto(contacto raiz,String apodo){
        contacto encontrado= new contacto();
        encontrado.apodo="no esta";
        contacto a;
        contacto b;

        if(raiz!=null){

            a=buscarContacto(raiz.izq,apodo);
            b=buscarContacto(raiz.der,apodo);
            if(a.apodo.equals(apodo)){
                encontrado=a;
            }
            if(b.apodo.equals(apodo)){
                encontrado=b;
            }
            if(raiz.apodo.equals(apodo)){
                encontrado=raiz;
            }
        }

        return encontrado;

    }


    public void getGrafo(contacto raiz){




        GraphViz gv = new GraphViz("gif");
        //iniciamos el grafo
        gv.addln(gv.start_graph());
        //creamos una conexion de A a B
        gv.addln(preordenGrafo1(raiz));
        //de A a C
        // gv.addln("A -> P;");
        // gv.addln("C->X;");
        //finalización de grafo
        gv.addln(gv.end_graph());
        //implime el dotsource que tiene el objeto gv
        System.out.println(gv.getDotSource());
        //creamos un gif a partir de ello
        File out = new File("/home/josue/NetBeansProjects/proyectoEDD/web/arbol.gif");
        //genera el grafo en el gif seleccionado
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);



    }


    public String preordenGrafo1(contacto raiz1){
        String m="";
        if(raiz1!=null){

            if(raiz1.der!=null){
                m=raiz1.apodo+"->"+raiz1.der.apodo+"[ label = \"\" ]; "+m;
            }
            if(raiz1.izq!=null){
                m=raiz1.apodo+"->"+raiz1.izq.apodo+"[ label = \"\" ]; "+m;
            }

            m=m+preordenGrafo1(raiz1.der);
            m=m+preordenGrafo1(raiz1.izq);
        }
        return m;
    }

}