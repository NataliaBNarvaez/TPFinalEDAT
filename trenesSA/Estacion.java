package trenesSA;

public class Estacion {
    /*
     * La red de trenes estará conformada por un conjunto de estaciones unidas entre
     * sí por rieles que permiten la circulación en ambos sentidos. Cada estación
     * recibe un nombre único dentro del sistema (por ejemplo, “Retiro”) y se desea
     * almacenar la siguiente información de cada una: su domicilio (calle, número,
     * ciudad y código postal), cantidad de vías y cantidad de plataformas
     * En el caso de las estaciones la clave será el nombre de las mismas.
     */
    private String nombre;
    private String calle;
    private int nro;
    private String ciudad;
    private int codPost;
    private int cantVias;
    private int cantPlataformas;

    public Estacion(String n, String c, int num, int cp, int v, int p) {
        this.nombre = n;
        this.calle = c;
        this.nro = num;
        this.codPost = cp;
        this.cantVias = v;
        this.cantPlataformas = p;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCodPost() {
        return codPost;
    }

    public void setCodPost(int codPost) {
        this.codPost = codPost;
    }

    public int getCantVias() {
        return cantVias;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }

    public String toString() {
        return "ESTACION " + nombre + " - Domicilio: " + calle + nro + " Ciudad: " + ciudad + " Cod. postal: " + codPost
                + " - Cant. de vias: " + cantVias + " - Cant. de plataformas: " + cantPlataformas;
    }
}
