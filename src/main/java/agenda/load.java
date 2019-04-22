package agenda;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.util.Iterator;
import java.util.List;

public class load {

    public load(String path)
    {
        SAXBUILDER builder = new SAXBUILDER(false);
        try{
            Document doc=builder.build(path);
            Element raiz=doc.getRootElement();

            List contactos=raiz.getChildren("contacto");
            Iterator i = contactos.iterator();

            while(i.hasNext()){
                Element e=(Element)i.next();
                Element nombre= e.getChild("nombre");
                System.out.println(nombre.getText());
                Element apellido=e.getChild("apellido");
                System.out.println(apellido.getText());
            }
        }
    }
}
