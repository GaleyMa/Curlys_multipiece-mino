
public class FichaTriomino extends FichaDomino {


    private int lado1, lado2, lado3;
    private boolean pointingUp;
    private int rotated;

    public FichaTriomino(int lado1, int lado2, int lado3) {
        super(lado1, lado2);
        this.lado3 = lado3;
        pointingUp = true;
        rotated = 0;
    }

    public int getPoints() {
        return lado1 + lado2 + lado3;
    }

    public void turn() {
        pointingUp = !pointingUp;
    }

    public String toString() {
        String tile;

        if (pointingUp) {
            tile = " /" + lado1 + "\\ \n/" + lado3 + " " + lado2 + "\\";
        } else tile = "\\" + lado2 + " " + lado3 + "/\n \\" + lado1 + "/ ";

        return tile;
    }

    public void rotate() {
        int aux = lado1;
        lado3 = lado2;
        lado2 = lado1;
        lado1 = aux;

        rotated += 1;
        if (rotated > 3) rotated = 1;
    }
}