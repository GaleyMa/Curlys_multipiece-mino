public class FichaDomino implements Movible{
    private int lado1;
    private int lado2;

    public FichaDomino(int lado1, int lado2) {
        this.lado1= lado1;
        this.lado2=lado2;
    }

    public int sumaDeLados(){
        return lado1+lado2;
    }

    public boolean esMula(){
        return lado1==lado2;
    }

    public int getLado1(){
        return lado1;
    }

    public int getLado2(){
        return lado2;
    }

    public String toString(){
        String fichaEnTexto= "[" + lado1+" | "+lado2+ "]";
        return fichaEnTexto;
    }

    @Override
    public void rotateRight() {

    }

    @Override
    public void rotateLeft() {

    }
}
