package co.InteractiveMusic.Main;

import co.InteractiveMusic.Principal;
import com.jme3.system.AppSettings;


/**
 * @author  Aguirre Juan 
 * @author Doncel Andres
 */
public class Main  {

    public static void main(String[] args) {
        
        Principal aplicacion = new Principal();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        settings.setSettingsDialogImage("Interface/inicio.jpg");
        aplicacion.setSettings(settings);
        aplicacion.start();
    }

}
