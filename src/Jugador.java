import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<FichaDomino> mano;
    private int puntos;

    /**
     * Constructor para jugador nuevo
      * @param nombre
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        puntos=0;
    }

    /**
     * Constructor para copia de jugador
     */
    public Jugador(Jugador copia){
        this.nombre = copia.nombre;
        this.puntos = copia.puntos;
        this.mano=copia.mano;
    }

    public String getNombre() {
        return nombre;
    }
    public ArrayList<FichaDomino> getMano() {
        return mano;
    }
    public int cantidadDeFichas(){
        return mano.size();
    }
    public void setMano(ArrayList<FichaDomino> mano) {
        this.mano = mano;
    }
    public FichaDomino getFicha(int posicion){
        FichaDomino pieza = mano.get(posicion);
        mano.remove(posicion);
        return pieza;
    }
    public void imprimeFicha(int posicion){
        System.out.println(mano.get(posicion));
    }
    public void imprimeMano(){
        StringBuilder cadena = new StringBuilder();
        for (int i = 0; i < mano.size(); i++) {
            cadena.append((i+1));
            cadena.append(". ");
            cadena.append(mano.get(i).toString());
            cadena.append("\n");
        }
        System.out.println(cadena);
    }
    public int getPuntos() {
        return puntos;
    }
    public void sumaPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int valorDeFicha(int posicionDeFicha){
        return mano.get(posicionDeFicha).sumaDePuntos();
    }
}
