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
        ganadorFinal = 0;
    }

    public void comenzarJuego() {
        boolean finalDeRonda = false;
        int jugadoresBloqueados = 0;
        Scanner scanner = new Scanner(System.in);
        int seleccion;
        boolean colocada;

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
                imprimeMesa();

                System.out.println(jugadores[i].getNombre() + " escoja una ficha para insertar o presiona 0 para tomar 2 fichas del pozo");

                jugadores[i].imprimeMano();

                int manoSize = jugadores[i].cantidadDeFichas();

                do {
                    seleccion = scanner.nextInt();
                } while (seleccion < 0 || seleccion > manoSize);

                if (seleccion == 0) colocada=false;
                else {
                    seleccion-=1;
                    FichaDomino fichaAInsertar = jugadores[i].getFicha(seleccion);
                    colocada = insertarAMesa(fichaAInsertar,i);
                }

                if (colocada) {
                    jugadores[i].eliminarPieza(seleccion);
                } else {
                    System.out.println("Se añaden dos fichas a tu mano.");
                    pause();
                    jugadores[i].agregar2FichasAMano(pozo.get2fichas());
                }
            }
        } while (!finalDeRonda);

        //se determina el ganador
        ganadorFinal = encuentraGanador();
        System.out.println("Felicidades " + jugadores[ganadorFinal].getNombre() + "has ganado el juego!");
    }

    /**
     * Reparte 10 fichas del pozo a cada jugador
     */
    private void reparteFichas() {

        System.out.println("...Repartiendo Fichas...");
        pause();
        for (int i = 0; i < 2; i++) {
            jugadores[i].setMano(pozo.get10fichas());
        }
    }

    /**
     * Genera las fichas y las mezcla para poder comenzar el juego
     */
    private void preparaMesa() {
        pozo.reiniciaSet();
        pozo.generaPiezas();
        pozo.mezclarPiezas();
        System.out.println("...Mezclando fichas...");
        pause();

    }

    /**
     * Crea los jugadores según el nombre dado.
     */
    private void agregarJugadores() {
        Scanner scanner = new Scanner(System.in);
        String nombre;
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Ingresa el nombre del jugador " + (i + 1) + ": ");
            nombre = scanner.next();
            jugadores[i] = new Jugador(nombre);
        }
    }

    /**
     * Encuentra el ganador del jugo segun la cantidad de puntos
     *
     * @return posicion del ganador
     */
    private int encuentraGanador() {
        int ganador = 0;
        int puntosMaximos = 0;
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getPuntos() > puntosMaximos) {
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
            System.out.println("Jugador: " + jugadores[i].getNombre() +
                    "\n Presiona 1 para seleccionar una ficha al azar");

            do {
                usuario = scanner.nextInt();
            } while (usuario != 1);
            posicion[i] = random.nextInt(0, 9);
            ficha = jugadores[i].getFicha(posicion[i]);
            fichaTemp[i] = ficha;
            valorDeFicha[i] = ficha.sumaDePuntos();
            pause();
            System.out.println(ficha+"\nPuntos: " + valorDeFicha[i]);
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
        System.out.println(jugadores[1].getNombre() + " colocará la primera ficha");
        pause();
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

    private boolean insertarAMesa(FichaDomino fichaAInsertar, int jugador) {
        FichaDomino fichaFinal = mesa.getLast();
        boolean colocada=false;

        //si la  ultima ficha de la mesa ficha es tridomino
        if (fichaFinal.isTridomino()) {
            FichaTridomino fichaFinal_tri = (FichaTridomino) fichaFinal;
            if (fichaFinal_tri.isPointingUp()) { //si la ficha de la mesa está apuntando arriba
                //solo se pueden poner tridomino abajo de ella
                if (fichaAInsertar.isTridomino()) {
                    FichaTridomino fichaAInsertar_tri = (FichaTridomino) fichaAInsertar;
                    if (fichaAInsertar_tri.tieneValor(fichaFinal_tri.getValorDerecho())
                            && fichaAInsertar_tri.tieneValor(fichaFinal_tri.getValorIzquierdo())) {
                        while (fichaAInsertar_tri.getValorIzquierdo() != fichaFinal_tri.getValorIzquierdo()
                                && fichaAInsertar_tri.getValorDerecho() != fichaFinal_tri.getValorDerecho()
                                && !fichaAInsertar_tri.isPointingUp()) {
                            fichaAInsertar_tri.rotateLeft();
                        }
                        mesa.addLast(fichaAInsertar_tri);
                        jugadores[jugador].sumaPuntos(fichaAInsertar_tri.sumaDePuntos());
                        colocada=true;

                    } else System.out.println("Tu ficha no coincide");

                } else System.out.println("tu ficha debe ser trinomino");


            } else {//si la ficha final está apuntando hacia arriba, solo se puede poner Domino abajo
                if (!fichaAInsertar.isTridomino()) {
                    if (fichaAInsertar.tieneValor(fichaFinal_tri.getValorArriba())) {
                        while (fichaAInsertar.getValorDerecho() != fichaFinal_tri.getValorArriba()) {
                            fichaAInsertar.rotateLeft();
                        }
                        mesa.addLast(fichaAInsertar);
                        colocada = true;
                        jugadores[jugador].sumaPuntos(fichaAInsertar.sumaDePuntos());
                    }else System.out.println("la ficha no coincide");

                } else System.out.println("la ficha debe ser Domino");
            }
        }else{ // si la ultima ficha de la mesa es Domino
            if (fichaAInsertar.isTridomino()){ //si la ficha a insertar es tridomino
                FichaTridomino fichaAInsertar_tri = (FichaTridomino) fichaAInsertar;
                if(fichaAInsertar.tieneValor(fichaFinal.getValorDerecho())){
                    while (fichaAInsertar_tri.getValorArriba()!=fichaFinal.getValorDerecho()
                            && fichaAInsertar_tri.isPointingUp()){
                        fichaAInsertar_tri.rotateLeft();
                    }
                    mesa.addLast(fichaAInsertar);
                    jugadores[jugador].sumaPuntos(fichaAInsertar_tri.sumaDePuntos());
                    colocada = true;
                }else System.out.println("La ficha no coincide");
            }else{//si la ficha a insertar es domino

                if(fichaAInsertar.tieneValor(fichaFinal.valorDerecho)){
                    while(fichaAInsertar.getValorIzquierdo()!=fichaFinal.valorDerecho){
                        fichaAInsertar.rotateLeft();
                    }
                    mesa.addLast(fichaAInsertar);
                    jugadores[jugador].sumaPuntos(fichaAInsertar.sumaDePuntos());
                    colocada = true;
                } else System.out.println("Esta ficha no coincide");
            }
        }

        if (colocada) {
            System.out.println("...agregando ficha...");
        }
        pause();
        return colocada;
    }


    private void imprimeMesa(){

        System.out.println("    .:MESA:.      ");
        for (FichaDomino ficha : mesa) {
            System.out.println(ficha.toString()+"\n");
        }
    }

    /**
     * Hace pausas elegantes
     */
    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.format("InterruptedException : %s%n", e);
        }
    }
}
