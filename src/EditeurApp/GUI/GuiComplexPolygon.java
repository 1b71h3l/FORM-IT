package EditeurApp.GUI;

import EditeurApp.Kernel.PolygonComplex;
import EditeurApp.MainApp;

public class GuiComplexPolygon extends GuiShape {

    private PolygonComplex complex;  // un polygon complex du kernel, pour extraire les infos de créàeation

    public GuiComplexPolygon(PolygonComplex polygon) {  // le constructeur
        this.complex=polygon;
        this.getPoints().addAll(polygon.getPoints());
    }
    public GuiComplexPolygon() {} // un autre constructeur vide pour une utilisation plus flexible


    // getters et setters
    public PolygonComplex getComplexinfo () { return complex; }
    public void setComplexinfo(PolygonComplex polygone) {this.complex = polygone; }

    // sauvegarder les coordonnées du polygon complex à chaque déplacement
    public void sauvegarder(double offsetX, double offsetY) {
        GuiComplexPolygon.this.setTranslateX(offsetX + GuiComplexPolygon.this.getTranslateX());
        GuiComplexPolygon.this.setTranslateY(offsetY + GuiComplexPolygon.this.getTranslateY());
    }

    // afficher les informations du polygon complex
    public void showInfos() {
        MainApp.showInformationComplex();
    }
}
