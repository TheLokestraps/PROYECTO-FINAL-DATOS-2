package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CuatroServidor {

    public static void main(String[] args) throws Exception {
        int puerto = 8901, backlog = 5;
        String ip = "10.103.1.125";
        ServerSocket ss = new ServerSocket(puerto);

        System.out.println("Servidor funcionando");
        System.out.println("--------------------");
        System.out.println("Dir: " + ss.getInetAddress());
        System.out.println("--------------------");
        System.out.println("Puerto: " + ss.getLocalSocketAddress());
        try {
            while (true) {
                Jugar j = new Jugar();
                Jugar.Jugador jugadorX = j.new Jugador(ss.accept(), "RED");//Se crea el objeto playerX
                Jugar.Jugador jugadorO = j.new Jugador(ss.accept(), "BLUE");//Se crea el objeto playerO
                jugadorX.setoponente(jugadorO);
                jugadorO.setoponente(jugadorX);
                j.gamer = jugadorX;
                jugadorX.start();
                jugadorO.start();
            }
        } finally {
            ss.close();
        }
    }
}

class Jugar {

    Jugador gamer;

    private Jugador[] Arreglo = {
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null
    };

    //Se verifica si hay un ganador
    public boolean Ganador() {
        // Verifica horizontalmente si se puede ganar
        for (int j = 0; j < 9 - 4; j++) {//Columna
            for (int i = 0; i < 48; i += 8) {//Fila
                if (Arreglo[i + j] != null && Arreglo[i + j] == Arreglo[i + j + 1] && Arreglo[i + j] == Arreglo[i + j + 2] && Arreglo[i + j] == Arreglo[i + j + 3]) {
                    return true;
                }
            }
        }
        // Verifica verticalmente si se puede ganar
        for (int i = 0; i < 24; i += 8) {
            for (int j = 0; j < 9; j++) {
                if (Arreglo[i + j] != null && Arreglo[i + j] == Arreglo[i + 8 + j] && Arreglo[i + j] == Arreglo[i + (16) + j] && Arreglo[i + j] == Arreglo[i + (24) + j]) {
                    return true;
                }
            }
        }
        // Verifica la diagonal ascendiente si se puede ganar
        for (int i = 24; i < 48; i += 8) {
            for (int j = 0; j < 4; j++) {
                if (Arreglo[i + j] != null && Arreglo[i + j] == Arreglo[(i - 8) + j + 1] && Arreglo[(i - 8) + j + 1] == Arreglo[i - 16 + j + 2] && Arreglo[(i - 16) + j + 2] == Arreglo[(i - 24) + j + 3]) {
                    return true;
                }
            }
        }
        // Verifica la diagonal descendiente si se puede ganar
        for (int i = 24; i < 48; i += 8) {
            for (int j = 3; j < 8; j++) {
                if (Arreglo[i + j] != null && Arreglo[i + j] == Arreglo[(i - 8) + j - 1] && Arreglo[(i - 8) + j - 1] == Arreglo[(i - 16) + j - 2] && Arreglo[(i - 16) + j - 2] == Arreglo[(i - 24) + j - 3]) {
                    return true;
                }
            }
        }

        return false;
    }

    //Verifica si el jugador hizo un movimiento legal
    public synchronized int MovimientoLegal(int locacion, Jugador player) {
        int minilocacion = (locacion % 8) + 8 * 5;
        for (int i = minilocacion; i >= locacion; i -= 8) {
            if (player == gamer && Arreglo[i] == null) {
                Arreglo[i] = gamer;
                gamer = gamer.oponente;
                gamer.jugadormovido(i);
                return i;
            }
        }
        return -1;
    }

    //Verifica si el tablero está lleno
    public boolean Lleno() {
        for (int i = 0; i < Arreglo.length; i++) {
            if (Arreglo[i] == null) {
                return false;
            }
        }
        return true;
    }

    //Clase con hilos
    class Jugador extends Thread {

        String marca;
        Jugador oponente;
        Socket socket;
        BufferedReader input;
        PrintWriter output;

        public Jugador(Socket socket, String marca) {
            this.socket = socket;
            this.marca = marca;
            try {
                input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + marca);
                output.println("MESSAGE Esperando oponente");
            } catch (IOException e) {
                System.out.println("Jugador ido: " + e);
            }
        }

        //Maneja mensajes
        public void jugadormovido(int location) {
            output.println("OPPONENT_MOVED " + location);
            output.println(
                    Ganador() ? "DEFEAT" : Lleno() ? "TIE" : "");
        }

        //Asignar oponente
        public void setoponente(Jugador oponente) {
            this.oponente = oponente;
        }

        /**
         * The run method of this thread.
         */
        public void run() {
            try {
                // The thread is only started after everyone connects.
                output.println("MESSAGE Todos los jugadores se han conectado!");

                // Tell the first player that it is her turn.
                if (marca.equals("RED")) {
                    output.println("MESSAGE Tu mueves");
                }

                // Repeatedly get commands from the client and process them.
                while (true) {
                    String command = input.readLine();
                    if (command.startsWith("MOVE")) {
                        int location = Integer.parseInt(command.substring(5));
                        int validlocation = MovimientoLegal(location, this);
                        if (validlocation != -1) {
                            output.println("VALID_MOVE" + validlocation);
                            output.println(Ganador() ? "VICTORY"
                                    : Lleno() ? "TIE"
                                            : "");
                        } else {
                            output.println("MESSAGE ?");
                        }
                    } else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Jugador ido: " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
