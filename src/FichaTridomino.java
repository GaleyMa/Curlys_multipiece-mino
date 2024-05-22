/**
 * Clase que se encarga de modelar la ficha de tres lados
 */
public class FichaTridomino extends FichaDomino {
    private int valorArriba;
    private boolean pointingUp;

    /**
     * Constructor parametrizado de FichaTridomino
     * @param valorDerecho es el valor que tendrá la vertice inferior derecho
     * @param valorIzquierdo es el valor que tendrá la vertice inferior izquierdo
     * @param valorArriba es el valor que tendrá la vertice superior
     */
    public FichaTridomino(int valorDerecho, int valorIzquierdo, int valorArriba) {
        super(valorDerecho, valorIzquierdo);
        this.valorArriba = valorArriba;
        pointingUp = true;
    }

    /**
     * Método que hace que ficha rote una posición a la derecha
     */
    public void rotateRight() {
        int newTop, newLeft, newRight;
        if (pointingUp) {
            newRight = valorArriba;
            newTop = valorDerecho;
            newLeft = valorIzquierdo;
        } else {
            newLeft = valorArriba;
            newTop = valorIzquierdo;
            newRight = valorDerecho;
        }

        valorArriba = newTop;
        valorIzquierdo = newLeft;
        valorDerecho = newRight;

        pointingUp = !pointingUp;
    }

    /**
     * Método que hace que la ficha rote una posición a la izquierda
     */
    public void rotateLeft() {
        int newTop, newLeft, newRight;

        if (pointingUp) {
            newRight = valorDerecho;
            newTop = valorIzquierdo;
            newLeft = valorArriba;
        } else {
            newLeft = valorIzquierdo;
            newTop = valorDerecho;
            newRight = valorArriba;
        }

        valorArriba = newTop;
        valorIzquierdo = newLeft;
        valorDerecho = newRight;
        pointingUp = !pointingUp;
    }

    @Override
    public int sumaDePuntos() {
        return super.sumaDePuntos()+valorArriba;
    }

    /**
     * Indica si la ficha tiene el valor dado
     * @param valor
     * @return
     */
    @Override
    public boolean tieneValor(int valor) {
        return super.tieneValor(valor)||valorArriba == valor;
    }

    /**
     * Indica si los tres valores de la ficha son iguales
     * @return
     */
    @Override
    public boolean esMula() {
        return super.esMula() && valorArriba == valorDerecho;
    }

    /**
     * Método que muestra la ficha en formato cadena
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (volteada) {
            if (pointingUp) { // it's pointing up
                // sb.append("  / \\\n");
                sb.append(" /").append(valorArriba).append("\\\n");
                sb.append("/").append(valorIzquierdo);
                sb.append(" ").append(valorDerecho);
                sb.append("\\");
            } else { // it's pointing down

                sb.append("\\").append(valorIzquierdo);
                sb.append(" ").append(valorDerecho).append("/\n");
                sb.append(" \\").append(valorArriba).append("/");
            }
        } else {
            // no se ven los puntos
            sb.append("/ \\\n");
            sb.append("/    \\\n");
        }
        return sb.toString();
    }
}