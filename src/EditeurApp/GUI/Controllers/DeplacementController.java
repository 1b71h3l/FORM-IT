package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeplacementController {
    // Ce contrôleur est associé au fichier Deplacement.fxml

    private Stage dialogStage; //stage de la fenêtre
    private boolean applyClicked = false;
    private PolygonRegulier polygone; //polygon noyau
    private RectangleShape rectangle; //rectangle noyau
    @FXML private TextField DepX;
    @FXML private TextField DepY;

    public void setPolygone(PolygonRegulier polygone){
        this.polygone = polygone;
    }
    public void setRectangle (RectangleShape rectangle){ this.rectangle = rectangle;}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyClicked() {
        return applyClicked;
    }

    @FXML
    private void handleApply(){
        double dx = Double.parseDouble(this.DepX.getText());
        double dy = Double.parseDouble(this.DepY.getText());
        if(polygone != null){ polygone.setDep(dx, dy);} //ajouter les écarts de déplacement saisits au polygone noyau
        if(rectangle != null){ rectangle.setDep(dx, dy);} //ajouter les écarts de déplacement saisits au rectangle noyau
        this.applyClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

}
