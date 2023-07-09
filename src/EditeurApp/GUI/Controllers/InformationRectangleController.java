package EditeurApp.GUI.Controllers;
import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InformationRectangleController {
    // Ce contrôleur est associé au fichier InformationRectangle.fxml : afficher les informations d'un rectangle
        private Stage dialogStage;
        private boolean applyClicked = false;
        private RectangleShape rectangle;

        @FXML private Label nom;
        @FXML private Label Hauteur;
        @FXML private Label Largeur;
        @FXML private Label centreX;
        @FXML private Label centreY;
        @FXML Rectangle intColor;
        @FXML Rectangle conColor;

        public void setRectangle(RectangleShape rectangle){
            this.rectangle = rectangle;
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }
        public boolean isApplyClicked() {
            return applyClicked;
        }

        public void setInformation()
        {
            nom.setText(String.valueOf(rectangle.getName()));
            Hauteur.setText(String.valueOf(rectangle.getHeight()));
            Largeur.setText(String.valueOf(rectangle.getWidth()));
            centreX.setText(String.valueOf(rectangle.getCenterX()));
            centreY.setText(String.valueOf(rectangle.getCenterY()));
            intColor.setFill(rectangle.getColor());
            conColor.setFill(rectangle.getBorder());
        }

        @FXML
        private void handleCancel(){
            dialogStage.close();
        }
}
