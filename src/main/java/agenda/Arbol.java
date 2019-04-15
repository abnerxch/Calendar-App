package agenda;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Stack;

public class Arbol {
    public FileOutputStream Output;
    public PrintStream fiel;

    //public Stack recor
    public Stack recorridos = new Stack();
        //public string reccorridos
        public contacto raiz;
    boolean comp;

        public Arbol(){
            this.raiz = null;
            this.comp = false;

        }

}
