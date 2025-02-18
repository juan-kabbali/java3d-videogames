/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uao.compu.appgames;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author Usuario
 */
public class Enemy {
    protected AssetManager assetManager;
    private static Spatial enemy;

    public static Spatial getEnemy() {
        return enemy;
    }

    public Enemy(Spatial enemy) {
        this.enemy = enemy;
    }
    
    
}
