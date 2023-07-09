package EditeurApp.GUI;

import EditeurApp.MainApp;
import javafx.scene.Cursor;
import javafx.scene.shape.Polygon;

public class GuiShape extends Polygon {

    private boolean is_selected = false; // un booléen pour indiquer si la forme est selectionnée
    private boolean is_doubleClicked = false; // un booléen pour indiquer si on a double-cliqué sur la forme
    private double orgSceneX, orgSceneY; // les coordonnées initiales de la forme avant chaque déplacement


    public GuiShape() {

         this.setCursor(Cursor.HAND);

         // la sélection
         this.setOnMouseClicked(mouseEvent -> {
              mouseEvent.consume();
              GuiShape.this.select();
             if(mouseEvent.getClickCount() == 2) {
                 GuiShape.this.showInfos();
             }

          });

          // le déplacement manuel
          this.setOnMousePressed((pressEvent) -> {
              orgSceneX = pressEvent.getSceneX();
              orgSceneY = pressEvent.getSceneY();
          });

          this.setOnMouseDragged((dragEvent) -> {
              double offsetX = dragEvent.getSceneX() - orgSceneX;
              double offsetY = dragEvent.getSceneY() - orgSceneY;

              orgSceneX = dragEvent.getSceneX();
              orgSceneY = dragEvent.getSceneY();

              GuiShape.this.sauvegarder(offsetX, offsetY);

          });

    }

     // afiichage des infos de la forme
    public void showInfos() {
    }


    // la sauvegarde des coordonnées de la forme apres chaque déplacement
    public void sauvegarder(double offsetX, double offsetY) {
        GuiShape.this.setTranslateX(offsetX + GuiShape.this.getTranslateX());
        GuiShape.this.setTranslateY(offsetY + GuiShape.this.getTranslateY());
    }


     // mise à jour de la bordure lors de la sélection
    public void select() {
        if (!is_selected) {
            is_selected = true;
            MainApp.setSelectedShape(this);
            MainApp.getContextMenu().setEnable();
            updateSelectionBorder();
        }

    }

    // revenir à la bordure initiale de la forme quand elle n'est plus sélectionnée
    public void deselect() {
        is_selected = false;
        updateSelectionBorder();

    }

     // applique les changements de bordure selon l'état de sélection de la forme
    private void updateSelectionBorder() {
        if (this.is_selected) {
            this.setStrokeWidth(3.0);
        } else {
            this.setStrokeWidth(1.0);
        }
    }


}
