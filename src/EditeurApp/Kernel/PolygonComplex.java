package EditeurApp.Kernel;

import javafx.scene.paint.Color;

//Cette classe est utilisée pour sauvegarder les informations de chaque polygone
// obtenu après l'application d'une des ops booléennes

public class PolygonComplex {

    private Color color;      // la couleur de remplissage
    private Color border;     // la couleur de bordure
    private Double[] points;  // les coordonnées des sommets
    private String nom;       // le nom

    // constructeur vide pour une utilisation plus flexible
    public PolygonComplex() {}

    //constructeur
    public PolygonComplex(Color color,Color border,Double[] points) {
        this.color= color;
        this.border = border;
        this.points = points ;
    }

       // getters
    public Color getColor() { return color;}

    public Color getBorder() { return border;}

    public Double[] getPoints() {
        return points;
    }

    public String getName() {return nom;}

    // setters
    public void setColor(Color color) {this.color = color;}

    public void setBorder(Color border) {this.border = border;}

    public void setPoints(Double[] points) {
        this.points = points;
    }

    public void setNom(String nom){this.nom = nom;}
}
