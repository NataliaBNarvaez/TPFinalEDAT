package estructuras.grafo;

public class PruebaGrafo {
    public static void main(String[] args) {
        Grafo g = new Grafo();
        int cont = 1;
        System.out.println(g.vacio());

        while (cont < 7) {
            g.insertarVertice(cont);
            cont++;
        }
        System.out.println(g.vacio());
        System.out.println(g.toString());

        g.insertarArco(1, 2, 3.0);
        g.insertarArco(2, 4, 6.0);
        g.insertarArco(3, 2, 5.0);
        g.insertarArco(3, 5, 8.0);
        g.insertarArco(4, 1, 5.0);
        g.insertarArco(4, 5, 9.0);
        g.insertarArco(5, 1, 6.0);
        g.insertarArco(5, 2, 7.0);

        System.out.println(g.toString());
        System.out.println("Existe arco entre 2 y 4? " + g.existeArco(2, 4));
        System.out.println("ELIMINADO DE ARCOS" + "\n");
        System.out.println("Elimino arco 2 a 4: " + g.eliminarArco(2, 4));
        System.out.println("Elimino arco 6 a 4 (6 no existe): " + g.eliminarArco(7, 4));
        System.out.println("Elimino arco 1 a 3 (no existe el arco): " + g.eliminarArco(1, 3));
        System.out.println("\n");
        System.out.println(g.toString());
        System.out.println("Existe arco entre 2 y 4? " + g.existeArco(2, 4));

        System.out.println("\n");
        // g.eliminarVertice(1);
        System.out.println(g.toString());

        System.out.println(g.existeCamino(7, 4));

    }
}
