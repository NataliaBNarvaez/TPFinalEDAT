package trenesSA;

import java.util.Scanner;
import java.util.HashMap;
import estructuras.conjuntistas.Diccionario;
import estructuras.grafo.Grafo;
import estructuras.linealesDinamicas.Lista;

public class TrenesSA {
    private static Scanner sc = new Scanner(System.in);
    private static Diccionario estaciones = new Diccionario();
    private static Diccionario trenes = new Diccionario();
    private static Grafo redRieles = new Grafo();
    private static HashMap<String, Lista> lineas = new HashMap<>();
    private static ManejadorDeArchivos manejadorDeArchivos = new ManejadorDeArchivos();

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    cargaInicial();
                    break;

                case 2:
                    abmTrenes();
                    break;

                case 3:
                    abmEstaciones();
                    break;

                case 4:
                    abmLineas();
                    break;

                case 5:
                    abmRieles();
                    break;

                case 6:
                    consultasTrenes();
                    break;

                case 7:
                    consultasEstaciones();
                    break;

                case 8:
                    consultasViajes();
                    break;

                case 9:
                    mostrarSistema();
                    break;

                default:
                    break;
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("        << TRENES SA >>");
        System.out.println("- Ingrese la accion que desea realizar -");
        System.out.println("1. Carga inicial del sistema.");
        System.out.println("2. ABM de Trenes.");
        System.out.println("3. ABM de Estaciones.");
        System.out.println("4. AMB de Lineas.");
        System.out.println("5. AMB de la Red de Rieles.");
        System.out.println("6. Consultas sobre Trenes.");
        System.out.println("7. Consultas sobre Estaciones.");
        System.out.println("8. Consultas sobre Viajes dadas dos estaciones.");
        System.out.println("9. Mostrar Sistema.");
        System.out.println("0. Salir.");
    }

    // Punto 1: Carga inicial del sistema -----------------------------
    public static void cargaInicial() {
        manejadorDeArchivos.cargaIncicialDeDatos(estaciones, trenes, redRieles, lineas);
    }

    // Punto 2: ABM de trenes -----------------------------
    public static void abmTrenes() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("        -- ABM TRENES --");
            System.out.println("1. Dar de alta un tren.");
            System.out.println("2. Dar de baja un tren.");
            System.out.println("3. Modificar un tren.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaTren();
                    break;

                case 2:
                    bajaTren();
                    break;

                case 3:
                    modifTren();
                    break;

                default:
                    break;
            }
        }
    }

    public static void altaTren() {
        System.out.println("Ingrese el ID del tren a dar de alta.");
        int id = sc.nextInt();
        sc.nextLine();

        if (trenes.pertenece(id)) {
            System.out.println(
                    "No es posible cargar el tren puesto que el ID ingresado corresponde a uno existente.");
        } else {
            String tp, lin;
            int cantPas, cantCarga;
            System.out.println("Ingrese el tipo de propulsion.");
            tp = sc.nextLine();
            System.out.println("Ingrese la cantidad de vagones para pasajeros.");
            cantPas = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de vagones para carga.");
            cantCarga = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese el nombre de la linea a la cual pertenece.");
            lin = sc.nextLine();

            if (lineas.containsKey(lin)) {
                Tren tren = new Tren(id, tp, cantPas, cantCarga, lin);
                trenes.insertar(id, tren);
                System.out.println("Se agrego exitosamente el tren con id: " + id);
                manejadorDeArchivos.escribir("Se agrego el tren con id: " + id);
            } else {
                Tren tren = new Tren(id, tp, cantPas, cantCarga, "No asignado");
                trenes.insertar(id, tren);
                System.out.println("Se agrego exitosamente el tren con id: " + id);
                manejadorDeArchivos.escribir("Se agrego el tren con id: " + id);
            }
        }
    }

    public static void bajaTren() {
        System.out.println("Ingrese el ID del tren a dar de baja.");
        int id = sc.nextInt();
        sc.nextLine();

        if (trenes.pertenece(id)) {
            if (trenes.eliminar(id)) {
                System.out.println("El tren con id: " + id + " fue eliminado correctamente.");
                manejadorDeArchivos.escribir("Se elimino el tren con id: " + id);
            } else {
                System.out.println("No se pudo eliminar el tren con id: " + id);
            }
        } else {
            System.out.println(
                    "No es posible dar de baja al tren puesto que el ID ingresado no corresponde a uno existente.");
        }
    }

    public static void modifTren() {
        System.out.println("Ingrese el ID del tren a modificar.");
        int id = sc.nextInt();
        sc.nextLine();

        if (trenes.pertenece(id)) {
            Tren tren = (Tren) trenes.buscar(id);
            System.out.println("Ingrese que dato desea modificar: ");
            System.out.println("1. El tipo de propulsion.");
            System.out.println("2. La cantidad de vagones para pasajeros.");
            System.out.println("3. La cantidad de vagones para carga.");
            System.out.println("4. La linea a la que pertenece.");
            System.out.println("0. Volver al menu principal.");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nuevo tipo de propulsion.");
                    String tProp = sc.nextLine();
                    tren.setTipoProp(tProp);
                    manejadorDeArchivos.escribir("Se modifico el tipo de propulsion a " + tProp + " del tren " + id);
                    break;

                case 2:
                    System.out.println("Ingrese la nueva cantidad de vagones para pasajeros.");
                    int cantPas = sc.nextInt();
                    sc.nextLine();
                    if (cantPas >= 0) {
                        tren.setCantVagPasajeros(cantPas);
                        manejadorDeArchivos.escribir(
                                "Se modifico la cantidad de vagones para pasajeros a " + cantPas + " del tren " + id);
                    } else {
                        System.out.println("El numero ingresado no es valido.");
                    }
                    break;

                case 3:
                    System.out.println("Ingrese la nueva cantidad de vagones para carga.");
                    int cantCarg = sc.nextInt();
                    sc.nextLine();
                    if (cantCarg >= 0) {
                        tren.setCantVagCarga(cantCarg);
                        manejadorDeArchivos.escribir(
                                "Se modifico la cantidad de vagones para carga a " + cantCarg + " del tren " + id);
                    } else {
                        System.out.println("El numero ingresado no es valido.");
                    }
                    break;

                case 4:
                    System.out.println("Ingrese la nueva linea.");
                    String linea = sc.nextLine();
                    tren.setLinea(linea);
                    manejadorDeArchivos.escribir("Se modifico la linea a " + linea + " del tren " + id);
                    break;

                default:
                    break;
            }
        } else {
            System.out.println(
                    "No es posible modificar datos del tren puesto que el ID ingresado no corresponde a uno existente.");
        }
    }

    // Punto 3: ABM de estaciones -----------------------------
    public static void abmEstaciones() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("        -- ABM ESTACIONES --");
            System.out.println("1. Dar de alta una estacion.");
            System.out.println("2. Dar de baja una estacion.");
            System.out.println("3. Modificar una estacion.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaEstacion();
                    break;

                case 2:
                    bajaEstacion();
                    break;

                case 3:
                    modifEstacion();
                    break;

                default:
                    break;
            }
        }
    }

    public static void altaEstacion() {
        System.out.println("Ingrese el nombre de la estacion a dar de alta.");
        String nombreEstacion = sc.nextLine();

        if (estaciones.pertenece(nombreEstacion)) {
            System.out.println(
                    "No es posible cargar la estacion puesto que el nombre ingresado corresponde a una existente.");
        } else {
            String ca, ci;
            int nro, codP, cantV, cantP;
            System.out.println("Ingrese el nombre de la calle.");
            ca = sc.nextLine();
            System.out.println("Ingrese el numero de la calle.");
            nro = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la ciudad.");
            ci = sc.nextLine();
            System.out.println("Ingrese el codigo postal.");
            codP = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de vias.");
            cantV = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de plataformas.");
            cantP = sc.nextInt();
            sc.nextLine();

            Estacion estacion = new Estacion(nombreEstacion, ca, nro, ci, codP, cantV, cantP);
            if (estaciones.insertar(nombreEstacion, estacion)) {
                System.out.println("Se agrego exitosamente la estacion " + nombreEstacion);
                redRieles.insertarVertice(nombreEstacion);
                manejadorDeArchivos.escribir("Se agrego la estacion " + nombreEstacion);
            } else {
                System.out.println("No se pudo agregar la estacion " + nombreEstacion);
            }
        }
    }

    public static void bajaEstacion() {
        System.out.println("Ingrese el nombre de la estacion a dar de baja.");
        String nombreEstacion = sc.nextLine();

        if (estaciones.pertenece(nombreEstacion)) {
            if (estaciones.eliminar(nombreEstacion) && redRieles.eliminarVertice(nombreEstacion)) {
                lineas.forEach((linea, estaciones) -> {
                    int pos = estaciones.localizar(nombreEstacion);
                    if (pos > 0) {
                        estaciones.eliminar(pos);
                    }
                });
                System.out.println("La estacion " + nombreEstacion + " fue eliminada correctamente.");
                manejadorDeArchivos.escribir("Se elimino la estacion " + nombreEstacion);
            } else {
                System.out.println("No se pudo eliminar la estacion " + nombreEstacion);
            }
        } else {
            System.out.println(
                    "No es posible dar de baja a la estacion puesto que el nombre ingresado no corresponde a una existente.");
        }
    }

    public static void modifEstacion() {
        System.out.println("Ingrese el nombre de la estacion a modificar.");
        String nombreEstacion = sc.nextLine();

        if (estaciones.pertenece(nombreEstacion)) {
            Estacion estacion = (Estacion) estaciones.buscar(nombreEstacion);
            System.out.println("Ingrese que dato desea modificar: ");
            System.out.println("1. La calle.");
            System.out.println("2. El numero de calle.");
            System.out.println("3. La ciudad.");
            System.out.println("4. El codigo postal.");
            System.out.println("5. La cantidad de vias.");
            System.out.println("6. La cantidad de plataformas.");
            System.out.println("0. Volver al menu principal.");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la nueva calle.");
                    String ca = sc.nextLine();
                    estacion.setCalle(ca);
                    manejadorDeArchivos.escribir("Se modifico la calle a " + ca + " de la estacion " + nombreEstacion);
                    break;

                case 2:
                    System.out.println("Ingrese el nuevo numero de calle.");
                    int nro = sc.nextInt();
                    sc.nextLine();
                    if (nro >= 0) {
                        estacion.setNro(nro);
                        manejadorDeArchivos.escribir(
                                "Se modifico el numero de calle a " + nro + " de la estacion " + nombreEstacion);
                    } else {
                        System.out.println("El numero de calle ingresado no es valido.");
                    }
                    break;

                case 3:
                    System.out.println("Ingrese la nueva ciudad.");
                    String ci = sc.nextLine();
                    estacion.setCiudad(ci);
                    manejadorDeArchivos.escribir("Se modifico la ciudad a " + ci + " de la estacion " + nombreEstacion);
                    break;

                case 4:
                    System.out.println("Ingrese el nuevo codigo postal.");
                    int codP = sc.nextInt();
                    sc.nextLine();
                    if (codP >= 0) {
                        estacion.setCodPost(codP);
                        manejadorDeArchivos.escribir(
                                "Se modifico el codigo postal a " + codP + " de la estacion " + nombreEstacion);
                    } else {
                        System.out.println("El codigo postal ingresado no es valido.");
                    }
                    break;

                case 5:
                    System.out.println("Ingrese la nueva cantidad de vias.");
                    int cantV = sc.nextInt();
                    sc.nextLine();
                    if (cantV >= 0) {
                        estacion.setCantVias(cantV);
                        manejadorDeArchivos.escribir(
                                "Se modifico la cantidad de vias a " + cantV + " de la estacion " + nombreEstacion);
                    } else {
                        System.out.println("El numero de vias ingresado no es valido.");
                    }
                    break;

                case 6:
                    System.out.println("Ingrese la nueva cantidad de plataformas.");
                    int cantP = sc.nextInt();
                    sc.nextLine();
                    if (cantP >= 0) {
                        estacion.setCantPlataformas(cantP);
                        manejadorDeArchivos.escribir("Se modifico la cantidad de plataformas a " + cantP
                                + " de la estacion " + nombreEstacion);
                    } else {
                        System.out.println("El numero de plataformas ingresado no es valido.");
                    }
                    break;

                default:
                    break;
            }
        } else {
            System.out.println(
                    "No es posible modificar datos de la estacion puesto que el nombre ingresado no corresponde a una existente.");
        }
    }

    // Punto 4: ABM de líneas -----------------------------
    public static void abmLineas() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("        -- ABM LINEAS --");
            System.out.println("1. Dar de alta una linea.");
            System.out.println("2. Dar de baja una linea.");
            System.out.println("3. Modificar una linea.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaLinea();
                    break;

                case 2:
                    bajaLinea();
                    break;

                case 3:
                    modifLinea();
                    break;

                default:
                    break;
            }
        }
    }

    public static void altaLinea() {
        System.out.println("Ingrese el nombre de la linea a dar de alta.");
        String nombreLinea = sc.nextLine();

        if (lineas.containsKey(nombreLinea)) {
            System.out.println(
                    "No es posible cargar la linea puesto que el nombre ingresado corresponde a una existente.");
        } else {
            Lista listaEstaciones = new Lista();
            System.out.println("Ingrese las estaciones pertenecientes a la linea o F para terminar.");
            String estacion = sc.nextLine();

            while (!estacion.equalsIgnoreCase("f")) {
                if (estaciones.pertenece(estacion)) {
                    listaEstaciones.insertar(estacion, listaEstaciones.longitud() + 1);
                    System.out.println("Ingrese la siguiente estacion o F para terminar.");
                    estacion = sc.nextLine();
                } else {
                    System.out.println(
                            "La estacion ingresada no se encuentra registrada, intente nuevamente o F para terminar.");
                    estacion = sc.nextLine();
                }
            }

            if (!listaEstaciones.esVacia()) {
                lineas.put(nombreLinea, listaEstaciones);
                System.out.println("Se agrego exitosamente la linea " + nombreLinea);
                manejadorDeArchivos.escribir("Se agrego la linea " + nombreLinea);
            } else {
                System.out.println("No se cargaron correctamente las estaciones. No es posible dar de alta la linea.");
            }
        }
    }

    public static void bajaLinea() {
        System.out.println("Ingrese el nombre de la linea a dar de baja.");
        String nombreLinea = sc.nextLine();

        if (lineas.containsKey(nombreLinea)) {
            lineas.remove(nombreLinea);
            System.out.println("La linea " + nombreLinea + " fue eliminada correctamente.");
            manejadorDeArchivos.escribir("Se elimino la linea " + nombreLinea);
        } else {
            System.out.println(
                    "No es posible dar de baja a la linea puesto que el nombre ingresado no corresponde a una existente.");
        }
    }

    public static void modifLinea() {
        System.out.println("Ingrese el nombre de la linea a modificar.");
        String nomLinea = sc.nextLine();
        if (lineas.containsKey(nomLinea)) {
            System.out.println("Ingrese que accion desea realizar: ");
            System.out.println("1. Agregar una estacion.");
            System.out.println("2. Eliminar una estacion.");
            System.out.println("0. Volver al menu principal.");
            int opcion = sc.nextInt();
            sc.nextLine();
            Lista listaEstaciones = lineas.get(nomLinea);
            String nomEstacion;
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre de la estacion a agregar.");
                    nomEstacion = sc.nextLine();
                    if (estaciones.pertenece(nomEstacion)) {
                        listaEstaciones.insertar(nomEstacion, listaEstaciones.longitud() + 1);
                        System.out.println(
                                "La estacion " + nomEstacion + " fue agregada exitosamente a la linea " + nomLinea);
                        manejadorDeArchivos
                                .escribir("Se agrego la estacion " + nomEstacion + " a la linea " + nomLinea);
                    } else {
                        System.out.println("La estacion ingresada no se encuentra registrada.");
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el nombre de la estacion a eliminar.");
                    nomEstacion = sc.nextLine();
                    if (estaciones.pertenece(nomEstacion)) {
                        int pos = listaEstaciones.localizar(nomEstacion);
                        if (pos != -1) {
                            listaEstaciones.eliminar(pos);
                            System.out.println("La estacion " + nomEstacion + " fue eliminada exitosamente de la linea "
                                    + nomLinea);
                            manejadorDeArchivos
                                    .escribir("Se elimino la estacion " + nomEstacion + " de la linea " + nomLinea);
                        } else {
                            System.out.println(
                                    "La estacion " + nomEstacion
                                            + " no pudo ser eliminada puesto que no pertenece a la linea "
                                            + nomLinea);
                        }
                    } else {
                        System.out.println("La estacion ingresada no se encuentra registrada.");
                    }
                    break;

                default:
                    break;
            }
        } else {
            System.out.println(
                    "No es posible modificar datos de la linea puesto que el nombre ingresado no corresponde a una existente.");
        }
    }

    // Punto 5: ABM de la red de rieles -----------------------------
    public static void abmRieles() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("        -- ABM RED DE RIELES --");
            System.out.println("1. Dar de alta un riel.");
            System.out.println("2. Dar de baja un riel.");
            System.out.println("3. Modificar un riel.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaRiel();
                    break;

                case 2:
                    bajaRiel();
                    break;

                case 3:
                    modifRiel();
                    break;

                default:
                    break;
            }
        }
    }

    public static void altaRiel() {
        System.out.println("Ingrese el nombre de la primer estacion.");
        String primEstacion = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String segEstacion = sc.nextLine();
        System.out.println("Ingrese la distancia en kilometros entre estaciones.");
        double km = sc.nextDouble();
        sc.nextLine();

        if (!estaciones.pertenece(primEstacion)) {
            System.out.println(
                    "No es posible cargar el riel puesto que la primera estacion ingresada no se encuentra registrada.");
        } else if (!estaciones.pertenece(segEstacion)) {
            System.out.println(
                    "No es posible cargar el riel puesto que la segunda estacion ingresada no se encuentra registrada.");
        } else {
            if (redRieles.insertarArco(primEstacion, segEstacion, (double) km)) {
                System.out.println("Se agrego exitosamente el riel de " + km + " km entre la estacion " + primEstacion
                        + " y la estacion " + segEstacion);
                manejadorDeArchivos.escribir("Se agrego  el riel de " + km + " km entre la estacion " + primEstacion
                        + " y la estacion " + segEstacion);
            } else {
                System.out.println("No se pudo cargar el nuevo riel.");
            }
        }
    }

    public static void bajaRiel() {
        System.out.println("Ingrese el nombre de la primer estacion.");
        String primEstacion = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String segEstacion = sc.nextLine();

        if (!estaciones.pertenece(primEstacion)) {
            System.out.println(
                    "No es posible eliminar el riel puesto que la primera estacion ingresada no se encuentra registrada.");
        } else if (!estaciones.pertenece(segEstacion)) {
            System.out.println(
                    "No es posible eliminar el riel puesto que la segunda estacion ingresada no se encuentra registrada.");
        } else {
            if (redRieles.eliminarArco(primEstacion, segEstacion)) {
                System.out.println("Se elimino exitosamente el riel entre la estacion " + primEstacion
                        + " y la estacion " + segEstacion);
                manejadorDeArchivos.escribir(
                        "Se elimino  el riel entre la estacion " + primEstacion + " y la estacion " + segEstacion);
            } else {
                System.out.println("No se pudo eliminar el riel.");
            }
        }
    }

    public static void modifRiel() {
        System.out.println("Ingrese el nombre de la primer estacion.");
        String primEstacion = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String segEstacion = sc.nextLine();
        System.out.println("Ingrese la nueva distancia en kilometros.");
        int nuevoKm = sc.nextInt();
        sc.nextLine();

        if (!estaciones.pertenece(primEstacion)) {
            System.out.println(
                    "No es posible modificar el riel puesto que la primera estacion ingresada no se encuentra registrada.");
        } else if (!estaciones.pertenece(segEstacion)) {
            System.out.println(
                    "No es posible modificar el riel puesto que la segunda estacion ingresada no se encuentra registrada.");
        } else {
            if (redRieles.modificarArco(primEstacion, segEstacion, (double) nuevoKm)) {
                System.out.println("Se modifico exitosamente el riel entre la estacion " + primEstacion
                        + " y la estacion " + segEstacion + " con nueva distancia: " + nuevoKm);
                manejadorDeArchivos.escribir("Se modifico el riel entre la estacion " + primEstacion
                        + " y la estacion " + segEstacion + " con nueva distancia: " + nuevoKm);
            } else {
                System.out.println("No se pudo modificar el riel puesto que no existe.");
            }
        }
    }

    // Punto 6: Consultas sobre trenes -----------------------------
    public static void consultasTrenes() {
        System.out.println("        -- Consultas Trenes --");
        System.out.println("1. Mostrar toda la informacion relacionada a un tren.");
        System.out.println("2. Verificar si un tren esta destinado a alguna linea y ver que ciudades visitaria.");
        System.out.println("0. Volver al menu principal.");
        int opcion = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese el ID del tren a consultar.");
        int idTren = sc.nextInt();
        sc.nextLine();
        Tren tren = (Tren) trenes.buscar(idTren);
        if (tren == null) {
            System.out.println("(!) El ID ingresado no corresponde a un tren existente.");
        } else {
            switch (opcion) {
                case 1:
                    System.out.println(tren.toString());
                    manejadorDeArchivos.escribir("Se consulto informacion del tren " + idTren);
                    break;

                case 2:
                    ciudadesVisitaTrenPorLinea(tren);
                    break;

                default:
                    break;
            }
        }
    }

    /*
     * Dado un código de tren, verificar si está destinado a alguna línea y mostrar
     * las ciudades que visitaría
     */
    public static void ciudadesVisitaTrenPorLinea(Tren tren) {
        String linea = tren.getLinea();
        Lista listaEstaciones = lineas.get(linea);
        if (listaEstaciones != null) {
            Lista result = new Lista();
            int longit = listaEstaciones.longitud();
            for (int i = 1; i <= longit; i++) {
                String nombreEst = (String) listaEstaciones.recuperar(i);
                Estacion estacion = (Estacion) estaciones.buscar(nombreEst);
                if (estacion != null && result.localizar(estacion.getCiudad()) == -1) {
                    result.insertar(estacion.getCiudad(), result.longitud() + 1);
                }
            }
            System.out.println("Ciudades que recorre el tren " + tren.getId() + " por la linea " + linea + ": ");
            System.out.println(result.toString());
            manejadorDeArchivos.escribir("Se consultaron las ciudades que visita el tren por la linea " + linea);
        } else {
            System.out.println("El tren no esta asignado a ninguna linea.");
        }
    }

    // Punto 7: Consultas sobre estaciones -----------------------------
    public static void consultasEstaciones() {
        System.out.println("        -- Consultas Estaciones --");
        System.out.println("1. Mostrar toda la informacion relacionada a una estacion.");
        System.out
                .println("2. Dada una cadena, obtener todas las estaciones cuyo nombre comienzan con dicha subcadena.");
        System.out.println("0. Volver al menu principal.");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nombre de la estacion a consultar.");
                String nomEstacion = sc.nextLine();
                Estacion estacion = (Estacion) estaciones.buscar(nomEstacion);
                if (estacion == null) {
                    System.out.println(
                            "(!) El nombre ingresado no corresponde a una estacion existente.");
                } else {
                    System.out.println(estacion.toString());
                    manejadorDeArchivos.escribir("Se consulto informacion de la estacion " + nomEstacion);
                }
                break;

            case 2:
                estacionesNombreIniciaConSubcadena();
                break;

            default:
                break;
        }
    }

    /*
     * Dada una cadena, devolver todas las estaciones cuyo nombre comienza con
     * dicha subcadena (por ejemplo si la cadena es “Villa” debería considerar
     * listar el rango desde “Villa” hasta “VillaZZZZ”)
     */
    public static void estacionesNombreIniciaConSubcadena() {
        System.out.println(
                "Ingrese la palabra inicial por la cual se buscaran las estaciones que la contengan al inicio.");
        String subcad = sc.nextLine();
        Lista estacionesConSubcad = estaciones.obtenerInicianConSubcad(subcad);
        if (!estacionesConSubcad.esVacia()) {
            System.out.println("Estaciones que comienzan con \"" + subcad + "\":");
            int longit = estacionesConSubcad.longitud();
            for (int i = 1; i <= longit; i++) {
                System.out.println("- " + estacionesConSubcad.recuperar(i));
            }
            manejadorDeArchivos.escribir("Se consultaron las estaciones cuyo nombre comienzan con \"" + subcad + "\"");
        } else {
            System.out.println("No existen estaciones que comiencen con \"" + subcad + "\".");
        }
    }

    // Punto 8: Consultas sobre viajes -----------------------------
    public static void consultasViajes() {
        System.out.println("        -- Consultas Viajes --");
        System.out.println("Dadas dos estaciones: ");
        System.out.println("1. Obtener el camino que pase por menos estaciones.");
        System.out.println("2. Obtener el camino que recorra la menor cantidad de kilometros.");
        System.out.println("3. Obtener todos los caminos posibles sin pasar por una estacion X.");
        System.out.println("4. Verificar si existe un camino con un limite maximo de kilometros.");
        System.out.println("0. Volver al menu principal.");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                caminoMenosEstaciones();
                break;

            case 2:
                caminoMenorDistancia();
                break;

            case 3:
                todosCaminosSinEstacion();
                break;

            case 4:
                caminoLimiteKm();
                break;

            default:
                break;
        }
    }

    /* Obtener el camino que llegue de A a B que pase por menos estaciones */
    public static void caminoMenosEstaciones() {
        System.out.println("Ingrese el nombre de la primera estacion.");
        String estacionA = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String estacionB = sc.nextLine();
        if (!estaciones.pertenece(estacionA)) {
            System.out.println("No es posible continuar puesto que la primer estacion no se encuentra registrada.");
        } else if (!estaciones.pertenece(estacionB)) {
            System.out.println("No es posible continuar puesto que la segunda estacion no se encuentra registrada.");
        } else {
            Lista camino = redRieles.caminoMasCorto(estacionA, estacionB);
            if (camino.esVacia()) {
                System.out.println("No existe un camino entre las estaciones" + estacionA + " y " + estacionB);
            } else {
                System.out
                        .println("El camino mas corto entre las estaciones " + estacionA + " y " + estacionB + " es:");
                System.out.println(camino.toString());
                manejadorDeArchivos.escribir(
                        "Se consulto el camino mas corto entre las estaciones " + estacionA + " y " + estacionB);
            }
        }
    }

    /* Obtener el camino que llegue de A a B de menor distancia en kilómetros */
    public static void caminoMenorDistancia() {
        System.out.println("Ingrese el nombre de la primera estacion.");
        String estacionA = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String estacionB = sc.nextLine();
        if (!estaciones.pertenece(estacionA)) {
            System.out.println("No es posible continuar puesto que la primer estacion no se encuentra registrada.");
        } else if (!estaciones.pertenece(estacionB)) {
            System.out.println("No es posible continuar puesto que la segunda estacion no se encuentra registrada.");
        } else {
            Lista camino = redRieles.caminoMenorDistancia(estacionA, estacionB);
            if (camino.esVacia()) {
                System.out.println("No existe un camino entre las estaciones" + estacionA + " y " + estacionB);
            } else {
                System.out
                        .println("El camino con menor distancia de kilometros entre las estaciones " + estacionA + " y "
                                + estacionB + " es:");
                System.out.println(camino.toString());
                manejadorDeArchivos
                        .escribir("Se consulto el camino con menor distancia de kilometros entre las estaciones "
                                + estacionA + " y " + estacionB);
            }
        }
    }

    /*
     * Obtener todos los caminos posibles para llegar de A a B sin pasar por una
     * estación C dada
     */
    public static void todosCaminosSinEstacion() {
        System.out.println("Ingrese el nombre de la primera estacion.");
        String estacionA = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String estacionB = sc.nextLine();
        System.out.println("Ingrese el nombre de la estacion que desea evitar.");
        String estacionC = sc.nextLine();
        if (!estaciones.pertenece(estacionA)) {
            System.out.println("No es posible continuar puesto que la primer estacion no se encuentra registrada.");
        } else if (!estaciones.pertenece(estacionB)) {
            System.out.println("No es posible continuar puesto que la segunda estacion no se encuentra registrada.");
        } else if (!estaciones.pertenece(estacionC)) {
            System.out.println("No es posible continuar puesto que la tercer estacion no se encuentra registrada.");
        } else {
            Lista camino = redRieles.todosCaminosSinEstacion(estacionA, estacionB, estacionC);
            if (camino.esVacia()) {
                System.out.println("No existe un camino entre las estaciones " + estacionA + " y " + estacionB
                        + " sin que pase por " + estacionC);
            } else {
                System.out
                        .println("Los caminos entre las estaciones " + estacionA + " y "
                                + estacionB + " sin pasar por " + estacionC + " son:");
                int longit = camino.longitud();
                for (int i = 1; i <= longit; i++) {
                    System.out.println("- " + camino.recuperar(i).toString());
                }
                manejadorDeArchivos.escribir("Se consultaron todos los caminos entre las estaciones " + estacionA
                        + " y " + estacionB + " sin pasar por " + estacionC);
            }
        }
    }

    /*
     * Verificar si es posible llegar de A a B recorriendo como máximo una cantidad
     * X de kilómetros
     */
    public static void caminoLimiteKm() {
        System.out.println("Ingrese el nombre de la primera estacion.");
        String estacionA = sc.nextLine();
        System.out.println("Ingrese el nombre de la segunda estacion.");
        String estacionB = sc.nextLine();
        System.out.println("Ingrese el limite maximo de kilometros.");
        int limiteKm = sc.nextInt();
        sc.nextLine();
        if (!estaciones.pertenece(estacionA)) {
            System.out.println("No es posible continuar puesto que la primer estacion no se encuentra registrada.");
        } else if (!estaciones.pertenece(estacionB)) {
            System.out.println("No es posible continuar puesto que la segunda estacion no se encuentra registrada.");
        } else if (limiteKm < 1) {
            System.out.println("No es posible continuar puesto que el numero ingresado no es valido.");
        } else {
            Lista camino = redRieles.caminoLimiteKm(estacionA, estacionB, limiteKm);
            if (camino.esVacia()) {
                System.out.println("No existe un camino entre las estaciones " + estacionA + " y " + estacionB
                        + " sin superar los " + limiteKm + " kilometros.");
            } else {
                System.out
                        .println("Existe un camino entre las estaciones " + estacionA + " y "
                                + estacionB + " con un limite de " + limiteKm + " kilometros.");
                System.out.println(camino.toString());
                manejadorDeArchivos.escribir("Se consulto si existe un camino entre las estaciones " + estacionA + " y "
                        + estacionB + " con un limite de " + limiteKm + " kilometros.");
            }
        }
    }

    // Punto 9: Mostrar estructura del sistema -----------------------------
    public static void mostrarSistema() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("        -- Muestra del Sistema --");
            System.out.println("1. Consultar estructura de Trenes.");
            System.out.println("2. Consultar estructura de Estaciones.");
            System.out.println("3. Consultar estructura de Lineas.");
            System.out.println("4. Consultar estructura de Red de Rieles.");
            System.out.println("0. Volver al menu principal.");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println(trenes.toString());
                    manejadorDeArchivos.escribir("Se consulto la estructura de los trenes.");
                    break;

                case 2:
                    System.out.println(estaciones.toString());
                    manejadorDeArchivos.escribir("Se consulto la estructura de las estaciones.");
                    break;

                case 3:
                    if (lineas.isEmpty()) {
                        System.out.println("No hay informacion disponible sobre las lineas.");
                    } else {
                        lineas.forEach((linea, estaciones) -> System.out
                                .println("Linea: " + linea + ", Pasa por estaciones: " + estaciones.toString()));
                    }
                    manejadorDeArchivos.escribir("Se consulto la estructura de las lineas.");
                    break;

                case 4:
                    System.out.println(redRieles.toString());
                    manejadorDeArchivos.escribir("Se consulto la estructura de la red de rieles.");
                    break;

                default:
                    break;
            }
        }
    }
}