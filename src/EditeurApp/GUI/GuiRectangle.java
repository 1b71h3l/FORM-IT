package EditeurApp.GUI;

import EditeurApp.Kernel.RectangleShape;
import EditeurApp.MainApp;

public class GuiRectangle extends GuiShape {

    private RectangleShape rectangle;  // un rectangle du kernel pour extraire les infos de la création

    public GuiRectangle(RectangleShape rectangle) {
        // construire un rectangle graphique à partir des infos extraits du kernel
        this.rectangle = rectangle;
        this.getPoints().clear();
        rectangle.calculerAngles();
        this.getPoints().addAll(rectangle.getAngles());
    }

     // setters et getters
    public RectangleShape getRectangleInfo()
    {
        return rectangle;
    }
    public void setRectangleInfo(RectangleShape rectangle) { this.rectangle = rectangle; }

    // sauvegarder les coordonnées du rectangle dans le kernel à chaque déplacement
    public void sauvegarder(double dx, double dy) {
        rectangle.setDep(dx, dy);
        rectangle.deplacer();
        this.getPoints().clear();
        this.getPoints().addAll(rectangle.getAngles());
    }

       // afficher les infos du rectangle
    public void showInfos() {
        MainApp.showInformationRectangle();
    }

}
