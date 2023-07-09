package EditeurApp.GUI;

import EditeurApp.MainApp;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;

  // une instance de cette classe représente un dessin
public class Doodle extends Path {

    private boolean is_selected = false; // un booléen qui indique si le dessin est selectionné
    private double orgSceneX, orgSceneY; // les coordonnées originales du dessin
    private Pen pen;  // un crayon

    public Doodle(Pen pen) {
      // constructeur
        this.pen = pen;
        this.setStrokeWidth(pen.getWidth()); // l'épaisseur du dessin
        this.setStroke(pen.getColor());   // la couleur du dessin

        if (MainApp.penEnabled == false )
        {
        this.setCursor(Cursor.HAND);

        // sélection du dessin
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
                Doodle.this.select();
            }
        });

        // déplacement manuel du dessin
        this.setOnMousePressed((pressEvent) -> {
            orgSceneX = pressEvent.getSceneX();
            orgSceneY = pressEvent.getSceneY();
        });

        this.setOnMouseDragged((dragEvent) -> {
            double offsetX = dragEvent.getSceneX() - orgSceneX;
            double offsetY = dragEvent.getSceneY() - orgSceneY;

            orgSceneX = dragEvent.getSceneX();
            orgSceneY = dragEvent.getSceneY();

            Doodle.this.setTranslateX(offsetX + Doodle.this.getTranslateX());
            Doodle.this.setTranslateY(offsetY + Doodle.this.getTranslateY());

        });
    }
    }

     // mise à jour de l'épaisseur du dessin lors de la sélection
    public void select() {
        if (!is_selected) {
            is_selected = true;
            MainApp.selectedDoodle = Doodle.this;
            updateSelectionBorder();
        }

    }

    // revenir à l'épaisseur initiale du dessin quand il n'est plus selectionné
    public void deselect() {
        is_selected = false;
        updateSelectionBorder();

    }

      // applique les changements de bordure selon l'état de sélection de la forme
    private void updateSelectionBorder() {
        if (this.is_selected) {
            this.setStrokeWidth(Doodle.this.pen.getWidth() + 2);
        } else {
            this.setStrokeWidth(Doodle.this.pen.getWidth());
        }
    }

}
