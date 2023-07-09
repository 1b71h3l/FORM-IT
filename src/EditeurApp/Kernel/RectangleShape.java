package EditeurApp.Kernel;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

//Cette classe est utilisée pour sauvegarder les informations de chaque rectangle crée
public class RectangleShape extends Shape {

    private double height;
    private double width;
    private double TLX, TLY;


    // constructeur initialisateur
    public RectangleShape(double xCenter, double yCenter, List<Double> angles, double height, double width, String name,Color color, Color border) {
        this.xCen = xCenter;
        this.yCen = yCenter;
        this.height = height;
        this.width = width;
        this.name = name;
        this.color = color ;
        this.color = border;
        this.angles=angles;
    }

    // un constructeur vide pour une utilisation plus flexible
    public RectangleShape() {}

    //setters
    public void setHeight(double height) {
        this.height = height;
    }
    public void setWidth(double width) {
        this.width = width;
    }


    //getters
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }

    //calculer les coordonnées du point TOP LEFT
    public void calculerTP()
    {
        TLX = xCen - (width / 2);
        TLY = yCen - (height / 2);
    }

    //Calculer les angles d'un rectangle en utilisant TOP LEFT POINT, largeur et hauteur
    public void calculerAngles()
    {
        angles.clear();
        calculerTP();
        //top left corner
        angles.add(TLX);
        angles.add(TLY);
        //top right corner
        angles.add(TLX + width);
        angles.add(TLY);
        //down right corner
        angles.add(TLX + width);
        angles.add(TLY + height);
        //down left corner
        angles.add(TLX);
        angles.add(TLY + height);

        if (rotateAngle != 0) { this.tourner(); }
    }

    //calculer les nouvelles coordonnées des sommets après le déplacement de (dx, dy)
    public void deplacer()
    {
        xCen = xCen + dx;
        yCen = yCen + dy;
        calculerAngles();
    }

    //calculer les nouvelles coordonnées des sommets après le rotation de (rotateAngle) deg
    public void tourner()
    {
        Double newX;
        Double newY;
        List<Double> L = new ArrayList();
        for(int i = 0; i < 8; i = i + 2)
        {
            newX = xCen + angles.get(i) * Math.cos(rotateAngle) - angles.get(i + 1) * Math.sin(rotateAngle);
            L.add(Math.round(newX *10000)/10000.0);
            System.out.println(newX);
            newY = yCen + angles.get(i) * Math.sin(rotateAngle) + angles.get(i + 1) * Math.cos(rotateAngle);
            L.add(Math.round(newY *10000)/10000.0);
            System.out.println(newY);
        }
        setAngles(L);
    }

}


