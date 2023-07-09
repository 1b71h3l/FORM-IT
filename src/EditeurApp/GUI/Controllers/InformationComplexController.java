package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonComplex;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InformationComplexController {
    // Ce contrôleur est associé au fichier InformationComplex.fxml : afficher les informations d'un polygone complex
        private Stage dialogStage;
        private boolean applyClicked = false;
        private PolygonComplex complex;
        @FXML
        private TextField name;
        @FXML private TextField couleur;
        @FXML private TextField contour;

        public void setComplex(PolygonComplex complex){
            this.complex = complex;
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }
        public boolean isApplyClicked() {
            return applyClicked;
        }

        public void setInformation()
        {
            name.setText(String.valueOf(complex.getName()));
            couleur.setText(String.valueOf(complex.getColor()));
            contour.setText(String.valueOf(complex.getBorder()));
        }

        @FXML
        private void handleCancel(){
            dialogStage.close();
        }
    }

