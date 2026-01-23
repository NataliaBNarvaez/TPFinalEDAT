package conjuntistas;

import linealesDinamicas.Lista;

//Arbol AVL
public class Diccionario {

    private NodoDiccionario raiz;

    public Diccionario() {
        raiz = null;
    }

    public boolean pertenece(Comparable clave) {
        boolean pertenece = false;
        if (!this.esVacio()) {
            pertenece = perteneceAux(this.raiz, clave);
        }
        return pertenece;
    }

    private boolean perteneceAux(NodoDiccionario n, Comparable clave) {
        boolean pertenece = false;
        if (n != null) {
            if (clave.compareTo(n.getClave()) == 0) { // si lo encontro
                pertenece = true;
            } else if (clave.compareTo(n.getClave()) < 0) { // si el elem es menor bajo a HI
                pertenece = perteneceAux(n.getIzquierdo(), clave);
            } else { // si el elem es mayor va a HD
                pertenece = perteneceAux(n.getDerecho(), clave);
            }
        }
        return pertenece;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoDiccionario(clave, null, null, dato);
        } else {
            exito = insertarAux(this.raiz, null, clave, dato);
        }
        return exito;
    }

    private boolean insertarAux(NodoDiccionario n, NodoDiccionario padre, Comparable clave, Object dato) {
        boolean exito = true;

        if ((clave.compareTo(n.getClave()) == 0)) {
            // elemento repetido
            exito = false;
        } else if (clave.compareTo(n.getClave()) < 0) {
            /*
             * elemento es menor que la clave de n
             * si tiene HI baja a la izquierda, sino agrega elemento
             */
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), n, clave, dato);
            } else {
                n.setIzquierdo(new NodoDiccionario(clave, null, null, dato));
            }
        } else {
            /*
             * elemento es mayor que la clave de n
             * si tiene HD baja a la derecha, sino agrega elemento
             */
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), n, clave, dato);
            } else {
                n.setDerecho(new NodoDiccionario(clave, null, null, dato));
            }
        }
        if (exito) {
            balancear(n, padre);
        }
        return exito;
    }

    private void balancear(NodoDiccionario n, NodoDiccionario padre) {
        int balance = balance(n);
        if (balance == -2) {
            // casos de ==1 o ==-1
        } else if (balance == 2) {
            // casos de ==1 o ==-1
        }
    }

    private int balance(NodoDiccionario n) {
        int alturaHI = -1;
        int alturaHD = -1;
        if (n.getIzquierdo() != null) {
            alturaHI = n.getIzquierdo().getAltura();
        }
        if (n.getDerecho() != null) {
            alturaHD = n.getDerecho().getAltura();
        }
        int balance = alturaHI - alturaHD;
        return balance;
    }

    public boolean eliminar(Comparable clave) {
        boolean exito = false;
        if (!this.esVacio()) {

        }
        return exito;
    }

    private Boolean eliminarAux(NodoDiccionario n, NodoDiccionario padre, Comparable buscado) {
        boolean exito = false;
        if (n != null) {

        }
        return exito;
    }

    public Lista listar() {
        Lista lis = new Lista();
        if (!this.esVacio()) {
            listarAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarAux(NodoDiccionario n, Lista lis) {
        if (n != null) {
            listarAux(n.getIzquierdo(), lis);
            lis.insertar(n.getClave(), lis.longitud() + 1);
            listarAux(n.getDerecho(), lis);
        }
    }

    public Lista listarRango(Comparable elMin, Comparable elMax) {
        Lista lis = new Lista();
        if (!this.esVacio()) {
            listarRangoAux(this.raiz, elMin, elMax, lis);
        }
        return lis;
    }

    private void listarRangoAux(NodoDiccionario n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {
            if (n.getClave().compareTo(min) > 0) {
                listarRangoAux(n.getIzquierdo(), min, max, lis);
            }
            if (n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) <= 0) {
                lis.insertar(n.getClave(), lis.longitud() + 1);
            }
            if (n.getClave().compareTo(max) < 0) {
                listarRangoAux(n.getDerecho(), min, max, lis);
            }
        }
    }

    public Comparable minimoElem() {
        Comparable min = null;
        if (!esVacio()) {
            min = minimoElemAux(this.raiz);
        }
        return min;
    }

    private Comparable minimoElemAux(NodoDiccionario n) {
        Comparable min;
        if (n.getIzquierdo() == null) {
            min = n.getClave();
        } else {
            min = minimoElemAux(n.getIzquierdo());
        }
        return min;
    }

    public Comparable maximoElem() {
        Comparable max = null;
        if (!esVacio()) {
            max = maximoElemAux(this.raiz);
        }
        return max;
    }

    private Comparable maximoElemAux(NodoDiccionario n) {
        Comparable max;
        if (n.getDerecho() == null) {
            max = n.getClave();
        } else {
            max = minimoElemAux(n.getDerecho());
        }
        return max;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    private NodoDiccionario obtenerNodo(NodoDiccionario n, Comparable buscado) {
        // busca un elemento y devuelve el nodo. Si no se encuentra devuelve null
        NodoDiccionario nodo = null;
        if (n != null) {
            if (buscado.compareTo(n.getClave()) == 0) {
                // si lo encontro
                nodo = n;
            } else if (buscado.compareTo(n.getClave()) < 0) {
                // si el elem es menor bajo a HI
                nodo = obtenerNodo(n.getIzquierdo(), buscado);
            } else {
                // si el elem es mayor va a HD
                nodo = obtenerNodo(n.getDerecho(), buscado);
            }
        }
        return nodo;
    }

    public Diccionario clone() {
        Diccionario clon = new Diccionario();
        if (!this.esVacio()) {
            NodoDiccionario aux = new NodoDiccionario(this.raiz.getClave(), null, null, null);
            clon.raiz = aux;
            cloneAux(aux, this.raiz);
        }
        return clon;
    }

    private void cloneAux(NodoDiccionario aux, NodoDiccionario n) {
        if (n != null) {
            if (n.getIzquierdo() != null) {
                aux.setIzquierdo(new NodoDiccionario(n.getIzquierdo().getClave(), null, null, null));
                cloneAux(aux.getIzquierdo(), n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                aux.setDerecho(new NodoDiccionario(n.getDerecho().getClave(), null, null, null));
                cloneAux(aux.getDerecho(), n.getDerecho());
            }
        }
    }

    public String toString() {
        String cad = "Diccionario vacio";
        if (!esVacio()) {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoDiccionario n) {
        String cad = "";
        if (n != null) {
            cad = cad + "nodo: " + n.getClave();
            if (n.getIzquierdo() != null) {
                cad = cad + " HI: " + n.getIzquierdo().getClave();
            } else {
                cad = cad + " HI: -";
            }
            if (n.getDerecho() != null) {
                cad = cad + " HD: " + n.getDerecho().getClave();
            } else {
                cad = cad + " HD: -";
            }
            cad = cad + '\n';
            if (n.getIzquierdo() != null) {
                cad = cad + toStringAux(n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                cad = cad + toStringAux(n.getDerecho());
            }
        }
        return cad;
    }
}
