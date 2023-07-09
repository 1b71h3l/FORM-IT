package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonRegulier;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InformationPolygoneController {

    // Ce contrôleur est associé au fichier InformationPolygone.fxml : afficher les informations d'un polygone regulier
    private Stage dialogStage;
    private boolean applyClicked = false;
    private PolygonRegulier polygone;
    @FXML private Label nom;
    @FXML private Label rayon;
    @FXML private Label centreX;
    @FXML private Label centreY;
    @FXML Rectangle intColor;
    @FXML Rectangle conColor;

    public void setPolygone(PolygonRegulier polygone){
        this.polygone = polygone;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isApplyClicked() {
        return applyClicked;
    }


    public void setInformation()
    {
        nom.setText(String.valueOf(polygone.getName()));
        rayon.setText(String.valueOf(polygone.getRayon()));
        centreX.setText(String.valueOf(polygone.getCenterX()));
        centreY.setText(String.valueOf(polygone.getCenterY()));
        intColor.setFill(polygone.getColor());
        conColor.setFill(polygone.getBorder());
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}
