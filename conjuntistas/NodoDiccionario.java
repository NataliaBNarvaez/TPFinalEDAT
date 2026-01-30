package conjuntistas;

// Nodo AVL de Diccionario
public class NodoDiccionario {

    private Comparable clave;
    private int altura;
    private NodoDiccionario izquierdo;
    private NodoDiccionario derecho;
    private Object dato;

    public NodoDiccionario(Comparable c, NodoDiccionario izq, NodoDiccionario der, Object d) {
        this.clave = c;
        this.izquierdo = izq;
        this.derecho = der;
        this.dato = d;
        this.altura = 0;
    }

    public void setClave(Comparable c) {
        this.clave = c;
    }

    public Comparable getClave() {
        return clave;
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura() {
        int alturaIzq = 0;
        int alturaDer = 0;
        if (this.izquierdo != null) {
            alturaIzq = this.izquierdo.getAltura() + 1;
        }
        if (this.derecho != null) {
            alturaDer = this.derecho.getAltura() + 1;
        }
        this.altura = Math.max(alturaIzq, alturaDer);
    }

    public NodoDiccionario getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoDiccionario izq) {
        this.izquierdo = izq;
    }

    public NodoDiccionario getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoDiccionario der) {
        this.derecho = der;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object d) {
        this.dato = d;
    }
}
