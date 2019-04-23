package agenda;

import java.io.File;

public class melOrto {

    int id;
    dia actual=null;
    dia inicio=null;
    melOrto sig,ant;
    String nombre;
    /**
     *
     */
    public melOrto(){
//Upadate name
        dia d=new dia("",0,"", 0, 0);
        inicio=d;

        actual=inicio;

        this.sig=null;
        this.ant=null;
    }

    public void setID(int foo){
        this.id=foo;
        this.setNombre(foo);

    }


    private void setNombre(int foo){
        switch(foo){
            case 1:this.nombre="enero";
                break;
            case 2: this.nombre="febrero";
                break;
            case 3: this.nombre="marzo";
                break;
            case 4: this.nombre="abril";
                break;
            case 5: this.nombre="mayo";
                break;
            case 6: this.nombre="junio";
                break;
            case 7: this.nombre="julio";
                break;
            case 8: this.nombre="agosto";
                break;
            case 9: this.nombre="septiembre";
                break;
            case 10: this.nombre="octubre";
                break;
            case 11: this.nombre="septiembre";
                break;
            case 12: this.nombre="diciembre";
                break;


        }
    }

    public String getNombre(){
        String s;
        int foo=this.getID();
        switch(foo){
            case 1: s=this.nombre;
                break;
            case 2: s= this.nombre;
                break;
            case 3: s= this.nombre;
                break;
            case 4:     s= this.nombre;
                break;
            case 5: s=this.nombre;
                break;
            case 6:     s= this.nombre;
                break;
            case 7: s= this.nombre;
                break;
            case 8: s= this.nombre;
                break;
            case 9: s=this.nombre;
                break;
            case 10: s= this.nombre;
                break;
            case 11: s= this.nombre;
                break;
            case 12: s= this.nombre;
                break;
            default: s= null;break;
        }

        return s;
    }

    public void setSiguiente(melOrto foo){
        this.sig=foo;
    }
    public void setAnterior(melOrto foo){
        this.ant=foo;
    }

    public melOrto getAnterior(){
        return this.ant;
    }

    public melOrto getSiguiente(){
        return this.sig;
    }
    public int getID(){
        return this.id;
    }
/**
 *
 * @return
 */

public boolean isVacia(){ //verifica si la matriz esta vacia
    if((this.inicio.lfs==null)&&(this.inicio.lcs==null))
        return true;
    else
        return false;
}
    public void gotoStart(){
        actual=inicio;

    }

    public void insertarIndice(char axis,int index){
        this.gotoStart();
        boolean a=false;
        dia tmp;
        if(axis=='x'){

            if((actual/*.getLcs()*/!=null)&&(a==false)){
                while((actual.getLcs()!=null)&&(a==false)){
                    if(actual.getLcs().getColumna()>index){
                        a=true;

                    }
                    actual=actual.getLcs();
                }
                if(a){
                    dia indicex=new dia("",0,"",index,0);


                    tmp=actual.getLcs();
                    actual.setLcs(indicex);
                    actual.getLcs().setLca(actual);
                    actual.getLcs().setLcs(tmp);
                    System.out.println("Inserto indice medio en posicion "+indicex.getColumna()+"," +indicex.getFila());

                }
                else{
                    dia indicex=new dia("",0,"",index,0);
                    actual.setLcs(indicex);
                    actual.getLcs().setLca(actual);
                    System.out.println("Se inserto x al final");

                }

            }


        }
        if(axis=='y'){
            if((actual/*.getLfs()*/!=null)&&(a==false)){
                while((actual.getLfs()!=null)&&(a==false)){
                    if(actual.getLfs().getFila()>index){
                        a=true;

                    }
                    actual=actual.getLfs();
                }
                if(a){
                    dia indicey=new dia("",0,"",0,index);


                    tmp=actual.getLfs();
                    actual.setLfs(indicey);
                    actual.getLfs().setLfa(actual);
                    actual.getLfs().setLfs(tmp);
                    System.out.println("Inserto medio en posicion "+indicey.getColumna()+"," +indicey.getFila());

                }

                else{
                    dia indicey=new dia("",0,"",0,index);
                    actual.setLfs(indicey);
                    actual.getLfs().setLfa(actual);
                    System.out.println("inserto el final y");

                }

            }

        }



    }//insertaIndice

