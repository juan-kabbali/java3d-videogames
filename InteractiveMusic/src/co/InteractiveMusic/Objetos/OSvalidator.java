package co.InteractiveMusic.Objetos;

/**
 *
 * @author Juan Pablo
 */
public class OSvalidator {
 
    

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String osArch = System.getProperty("os.arch").toLowerCase();

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    //Valida si es linux o cualquier distro de unix
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    public static boolean is64bit() {
        return "amd64".equals(osArch) || "x86_64".equals(osArch) || (osArch.indexOf("64") >= 0);
    }
    
    //Devuelve el nombre del SO completo
    public static String getOSRawName(){
        return OS+" "+osArch;
    }
    
    
}
