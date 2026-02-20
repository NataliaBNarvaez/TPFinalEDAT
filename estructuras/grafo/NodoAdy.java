package estructuras.grafo;

public class NodoAdy {
    private NodoVertice vertice;
    private NodoAdy sigAdyacente;
    private Double etiqueta;

    public NodoAdy(NodoVertice v, NodoAdy sigA, Double e) {
        this.vertice = v;
        this.sigAdyacente = sigA;
        this.etiqueta = e;
    }

    public NodoVertice getVertice() {
        return this.vertice;
    }

    public void setVertice(NodoVertice nVert) {
        this.vertice = nVert;
    }

    public NodoAdy getSigAdyacente() {
        return this.sigAdyacente;
    }

    public void setSigAdyacente(NodoAdy sigAdy) {
        this.sigAdyacente = sigAdy;
    }

    public Object getEtiqueta() {
        return this.etiqueta;
    }

    public void setEtiqueta(Double et) {
        this.etiqueta = et;
    }
}
