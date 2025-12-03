package TPFinalEDAT.estructuras.lineales.dinamicas;

/**
 *
 * @author Natalia Narvaez
 */
public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        cabecera = null;
        longitud = 0;
    }

    public boolean insertar(Object elem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(elem, this.cabecera);
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud = this.longitud + 1;
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = false;
        if (pos > 0 && pos <= this.longitud()) {
            // el enlace del nodo que apunta a la cabecera avanza al siguiente
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            this.longitud -= 1;
            exito = true;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem = null;
        if (!(pos < 1 || pos > this.longitud())) {
            if (cabecera != null) {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos) {
                    aux = aux.getEnlace();
                    i++;
                }
                elem = aux.getElem();
            }
        }
        return elem;
    }

    public int localizar(Object elem) {
        int pos = -1;
        int i = 1;
        boolean encontrado;
        Nodo aux = this.cabecera;
        if (!this.esVacia()) {
            do {
                encontrado = (elem.equals(aux.getElem()));
                aux = aux.getEnlace();
                i++;
            } while (!encontrado && i <= this.longitud());

            if (encontrado) {
                pos = i - 1;
            }
        }
        return pos;
    }

    public int longitud() {
        return this.longitud;
    }

    public boolean esVacia() {
        boolean vacia = false;
        if (this.cabecera == null) {
            vacia = true;
        }
        return vacia;
    }

    public void vaciar() {
        this.cabecera = null;
        longitud = 0;
    }

    public Lista clone() {
        Lista clon = new Lista();
        if (cabecera != null) {
            Nodo aux = this.cabecera;
            Nodo nuevo = new Nodo(aux.getElem(), null);
            clon.cabecera = nuevo;
            Nodo auxClon;
            auxClon = clon.cabecera;
            aux = aux.getEnlace();

            while (aux != null) {
                nuevo = new Nodo(aux.getElem(), null);
                auxClon.setEnlace(nuevo);
                auxClon = auxClon.getEnlace();
                aux = aux.getEnlace();
            }
            clon.longitud = this.longitud;
        }
        return clon;
    }

    public String toString() {
        String cad = "";
        if (this.cabecera == null) {
            cad = "Lista vacia";
        } else {
            Nodo aux = this.cabecera;
            cad = "[ ";

            while (aux != null) {
                cad += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    cad += ", ";
                }
            }
            cad += " ]";
        }
        return cad;
    }

    public Lista obtenerMultiplos(int num) {
        Lista lis = new Lista();
        if (num <= this.longitud() && num != 0) {
            if (!esVacia()) {
                int pos = 1;
                Nodo aux, auxNueva;
                aux = this.cabecera;
                auxNueva = null;
                while (pos <= this.longitud()) {
                    if (pos % num == 0) {
                        if (auxNueva == null) { // primer elem q agrego
                            lis.cabecera = new Nodo(aux.getElem(), null);
                            auxNueva = lis.cabecera;
                        } else {
                            auxNueva.setEnlace(new Nodo(aux.getElem(), null));
                            auxNueva = auxNueva.getEnlace();
                        }
                    }
                    aux = aux.getEnlace();
                    pos++;
                }
            }
        }
        return lis;
    }

    public boolean tieneRepetidos() {
        boolean exito = false;
        if (!esVacia()) {
            Nodo aux1 = this.cabecera;
            String cad = "";
            while (aux1 != null && !exito) {
                String cad1 = "" + aux1.getElem();
                if (!cad.contains(cad1)) {
                    cad = cad + "" + aux1.getElem();

                } else {
                    exito = true;
                }
                aux1 = aux1.getEnlace();
            }
        }
        return exito;
    }

}
