package Cliente;

import Clases.Jugador;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author TheLokestraps
 */


public class JuegoReloades {

    public JFrame frame = new JFrame("Conecta 4");
    private JLabel messageLabel = new JLabel("");
    private JLabel Info;
    private JLabel backgroundpanel;
    private JLabel NombrePlayer;
    private JLabel imagen;
    private ImageIcon icon;
    private ImageIcon opponentIcon;

    private Casillas[] tablero = new Casillas[48];
    
    private Casillas casillaactual;

    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public JuegoReloades(String serverAddress, Jugador jugador) throws Exception {

        // Setup networking
        frame.setLayout(null);
        
        
        socket = new Socket("localhost", 8901);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Layout GUI
        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.ORANGE);
        boardPanel.setLayout(new GridLayout(6, 8, 2, 2));
        for (int i = 0; i < tablero.length; i++) {
            final int j = i;
            tablero[i] = new Casillas();
            tablero[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    casillaactual = tablero[j];
                    out.println("MOVE " + j);}});
            boardPanel.add(tablero[i]);
        }
        frame.getContentPane().add(boardPanel);
        boardPanel.setBounds(260, 120, 490, 360);
        
        imagen = new javax.swing.JLabel();
        frame.getContentPane().add(imagen);
        imagen.setBounds(40, 130, 130, 140);
        ImageIcon miicono = new ImageIcon(new ImageIcon(jugador.rutaAvatar.toString()).getImage().getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH));
        imagen.setIcon(miicono);
        
        NombrePlayer = new javax.swing.JLabel();
        NombrePlayer.setFont(new java.awt.Font("Eras Demi ITC", 0, 18));
        NombrePlayer.setText(jugador.nombre);
        frame.getContentPane().add(NombrePlayer);
        NombrePlayer.setBounds(50, 350, 90, 16);
        
        
        backgroundpanel = new javax.swing.JLabel();
        backgroundpanel.setBackground(new java.awt.Color(255, 204, 0));
        backgroundpanel.setOpaque(true);
        backgroundpanel.setBounds(0, 0, 210, 608);
        frame.getContentPane().add(backgroundpanel);
        
        Info = new javax.swing.JLabel();
        Info.setFont(new java.awt.Font("Eras Demi ITC", 0, 18)); // NOI18N
        Info.setText("Esperando por los oponentes");
        Info.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 1, 2, new java.awt.Color(255, 153, 0)));
        frame.getContentPane().add(Info);
        Info.setBounds(370, 10, 260, 40);
       
        
    }


    public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                String Ficha = response.substring(8);
                File Aux1 = new File("src\\Imagenes","coin-blue.png");
                File Aux2 = new File("src\\Imagenes","coin-red.png");
                if(Ficha.equals("RED")){
                icon = new ImageIcon(new ImageIcon(Aux1.toString()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                opponentIcon = new ImageIcon(new ImageIcon(Aux2.toString()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                } else {
                    icon = new ImageIcon(new ImageIcon(Aux2.toString()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                    opponentIcon = new ImageIcon(new ImageIcon(Aux1.toString()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                }
                
                frame.setTitle("Conecta 4, juegas con las " + Ficha);
            }
            while (true) {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE")) {
                    Info.setText("Movimiento valido, espere un momento");
                    casillaactual=tablero[Integer.parseInt(response.substring(10))];
                    casillaactual.setIcon(icon);
                    casillaactual.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    tablero[loc].setIcon(opponentIcon);
                    tablero[loc].repaint();
                    Info.setText("El Oponente movio, es tu turno");
                } else if (response.startsWith("VICTORY")) {
                    Info.setText("Victoria!!!!");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    Info.setText("Derrotado");
                    break;
                } else if (response.startsWith("TIE")) {
                    Info.setText("Empate");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    Info.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
    }

    public boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
            "Deseas Jugar otra vez",
            "Conecta 4",
            JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }
    
    static class Casillas extends JPanel {
        JLabel label = new JLabel((Icon)null);

        public Casillas() {
            setBackground(Color.white);
            add(label);
        }

        public void setIcon(Icon icon) {
            label.setIcon(icon);
        }
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            //serverAddress = "10.103.1.125";
            System.out.println(serverAddress);
            JuegoReloades client = new JuegoReloades(serverAddress,new Jugador("Jugador", new File("src\\Imagenes", "icondefault.png")));
            client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.frame.setSize(800,600);
            client.frame.setVisible(true);
            client.frame.setResizable(false);
            client.play();
            if (!client.wantsToPlayAgain()) {
                break;
            }
        }
    }
}