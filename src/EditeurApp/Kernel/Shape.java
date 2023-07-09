package EditeurApp.Kernel;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class  Shape {

    protected double xCen;   // x coordonnée du center
    protected double yCen;   // y coordonnée du centre
    protected String name;   // le nom
    protected Color color;   // la couleur de remplissage
    protected Color border;  // la couleur de bordure
    protected List<Double> angles = new ArrayList<Double>();  // liste des angles des sommets
    protected double dx = 0;    // déplacement selon x
    protected double dy = 0;    // déplacement selon y
    protected double rotateAngle = 0;  // l'angle de rotation

       // constructeur
    public Shape() {}

      // getters
    public double getCenterX() { return xCen; }
    public double getCenterY() {
        return yCen;
    }

    public String getName() {
        return name;
    }
    public Color getColor() { return color;}

    public Color getBorder() { return border;}
    public List<Double> getAngles() { return angles;}

       // setters
    public void setCenterX(double x) { xCen = x; }
    public void setCenterY(double y) { yCen = y; }

    public void setName(String name) {this.name = name;}
    public void setColor(Color color) {this.color = color;}

    public void setBorder(Color border) {this.border = border;}
    public void setAngles(List <Double> LL)
    {
        angles.clear();
        angles.addAll(LL);
    }

    public void setDep(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public void setRotateAngle(double angle)
    {
        this.rotateAngle = angle;
    }

      // calculer les angles
    public void calculer_angles() { }

}
