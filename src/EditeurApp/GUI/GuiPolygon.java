package EditeurApp.GUI;

import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.MainApp;

public class GuiPolygon extends GuiShape {

    private PolygonRegulier polygon;  // un polygon régulier du Kernel pour extraire les infos de la création

    public GuiPolygon(PolygonRegulier polygon) {
        // construire un polygone régulier graphique a partir des infos extraits du kernel
        this.polygon=polygon;
        polygon.calculer_angles();
        this.getPoints().addAll(polygon.getAngles());
    }

    // setters et getters
    public PolygonRegulier getPolygonInfo() { return polygon; }
    public void setPolygonInfo(PolygonRegulier polygone) {this.polygon = polygone; }

      // sauvegarder les coordonnées du polygon régulier à chaque déplacement
    public void sauvegarder(double dx, double dy) {
        polygon.setDep(dx, dy);
        polygon.deplacer();
        this.getPoints().clear();
        this.getPoints().addAll(polygon.getAngles());
    }

    // afficher les infos du polygon régulier
    public void showInfos() {
        MainApp.showInformationPolygone();
    }

}
