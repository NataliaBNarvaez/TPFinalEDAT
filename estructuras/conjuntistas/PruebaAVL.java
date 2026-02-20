package estructuras.conjuntistas;

public class PruebaAVL {
    public static void main(String[] args) {
        Diccionario d = new Diccionario();

        d.insertar(20, "a");
        d.insertar(10, "b");
        d.insertar(30, "c");
        d.insertar(15, "c");
        d.insertar(18, "c");
        d.insertar(19, "c");
        d.insertar(25, "c");
        d.insertar(27, "c");
        d.insertar(22, "c");
        d.insertar(5, "c");
        d.insertar(35, "c");

        System.out.println(d.toString());
        d.insertar(2, "c");
        d.insertar(8, "c");
        d.insertar(12, "c");
        d.insertar(17, "c");
        d.insertar(7, "c");

        d.eliminar(19);
        d.eliminar(25);

        System.out.println(d.toString());

        /*
         * System.out.println("\n");
         * d.eliminar(15);
         * d.eliminar(35);
         * d.eliminar(30);
         * d.eliminar(25);
         * d.eliminar(22);
         * 
         * d.insertar(14, "c");
         * d.insertar(12, "c");
         * 
         * d.eliminar(18);
         * d.insertar(16, "c");
         * d.eliminar(5);
         * d.eliminar(14);
         * 
         * System.out.println(d.toString());
         */

    }
}
