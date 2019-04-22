package agenda;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class apunteServlet extends HttpServlet {
    listaApunte la;
    miCSS css;
    String pase;
    int ipase=0;
    String nombre;
    String nota;
    apunte actual;

    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        la=new listaApunte();
        css=new miCSS();
    }

    protected void processRequest (HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException{
        response.setContentType("text-html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet apunteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet apunteServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */

        }finally{
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        //doPost(request, response);
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        this.ipase=Integer.parseInt(request.getParameter("pass"));
        this.nombre=request.getParameter("titulo");
        this.nota=request.getParameter("nota");
        if(ipase==0){
            this.la.agrega(new apunte(nombre, nota));
            imprime(response,la.inicio.getSig());
            actual=la.inicio.getSig();
        }
        else if(ipase==1){
            this.la.agrega(new apunte(nombre,nota));
            imprime(response,actual.getSig());
            actual=actual.getSig();
        }
        else if(ipase==5){
            imprime(response,actual.getAnt());
            actual=actual.getAnt();
        }
    }

    protected void imprime(HttpServletResponse response, apunte foo) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(css.parte1());
            out.println("<center>");
            out.println("<table bgcolor=\"yellow\">");
            out.println("<tr>");
            out.println("<th>");
            out.println(foo.nombre);
            out.println("</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td width=200 height=100>");
            out.print(foo.apunte);
            out.println("</tr>");
            out.println("</td>");
            out.println("</table>");
            out.println("<form name=\"siguinte\" method=\"POST\" action=\"/proyectoEDD/apunteServlet\">");
            out.println("<input type=\"hidden\" name=\"pass\" value=\"4\"/> ");
            out.println("<input type=\"submit\" value=\"Siguiente\" name=\"sigButton\" />");
            out.println("</form>");
            out.println("<form name=\"anterior\" method=\"POST\" action=\"/proyectoEDD/apunteServlet\">");
            out.println("<input type=\"hidden\" name=\"pass\" value=\"5\"/> ");
            out.println("<input type=\"submit\" value=\"Anterior\" name=\"antButton\" />");
            out.println("</form>");
            out.println("</center>");
            out.println("Agregar nueva nota");
            out.println("<br>");
            out.println("<form name=\"apunte\" method=\"POST\" action=\"/proyectoEDD/apunteServlet\">");
            out.println("<input type=\"hidden\" name=\"pass\" value=\"1\"/> ");
            out.println("titulo:");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"titulo\"/> ");
            out.println("<br>");
            out.println("NOTA:");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"nota\"/> ");
            out.println("<br>");
            out.println("<input type=\"submit\" value=\"ok\" name=\"ok button\" />");
            out.println("</form>");
            out.print(css.parte2());

        } finally {
            out.close();
        }
    }

        protected void imprimeVacio(HttpServletResponse response)throws ServletException, IOException{
           response.setContentType("text/html;charset=UTF-8");

           PrintWriter out=null;
           try{
               out=response.getWriter();

           }catch (Exception e){

           }
            out.println(css.parte1());
            out.println("<form name=\"apunte\" method=\"POST\" action=\"/Calendar-App2/apunteServlet\">");
            out.println("<input type=\"hidden\" name=\"pass\" value=\"1\"/> ");
            out.println("<h2> Usted no a ingresado ninguna nota, llene los campos para ingresar una nueva</h2>");
            out.println("Nombre:");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"nombre\"/> ");
            out.println("<br>");
            out.println("NOTA:");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"nota\"/> ");
            out.println("<br>");

            out.println("<input type=\"submit\" value=\"ok\" name=\"ok button\" />");
            out.println("</form>");
            out.println(css.parte2());
            out.flush();
            out.close();

        }

        @Override
    public String getServletInfo(){
        return "Short description";
        }





}
