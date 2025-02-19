
package jajagames.compu.juego.utils;

import jajagames.compu.juego.domain.Pared;

public class LocalizadorPared {

    public static Pared obtenerPared(String claseQueRepresentaAlPlaneta) {
        try {
            Class claseDePlaneta = Class.forName(claseQueRepresentaAlPlaneta);
            Pared planeta = (Pared) claseDePlaneta.newInstance();
            return planeta;
        } catch (Exception e) {
            System.out.println("No se pudo localizar el planeta [" + claseQueRepresentaAlPlaneta
                    + "] Error:" + e.getMessage());
        }
        return null;
    }
}
