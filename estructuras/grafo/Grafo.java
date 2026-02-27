package estructuras.grafo;

import estructuras.linealesDinamicas.Lista;

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
            elimArcoAux(borrarleArco, aux.getElem());
            adyacentes = adyacentes.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object vertice) {
        return buscarVertice(vertice) != null;
    }

    public boolean insertarArco(Object nUno, Object nDos, Double etiqueta) {
        boolean exito = false;
        NodoVertice nodoUno = buscarVertice(nUno);
        NodoVertice nodoDos = buscarVertice(nDos);
        if (nodoUno != null && nodoDos != null && !existeArco(nUno, nDos)) {
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
            if (elimArcoAux(nodoUno, nDos)) {
                exito = elimArcoAux(nodoDos, nUno);
            }
        }
        return exito;
    }

    private boolean elimArcoAux(NodoVertice nUno, Object nDos) {
        boolean eliminado = false;
        NodoAdy aux = nUno.getPrimerAdy();
        if (aux != null) {
            NodoAdy sigAux = aux.getSigAdyacente();

            if (aux.getVertice().getElem().equals(nDos)) {
                nUno.setPrimerAdy(sigAux);
                eliminado = true;
            } else {
                while (sigAux != null && !eliminado) {
                    if (sigAux.getVertice().getElem().equals(nDos)) {
                        aux.setSigAdyacente(sigAux.getSigAdyacente());
                        eliminado = true;
                    } else {
                        aux = sigAux;
                        sigAux = sigAux.getSigAdyacente();
                    }
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

    public boolean modificarArco(Object origen, Object destino, Double nuevaEtiq) {
        boolean exitoA = false, exitoB = false;
        NodoVertice nOrigen = buscarVertice(origen);
        NodoVertice nDestino = buscarVertice(destino);
        NodoAdy adyOrigen = nOrigen.getPrimerAdy();
        NodoAdy adyDestino = nDestino.getPrimerAdy();

        if (nOrigen != null && nDestino != null) {
            while ((adyOrigen != null && !exitoA)) {
                if (adyOrigen.getVertice().getElem().equals(destino)) {
                    exitoA = true;
                } else {
                    adyOrigen = adyOrigen.getSigAdyacente();
                }
            }
            while (adyDestino != null && !exitoB) {
                if (adyDestino.getVertice().getElem().equals(origen)) {
                    exitoB = true;
                } else {
                    adyDestino = adyDestino.getSigAdyacente();
                }
            }
        }
        if (exitoA && exitoB) {
            adyOrigen.setEtiqueta(nuevaEtiq);
            adyDestino.setEtiqueta(nuevaEtiq);
        }
        return exitoA && exitoB;
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
            System.out.println(visitados.toString());
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVertice n, Object destino, Lista visit) {
        boolean existe = false;
        if (n != null) {
            if (n.getElem().equals(destino)) {
                existe = true;
            } else {
                visit.insertar(n.getElem(), visit.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!existe && ady != null) {
                    if (visit.localizar(ady.getVertice().getElem()) < 0) {
                        existe = existeCaminoAux(ady.getVertice(), destino, visit);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return existe;
    }

    public String toString() {
        String grafo = "";
        NodoVertice aux = this.inicio;
        while (aux != null) {
            NodoAdy adyAux = aux.getPrimerAdy();
            if (adyAux == null) {
                grafo += aux.getElem().toString();
            }
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

    public boolean vacio() {
        return this.inicio == null;
    }

    // Metodos punto 8
    /* Obtener el camino que llegue de A a B que pase por menos estaciones */
    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista resultado = new Lista();
        Lista visitados = new Lista();
        NodoVertice nOrigen = buscarVertice(origen);
        NodoVertice nDestino = buscarVertice(destino);

        if (nOrigen != nDestino && nOrigen != null && nDestino != null) {
            resultado = caminoMasCortoAux(nOrigen, destino, resultado, visitados);
        }
        return resultado;
    }

    private Lista caminoMasCortoAux(NodoVertice n, Object destino, Lista resultado, Lista visitados) {
        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        if (n.getElem().equals(destino)) {
            resultado = visitados.clone();
        } else {
            NodoAdy nAdy = n.getPrimerAdy();
            while (nAdy != null) {
                NodoVertice nVert = nAdy.getVertice();
                if ((visitados.longitud() + 1 < resultado.longitud() || resultado.esVacia())
                        && visitados.localizar(nVert.getElem()) == -1) {
                    resultado = caminoMasCortoAux(nVert, destino, resultado, visitados);
                }
                nAdy = nAdy.getSigAdyacente();
            }
        }
        visitados.eliminar(visitados.longitud());
        return resultado;
    }

    /* Obtener el camino que llegue de A a B de menor distancia en kilómetros */
    public Lista caminoMenorDistancia(Object origen, Object destino) {
        Lista resultado = new Lista();
        Lista visitados = new Lista();
        double[] kmActuales = { 0 };
        double[] kmResultado = { 0 };
        NodoVertice nOrigen = buscarVertice(origen);
        NodoVertice nDestino = buscarVertice(destino);

        if (nOrigen != nDestino && nOrigen != null && nDestino != null) {
            resultado = caminoMenorDistanciaAux(nOrigen, destino, kmActuales, kmResultado, 0, resultado, visitados);
        }
        return resultado;
    }

    private Lista caminoMenorDistanciaAux(NodoVertice n, Object destino, double[] kmActuales,
            double[] kmResultado, double suma, Lista resultado, Lista visitados) {
        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        kmActuales[0] = kmActuales[0] + suma;

        if (n.getElem().equals(destino)) {
            resultado = visitados.clone();
            kmResultado[0] = kmActuales[0];
        } else {
            NodoAdy nAdy = n.getPrimerAdy();
            while (nAdy != null) {
                NodoVertice nVert = nAdy.getVertice();
                double pesoEtiqueta = (double) nAdy.getEtiqueta();

                if ((kmActuales[0] + pesoEtiqueta < kmResultado[0] || resultado.esVacia())
                        && visitados.localizar(nVert.getElem()) == -1) {
                    resultado = caminoMenorDistanciaAux(nVert, destino, kmActuales, kmResultado, pesoEtiqueta,
                            resultado, visitados);
                }
                nAdy = nAdy.getSigAdyacente();
            }
        }

        kmActuales[0] = kmActuales[0] - suma;
        visitados.eliminar(visitados.longitud());
        return resultado;
    }

    /*
     * Obtener todos los caminos posibles para llegar de A a B sin pasar por una
     * estación C dada
     */
    public Lista todosCaminosSinEstacion(Object origen, Object destino, Object evitar) {
        Lista resultado = new Lista();
        Lista visitados = new Lista();
        NodoVertice nOrigen = buscarVertice(origen);
        NodoVertice nDestino = buscarVertice(destino);
        NodoVertice nEvitar = buscarVertice(evitar);

        if (nOrigen != nDestino && nOrigen != nEvitar && nDestino != nEvitar && nOrigen != null && nDestino != null
                && nEvitar != null) {
            todosCaminosSinEstacionAux(nOrigen, destino, evitar, resultado, visitados);
        }
        return resultado;
    }

    private void todosCaminosSinEstacionAux(NodoVertice n, Object destino, Object evitar, Lista resultado,
            Lista visitados) {
        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        if (n.getElem().equals(destino)) {
            resultado.insertar(visitados.clone(), resultado.longitud() + 1);
        } else {
            NodoAdy nAdy = n.getPrimerAdy();
            while (nAdy != null) {
                NodoVertice nVert = nAdy.getVertice();
                if (!nVert.getElem().equals(evitar) && visitados.localizar(nVert.getElem()) == -1) {
                    todosCaminosSinEstacionAux(nVert, destino, evitar, resultado, visitados);
                }
                nAdy = nAdy.getSigAdyacente();
            }
        }
        visitados.eliminar(visitados.longitud());
    }

    /*
     * Verificar si es posible llegar de A a B recorriendo como máximo una cantidad
     * X de kilómetros
     */
    public Lista caminoLimiteKm(Object origen, Object destino, double limiteKm) {
        Lista resultado = new Lista();
        Lista visitados = new Lista();
        double[] kmActuales = { 0 };
        NodoVertice nOrigen = buscarVertice(origen);
        NodoVertice nDestino = buscarVertice(destino);

        if (nOrigen != nDestino && nOrigen != null && nDestino != null) {
            resultado = caminoLimiteKmAux(nOrigen, destino, kmActuales, 0.0, limiteKm, resultado, visitados);
        }
        return resultado;
    }

    private Lista caminoLimiteKmAux(NodoVertice n, Object destino, double[] kmActuales, double suma, double limiteKm,
            Lista resultado, Lista visitados) {
        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        kmActuales[0] = kmActuales[0] + suma;

        if (n.getElem().equals(destino)) {
            resultado = visitados.clone();
        } else {
            NodoAdy nAdy = n.getPrimerAdy();
            while (nAdy != null && resultado.esVacia()) {
                NodoVertice nVert = nAdy.getVertice();
                double pesoEtiqueta = (double) nAdy.getEtiqueta();
                if (kmActuales[0] + pesoEtiqueta < limiteKm && visitados.localizar(nVert.getElem()) == -1) {
                    resultado = caminoLimiteKmAux(nVert, destino, kmActuales, pesoEtiqueta, limiteKm,
                            resultado, visitados);
                }
                nAdy = nAdy.getSigAdyacente();
            }
        }

        kmActuales[0] = kmActuales[0] - suma;
        visitados.eliminar(visitados.longitud());
        return resultado;
    }
}
