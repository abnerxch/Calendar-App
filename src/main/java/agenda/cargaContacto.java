package agenda;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.util.List;


public class cargaContacto {
    public static void main(String args[]){
        try{
            SAXBuilder builder=new SAXBuilder(false);
            Document doc=builder.build("contactos.xml");
            Element raiz=doc.getRootElement();
            List nombres=raiz.getChildren("Nombre");
            for(int i=0;i<nombres.size();i++){
                System.out.println(i);
                System.out.println((String)nombres.get(i));
            }
        }
        catch(Exception e){}

    }
    }
