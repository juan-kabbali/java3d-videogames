package co.InteractiveMusic.Utilities;


import co.InteractiveMusic.Principal;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Gui2d extends javax.swing.JFrame implements MouseListener{

    
    public static AudioNode sonidoInstrumento;
    private static BufferedImage icono;
    private Panel panel;
    private int comando;
    public static boolean estoyVisible;
    private String instrumentoPlus;
    public static boolean isPlus;
     
    
        public Gui2d(Node rootNode, AssetManager assetManager, String ruta) {

        super("Interactive Music Computacion grafica 2");
        initComponents();
        setUpGui2d(ruta);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        estoyVisible = true;
        isPlus= false;
        iniciarInstrumento(rootNode, assetManager, ruta);
        this.addMouseListener(this);
        
    }
    
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void setUpGui2d(String ruta) {
                   
        try {
            icono = ImageIO.read(new File("assets/Interface/logo.png"));

        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(icono);
        panel = new Panel(ruta);
        add(BorderLayout.CENTER, panel);
        panel.setToolTipText("Interactive Music");
        panel.addMouseListener(this);
        setLocationRelativeTo(null);
        panel.setSize(this.getSize());
        this.setResizable(false);
       
    }
  
    public void mousePressed(MouseEvent e) {
     
        System.out.println(e.getPoint());
        colisionesGUI2d(e);
    }

    public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mouseClicked(MouseEvent e) {}

    private void iniciarInstrumento(Node rootNode, AssetManager assetManager, String ruta) {

        if (ruta.equalsIgnoreCase("assets/Interface/saxofon.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/saxofon.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/trompeta.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/trompeta.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/bateria.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/bateria.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/bombo.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/bombo.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/electrica.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/electrica.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/acustica.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/acustica.wav");
            sonidoInstrumento.play();
        }
        if (ruta.equalsIgnoreCase("assets/Interface/acordeon.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/acordeon.wav");
            sonidoInstrumento.play();
        }

        if (ruta.equalsIgnoreCase("assets/Interface/piano.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/piano.wav");
            sonidoInstrumento.play();
        }
        if (ruta.equalsIgnoreCase("assets/Interface/congas.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/congas.wav");
            sonidoInstrumento.play();
        }
          if (ruta.equalsIgnoreCase("assets/Interface/cencerro.jpg")) {
            cargarSonido(rootNode, assetManager, "Sounds/campana.wav");
            sonidoInstrumento.play();
              System.out.println("deberiaSonar");
        }
        if (ruta.equalsIgnoreCase("assets/Interface/horarios.jpg") || ruta.equalsIgnoreCase("assets/Interface/enfermeria.jpg") || ruta.equalsIgnoreCase("assets/Interface/talleres.jpg")) {
            estoyVisible = false;
        }

      
        instrumentoPlus = ruta;
    }
    
    private void cargarSonido(Node rootNode,AssetManager assetManager,String ruta){
    
        sonidoInstrumento = new AudioNode(assetManager, ruta,false);
        sonidoInstrumento.setPositional(false);
        sonidoInstrumento.setVolume(1.2f);
        rootNode.attachChild(sonidoInstrumento);
        
    
    }

    private void colisionesGUI2d(MouseEvent e) {
      
        comando = 0;
        double posi;

        posi = Colisiones2d.distancia(e.getPoint(), new Point(984, 42));

        if (posi < 33) {
            comando = 1;
        }


        if (Colisiones2d.deteccionBotones(e.getPoint(), new Point(582, 236), 114, 424)) {
            comando = 2;
        }

        switch (comando) {

            case 1:
                estoyVisible = false;
                this.setVisible(false);
                break;



            case 2:

                if (instrumentoPlus.equalsIgnoreCase("assets/Interface/saxofon.jpg")) {
                    cargarPantallaInstrumentoPlus("assets/Interface/saxofon_plus.jpg");

                }

                if (instrumentoPlus.equalsIgnoreCase("assets/Interface/congas.jpg")) {
                    cargarPantallaInstrumentoPlus("assets/Interface/congas_plus.jpg");

                }

                if (instrumentoPlus.equalsIgnoreCase("assets/Interface/cencerro.jpg")) {
                    cargarPantallaInstrumentoPlus("assets/Interface/cencerro_plus.jpg");

                }

                if (instrumentoPlus.equalsIgnoreCase("assets/Interface/bateria.jpg")) {
                    cargarPantallaInstrumentoPlus("assets/Interface/bateria_plus.jpg");
                }
        }
    }

    public static AudioNode getSonidoInstrumento() {
        return sonidoInstrumento;
    }

    public static void setSonidoInstrumento(AudioNode sonidoInstrumento) {
        Gui2d.sonidoInstrumento = sonidoInstrumento;
    }
    
    public void cargarPantallaInstrumentoPlus(String rutaPantallPlus){
    
                    isPlus = true;
                    estoyVisible = false;
                    Plus p = new Plus(rutaPantallPlus);
                    p.setVisible(true);
                    this.setVisible(false);
    
    }
    
    
}
