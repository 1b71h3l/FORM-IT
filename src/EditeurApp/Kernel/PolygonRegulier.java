package EditeurApp.Kernel;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;


//Cette classe est utilisée pour sauvegarder les informations de chaque polygone régulier crée
public class PolygonRegulier extends Shape {

    private int nSeg;       // le nombre de segments
    private double rayon;   // la longueur du rayon

    // constructeur vide pour une utilisation plus flexible
    public PolygonRegulier() {}

      // le constructeur initialisateur
    public PolygonRegulier(double xCen, double yCen,List<Double> angles, double rayon, int nSeg, String name,Color color,Color border) {

        this.xCen = xCen;
        this.yCen = yCen;
        this.rayon = rayon;
        this.nSeg = nSeg;
        this.name = name ;
        this.color= color;
        this.border = border;
        this.angles=angles;
    }

        //Getters
    public double getRayon() {
        return rayon;
    }

    public int getnSeg() {
            return nSeg;
        }



        //Setters
    public void setnSeg(int n) { nSeg = n; }

    public void setRayon(double r) { rayon = r; }



    //Calculer les coordonnées des sommets d'un polygone en utilisant le centre et le rayon
    public void calculer_angles()
    {
        double x, y;
        angles.clear();
        for(int i = 0; i <nSeg; i++)
        {
            x = xCen + rayon * Math.cos(Math.PI/ 2 + (i * 2 * Math.PI/nSeg));
            y = yCen + rayon * Math.sin(Math.PI/ 2 + (i * 2 * Math.PI/nSeg));
            angles.add(Math.round(x * 10000)/10000.0);
            angles.add(Math.round(y * 10000)/10000.0);
        }
        if (rotateAngle != 0) { this.tourner(); }

    }

    //calculer les nouvelles coordonnées des sommets après le rotation de (rotateAngle) deg
    public void tourner()
    {
        //le centre ne change pas, on modifie que les coordonnées des sommets
        List<Double> L = new ArrayList<Double>();
        Double newX;
        Double newY;
        for(int i = 0; i < angles.size(); i = i + 2)
        {
            newX = getCenterX() + (angles.get(i) - getCenterX()) * Math.cos(rotateAngle) + (angles.get(i+1) - getCenterY()) * Math.sin(rotateAngle);
            L.add(Math.round(newX *10000)/10000.0);
            newY = getCenterY() - (angles.get(i) - getCenterX()) * Math.sin(rotateAngle) + (angles.get(i+1) - getCenterY()) * Math.cos(rotateAngle);
            L.add( Math.round(newY *10000)/10000.0);
        }
        setAngles(L);
    }

    //calculer les nouvelles coordonnées des sommets après le déplacement de (dx, dy)
    public void deplacer() {
        this.setCenterX(getCenterX() + this.dx);
        this.setCenterY(getCenterY() + this.dy);
        this.calculer_angles();
    }

}

