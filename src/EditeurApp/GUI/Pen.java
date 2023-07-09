package EditeurApp.GUI;

import javafx.scene.paint.Color;


public class Pen {

    private double width; // l'épaisseur du crayon
    private Color color;  // la couleur du crayon

    // setters et getters
    public void setWidth(double width) {
        this.width=width;
    }

    public void setColor(Color color) {
        this.color=color;
    }

    public double getWidth() {
        return this.width;
    }

    public Color getColor() {
        return this.color;
    }
}
