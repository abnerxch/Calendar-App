package agenda;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class load {

    public load(String path)
    {
        SAXBuilder builder =new SAXBuilder(false);
        //File xmlFile = new File("C:\\Users\\yohan\\IdeaProjects\\Calendar-App2\\src\\main\\java\\agenda");

        try{
            Document document=(Document) builder.build(path);
            Element rootNode=document.getRootElement();
            List contactos=rootNode.getChildren("contacto");
            Iterator i = contactos.iterator();

            while(i.hasNext()){
                Element e=(Element)i.next();
                Element nombre= e.getChild("nombre");
                System.out.println(nombre.getText());
                Element apellido=e.getChild("apellido");
                System.out.println(apellido.getText());
                Element pse=e.getChild("seudonimo");
                System.out.println(pse.getText());
                Element clas=e.getChild("clasificacion");
                System.out.println(clas.getText());
                Element tel=e.getChild("telefono");
                System.out.println(tel.getText());
                Element cel=e.getChild("celular");
                System.out.println(cel.getText());
                Element dir=e.getChild("direccion");
                System.out.println(dir.getText());
                Element email=e.getChild("email");
                System.out.println(email.getText());
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){

        load cc=new load("contactos.xml");

    }
}
