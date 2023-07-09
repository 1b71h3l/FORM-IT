package EditeurApp.Kernel;

import javafx.geometry.Point2D;


public class Segment {
    private Point2D P1;
    private Point2D P2;
    private double Length;
    private double Angle;

    //constructeurs
    public Segment(){}
    public Segment(Point2D P1, Point2D P2)
    {
        this.P1 = P1;
        this.P2 = P2;
    }

    //getters
    public Point2D getP1()
    {
        return this.P1;
    }

    public Point2D getP2()
    {
        return this.P2;
    }

    public void afficher(){
        System.out.println("Segment = " + P1 + " " + P2);
    }

    //setters
    public void setP1 (Point2D P1)
    {
        this.P1 = P1;
    }

    public void setP2 (Point2D P2)
    {
        this.P2 = P2;
    }

    //calculer la longueur du segment
    public double getLength()
    {
        double x = P1.getX() - P2.getX();
        double y = P1.getY() - P2.getY();
        this.Length = Math.sqrt(x*x+y*y);
        return this.Length;
    }

    //calculer l'angle du segment
    public double getAngle()
    {
        double x = P1.getX() - P2.getX();
        double y = P1.getY() - P2.getY();
        double angle = Math.atan2(y,x);
        if (angle <= 0)
        {
            angle += 2*Math.PI;
        }
        this.Angle = angle;
        return this.Angle;
    }

    // verifier si un point appartient à un segment
    public boolean insideSeg (Point2D Q)
    {
        boolean res = false;
        if(Q.getX() < Math.max(P1.getX(), P2.getX()) || isEqual(Q.getX(), Math.max(P1.getX(), P2.getX())))
        {
            if(Q.getX() > Math.min(P1.getX(), P2.getX()) || isEqual(Q.getX(), Math.max(P1.getX(), P2.getX())))
            {
                if(Q.getY() < Math.max(P1.getY(), P2.getY()) || isEqual(Q.getY(), Math.max(P1.getY(), P2.getY())))
                {
                    if(Q.getY() > Math.min(P1.getY(), P2.getY()) || isEqual(Q.getY(), Math.max(P1.getY(), P2.getY())))
                    {
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    //verfier si un segment est vertical
    public boolean isVertical()
    {
        return (isEqual(getP1().getX(), getP2().getX()));
    }

    //Verifier si un segment est horizontal
    public boolean isHorizontal()
    {
        return (isEqual(getP1().getY(), getP2().getY()));
    }

    //trouver les points d'intersection entre deux segments
    public Point2D segIntersection(Segment S) {
        double x = 0;
        double y = 0;

        double a1 = (S.getP2().getY() - S.getP1().getY()) / (S.getP2().getX() - S.getP1().getX());
        double a2 = (P2.getY() - P1.getY()) / (P2.getX() - P1.getX());
        double b1 = S.getP1().getY() - a1 * S.getP1().getX();
        double b2 = P1.getY() - a2 * P1.getX();

        if (this.isVertical() || S.isVertical()) {
            if (this.isVertical()) {
                x = this.getP1().getX();
                y = a1 * x + b1; }
            else { x = S.getP1().getX();
                y = a2 * x + b2;}
        }
        else {
            x = (b2 - b1) / (a1 - a2);
            y = a2 * x + b2;
        }
        Point2D P = new Point2D(Math.round(x *10000)/10000.0, Math.round(y *10000)/10000.0);
        if (this.insideSeg(P) == true && S.insideSeg(P) == true) {
            return P;
        } else {
            return null;
        }
    }


    //verifier si deux valeurs sont très proche on peut les considérés le même
    private boolean isEqual (double x, double y) {
        double diff = x - y;
        return (Math.abs(diff) < 0.01);

    }


}
