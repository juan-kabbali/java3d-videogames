package co.InteractiveMusic.Utilities;

import co.InteractiveMusic.Principal;
import com.jme3.audio.AudioNode;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javazoom.jl.player.Player;

public class Plus extends javax.swing.JFrame implements MouseListener, KeyListener{

    public static AudioNode muestra;
    private static BufferedImage icono;
    private Panel panel;
    private static int comando;
    public static boolean estoyVisible;
    private  String ruta;
    public static Player sonidoPlus;
    public static  Clip sonidoD1 ;
    private static AudioInputStream d1 = null;
    
    public Plus(String ruta) {
        
        super("Interactive Music Computacion grafica 2");
        initComponents();
        setUpPlus(ruta);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        estoyVisible = true;
        this.addMouseListener(this);
        this.ruta = ruta;
 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1026, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void setUpPlus(String ruta) {
       
        try {
            icono = ImageIO.read(new File("assets/Interface/logo.png"));

        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(icono);
        panel = new Panel(ruta);
        add(BorderLayout.CENTER, panel);
        panel.setToolTipText("Interactive Music - PLUS");
        panel.addMouseListener(this);
        setLocationRelativeTo(null);
        panel.setSize(this.getSize());
        this.setResizable(false);
        this.addKeyListener(this);
    
    }

    public void mouseClicked(MouseEvent e) {
    
    }  public void mouseReleased(MouseEvent e) {}    public void mouseEntered(MouseEvent e) {}    public void mouseExited(MouseEvent e) { }

    public void mousePressed(MouseEvent e) {
       
        comando = 0;
        System.out.println(e.getPoint());
        
            if (Colisiones2d.deteccionBotones(e.getPoint(), new Point(148,679),76, 688)) {
            comando = 1; //volver
        }
                
            if(ruta.equalsIgnoreCase("assets/Interface/saxofon_plus.jpg")){
         activarColisionSaxo(e);}
        
          if(ruta.equalsIgnoreCase("assets/Interface/bateria_plus.jpg")){
         activarColisionBateria(e);}
        
           if(ruta.equalsIgnoreCase("assets/Interface/congas_plus.jpg")){activarColisionCongas(e);}
           
             if(ruta.equalsIgnoreCase("assets/Interface/cencerro_plus.jpg")){
         activarColisionCencerro(e);}
                  
                   switch (comando) {
             
            case 1:
            this.setVisible(false);
            break;
}
 
    }

    private void activarColisionSaxo(MouseEvent e) {

        double posi;
      
        posi = Colisiones2d.distancia(e.getPoint(), new Point(699, 593));
        if (posi < 20) {
            comando = 2; // do
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(718, 555));
        if (posi < 15) {
            comando = 3; // re
        }
        
          posi = Colisiones2d.distancia(e.getPoint(), new Point(696,519));
        if (posi < 15) {
            comando = 4; // mi
        }
        
            posi = Colisiones2d.distancia(e.getPoint(), new Point(662,488));
        if (posi < 15) {
            comando = 5; // fa
        }
            posi = Colisiones2d.distancia(e.getPoint(), new Point(548,294));
        if (posi <  11) {
            comando = 6; // sol
        }
        
            posi = Colisiones2d.distancia(e.getPoint(), new Point(519,287));
        if (posi < 11) {
            comando = 7; // la
        }
            posi = Colisiones2d.distancia(e.getPoint(), new Point(495,260));
        if (posi < 11) {
            comando = 8; // si
        }
        
         switch (comando) {

            case 2:
                reproducir("assets/Sounds/plusSaxo/do.wav");
                System.out.println("saxoPlus");
                break;
            case 3:
                reproducir("assets/Sounds/plusSaxo/re.wav");
                System.out.println("saxoPlus");
                break;
            case 4:
                reproducir("assets/Sounds/plusSaxo/mi.wav");
                System.out.println("saxoPlus");
                break;
            case 5:
                reproducir("assets/Sounds/plusSaxo/fa.wav");
                System.out.println("saxoPlus");
                break;
            case 6:
                reproducir("assets/Sounds/plusSaxo/sol.wav");
                System.out.println("saxoPlus");
                break;
            case 7:
                reproducir("assets/Sounds/plusSaxo/la.wav");
                System.out.println("saxoPlus");
                break;
            case 8:
                reproducir("assets/Sounds/plusSaxo/si.wav");
                System.out.println("saxoPlus");
                break;
                       } 
       
    }

    private void activarColisionBateria(MouseEvent e) {
     
        double posi;
        if ((Colisiones2d.deteccionBotones(e.getPoint(), new Point(461, 432), 121, 56))) {
            comando = 2; //kick
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(230, 249));
        if (posi < 100) {
            comando = 3; // crash1
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(820, 225));
        if (posi < 110) {
            comando = 4; // crash2
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(115, 415));
        if (posi < 90) {
            comando = 5; // ride1
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(930, 405));
        if (posi < 80) {
            comando = 6; // ride2
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(330, 510));
        if (posi < 90) {
            comando = 7; // snare
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(400, 320));
        if (posi < 75) {
            comando = 8; // tom1
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(620, 335));
        if (posi < 95) {
            comando = 9; // tom2
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(775, 515));
        if (posi < 80) {
            comando = 10; // tom3
        }
        switch (comando) {

            case 2:
                reproducir("assets/Sounds/plusBateria/kick.wav");
                System.out.println("bateriaPlus");
                break;
            case 3:
                reproducir("assets/Sounds/plusBateria/crash1.wav");
                System.out.println("bateriaPlus");
                break;
            case 4:
                reproducir("assets/Sounds/plusBateria/crash2.wav");
                System.out.println("bateriaPlus");
                break;
            case 5:
                reproducir("assets/Sounds/plusBateria/ride1.wav");
                System.out.println("bateriaPlus");
                break;
            case 6:
                reproducir("assets/Sounds/plusBateria/ride2.wav");
                System.out.println("bateriaPlus");
                break;
            case 7:
                reproducir("assets/Sounds/plusBateria/snare.wav");
                System.out.println("bateriaPlus");
                break;
            case 8:
                reproducir("assets/Sounds/plusBateria/tom1.wav");
                System.out.println("bateriaPlus");
                break;
            case 9:
                reproducir("assets/Sounds/plusBateria/tom2.wav");
                System.out.println("bateriaPlus");
                break;
            case 10:
                reproducir("assets/Sounds/plusBateria/tom3.wav");
                System.out.println("bateriaPlus");
                break;
        }
    }
 
     private void activarColisionCongas(MouseEvent e) {
         
          double posi;
      
        posi = Colisiones2d.distancia(e.getPoint(), new Point(250, 250));
        if (posi < 115) {
            comando = 2; // quinto
        }
        posi = Colisiones2d.distancia(e.getPoint(), new Point(530, 440));
        if (posi < 145) {
            comando = 3; // requinto
        }
        
          posi = Colisiones2d.distancia(e.getPoint(), new Point(785,265));
        if (posi < 140) {
            comando = 4; // tumbadora
        }
        
         switch (comando) {

            case 2:
                reproducir("assets/Sounds/plusCongas/quintoo.wav");
                System.out.println("congasPlus");
                break;
            case 3:
                reproducir("assets/Sounds/plusCongas/requinto.wav");
                System.out.println("congasPlus");
                break;
            case 4:
                reproducir("assets/Sounds/plusCongas/tumbadora.wav");
                System.out.println("congasPlus");
                break;
                       } 
               }
     
     public  static void reproducir(String rutaSonido) {
                 
                  try {
            sonidoD1 = (Clip) AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Plus.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
        try {        
            d1 = AudioSystem.getAudioInputStream(new File(rutaSonido));
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Plus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Plus.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sonidoD1.open(d1);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Plus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Plus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sonidoD1.start();
        
    }

    private void activarColisionCencerro(MouseEvent e) {
        
        
        if (Colisiones2d.deteccionBotones(e.getPoint(), new Point(379,172),163,254)) {
            comando = 2; //arriba
        }
        
        
        if (Colisiones2d.deteccionBotones(e.getPoint(), new Point(429,355),241,147)) {
            comando = 3; //abajo
        }
        
             switch (comando) {

            case 2:
                reproducir("assets/Sounds/plusCencerro/arriba.wav");
               
                break;
            case 3:
                reproducir("assets/Sounds/plusCencerro/abajo.wav");
          
                break;
         
                       } 
        
        
    }

    public void keyTyped(KeyEvent e) {} public void keyReleased(KeyEvent e) {  }

    public void keyPressed(KeyEvent teclado) {      
    
        System.out.println(teclado.getKeyChar());
        comando = 0;
        activarTeclasBateria(teclado);
    }

    private static void activarTeclasBateria(KeyEvent teclado) {
        
  
         
        if (teclado.VK_SPACE == teclado.getKeyCode()) { comando = 2; /*kick*/ }
        
        if (teclado.VK_Z == teclado.getKeyCode()) {comando = 3; /*crash1*/ }
      
        if (teclado.VK_X == teclado.getKeyCode()) {comando = 4; /*crash2*/ }
        
         if (teclado.VK_C == teclado.getKeyCode()) {comando = 5; /*ride1*/ }
         
         if (teclado.VK_V == teclado.getKeyCode()) {comando = 6; /*ride2*/ }
         
          if (teclado.VK_A == teclado.getKeyCode()) {comando = 7; /*saner*/ }
          
          if (teclado.VK_S == teclado.getKeyCode()) {comando = 8; /*tom1*/ }
         
          if (teclado.VK_D == teclado.getKeyCode()) {comando = 9; /*tom2*/ }
         
          if (teclado.VK_F == teclado.getKeyCode()) {comando = 10; /*tom3*/ }

        switch (comando) {

            case 2:
                reproducir("assets/Sounds/plusBateria/kick.wav");
                System.out.println("bateriaPlus");
                break;
            case 3:
                reproducir("assets/Sounds/plusBateria/crash1.wav");
                System.out.println("bateriaPlus");
                break;
            case 4:
                reproducir("assets/Sounds/plusBateria/crash2.wav");
                System.out.println("bateriaPlus");
                break;
            case 5:
                reproducir("assets/Sounds/plusBateria/ride1.wav");
                System.out.println("bateriaPlus");
                break;
            case 6:
                reproducir("assets/Sounds/plusBateria/ride2.wav");
                System.out.println("bateriaPlus");
                break;
            case 7:
                reproducir("assets/Sounds/plusBateria/snare.wav");
                System.out.println("bateriaPlus");
                break;
            case 8:
                reproducir("assets/Sounds/plusBateria/tom1.wav");
                System.out.println("bateriaPlus");
                break;
            case 9:
                reproducir("assets/Sounds/plusBateria/tom2.wav");
                System.out.println("bateriaPlus");
                break;
            case 10:
                reproducir("assets/Sounds/plusBateria/tom3.wav");
                System.out.println("bateriaPlus");
                break;
        }
        
    }

   
      

}