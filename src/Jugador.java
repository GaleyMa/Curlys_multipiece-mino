import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<FichaDomino> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public ArrayList<FichaDomino> getMano() {
        return mano;
    }
    public void setMano(ArrayList<FichaDomino> mano) {
        this.mano = mano;
    }
    public FichaDomino getFicha(int posicion){
        FichaDomino pieza = mano.get(posicion);
        mano.remove(posicion);
        return pieza;
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
}
