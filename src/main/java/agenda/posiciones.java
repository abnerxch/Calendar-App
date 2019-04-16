package agenda;

public class posiciones {

    public posiciones(){
    }

    protected int getX(int foo){
        int foobar=0;
        switch(foo){
            case 1:
            case 7:
            case 13:
            case 19:
            case 25:
            case 31:
                foobar=1;
                break;
            case 2:
            case 8:
            case 14:
            case 20:
            case 26:
                foobar=2;
                break;
            case 3:
            case 9:
            case 15:
            case 21:
            case 27:
                foobar=3;
                break;
            case 4:
            case 10:
            case 16:
            case 22:
            case 28:
                foobar=4;
                break;
            case 5:
            case 11:
            case 17:
            case 23:
            case 29:
                foobar=5;
                break;
            case 6:
            case 12:
            case 18:
            case 24:
            case 30:
                foobar=6;
                break;
        }
        return foobar;
    }

    protected int getY(int foobar){
        int foo=0;
        switch(foobar){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                foo=1;
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                foo=2;
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                foo=3;
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                foo=4;
                break;
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                foo=5;
                break;
            case 31:
                foo=6;
                break;


        }

        return foo;
    }


}
