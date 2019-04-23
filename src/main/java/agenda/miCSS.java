package agenda;

/*
CSS que se utiliza para hacer la interfaz web en proceso
 */

public class miCSS {
    public miCSS(){}

    public String parte1(){

        String s1="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"+
                " <head>"+
                "<title>Agenda EDD</title>"+
                "<meta http-equiv=\"content-type\" content=\"text/html\" />"+
                " <link rel=\"stylesheet\" href=\"images/style.css\" type=\"text/css\" />"+
                "</head>"+
                "<body>"+
                " <div class=\"content\">"+
                " <div class=\"preheader\">"+
                "  <div class=\"padding\"> <a href=\"http://www.free-css.com/\">About</a>&nbsp; <a href=\"http://www.free-css.com/\">Contact</a> </div>"+
                " </div>"+
                "<div class=\"header\">"+
                "<div class=\"title\">AGENDA FX</div>"+
                "<div class=\"slogan\">MAKING YOUR LIFE EASY</div>"+
                "</div>"+
                "<div id=\"nav\">"+
                " <ul>"+
                "                  <li><a href=\"index.jsp\">Home</a></li>"+
                "  <li><a href=\"calendario.jsp\">Calendario</a></li>"+
                "<li><a href=\"Contactos.jsp\">Contactos</a></li>"+
                "<li><a href=\"http://www.free-css.com/\">Affiliates</a></li>"+
                "<li id=\"current\"><a href=\"http://www.free-css.com/\">About</a></li>"+
                "<li><a href=>Contact</a></li>"+
                " </ul>"+
                "            </div>"+
                " <div class=\"main_content\">"+
                "<div class=\"sd_right\">"+
                "<div >"+


                "</div>"+
                "                </div>"+
                "<div class=\"sd_left\">"+
                "        <div class=\"text_padding\">";
        return s1;
    }

    public String parte2(){
        String s2="<p class=\"date\"><img src=\"images/comment.gif\" alt=\"\" /> <a class=\"date\" href=\"http://www.free-css.com/\">Comments(2)</a> <img src=\"images/timeicon.gif\" alt=\"\" /> 21.02.</p>"+" <br />"+

                "      <p class=\"date\"><img src=\"images/comment.gif\" alt=\"\" /> <a class=\"date\" href=\"http://www.free-css.com/\">Comments(15)</a> <img src=\"images/timeicon.gif\" alt=\"\" /> 13.01.</p>"+
                "     <br />"+
                "</div>"+
                " </div>"+
                "<div class=\"footer\">"+
                "<div class=\"padding\"> Powered by <a href=\"http://snews.solucija.com\" title=\"Single file CSS and XHTML valid CMS\">sNews</a> | &copy; Copyright BinaryNews Template :: Design: <a href=\"http://www.free-css-templates.com/\" title=\"Free CSS Templates\">David Herreman</a> | <a href=\"rss/\">RSS Feed</a> | <a href=\"http://jigsaw.w3.org/css-validator/check/referer\">CSS</a> and <a href=\"http://validator.w3.org/check?uri=referer\">XHTML</a> | <a href=\"http://www.free-css.com/\">Login</a> </div>"+
                "</div>"+
                "</div>"+
                "</div>"+
                "</body>"+
                "</html>";
        return s2;
    }
}
