package agenda;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.input.SAXBuilder;
import org.jdom.*;
import org.w3c.dom.Element;

public class calendarioServlet { //extends HttpServlet{

    String eDescripcion;
    String eRekordatorio;
    String fest;
    String fecha;
    melOrto orto;
    String ano;
    String mes;
    String dia;
    int pass/*=0*/;
    String pase/*="0"*/;
    int prioridad;
    //int hora;
    String tEvento;
    String sPrioridad;
    String sHora;
    listaEvento le;
    listaMelorto listano;
    String grafo=null;
    //ArrayList listaHora,listaNombre,listaPrioridad;
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        listano=new listaMelorto();
        miCSS css=new miCSS();
            /* listano=new listaMelorto();
             melOrto enero=new melOrto();
             enero.setID(1);
            */
    }

    protected void processRequest( HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here*/
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet calendarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Prueba </h1>");
            out.println("Esta es la festividad "+fest);
            out.println("<br>");
            out.println("esta es la fecha" +fecha);
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet 1request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            boolean ok=GuardarArchivo(request);
            if(ok)
                System.out.println("exito");
            else
                System.out.println("fail");
            SAXBuilder builder=new SAXBuilder(false);
            try{//subida de archivo
                Document doc=builder.build("/tmp/exml.xml");
                Element raiz=doc.getRootElement();
                List evento=raiz.getChildren("evento");
                Iterator i=evento.iterator();
                while(i.hasNext()){
                    Element e=(Element)i.next();
                    Element dias=e.getChild("dia");
                    dia=dias.getText();
                    System.out.println(dias.getText());
                    Element mess=e.getChild("mes");
                    mes=mess.getText();
                    System.out.println(mess.getText());
                    Element anio=e.getChild("anio");
                    ano=anio.getText();
                    System.out.println(anio.getText());
                    Element hora=e.getChild("hora");
                    sHora=hora.getText();
                    System.out.println(hora.getText());
                    Element nombre=e.getChild("nombre");
                    tEvento=nombre.getText();
                    System.out.println(nombre.getText());
                    Element descripcion=e.getChild("descripcion");
                    eDescripcion=descripcion.getText();
                    System.out.println(descripcion.getText());
                    Element prioridads=e.getChild("prioridad");
                    sPrioridad=prioridads.getText();
                    System.out.println(prioridads.getText());
                    Element recordatorio=e.getChild("recordatorio");
                    eRekordatorio=recordatorio.getText();
                    System.out.println(recordatorio.getText());
                    posiciones p=new posiciones();
                    int v[]=p.vpos(dia);
                    melOrto m=listano.getMes(Integer.parseInt(mes));
                    dia d=m.go2Dia(v[0],v[1]);
                    if(d==null){
                        int r[]=p.vpos(dia);
                        System.out.println("posicion vecto x"+r[0]);
                        System.out.println("posicion vecto y"+r[1]);
                        dia d1=new dia("AAAAAAAA",Integer.parseInt(dia),"",r[0],r[1]);
                        d1.nuevoEvento(sHora, tEvento,eDescripcion, Integer.parseInt(sPrioridad), true);
                        m.insertar(d1);


                    }
                    if(d!=null){
                        le=d.getLista();
                        Evento foo=new Evento(sHora, tEvento,eDescripcion, Integer.parseInt(sPrioridad), true);
                        le.addEvento(foo);

                    }

                }

            }catch(Exception e){
                System.out.println(e);
            }

            imprimeCarga(response)     ;
        }
        else{
            fest=request.getParameter("festividad");
            dia=request.getParameter("dia");
            fecha=request.getParameter("fecha");
            ano=request.getParameter("ano");
            mes=request.getParameter("mes");
            pase=request.getParameter("pase");
            pass=Integer.parseInt(pase);
            posiciones p=new posiciones();
            sPrioridad=request.getParameter("priori");
            sHora=request.getParameter("hora");
            tEvento=request.getParameter("nEvento");
            eDescripcion=request.getParameter("desc");
            System.out.println(dia);
            grafo=request.getParameter("grafo");

            switch (Integer.parseInt(mes)){
                case 1:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(1);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listaDescripcion=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listaDescripcion.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listaDescripcion);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listaDescripcion=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listaDescripcion.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listaDescripcion);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listaDescripcion=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listaDescripcion.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listaDescripcion);


                    }
                    if(grafo!=null){
                        orto.getGrafo(orto);
                        grafo=null;
                    }
                }//case1
                break;
                case 2:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(2);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listaDescripcion=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listaDescripcion.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listaDescripcion);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listaD=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listaD.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listaD);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }






                }break;//case 2;

                case 3:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(3);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        // d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }



                }break;//case 3

                case 4:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(4);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }



                }break;//case 4

                case 5:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(5);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }






                }break; //case 5

                case 6:{

                    int v[]=p.vpos(dia);
                    orto=listano.getMes(6);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();

                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();

                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();


                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }




                }break;//case 6


                case 7:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(7);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();

                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }

                }break; //case 7

                case 8:{


                    int v[]=p.vpos(dia);
                    orto=listano.getMes(8);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }



                }break;//case 8

                case 9:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(9);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        // d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listad.add(actual.descripcion);
                                listaPrioridad.add(actual.prioridad);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }

                }break; //case 9

                case 10:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(10);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }

                }break;//case 10

                case 11:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(11);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listad.add(actual.descripcion);
                            listaPrioridad.add(actual.prioridad);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listad.add(actual.descripcion);
                                listaPrioridad.add(actual.prioridad);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }

                }break; //case 11

                case 12:{
                    int v[]=p.vpos(dia);
                    orto=listano.getMes(12);
                    dia d=orto.go2Dia(v[0], v[1]);
                    if((d==null)&&(pass==0)){

                        imprimenulo(response);


                    }
                    else if((d!=null)&&(pass==0)){
                        fest=d.fest;
                        le=d.getLista();
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }

                        imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                    }
                    else if(pass==1){
                        dia d1=new dia("",Integer.parseInt(dia),fest,v[0],v[1]);
                        //d1.nuevoEvento(dia, ano, pass, true);
                        if((sPrioridad!=null)&&(sHora!=null)&&(tEvento!=null)){
                            le=d1.getLista();
                            le.addEvento(new Evento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true));
                        }
                        orto.insertar(d1);
                        imprimeNoti(response);
                        pass=2;
                    }
                    else if(pass==2){
                        dia t=orto.go2Dia(v[0],v[1]);
                        if(t!=null){
                            fest=t.fest;

                            listaEvento u=t.ev;


                            Evento head=u.inicio.getSiguiente();
                            System.out.println(""+head.prioridad);
                            Evento actual=head;
                            ArrayList listaHora=new ArrayList();
                            ArrayList listaNombre=new ArrayList();
                            ArrayList listaPrioridad=new ArrayList();
                            ArrayList listad=new ArrayList();
                            while(actual/*.getSiguiente()*/!=null){
                                listaHora.add(actual.hora);
                                listaNombre.add(actual.nombre);
                                listaPrioridad.add(actual.prioridad);
                                listad.add(actual.descripcion);
                                actual=actual.getSiguiente();
                            }
                            this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);
                            pass=3;
                        }

                        //imprimeNoti(response);


                    }

                    else if(pass==3){
                        System.out.println("entra 3");
                        dia de=orto.go2Dia(v[0], v[1]);
                        de.nuevoEvento(sHora, tEvento,eDescripcion,Integer.parseInt(sPrioridad), true);//agrego evento a la lista

                        fest=de.fest;

                        listaEvento le=de.ev;
                        Evento head=le.inicio.getSiguiente();
                        Evento actual=head;
                        ArrayList listaHora=new ArrayList();
                        ArrayList listaNombre=new ArrayList();
                        ArrayList listaPrioridad=new ArrayList();
                        ArrayList listad=new ArrayList();
                        while(actual/*.getSiguiente()*/!=null){
                            listaHora.add(actual.hora);
                            listaNombre.add(actual.nombre);
                            listaPrioridad.add(actual.prioridad);
                            listad.add(actual.descripcion);
                            actual=actual.getSiguiente();
                        }
                        this.imprimeInfo(response,listaHora,listaNombre,listaPrioridad,listad);


                    }
                }break;//case 12
            }//switch

        }//fin else multipart


    }
    //processRequest (response);*/

    protected void imprimenulo(HttpServletResponse response) throws ServletException,IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=null;
        try{
            out=response.getWriter();
        }
        catch(Exception e){}
        miCSS c=new miCSS();
        out.println(c.parte1());
        out.println("<h1>Ingresa nueva Actividad </h1>");

        out.println("<br>");
        out.println("Fecha: " +dia+"-"+mes+"-"+ano);
        out.println("<br>");
        out.println("<form name=\"formDia\" action=\"/proyectoEDD/calendarioServlet\" method=\"POST\">");
        out.println("<br>");
        out.println("Festividad: <input type=\"text\" name=\"festividad\" /> ");
        out.println("<input type=\"hidden\" name=\"dia\" value="+dia+">");
        out.println("<input type=\"hidden\" name=\"ano\" value="+ano+">");
        out.println("<input type=\"hidden\" name=\"mes\" value="+mes+">");
        out.println("<input type=\"hidden\" name=\"pase\" value=\"1\">");
        out.println("<h2>INGRESO DE EVENTOS</h2>");
        out.println("HORA: <br>");
        out.println("<input type=\"text\" name=\"hora\" />");
        out.print("<br>");
        out.print("Evento: <br>");
        out.println("<input type=\"text\" name=\"nEvento\" />");
        out.println("<br>");
        out.println("Descripcion: <br>");
        out.println("<input type=\"text\" name=\"desc\" />");
        out.println("<br>");
        out.println("Prioridad: <br>");
        // out.println("<input type=\"text\" name=\"priori\"/>");
        out.println("<select name=\"priori\">");
        out.println("<option value=\"1\">1");
        out.println("<option value=\"2\">2");
        out.println("<option value=\"3\">3");
        out.println("<option value=\"4\">4");
        out.println("<option value=\"5\">5");
        out.println("</select>");
            /* <FORM NAME="myform">
<SELECT NAME="mylist">
<OPTION VALUE="m1">Cape Fear
<OPTION VALUE="m2">The Good, the Bad and the Ugly
<OPTION VALUE="m3">The Omen
<OPTION VALUE="m4">The Godfather
<OPTION VALUE="m5">Forrest Gump
</SELECT>
</FORM>*/

        out.println("<br>");
        out.println("<input type=\"submit\" value=\"ok\" name=\"okbutton\" />");
        out.println("</form>");
        out.println("<a href=\"index.jsp\">HOME  </a>");
        out.println("<br>");
        out.println("<a href=\"calendario.jsp\"> CALENDARIO</a>");
        out.println(c.parte2());
        out.flush();
        out.close();

    }



    protected void imprimeInfo(HttpServletResponse response,ArrayList listaHora,ArrayList listaNombre, ArrayList listaPrioridad, ArrayList listad)throws ServletException,IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=null;
        try{
            out=response.getWriter();
        }
        catch(Exception e){}
        miCSS ee=new miCSS();
        out.println(ee.parte1());
        out.println("<h1>DIA: "+dia+"-"+mes+"-"+ano+" </h1>");

        out.println("<br>");
        out.println("Festividad: "+fest);
        out.println("<br>");
        out.println("<table border=1>");
        out.println("<tr>");
        out.println("<th> hora  </th>");
        out.println("<th>Evento  </th>");
        out.println("<th>Descripcion</th>");
        out.println("<th>Prioridad</th>");
        out.println("</tr");
        for(int i=0;i<listaHora.size();i++){
            String h=(String)listaHora.get(i);
            String e=(String)listaNombre.get(i);
            String d=(String)listad.get(i);
            String p=""+(Integer)listaPrioridad.get(i);
            out.println("<tr>");
            out.println("<td>"+h +"</td>");
            out.println("<td>"+e +"</td>");
            out.println("<td>"+d +"</td>");
            out.println("<td>"+p +"</td>");
            out.print("</tr>");
        }
        out.print("</table>");

        out.println("<h2>Agregar Evento</h2>");


        out.println("<form name=\"formDia\" action=\"/proyectoEDD/calendarioServlet\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"dia\" value="+dia+">");
        out.println("<input type=\"hidden\" name=\"ano\" value="+ano+">");
        out.println("<input type=\"hidden\" name=\"mes\" value="+mes+">");
        out.println("<input type=\"hidden\" name=\"pase\" value=3>");
        out.println("HORA: <br>");
        out.println("<input type=\"text\" name=\"hora\" />");
        out.println("<br>");
        out.println("Evento: <br>");
        out.println("<input type=\"text\" name=\"nEvento\" />");
        out.println("<br>");
        out.println("Descripcion: <br>");
        out.println("<input type=\"text\" name=\"desc\" />");
        out.println("<br>");
        out.println("Prioridad <br>");
        //out.println("<input type=\"text\" name=\"priori\" />");*/
        out.println("<select name=\"priori\">");
        out.println("<option value=\"1\">1");
        out.println("<option value=\"2\">2");
        out.println("<option value=\"3\">3");
        out.println("<option value=\"4\">4");
        out.println("<option value=\"5\">5");
        out.println("</select>");
        out.println("<br>");
        out.println("<input type=\"submit\" value=\"ok\" name=\"okButton\"/>");

        out.println("</form>");


        out.println("<form name=\"formgrafo\" action=\"/proyectoEDD/calendarioServlet\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"grafo\" value=\"4\">");
        out.println("<input type=\"hidden\" name=\"dia\" value="+dia+">");
        out.println("<input type=\"hidden\" name=\"ano\" value="+ano+">");
        out.println("<input type=\"hidden\" name=\"mes\" value="+mes+">");
        out.println("<input type=\"submit\" value=\"Grafo\" name=\"okButton\"/>");
        out.println("<input type=\"hidden\" name=\"pase\" value=7>");
        out.println("</form>");




        out.println("<a href=\"index.jsp\">HOME  </a>");
        out.println("<br>");
        out.println("<a href=\"calendario.jsp\"> CALENDARIO</a>");
        out.println(ee.parte2());
        out.flush();
        out.close();

    }

    protected void imprimeNoti(HttpServletResponse response)throws ServletException,IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=null;
        try{
            out=response.getWriter();
        }
        catch(Exception e){}
        miCSS mc=new miCSS();
        out.println(mc.parte1());
        out.println("<h3>Aviso:Se ha Agregado el Dato con exito </h3>");

        out.println("<br>");
        out.println("<img src=\"a.gif\">");
        // out.println("Festividad"+fest);
        out.println("<form name=\"formDia\" action=\"/proyectoEDD/calendarioServlet\" method=\"POST\">");

        out.println("<input type=\"hidden\" name=\"dia\" value="+dia+">");
        out.println("<input type=\"hidden\" name=\"ano\" value="+ano+">");
        out.println("<input type=\"hidden\" name=\"mes\" value="+mes+">");
        out.println("<input type=\"hidden\" name=\"pase\" value=2>");
        out.println("<input type=\"submit\" value=\"ok\" name=\"okbutton\" />");
        out.println("</form>");
        out.print(mc.parte2());
        out.flush();
        out.close();


    }
    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    public boolean GuardarArchivo(HttpServletRequest request) {
        try {
            // Chequea que sea un request multipart (que se este enviando un file)
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            System.out.println("Is multipart=" + isMultipart);
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maxima talla que sera guardada en memoria, poner atencion en esto ya que
            //el archivo de carga puede ser de considerable tamaño
            factory.setSizeThreshold(4096);
            // si se excede de la anterior talla, se ira guardando temporalmente, en la sgte direccion
            factory.setRepository(new File("/tmp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            //maxima talla permisible (si se excede salta al catch)
            upload.setSizeMax(10000000);
            List fileItems = upload.parseRequest(request);
            //obtiene el file enviado
            Iterator i = fileItems.iterator();
            FileItem fi = (FileItem) i.next();
            // lo que hacemos es hacer una copia del archivo en el servidor
            //graba el file enviado en el servidor local
            //path y Nombre del archivo destino (en el server)
            String path = "/tmp";
            String fileName = "exml.xml";
            fi.write(new File(path, fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void imprimeCarga(HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=null;
        try{
            out=response.getWriter();
        }
        catch(Exception e){}
        miCSS mc=new miCSS();

        out.println(mc.parte1());
        out.println("<h3>Aviso:Se ha Cargado el XML  con exito </h3>");

        out.println("<br>");
        out.println("<img src=\"a.gif\">");
        // out.println("Festividad"+fest);
        out.println("<form name=\"formDia\" action=\"/proyectoEDD/calendario.jsp\" method=\"get\">");


        out.println("<input type=\"submit\" value=\"ok\" name=\"okbutton\" />");
        out.println("</form>");
        out.print(mc.parte2());
        out.flush();
        out.close();



    }

}
