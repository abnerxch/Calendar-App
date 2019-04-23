package agenda;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class cargaEventosss {


    public cargaEventosss(String path){
        SAXBuilder builder = new SAXBuilder(false);
        try{
            Document doc=builder.build(path);
            Element raiz=doc.getRootElement();
            List evento =raiz.getChildren("evento");
            Iterator i= evento.iterator();
            while(i.hasNext()){
                Element e=(Element)i.next();
                Element dia=e.getChild("dia");
                System.out.println(dia.getText());
                Element mes=e.getChild("mes");
                System.out.println(mes.getText());
                Element anio=e.getChild("año");
                System.out.println(anio.getText());
                Element hora=e.getChild("hora");
                System.out.println(hora.getText());
                Element nombre=e.getChild("nombre");
                System.out.println(nombre.getText());
                Element descripcion=e.getChild("descripción");
                System.out.println(descripcion.getText());
                Element prioridad=e.getChild("prioridad");
                System.out.println(prioridad.getText());
                Element recordatorio=e.getChild("recordatorio");
                System.out.println(recordatorio.getText());
            }



        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }}

    public static void main(String args[]){

        cargaEventosss c=new cargaEventosss("eventos.xml");


    }
    }
