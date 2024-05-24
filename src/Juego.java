import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * Clase aplicación, contiene las reglas del juego
 */
public class Juego {

    Jugador[] jugadores;
    Pozo pozo;
    ArrayList<FichaDomino> mesa;
    int ganadorFinal;

    /**
     * Constructor
     */
    public Juego() {
        jugadores = new Jugador[2];
        pozo = new Pozo();
        mesa = new ArrayList<>();
        ganadorFinal=0;
    }

    public void comenzarJuego(){
        boolean finalDeRonda= false;
        int jugadoresBloqueados = 0;
        Scanner scanner= new Scanner(System.in);
        int seleccion;

        //se agregan los jugadores
        agregarJugadores();
        //reparte fichas
        preparaMesa();
        reparteFichas();
        //se determina cuál jugador comienza la ronda
        determinaQuienComienza();

        //comienza la ronda
        do {
            for (int i = 0; i < jugadores.length; i++) {
                finalDeRonda = DeterminarRondaTerminada(jugadoresBloqueados);

                System.out.println(mesa);

                System.out.println(jugadores[i].getNombre() + " escoja una ficha para insertar.");
                jugadores[i].imprimeMano();
                int manoSize= jugadores[i].cantidadDeFichas();
                do{
                    seleccion = scanner.nextInt();
                } while (seleccion < 0 || seleccion >= manoSize);

                FichaDomino fichaAInsertar = jugadores[i].getFicha(seleccion);
                boolean colocada = insertarAMesa(fichaAInsertar);

                if(colocada){
                    jugadores[i].eliminarPieza(seleccion);
                }else {
                    System.out.println("Se añaden dos fichas a tu mano.");
                    jugadores[i].agregar2FichasAMano(pozo.get2fichas());
                }
            }
        } while(!finalDeRonda);

        //se determina el ganador
        ganadorFinal = encuentraGanador();
        System.out.println("Felicidades " + jugadores[ganadorFinal].getNombre() + "has ganado el juego!");
    }

    /**
     * Reparte 10 fichas del pozo a cada jugador
     */
    private void reparteFichas(){

        System.out.println("...Repartiendo Fichas...");
        for (int i = 0; i < 2; i++) {
            jugadores[i].setMano(pozo.get10fichas());
        }
    }

    /**
     * Genera las fichas y las mezcla para poder comenzar el juego
     */
    private void preparaMesa(){
        pozo.reiniciaSet();
        pozo.generaPiezas();
        pozo.mezclarPiezas();
        System.out.println("...Mezclando fichas...");

    }

    /**
     * Crea los jugadores según el nombre dado.
     */
    private void agregarJugadores() {
        Scanner scanner= new Scanner(System.in);
        String nombre;
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Ingresa el nombre del jugador "+(i+1)+": ");
            nombre = scanner.next();
            jugadores[i]=new Jugador(nombre);
        }
    }

    /**
     * Encuentra el ganador del jugo segun la cantidad de puntos
     * @return posicion del ganador
     */
    private int encuentraGanador(){
        int ganador = 0;
        int puntosMaximos = 0;
        for (int i = 0; i<jugadores.length;i++){
            if(jugadores[i].getPuntos() > puntosMaximos){
                ganador = i;
                puntosMaximos = jugadores[i].getPuntos();
            }
        }
        return ganador;
    }

    /**
     * Realiza la selección del jugador que comienza la ronda según los puntos
     * de una ficha al azar de la mano de cada jugador
     */
    private void determinaQuienComienza() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int[] valorDeFicha = new int[2], posicion = new int[2];
        int usuario;
        FichaDomino[] fichaTemp = new FichaDomino[2];
        FichaDomino ficha;

        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Jugador: "+ jugadores[i].getNombre()+
                    "presiona 1 para seleccionar una ficha al azar...");

            do {
                usuario = scanner.nextInt();
            } while (usuario != 1);
            posicion[i] = random.nextInt(0, 9);
            ficha = jugadores[i].getFicha(posicion[i]);
            fichaTemp[i] = ficha;
            valorDeFicha[i] = ficha.sumaDePuntos();
            System.out.println("Puntos: " + valorDeFicha[i]);
        }

        if (valorDeFicha[0] > valorDeFicha[1]) {
            mesa.add(fichaTemp[0]);
            jugadores[0].eliminarPieza(posicion[0]);

            Jugador temporal = new Jugador(jugadores[0]);
            jugadores[0] = new Jugador(jugadores[1]);
            jugadores[1] = new Jugador(temporal);

        } else {
            mesa.add(fichaTemp[1]);
            jugadores[1].eliminarPieza(posicion[1]);
        }
        System.out.println(jugadores[1].getNombre() + "colocará la primera ficha");
    }

    /**
     * Busca si las se cumplen las condiciones para terminar el juego
     */
    private boolean DeterminarRondaTerminada(int jugadoresBloqueados) {
        boolean rondaCompleta = false;
        if (jugadoresBloqueados == jugadores.length) {
            rondaCompleta = true;
        } else {
            for (Jugador jugador : jugadores) {
                if (jugador.getMano().isEmpty()) {
                    rondaCompleta = true;
                    break;
                }
            }
        }
        return rondaCompleta;
    }

    private void insertarAMesa(FichaDomino fichaAInsertar) {

    }
}
