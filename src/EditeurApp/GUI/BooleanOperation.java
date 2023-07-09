package EditeurApp.GUI;

import EditeurApp.Kernel.Segment;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class BooleanOperation {
    private GuiShape Poly1;
    private GuiShape Poly2;

    public BooleanOperation() {
    }


    public GuiShape getP1() {
        return this.Poly1;
    }

    public GuiShape getP2() {
        return this.Poly2;
    }

    public void setP1(GuiShape p1) {
        Poly1 = p1;
    }

    public void setP2(GuiShape p2) {
        Poly2 = p2;
    }

    //supprimer les doublons dans une liste de points 2D
    private void delete_duplicates(List<Point2D> R) {

        LinkedHashSet<Point2D> LL = new LinkedHashSet<Point2D>(R);
        R.clear();
        R.addAll(LL);
    }

    //créer une liste de points 2D à partir d'une liste de coordonnées (x,y)
    private List<Point2D> List_Points(List<Double> L) {
        List<Point2D> R = new ArrayList<>();
        for (int i = 0; i < L.size(); i = i + 2) {
            Point2D P = new Point2D(L.get(i), L.get(i + 1));
            R.add(P);
        }
        return R;
    }

    //créer une liste de coordonnées (x,y) à partir d'une liste de points 2D
    private List<Double> List_Coors(List<Point2D> L) {
        List<Double> RES = new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            RES.add(L.get(i).getX());
            RES.add(L.get(i).getY());
        }
        return RES;
    }

    //trouver la position d'un point 2D dans une liste
    private int isInList(List<Point2D> L, Point2D point) {
        int i = 0;
        while (i < L.size()) {
            if (L.get(i).getX() == point.getX() && L.get(i).getY() == point.getY()) {
                return i;
            } else i++;
        }
        return -1;
    }

    //ordonner une liste à partir d'un point donné
    private void orderFromPoint(List<Point2D> L, Point2D point) {
        List<Point2D> RES = new ArrayList<>();
        RES.add(point);
        for (int i = isInList(L, point) + 1; i < L.size(); i++) {
            RES.add(L.get(i));
        }
        //add points before point
        for (int i = 0; i < isInList(L, point); i++) {
            RES.add(L.get(i));
        }
        L.clear();
        L.addAll(RES);
    }

    //trouver la meilleure positions pour ajouter un point la list des points d'un polygone
    private int indexPolygon(List<Point2D> L, Point2D point) {
        GuiShape poly = new GuiShape();
        poly.getPoints().addAll(List_Coors(L));
        int i = -1;

        List<Segment> LS = List_Segments(poly);
        for (int j = 0; j < LS.size(); j++) {
            if (LS.get(j).insideSeg(point)) {
                i = isInList(L, LS.get(j).getP1()) + 1;
                return i;
            }
        }
        return i;
    }

    //créer une liste à partir d'un tableau
    private List<Double> arrayToList(Point2D[] L) {
        List<Point2D> M = Arrays.asList(L);
        List<Double> N = new ArrayList<>();
        for (int i = 0; i < M.size(); i++) {
            N.add(M.get(i).getX());
            N.add(M.get(i).getY());
        }
        return N;
    }


    //ordonner une liste de points 2D clockwise
    private Point2D[] OrderClockwise(Point2D[] points) {
        Double Xc = 0.0, Yc = 0.0;
        for (Point2D p : points) {
            Xc += p.getX();
            Yc += p.getY();
        }

        Xc = Xc / points.length;
        Yc = Yc / points.length;
        Point2D c = new Point2D(Xc, Yc);

        Point2D tmp;
        for (int i = points.length; i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (ComparePoint(c, points[j], points[j + 1]) == false) {
                    tmp = points[j];
                    points[j] = points[j + 1];
                    points[j + 1] = tmp;
                }
            }
        }
        return points;
    }


    //ordonner une liste de points 2D counter (inverse) clockwise
    private void orderCounter(List<Point2D> L) {
        List<Point2D> RES = new ArrayList<>();
        RES.add(L.get(0));
        for (int i = L.size() - 1; i > 0; i--) {
            RES.add(L.get(i));
        }
        L.clear();
        L.addAll(RES);
    }

    //comparer
    private boolean ComparePoint(Point2D ptc, Point2D pt1, Point2D pt2) {
        Segment S1 = new Segment(ptc, pt1);
        Segment S2 = new Segment(ptc, pt2);

        if (S1.getAngle() < S2.getAngle()) {
            return true;
        }
        if ((S1.getAngle() == S2.getAngle()) && (S1.getLength() < S2.getLength())) {
            return true;
        } else return false;
    }


    //verifier si un point appartient à la bordure d'un polygon
    private boolean pointBorder(GuiShape P, Point2D point) {
        boolean res = false;
        List<Point2D> R = List_Points(P.getPoints());
        Segment S = new Segment();
        for (int i = 0; i < R.size() - 1; i++) {
            S.setP1(R.get(i));
            S.setP2(R.get(i + 1));
            if (S.insideSeg(point) == true) {
                res = true;
            }
        }

        S.setP1(R.get(R.size() - 1));
        S.setP2(R.get(0));
        if (S.insideSeg(point) == true) {
            res = true;
        }
        return res;

    }


    //Trouver les points intersection entre un polygone et un segment
    private List<Point2D> intersectionPoints(GuiShape A, Segment Q) {
        List<Point2D> R = List_Points(A.getPoints());
        Segment S = new Segment();
        List<Point2D> list = new ArrayList<>();
        for (int i = 0; i < R.size() - 1; i++) {
            S.setP1(R.get(i));
            S.setP2(R.get(i + 1));
            if (S.segIntersection(Q) != null) {
                if (pointBorder(A, S.segIntersection(Q)) == true) {
                    list.add(S.segIntersection(Q));
                }
            }
        }
        S.setP1(R.get(R.size() - 1));
        S.setP2(R.get(0));
        if (S.segIntersection(Q) != null) {
            if (pointBorder(A, S.segIntersection(Q)) == true) {
                list.add(S.segIntersection(Q));
            }
        }
        return list;
    }


    //trouver les points intersection entre les deux polygones Poly1 et Poly2
    private List<Point2D> intersPolygone() {
        Segment S = new Segment();
        List<Point2D> RES = new ArrayList<>();
        List<Point2D> R = List_Points(Poly2.getPoints());
        for (int i = 0; i < R.size() - 1; i++) {
            S.setP1(R.get(i));
            S.setP2(R.get(i + 1));
            if (intersectionPoints(Poly1, S) != null) {
                RES.addAll(intersectionPoints(Poly1, S));
            }
        }
        S.setP1(R.get(R.size() - 1));
        S.setP2(R.get(0));
        if (intersectionPoints(Poly1, S) != null) {
            RES.addAll(intersectionPoints(Poly1, S));
        }
        return RES;
    }


    //trouver les points de P2 qui sont dans P1
    private List<Point2D> insidepoints(GuiShape P1, GuiShape P2) {
        List<Point2D> S = List_Points(P2.getPoints());
        List<Point2D> RES = new ArrayList();
        for (int i = 0; i < S.size(); i++) {
            //
            if (P1.contains(S.get(i)) == true) {
                RES.add(S.get(i));
            }
        }
        return RES;
    }


    //créer une liste de segements d'un polygone
    private List<Segment> List_Segments(GuiShape P1) {
        List<Segment> LS = new ArrayList<>();
        List<Point2D> L = List_Points(P1.getPoints());
        for (int i = 0; i < L.size() - 1; i++) {
            LS.add(new Segment(L.get(i), L.get(i + 1)));
        }
        LS.add(new Segment(L.get(L.size() - 1), L.get(0)));
        return LS;
    }


    //vérifier si un polygone est convex
    private boolean isConvex(GuiShape P) {
        //return true if all inside angles are less than 180
        boolean RES = true;
        double deg; //angle in degrees
        List<Segment> L = List_Segments(P);
        for (int i = 0; i < L.size() - 1; i++) {
            deg = Math.toDegrees(L.get(i + 1).getAngle() - L.get(i).getAngle());
            if (deg > 180) {
                RES = false;
            }
        }
        deg = Math.toDegrees(L.get(0).getAngle() - L.get(L.size() - 1).getAngle());
        if (deg > 180) {
            RES = false;
        }
        return RES;
    }

    //verifier si P1 est à l'intérieur de P2
    private boolean PolyinsidePoly(GuiShape P1, GuiShape P2) {
        boolean res = true;
        //returns true if P1 is inside P2
        List<Point2D> L = List_Points(P1.getPoints());
        for (int i = 0; i < L.size(); i++) {
            if (P2.contains(L.get(i)) == false) {
                res = false;
            }
        }
        return res;
    }



    public class OperationsException extends Exception{
    }



    //operation booléenne : intersection
    public GuiComplexPolygon intersection() throws OperationsException {
        GuiComplexPolygon RES = new GuiComplexPolygon();
        List<Point2D> L = new ArrayList<>();
        List<Double> CL;
        L.addAll(insidepoints(Poly1, Poly2));
            L.addAll(insidepoints(Poly2, Poly1));
            L.addAll(intersPolygone());
            delete_duplicates(L);
            CL = arrayToList(OrderClockwise(L.toArray(new Point2D[L.size()])));
            RES.getPoints().addAll(CL);

        RES.getPoints().addAll(CL);
        RES.setFill(Poly1.getFill());
        RES.setFill(Poly2.getFill());
        if (RES.getPoints().size() == 0) throw new OperationsException();
        else {
            return RES;
        }
    }


    //Opération booléenne : Union
    public GuiComplexPolygon Union() throws OperationsException {

        GuiComplexPolygon RES = new GuiComplexPolygon();
        List<Point2D> L = new ArrayList<>();
        List<Double> CL;

        //Cas1 : aucun point d'intersection entre les deux polygones
        if (intersPolygone().size() == 0) throw new OperationsException();

        //Cas2 : Poly1 is inside Poly2
        else if (PolyinsidePoly(Poly1, Poly2) == true) {
            RES.getPoints().addAll(Poly2.getPoints());
            return RES;
        }
        //Cas3 : Poly2 is inside Poly1
        else if (PolyinsidePoly(Poly2, Poly1) == true) {
            RES.getPoints().addAll(Poly1.getPoints());
            return RES;
        }

        else {
            System.out.println("Concave");
            int cpt = intersPolygone().size();
            //Add intersection points to both polygons points lists
            List<Point2D> L1 = List_Points(Poly1.getPoints());
            for (int i = 0; i < cpt; i++) {
                int j = indexPolygon(L1, intersPolygone().get(i));
                L1.add(j, intersPolygone().get(i));
            }
            delete_duplicates(L1);
            List<Point2D> L2 = List_Points(Poly2.getPoints());
            for (int i = 0; i < cpt; i++) {
                int j = indexPolygon(L2, intersPolygone().get(i));
                L2.add(j, intersPolygone().get(i));
            }
            delete_duplicates(L2);
            //to start from not an intersection/not an inside point
            int k = 0;
            while (isInList(intersPolygone(), L1.get(k)) != -1 || isInList(insidepoints(Poly2, Poly1), L1.get(k)) != -1) {
                k++;
            }
            orderFromPoint(L1, L1.get(k));

            int i = 0;
            while (cpt > 0) {
                if (isInList(insidepoints(Poly2, Poly1), L1.get(i)) == -1) {
                    L.add(L1.get(i));
                    i++;
                }

                if (isInList(intersPolygone(), L1.get(i)) != -1) {
                    cpt = cpt - 2;
                    //point d'intersection
                    //Rearrange L2 from point d'intersection
                    orderFromPoint(L2, L1.get(i));
                    L.add(L2.get(0)); //case the intersection point is also an inside point
                    int j = 1;
                    while (j < L2.size() && isInList(intersPolygone(), L2.get(j)) == -1) {
                        if (isInList(insidepoints(Poly1, Poly2), L2.get(j)) == -1) {
                            L.add(L2.get(j));
                        }
                        j++;
                    }
                    L.add(L2.get(j-1));
                    //index de deuxieme point d'intersection
                    if (j < L2.size()) i = isInList(L1, L2.get(j));
                }
            }
            //the rest of the poly1 points if they exist
            for (int j = i; j < L1.size(); j++) {
                L.add(L1.get(j));
            }

            delete_duplicates(L);
            CL = arrayToList(OrderClockwise(L.toArray(new Point2D[L.size()])));
            RES.getPoints().clear();
            RES.getPoints().addAll(CL);
            return RES;
        }
    }


    //Opération booléenne : Soustraction
    public GuiComplexPolygon soustraction(GuiShape poly1, GuiShape poly2) throws OperationsException {
        Poly1 = poly1;
        Poly2 = poly2;
        boolean first = true;
        GuiComplexPolygon RES = new GuiComplexPolygon();
        List<Point2D> LR = new ArrayList<>();
        List<Point2D> inters = intersPolygone();
        List<Point2D> L1 = List_Points(poly1.getPoints());
        List<Point2D> L2 = List_Points(poly2.getPoints());
        List<Double> L;
        if (inters.size() == 2) {
            //adding the intersection points to the list of polygon points but in the correct order
            L1.add(indexPolygon(L1, inters.get(0)), inters.get(0));
            L1.add(indexPolygon(L1, inters.get(1)), inters.get(1));
            L2.add(indexPolygon(L2, inters.get(0)), inters.get(0));
            L2.add(indexPolygon(L2, inters.get(1)), inters.get(1));
            //points of polygon2 are ordered in the inverse way
            orderCounter(L2);


            //find the firt non intersection point and order the list starting from it
            int k = 0;
            while (isInList(inters, L1.get(k)) != -1 || isInList(insidepoints(poly2, poly1), L1.get(k)) != -1) {
                k++;
            }
            orderFromPoint(L1, L1.get(k));


            int i = 0;
            while (i < L1.size()) {
                //inside points are removed
                if (isInList(insidepoints(poly2, poly1), L1.get(i)) == -1) {
                    //if it's the first intersersection point
                    if (isInList(inters, L1.get(i)) != -1 && first == true) {
                        first = false;
                        //order points of polygon2 starting from the intersection point
                        //makes it easier to go through inisde points in the right order
                        orderFromPoint(L2, L1.get(i));
                        //add the first intersection point
                        LR.add(L2.get(0));
                        //add the inside points
                        int j = 1;
                        while (isInList(inters, L2.get(j)) == -1) {
                            LR.add(L2.get(j));
                            j++;
                        }
                        //add the second intersection point
                        LR.add(L2.get(j)); //the second intersection point

                        //skip to the point after the second intersection point
                        i = i + j;
                    }
                    //add if it's not an inside point
                    if (i < L1.size()) {
                        LR.add(L1.get(i));
                    }
                }
                i++;
            }
            delete_duplicates(LR);
            L = List_Coors(LR);
            RES.getPoints().addAll(L);
            return RES;
        }

        //Erreur
        else throw new OperationsException();
    }


    //Opération booléenne : Miroir Horizontal
    public GuiComplexPolygon MiroirH(GuiShape P) {
        GuiComplexPolygon RES = new GuiComplexPolygon();
        List<Point2D> L = List_Points(P.getPoints());
        List<Point2D> R = new ArrayList<>();
        Point2D point = L.get(0);
        for (int i = 1; i < L.size(); i++) {
            //find le point le plus loins
            if (L.get(i).getX() > point.getX()) {
                point = L.get(i);
            }
        }
        for (int j = 0; j < L.size(); j++) {
            double d = point.getX() - L.get(j).getX();
            R.add(new Point2D(L.get(j).getX() + 2 * d, L.get(j).getY()));
        }

        RES.getPoints().addAll(List_Coors(R));
        return RES;
    }


    //Miroir Vertical
    public GuiComplexPolygon MiroirV(GuiShape P) {
        GuiComplexPolygon RES = new GuiComplexPolygon();
        List<Point2D> L = List_Points(P.getPoints());
        List<Point2D> R = new ArrayList<>();
        Point2D point = L.get(0);
        for(int i = 1; i < L.size(); i++)
        {
            if(L.get(i).getY() > point.getX()){
                point = L.get(i);
            }
        }

        for(int j = 0; j < L.size(); j++)
        {
            double d = point.getY() - L.get(j).getY();
            R.add(new Point2D(L.get(j).getX(), L.get(j).getY()  + 2 * d));
        }

        RES.getPoints().addAll(List_Coors(R));
        return RES;
    }

}
