package edu.uao.compu2.mars.utilities;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.ui.Picture;

/*
 * @author Juan pablo aguirre
 */
public class GUIUtilities {

    public static void marcoVida(Node guiNode, AssetManager assetManager) {
        Geometry recuadro = new Geometry("Recuadro", new Quad(210, 40));
        Material mtl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mtl.setColor("Color", ColorRGBA.Black);
        recuadro.setMaterial(mtl);
        recuadro.setLocalTranslation(10, 550, 0);
        guiNode.attachChild(recuadro);
    }

    public static void crearArma(Node guiNode, AssetManager assetManager) {
        Picture arma = new Picture("Gun");
        arma.setImage(assetManager, "Textures/Gun2.png", true);
        arma.setWidth(425);
        arma.setHeight(268);
        arma.setPosition(375, 0);
        guiNode.attachChild(arma);
    }

    public static void iniciarDiamantes(Node guiNode, AssetManager assetManager) {

        crearDiamante(250, 550, guiNode, assetManager, "notDi1", false);
        crearDiamante(320, 550, guiNode, assetManager, "notDi2", false);
        crearDiamante(390, 550, guiNode, assetManager, "notDi3", false);
        crearDiamante(460, 550, guiNode, assetManager, "notDi4", false);
        crearDiamante(530, 550, guiNode, assetManager, "notDi5", false);

    }

    public static void crearDiamante(int x, int y, Node guiNode, AssetManager assetManager, String name, boolean esRecolectado) {

        if (esRecolectado) {
            Picture diamond1 = new Picture("Diamond1");
            diamond1.setName(name);
            diamond1.setImage(assetManager, "Interface/diamond.png", true);
            diamond1.setWidth(65);
            diamond1.setHeight(35);
            diamond1.setPosition(x, y - 5);
            guiNode.attachChild(diamond1);
        } else {
            Picture diamond1 = new Picture("Diamond1");
            diamond1.setName(name);
            diamond1.setImage(assetManager, "Interface/notdiamondd.png", true);
            diamond1.setWidth(60);
            diamond1.setHeight(30);
            diamond1.setPosition(x, y);
            guiNode.attachChild(diamond1);
        }

    }

    public static void miraArma(Node guiNode, AssetManager assetManager, BitmapFont guiFont) {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("[+]"); // crosshairs
        ch.setColor(ColorRGBA.Red);
        ch.setLocalTranslation( // center
                400 - ch.getLineWidth() / 2, 300 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

    }

}
