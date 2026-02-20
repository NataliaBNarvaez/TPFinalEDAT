package trenesSA;

public class Tren {
    /*
     * Se almacenará para cada tren un identificador numérico único, el tipo de
     * propulsión (electricidad, diesel, fuel oil, gasolina, híbrido), cantidad de
     * vagones para pasajeros, cantidad de vagones para carga y la línea en la que
     * está siendo utilizado (si el tren no está destinado a ninguna línea se
     * considerará libre)
     */

    private int id;
    private String tipoProp;
    private int cantVagPasajeros;
    private int cantVagCarga;
    private String linea;

    public Tren(int ident, String tp, int pas, int carg, String l) {
        this.id = ident;
        this.tipoProp = tp;
        this.cantVagPasajeros = pas;
        this.cantVagCarga = carg;
        this.linea = l;
    }

    public int getId() {
        return id;
    }

    public String getTipoProp() {
        return tipoProp;
    }

    public void setTipoProp(String tipoProp) {
        this.tipoProp = tipoProp;
    }

    public int getCantVagPasajeros() {
        return cantVagPasajeros;
    }

    public void setCantVagPasajeros(int cantVagPasajeros) {
        this.cantVagPasajeros = cantVagPasajeros;
    }

    public int getCantVagCarga() {
        return cantVagCarga;
    }

    public void setCantVagCarga(int cantVagCarga) {
        this.cantVagCarga = cantVagCarga;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String toString() {
        return "TREN " + id + " - Tipo propulsion: " + tipoProp + " - Cant. de vagones pasajeros: " + cantVagPasajeros
                + " - Cant. de vagones para carga" + cantVagCarga + " - Linea: " + linea;
    }
}
