/**
 * Crea y modela la ficha de dominó
 */
public class FichaDomino implements Movible {
    protected int valorDerecho;
    protected int valorIzquierdo;
    protected boolean volteada; // true -> se ven los puntitos

    private Direccion facing = Direccion.EAST;

    /**
     * Constructor no parametrizado
     */
    public FichaDomino() {
        valorDerecho = -1;
        valorIzquierdo = -1;
        volteada = true;
    }

    /**
     * Constructor parametrizado
     * @param valorDerecho
     * @param valorIzquierdo
     */
    public FichaDomino(int valorDerecho, int valorIzquierdo) {
        this.valorDerecho = valorDerecho;
        this.valorIzquierdo = valorIzquierdo;
        volteada = true;
    }

    /**
     * Getter de valorDerecho
     * @return
     */
    public int getValorDerecho() {
        return valorDerecho;
    }

    /**
     * Setter de valorDerecho
     * @param valorDerecho
     */
    public void setValorDerecho(int valorDerecho) {
        this.valorDerecho = valorDerecho;
    }

    /**
     * Getter de valorIzquierdo
     * @return valorIzquierdo
     */
    public int getValorIzquierdo() {
        return valorIzquierdo;
    }

    /**
     * Setter de valorIzquierdo
     * @param valorIzquierdo
     */
    public void setValorIzquierdo(int valorIzquierdo) {
        this.valorIzquierdo = valorIzquierdo;
    }

    /**
     * Getter del atributo que indica el estado de visibilidad de la ficha
     * @return
     */
    public boolean isVolteada() {
        return volteada;
    }

    /**
     * Setter del estado de visibilidad de la ficha
      * @param volteada
     */
    public void setVolteada(boolean volteada) {
        this.volteada = volteada;
    }

    /**
     * Devuelve la dirección en la que se encuentra apuntando la ficha
     * según el valor derecho asignado a la hora de la creación de la ficha
     * @return
     */
    public Direccion getFacing() {
        return facing;
    }

    /**
     * Se asigna la dirección de la ficha
     * @param facing
     */
    public void setFacing(Direccion facing) {
        this.facing = facing;
    }

    /**
     * Rota la ficha en una posición hacia la derecha
     */
    public void rotateRight() {
        switch (facing) {
            case SOUTH:
            case NORTH:
                girar();
        }
        // change facing to next direction
        facing = facing.values()[facing.ordinal()+1];
    }

    /**
     * Rota la ficha en una posición hacia la izquierda
     */
    public void rotateLeft() {
        // change facing to next direction
        if (facing == Direccion.NORTH) {
            facing = Direccion.WEST;
        } else {
            facing = facing.values()[facing.ordinal()-1];
        }
        switch (facing) {
            case SOUTH:
            case NORTH:
                girar();
        }

    }

    /**
     * Intercambia el sentido de los valores en la ficha
     */
    private void girar() {
        int temp;
        temp = valorDerecho;
        valorDerecho = valorIzquierdo;
        valorIzquierdo = temp;
    }

    /**
     * Busca un valor dado en los lados de la ficha
     * @param valor
     * @return
     */
    public boolean tieneValor(int valor) {
        return (valor==valorDerecho || valor==valorIzquierdo);
    }

    /**
     * Pone el lado del valor indicado en el lado derecho
     * @param valor
     */
    public void colocarValorEnLadoIzquierdo(int valor) {
        if (valorDerecho == valor) {
            girar();
        }
    }

    /**
     * Indica si la ficha es mula
     * @return
     */
    public boolean esMula() {
        return valorDerecho == valorIzquierdo;
    }

    /**
     * Crea una cadena que contenga la representación de la ficha
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        if (volteada) {
            switch (facing) {
                case EAST:
                case WEST:
                    sb.append("[").append(valorDerecho);
                    sb.append("|").append(valorIzquierdo);
                    sb.append(']');
                    break;
                case NORTH:
                case SOUTH:
                    sb.append("=\n");
                    sb.append(valorDerecho + "\n");
                    sb.append("-\n");
                    sb.append(valorIzquierdo + "\n");
                    sb.append('=');
                    break;
            }
        } else {
            // no se ven los puntos
            sb.append("[   ]\n");
        }
        return sb.toString();
    }
}