package grafo;

import linealesDinamicas.Lista;

public class Grafo {
    private NodoVertice inicio = null;

    public boolean insertarVertice(Object vertice) {
        boolean exito = false;
        NodoVertice aux = this.buscarVertice(vertice);
        if (aux == null) {
            this.inicio = new NodoVertice(vertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVertice buscarVertice(Object buscado) {
        NodoVertice encontrado = this.inicio;
        while (encontrado != null && !encontrado.getElem().equals(buscado)) {
            encontrado = encontrado.getSigVertice();
        }
        return encontrado;
    }

    public boolean eliminarVertice(Object elim) {
        boolean exito = false;
        NodoVertice aux = this.inicio;
        if (aux.getElem().equals(elim)) { // si es el primero lo borro sin buscar nada
            eliminarVerticeAux(aux);
            this.inicio = inicio.getSigVertice();
            exito = true;

        } else {
            NodoVertice buscado = aux.getSigVertice();
            while (buscado != null && !buscado.getElem().equals(elim)) {
                aux = buscado;
                buscado = buscado.getSigVertice();
            }
            if (buscado != null) {
                eliminarVerticeAux(buscado);
                aux.setSigVertice(buscado.getSigVertice()); // eliminados los arcos, quito el nodo vertice
                exito = true;
            }
        }
        return exito;
    }

    private void eliminarVerticeAux(NodoVertice aux) {
        NodoAdy adyacentes = aux.getPrimerAdy();
        NodoVertice borrarleArco;
        while (adyacentes != null) {
            borrarleArco = adyacentes.getVertice();
            elimArcoAux(borrarleArco, aux);
            adyacentes = adyacentes.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object vertice) {
        return buscarVertice(vertice) != null;
    }

    public boolean insertarArco(Object nUno, Object nDos, Object etiqueta) {
        boolean exito = false;
        NodoVertice nodoUno = buscarVertice(nUno);
        NodoVertice nodoDos = buscarVertice(nDos);
        if (nodoUno != null && nodoDos != null) {
            nodoUno.setPrimerAdy(new NodoAdy(nodoDos, nodoUno.getPrimerAdy(), etiqueta));
            nodoDos.setPrimerAdy(new NodoAdy(nodoUno, nodoDos.getPrimerAdy(), etiqueta));
            exito = true;
        }
        return exito;
    }

    public boolean eliminarArco(Object nUno, Object nDos) {
        boolean exito = false;
        NodoVertice nodoUno = buscarVertice(nUno);
        NodoVertice nodoDos = buscarVertice(nDos);
        if (nodoUno != null && nodoDos != null) {
            if (elimArcoAux(nodoUno, nodoDos)) {
                exito = elimArcoAux(nodoDos, nodoUno);
            }
        }
        return exito;
    }

    private boolean elimArcoAux(NodoVertice nUno, NodoVertice nDos) {
        boolean eliminado = false;
        NodoAdy aux = nUno.getPrimerAdy();
        NodoAdy sigAux = aux.getSigAdyacente();

        if (aux.getVertice().getElem().equals(nDos.getElem())) {
            nUno.setPrimerAdy(sigAux);
            eliminado = true;
        } else {
            while (sigAux != null && !eliminado) {
                if (sigAux.getVertice().getElem().equals(nDos.getElem())) {
                    aux.setSigAdyacente(sigAux.getSigAdyacente());
                    eliminado = true;
                } else {
                    aux = sigAux;
                    sigAux = sigAux.getSigAdyacente();
                }
            }
        }
        return eliminado;
    }

    public boolean existeArco(Object nUNo, Object nDos) {
        boolean exito = false;
        NodoVertice nodoUno = buscarVertice(nUNo);
        NodoVertice nodoDos = buscarVertice(nDos);
        if (nodoUno != null && nodoDos != null) {
            exito = existeArcoAux(nodoUno, nodoDos);
        }
        return exito;
    }

    private boolean existeArcoAux(NodoVertice nUno, NodoVertice nDos) {
        boolean existe = false;
        NodoAdy aux = nUno.getPrimerAdy();
        while (aux != null && !existe) {
            if (aux.getVertice().getElem().equals(nDos.getElem())) {
                existe = true;
            } else {
                aux = aux.getSigAdyacente();
            }
        }
        return existe;
    }

    public boolean vacio() {
        return this.inicio == null;
    }

    public String toString() {
        String grafo = "";
        NodoVertice aux = this.inicio;
        while (aux != null) {
            NodoAdy adyAux = aux.getPrimerAdy();
            while (adyAux != null) {
                grafo += aux.getElem().toString() + " --" + adyAux.getEtiqueta().toString() + "--> "
                        + adyAux.getVertice().getElem().toString();
                adyAux = adyAux.getSigAdyacente();
                if (adyAux != null) {
                    grafo += "  ,  ";
                }
            }
            grafo += "\n";
            aux = aux.getSigVertice();
        }
        return grafo;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean existe = false;
        NodoVertice aux = this.inicio;
        NodoVertice auxOrigen = null;
        NodoVertice auxDestino = null;

        while ((auxOrigen == null || auxDestino == null) && aux != null) {
            if (aux.getElem().equals(origen))
                auxOrigen = aux;
            if (aux.getElem().equals(destino))
                auxDestino = aux;
            aux = aux.getSigVertice();
        }

        if (auxOrigen != null && auxDestino != null) {
            Lista visitados = new Lista();
            existe = existeCaminoAux(auxOrigen, destino, visitados);
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVertice n, Object destino, Lista visit) {
        boolean existe = false;

        return existe;
    }

    public Lista camioMasCorto() {
        Lista resultado = new Lista();

        return resultado;
    }
}
