package trenesSA;

import estructuras.conjuntistas.Diccionario;
import estructuras.linealesDinamicas.Lista;

public class Prueba {
    public static void main(String[] args) {
        Diccionario dic = new Diccionario();
        Estacion a = new Estacion("neuquen", "null", 0, 0, 0, 0);
        Estacion b = new Estacion("neuaaa", "null", 0, 0, 0, 0);
        Estacion c = new Estacion("cipoletti", "null", 0, 0, 0, 0);
        Estacion d = new Estacion("roca", "null", 0, 0, 0, 0);
        Estacion e = new Estacion("neuquena", "null", 0, 0, 0, 0);
        Estacion f = new Estacion("aaaala", "null", 0, 0, 0, 0);
        Estacion g = new Estacion("zapala", "null", 0, 0, 0, 0);
        Estacion h = new Estacion("dado", "null", 0, 0, 0, 0);

        String subcad = "neu";
        dic.insertar("neuquen", a);
        dic.insertar("neuaaa", b);
        dic.insertar("cipoletti", c);
        dic.insertar("roca", d);
        dic.insertar("neuquena", e);
        dic.insertar("aaaala", f);
        dic.insertar("zapala", g);
        dic.insertar("neuquen", a);
        dic.insertar("dado", h);

        System.out.println(dic.toString());

        Lista estacionesConSubcad = dic.obtenerInicianConSubcad(subcad);
        if (!estacionesConSubcad.esVacia()) {
            System.out.println("Estaciones que comienzan con \"" + subcad + "\":");
            int longit = estacionesConSubcad.longitud();
            for (int i = 1; i <= longit; i++) {
                System.out.println("- " + estacionesConSubcad.recuperar(i));
            }
        } else {
            System.out.println("No existen estaciones que comiencen con \"" + subcad + "\".");
        }
    }
}
