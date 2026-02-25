package estructuras.grafo;

public class NodoVertice {
    private Object elem;
    private NodoVertice sigVertice;
    private NodoAdy primerAdy;

    public NodoVertice(Object e, NodoVertice sig) {
        this.elem = e;
        this.sigVertice = sig;
    }

    public Object getElem() {
        return this.elem;
    }

    public void setElem(Object e) {
        this.elem = e;
    }

    public NodoVertice getSigVertice() {
        return this.sigVertice;
    }

    public void setSigVertice(NodoVertice sigVert) {
        this.sigVertice = sigVert;
    }

    public NodoAdy getPrimerAdy() {
        return this.primerAdy;
    }

    public void setPrimerAdy(NodoAdy nAdy) {
        this.primerAdy = nAdy;
    }
}
