package estructuras.conjuntistas;

import estructuras.linealesDinamicas.Lista;

//TDA Diccionario implementado con Arbol AVL 
public class Diccionario {

    private NodoDiccionario raiz;

    public Diccionario() {
        raiz = null;
    }

    public boolean pertenece(Comparable clave) {
        return obtenerNodo(this.raiz, clave) != null;
    }

    public Object buscar(Comparable clave) {
        NodoDiccionario buscado = obtenerNodo(this.raiz, clave);
        Object res = null;
        if (buscado != null) {
            res = buscado.getDato();
        }
        return res;
    }

    private NodoDiccionario obtenerNodo(NodoDiccionario n, Comparable buscado) {
        // busca un elemento y devuelve el nodo. Si no se encuentra devuelve null
        NodoDiccionario nodo = null;
        if (n != null) {
            if (buscado.compareTo(n.getClave()) == 0) {
                nodo = n;
            } else if (buscado.compareTo(n.getClave()) < 0) {
                nodo = obtenerNodo(n.getIzquierdo(), buscado);
            } else {
                nodo = obtenerNodo(n.getDerecho(), buscado);
            }
        }
        return nodo;
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
            exito = false;
        } else if (clave.compareTo(n.getClave()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), n, clave, dato);
            } else {
                n.setIzquierdo(new NodoDiccionario(clave, null, null, dato));
            }
        } else {
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), n, clave, dato);
            } else {
                n.setDerecho(new NodoDiccionario(clave, null, null, dato));
            }
        }
        if (exito) {
            n.recalcularAltura();
            balancear(n, padre);
        }
        return exito;
    }

    private void balancear(NodoDiccionario n, NodoDiccionario padre) {
        int balance = calcBalance(n);
        boolean rotar = false;
        NodoDiccionario result = null;
        if (balance == -2) {
            if (calcBalance(n.getDerecho()) == 1) {
                // rotacion doble der-izq
                result = rotacionDobleDerIzq(n);
                rotar = true;
            } else if (calcBalance(n.getDerecho()) <= 0) {
                // rotacion simple a izquierda
                result = rotacionSimpleIzq(n);
                rotar = true;
            }
        } else if (balance == 2) {
            if (calcBalance(n.getIzquierdo()) == -1) {
                // rotacion doble izq-der
                result = rotacionDobleIzqDer(n);
                rotar = true;
            } else if (calcBalance(n.getIzquierdo()) >= 0) {
                // rotacion simple a derecha
                result = rotacionSimpleDer(n);
                rotar = true;
            }
        }

        if (rotar) {
            if (padre != null) {
                if (n.getClave().compareTo(padre.getClave()) < 0) {
                    padre.setIzquierdo(result);
                } else {
                    padre.setDerecho(result);
                }
            } else {
                this.raiz = result;
            }
        }
    }

    private int calcBalance(NodoDiccionario n) {
        int alturaHI = -1, alturaHD = -1;
        if (n.getIzquierdo() != null) {
            alturaHI = n.getIzquierdo().getAltura();
        }
        if (n.getDerecho() != null) {
            alturaHD = n.getDerecho().getAltura();
        }
        int balance = alturaHI - alturaHD;
        return balance;
    }

    private NodoDiccionario rotacionSimpleIzq(NodoDiccionario n) {
        NodoDiccionario h = n.getDerecho();
        NodoDiccionario temp = h.getIzquierdo();
        h.setIzquierdo(n);
        n.setDerecho(temp);

        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoDiccionario rotacionSimpleDer(NodoDiccionario n) {
        NodoDiccionario h = n.getIzquierdo();
        NodoDiccionario temp = h.getDerecho();
        h.setDerecho(n);
        n.setIzquierdo(temp);

        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoDiccionario rotacionDobleIzqDer(NodoDiccionario n) {
        NodoDiccionario result, h = n.getIzquierdo();
        n.setIzquierdo(rotacionSimpleIzq(h));
        result = rotacionSimpleDer(n);
        return result;
    }

    private NodoDiccionario rotacionDobleDerIzq(NodoDiccionario n) {
        NodoDiccionario result, h = n.getDerecho();
        n.setDerecho(rotacionSimpleDer(h));
        result = rotacionSimpleIzq(n);
        return result;
    }

    public boolean eliminar(Comparable clave) {
        boolean exito = false;
        if (!this.esVacio()) {
            exito = eliminarAux(this.raiz, null, clave);
        }
        return exito;
    }

    private Boolean eliminarAux(NodoDiccionario n, NodoDiccionario padre,
            Comparable buscado) {
        boolean exito = false;
        if (n.getClave().compareTo(buscado) == 0) {
            exito = true;
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                eliminarHoja(n, padre);
            } else if (n.getIzquierdo() != null && n.getDerecho() != null) {
                eliminarConDosHijos(n, n, n.getIzquierdo());
            } else {
                if (n.getIzquierdo() != null) {
                    eliminarConUnHijo(n, padre, n.getIzquierdo());
                } else {
                    eliminarConUnHijo(n, padre, n.getDerecho());
                }
            }
        } else {
            if (n.getClave().compareTo(buscado) > 0) {
                if (n.getIzquierdo() != null) {
                    exito = eliminarAux(n.getIzquierdo(), n, buscado);
                }
            } else if (n.getDerecho() != null) {
                exito = eliminarAux(n.getDerecho(), n, buscado);
            }
        }

        if (exito) {
            n.recalcularAltura();
            balancear(n, padre);
        }
        return exito;
    }

    private void eliminarHoja(NodoDiccionario n, NodoDiccionario padre) {
        if (padre.getIzquierdo().getClave().compareTo(n.getClave()) == 0) {
            padre.setIzquierdo(null);
        } else {
            padre.setDerecho(null);
        }
    }

    private void eliminarConUnHijo(NodoDiccionario n, NodoDiccionario padre, NodoDiccionario hijo) {
        if (padre == null) {
            if (n.getIzquierdo() != null) {
                this.raiz = n.getIzquierdo();
            } else {
                this.raiz = n.getDerecho();
            }
        } else {
            if (padre.getClave().compareTo(n.getClave()) > 0) {
                padre.setIzquierdo(hijo);
            } else {
                padre.setDerecho(hijo);
            }
        }
    }

    private void eliminarConDosHijos(NodoDiccionario n, NodoDiccionario padreAux, NodoDiccionario aux) {
        if (aux.getDerecho() == null) {
            n.setClave(aux.getClave());
            n.setDato(aux.getDato());

            NodoDiccionario hijo = aux.getIzquierdo();
            if (padreAux.getDerecho().getClave().compareTo(aux.getClave()) == 0) {
                padreAux.setDerecho(hijo);
            } else {
                padreAux.setIzquierdo(hijo);
            }
        } else {
            eliminarConDosHijos(n, aux, aux.getDerecho());
        }
        aux.recalcularAltura();
        balancear(aux, padreAux);
        // aux.recalcularAltura():
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

    public boolean esVacio() {
        return this.raiz == null;
    }

    // Metodo para busqueda de estaciones que comienzan con una subcadena (Punto 7)
    public Lista obtenerInicianConSubcad(String cad) {
        Lista result = new Lista();
        if (!this.esVacio()) {
            int longitSubCad = cad.length();
            obtenerIniConSubcAux(this.raiz, cad, longitSubCad, result);
        }
        return result;
    }

    private void obtenerIniConSubcAux(NodoDiccionario n, String cad, int longitSubCad, Lista lista) {
        if (n != null) {
            String claveAcortada = ((String) n.getClave()).substring(0, longitSubCad);
            System.out.println(claveAcortada);
            if (claveAcortada.compareToIgnoreCase(cad) >= 0) {
                obtenerIniConSubcAux(n.getIzquierdo(), cad, longitSubCad, lista);
            }
            if (claveAcortada.compareToIgnoreCase(cad) == 0) {
                lista.insertar(n.getDato().toString(), lista.longitud() + 1);
            }
            if (claveAcortada.compareToIgnoreCase(cad) <= 0) {
                obtenerIniConSubcAux(n.getDerecho(), cad, longitSubCad, lista);
            }
        }
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
}
