package trenesSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import estructuras.conjuntistas.Diccionario;
import estructuras.grafo.Grafo;
import estructuras.linealesDinamicas.Lista;

public class ManejadorDeArchivos {

    private String rutaCargaIni = "datos/cargaInicial.txt";
    private String rutaLog = "datos/log.txt";

    public ManejadorDeArchivos() {
    }

    public void escribir(String linea) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaLog, true))) {
            escritor.write(linea);
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void cargaIncicialDeDatos(Diccionario estaciones, Diccionario trenes, Grafo redRieles,
            HashMap<String, Lista> lineas) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(rutaCargaIni))) {
            String linea;
            escribir("[ CARGA INICIAL DE DATOS ]");
            while ((linea = bufferedReader.readLine()) != null) {
                char tipo = linea.charAt(0);
                switch (tipo) {
                    case 'E':
                        cargarEstacion(linea, estaciones, redRieles);
                        break;

                    case 'L':
                        cargarLinea(linea, lineas);
                        break;

                    case 'R':
                        cargarRiel(linea, redRieles);
                        break;

                    case 'T':
                        cargarTren(linea, trenes);
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private void cargarEstacion(String linea, Diccionario estaciones, Grafo redRieles) {
        String[] datos = linea.split(";");
        // nom estación, calle, número, ciudad, cod postal, cant vías, cant plataformas
        Estacion estacion = new Estacion(datos[1], datos[2], Integer.parseInt(datos[3]), datos[4],
                Integer.parseInt(datos[5]), Integer.parseInt(datos[6]), Integer.parseInt(datos[7]));
        estaciones.insertar(datos[1], estacion);
        redRieles.insertarVertice(estacion);
    }

    private void cargarLinea(String linea, HashMap<String, Lista> lineas) {
        String[] datos = linea.split(";");
        // nombre de la línea y nombre de las estaciones por las que pasa
        Lista listaEstaciones = new Lista();
        int longit = datos.length;
        if (longit >= 3) {
            for (int i = 2; i <= longit; i++) {
                listaEstaciones.insertar(datos[i], listaEstaciones.longitud() + 1);
            }
            lineas.put(datos[1], listaEstaciones);
        }
    }

    private void cargarRiel(String linea, Grafo redRiles) {
        String[] datos = linea.split(";");
        // nom de las estaciones que se conectan y la distancia en km entre ellas
        double km = Double.parseDouble(datos[3]);
        if (km > 0) {
            redRiles.insertarArco(datos[1], datos[2], km);
        }
    }

    private void cargarTren(String linea, Diccionario trenes) {
        String[] datos = linea.split(";");
        /*
         * código único numérico, tipo de propulsión, cantidad de vagones para
         * pasajeros,
         * cantidad de vagones para carga y línea en la que está siendo utilizado (si el
         * tren no está
         * destinado a ninguna línea se considerará libre o no-asignado)
         */
        Tren tren = new Tren(Integer.parseInt(datos[1]), datos[2], Integer.parseInt(datos[3]),
                Integer.parseInt(datos[4]), datos[5]);
        trenes.insertar(Integer.parseInt(datos[1]), tren);
    }
}
