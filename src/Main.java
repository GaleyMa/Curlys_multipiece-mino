public class Main {
    public static void main(String[] args) {
        //Juego juego = new Juego();
        //juego.comenzarJuego();

        FichaDomino ficha= new FichaDomino(1,2);
        System.out.println(ficha+"\n");
        for (int i = 0; i < 4; i++) {
            ficha.rotateLeft();
            System.out.println(ficha);
        }

        System.out.println(ficha+"\n");
        for (int i = 0; i < 4; i++) {
            ficha.rotateRight();
            System.out.println(ficha);
        }
    }
}
