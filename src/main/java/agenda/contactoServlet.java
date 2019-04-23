package agenda;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class contactoServlet extends HttpServlet {
    String nombre;
    String apellido;
    String apodo;
    String email;
    String tel;
    String cel;
    String direccion;
    String val;
    String clas;
    String s;
    int Switch=0;
    int pass=0;
    ArrayList preLista;
    ArrayList postLista;
    ArrayList inLista;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        preLista=new ArrayList();
        postLista=new ArrayList();
        inLista=new ArrayList();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //TODO output your page here
            out.println("<html>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"iso-8859-1\" />");
            out.println("<link href=\"images/style.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.println("<head>");
            out.println("<title>Servlet contactoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet contactoServlet at " + request.getContextPath () + "</h1>");
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
        // processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList lista=new ArrayList();
        Arbol arbol=new Arbol ();
        Arbol preArbol=new Arbol();
        Arbol postArbol=new Arbol();
        //ArrayList preLista=new ArrayList();
        // ArrayList postLista=new ArrayList();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart)
            val="1";
        else
            val=request.getParameter("a");
        System.out.println(val);
        if(Integer.parseInt(val)==1){
            boolean ok=GuardarArchivo(request);
            if(ok)
                System.out.println("exito");
            else
                System.out.println("fail");
            SAXBuilder builder=new SAXBuilder(false);

            try{
                Document doc=builder.build("/tmp/coxml.xml");
                Element raiz=doc.getRootElement();

                List contactos=raiz.getChildren("contacto");
                Iterator i=contactos.iterator();

                while(i.hasNext()) {
                    Element e=(Element)i.next();
                    Element name=e.getChild("nombre");
                    System.out.println(name.getText());
                    Element lastname=e.getChild("apellido");
                    System.out.println(lastname.getText());
                    Element pse=e.getChild("seudonimo");
                    System.out.println(pse.getText());
                    Element clasi=e.getChild("clasificacion");
                    System.out.println(clasi.getText());
                    Element tele=e.getChild("telefono");
                    System.out.println(tele.getText());
                    Element celu=e.getChild("celular");
                    System.out.println(celu.getText());
                    Element dir=e.getChild("direccion");
                    System.out.println(dir.getText());
                    Element mail=e.getChild("email");
                    System.out.println(mail.getText());

                    contacto  c1=new contacto("",name.getText(),lastname.getText(),pse.getText(),dir.getText(),clasi.getText(),tele.getText(),celu.getText(),mail.getText());

                    arbol.InsertContact(c1);
                    //  preArbol.InsertContact(c1);
                    // postArbol.InsertContact(c1);

                    //lista.add(c1);
                    //arbol.InsertContact(c1);
                }
                arbol.in(arbol.getRaiz());
                Stack tmp=new Stack();
//recorrido in order
                tmp=arbol.recorridos;
                while(tmp.isEmpty()==false){
                    lista.add(tmp.pop());
                }
                //recorrido pre
                arbol.pre(arbol.getRaiz());
                Stack preStack=new Stack();
                preStack=arbol.recorridos;
                while(preStack.isEmpty()==false){
                    preLista.add(preStack.pop());
                }

                arbol.post(arbol.getRaiz());
                Stack poStack=new Stack();
                poStack=arbol.recorridos;
                while(poStack.isEmpty()==false){
                    postLista.add(poStack.pop());
                }

                arbol.getGrafo(arbol.getRaiz());
            }
            catch (Exception e){System.out.println(e);}

            inLista=lista;
            this.imprimeContacto1(response,lista);
        }
        else if(Integer.parseInt(val)==0){
            nombre=request.getParameter("nombre");
            apellido=request.getParameter("apellido");
            apodo=request.getParameter("apodo");
            email=request.getParameter("mail");
            tel=request.getParameter("telefono");
            cel=request.getParameter("cel");
            direccion=request.getParameter("direccion");
            clas=request.getParameter("clasificacion");
            contacto c=new contacto("",nombre,apellido,apodo,direccion,clas,tel,cel,email);
            s=request.getParameter("switch");
            Switch=Integer.parseInt(s);
            if(Switch==7){
                this.imprimeContacto1(response,preLista);
            }
            else if(Switch==8){
                this.imprimeContacto1(response,postLista);
            }
            else if(Switch==6){
                this.imprimeContacto1(response, inLista);
            }
            else{
                //lista.add(c);
                arbol.InsertContact(c);
                arbol.in(arbol.getRaiz());
                Stack tmp=new Stack();
                Stack tmp2= new Stack();
                tmp=arbol.recorridos;
                while(!tmp.isEmpty()){
                    tmp2.add(tmp.pop());
                }
                while(!tmp2.isEmpty()){
                    lista.add(tmp2.pop());
                }
                this.imprimeContacto1(response,lista);

            }
        }
    }
    protected void imprimeContacto1(HttpServletResponse response,ArrayList lista)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=null;
        try{
            out=response.getWriter();
        }
        catch(Exception e){}
        miCSS stilo=new miCSS();
            /* out.println("<html>");
            out.println("<head>");
            out.println("<title>CONTACTOS</title>");
             out.println("<meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"iso-8859-1\" />");
            out.println("<link href=\"images/style.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.println("</head>");
            out.println("<body bgcolor=\"white\">");*/
        // out.println("<h1>DIA: "+dia+"-"+mes+"-"+ano+" </h1>");
        out.println(stilo.parte1());
        out.println("<br>");
        //out.println("Festividad: "+fest);
        out.println("<br>");
        out.println("<table border=1>");
        out.println("<tr>");
        out.println("<th> NOMBRE </th>");
        out.println("<th>APELLIDO </th>");
        out.println("<th>APODO</th>");
        out.println("<th>CLASIFICACION</th>");
        out.println("<th>TELEFONO</th>");
        out.println("<th>CELULAR</th>");
        out.println("<th>DIRECCION</th>");
        out.println("<th>EMAIL</th>");
        out.println("</tr");

        for(int i=(lista.size()-1);i>=0;i--){
            contacto c=(contacto)lista.get(i);
            out.println("<tr>");
            out.println("<td>"+c.nombre +"</td>");
            out.println("<td>"+c.apellido +"</td>");
            out.println("<td>"+c.apodo +"</td>");
            out.println("<td>"+c.clasificacion +"</td>");
            out.println("<td>"+c.telefono +"</td>");
            out.println("<td>"+c.celular +"</td>");
            out.println("<td>"+c.direccion +"</td>");
            out.println("<td>"+c.email+"</td>");
            out.print("</tr>");
        }
        out.print("</table>");
            /*out.println("</body>");
             out.println("</head>");
            out.println("</html>");*/
        out.println("<br>");
        out.println("<form name=\"pref\" action=\"/proyectoEDD/contactoServlet\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"switch\" value=\"7\" />");
        out.println("<input type=\"hidden\" name=\"a\" value=\"0\" />");
        out.println("<input type=\"submit\" value=\"PreOrden\" name=\"prebutton\"/ >");
        out.println("</form>");
        out.println("<br>");
        out.println("<form name=\"postf\" action=\"/proyectoEDD/contactoServlet\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"switch\" value=\"8\" />");
        out.println("<input type=\"hidden\" name=\"a\" value=\"0\" />");
        out.println("<input type=\"submit\" value=\"PostOrden\" name=\"postbutton\"/ >");
        out.println("</form>");



        out.println("<form name=\"inff\" action=\"/proyectoEDD/contactoServlet\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"switch\" value=\"6\" />");
        out.println("<input type=\"hidden\" name=\"a\" value=\"0\" />");
        out.println("<input type=\"submit\" value=\"PostOrden\" name=\"postbutton\"/ >");
        out.println("</form>");
        out.println("<a href=\"grafoArbol.jsp\">ver Grafo<a>");

        out.println(stilo.parte2());
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
            String fileName = "coxml.xml";
            fi.write(new File(path, fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

}