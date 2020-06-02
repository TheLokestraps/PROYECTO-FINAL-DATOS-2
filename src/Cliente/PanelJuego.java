/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author TheLokestraps
 */
public class PanelJuego extends javax.swing.JFrame {

    /**
     * Creates new form PanelJuego
     */
    String URL;
    File source,dest;
    public PanelJuego() {
        initComponents();
         File imagen1 = new File("src\\Imagenes","buton.jpg");
         File imagen2 = new File("src\\Imagenes","imageVertical.png");
         ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(imagen1.toString()).getImage().getScaledInstance(image2.getWidth(),image2.getHeight(), Image.SCALE_DEFAULT));
         
         ImageIcon imageIcon2 = new ImageIcon(new ImageIcon(imagen2.toString()).getImage().getScaledInstance(background.getWidth(),background.getHeight(), Image.SCALE_DEFAULT));
         background.setIcon(imageIcon2);
         image2.setIcon(imageIcon1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        col1 = new javax.swing.JButton();
        col2 = new javax.swing.JButton();
        col3 = new javax.swing.JButton();
        col4 = new javax.swing.JButton();
        col5 = new javax.swing.JButton();
        col6 = new javax.swing.JButton();
        image2 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 660, 550));

        col1.setText("Introducir ");
        getContentPane().add(col1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, -1, -1));

        col2.setText("Introducir ");
        getContentPane().add(col2, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 639, -1, -1));

        col3.setText("Introducir ");
        getContentPane().add(col3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 640, -1, -1));

        col4.setText("Introducir ");
        getContentPane().add(col4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 640, -1, -1));

        col5.setText("Introducir ");
        getContentPane().add(col5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 640, -1, -1));

        col6.setText("Introducir ");
        getContentPane().add(col6, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 639, -1, -1));

        image2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buton.jpg"))); // NOI18N
        getContentPane().add(image2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 700, 110));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imageVertical.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanelJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton col1;
    private javax.swing.JButton col2;
    private javax.swing.JButton col3;
    private javax.swing.JButton col4;
    private javax.swing.JButton col5;
    private javax.swing.JButton col6;
    private javax.swing.JLabel image2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