    public void insertar(dia nuevo){

        int x=nuevo.getColumna();
        int y=nuevo.getFila();
        dia indicex,indicey,tmp=null;

        if((x>0)&&(y>0)){
            if(this.isVacia()){
                this.insertarIndice('x', x);
                this.insertarIndice('y',y);
                indicex=go2Dia(x,0);
                indicey=go2Dia(0,y);
                indicex.setLfs(nuevo);
                nuevo.setLfa(indicex);

                indicey.setLcs(nuevo);
                nuevo.setLca(indicey);


                //ready^
            }
            else{
                tmp=go2Dia(x,y);
                if(tmp==null){
                    indicex=go2Dia(x,0);
                    indicey=go2Dia(0,y);
                    if(indicex==null){
                        System.out.println("nuevo indice medio "+x);
                        insertarIndice('x',x);
                        indicex=go2Dia(x,0);

                    }
                    if(indicey==null){
                        System.out.println("nuevo indice medio y "+y);
                        insertarIndice('y', y);
                        indicey=go2Dia(0,y);

                    }
                    tmp=buskaColumnaAnterior(nuevo);
                    System.out.println("("+tmp.getColumna()+","+tmp.getFila()+")");
                    nuevo.setLca(tmp);
                    nuevo.setLcs(tmp.getLcs());
                    tmp.setLcs(nuevo);
                    if(nuevo.getLcs()!=null){
                        nuevo.getLcs().setLca(nuevo);

                    }
                    tmp=buskaFilaAnterior(nuevo);
                    nuevo.setLfa(tmp);
                    nuevo.setLfs(tmp.getLfs());
                    tmp.setLfs(nuevo);
                    if(nuevo.getLfs()!=null){
                        nuevo.getLfs().setLfa(nuevo);
                    }
                }




            }

        }


    }


    protected dia buskaColumnaAnterior(dia day){

        int y=day.getFila();
        int x=day.getColumna();
        actual=go2Dia(0,y);
        while((actual.getLcs()!=null)&&(actual.getLcs().getColumna()<x)){
            actual=actual.getLcs();
        }
        System.out.println("valor de columna anterior "+actual.getColumna());
        return actual;
    }

    protected dia buskaFilaAnterior(dia day){

        int x=day.getColumna();
        int y=day.getFila();
        actual=go2Dia(x,0);
        while((actual.getLfs()!=null)&&(actual.getLfs().getFila()<y)){
            actual=actual.getLfs();
        }
        return actual;
    }

    public dia go2Dia(int x, int y){
        boolean e=false;
        this.gotoStart();
        System.out.println("1");
        if(x>0){
            System.out.println("2");
            while((actual!=null)&&(e==false)){
                System.out.println("while");
                if(actual.getColumna()==x){
                    System.out.println("if X");
                    while((actual!=null)&&(e==false)){
                        if(actual.getFila()==y){
                            e=true;
                            System.out.println("encontro y");
                        }//ify
                        else
                            actual=actual.getLfs();
                    }
                }//ifx
                else{
                    actual=actual.getLcs();
                }
            }
        }
        else {
            System.out.println("3");
            while((actual!=null)&&(e==false)){
                if(actual.getFila()==y){
                    while((actual!=null)&&(e==false)){
                        if(actual.getColumna()==x){
                            e=true;
                        }
                        else
                            actual=actual.getLcs();
                    }
                }
                else
                    actual=actual.getLfs();
            }
        }
        if(e){
            System.out.println("regresa bueno");
            return actual;
        }
        else{
            System.out.println("regresa Null");
            return null;
        }
    }

    public void imprime(char axis, int c){
        if(axis=='x'){
            dia head=go2Dia(c, 0);
            if(head.getLfs()!=null){
                while(head!=null){
                    System.out.println(head.fecha);
                    head=head.getLfs();
                }
            }
            else
                System.out.println("Fila vacia");
        }
        if(axis=='y'){
            dia head=go2Dia(0,c);
            if(head.getLcs()!=null){
                while(head!=null){
                    System.out.println(head.fecha);
                    head=head.getLcs();
                }
            }
            else
                System.out.print("Columna Vacia");
        }
    }

    public String recorremelorto(melOrto foo){
        String sfoo="";
        dia head=foo.inicio;
        dia tmpx=head;
        dia tmpy=head;
        while(tmpx!=null&&tmpy!=null){
            sfoo=sfoo+""+tmpx.fecha+"->"+""+tmpx.lcs.fecha+"[ label = \"\" ];";
            sfoo=sfoo+""+tmpy.fecha+"->"+""+tmpy.lfs.fecha+"[ label = \"\" ]; ";
            tmpx=tmpx.getLcs();
            tmpy=tmpy.getLfs();
        }
        return sfoo;
    }

    public void getGrafo(melOrto foo){




        GraphViz gv = new GraphViz("gif");
        //iniciamos el grafo
        gv.addln(gv.start_graph());
        //creamos una conexion de A a B
        gv.addln(recorremelorto(foo));
        //de A a C
        // gv.addln("A -> P;");
        // gv.addln("C->X;");
        //finalización de grafo
        gv.addln(gv.end_graph());
        //implime el dotsource que tiene el objeto gv
        System.out.println(gv.getDotSource());
        //creamos un gif a partir de ello
        File out = new File("/home/josue/NetBeansProjects/proyectoEDD/web/orto.gif");
        //genera el grafo en el gif seleccionado
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);



    }





}
