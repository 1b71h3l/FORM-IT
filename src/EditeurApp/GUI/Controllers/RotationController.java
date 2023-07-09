package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RotationController {
     // Ce contrôleur est associé au fichier Rotation.fxml
    private Stage dialogStage;
    private boolean applyClicked = false;
    private PolygonRegulier polygone;
    private RectangleShape rectangle;
    @FXML
    private TextField angle;

       // setters
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

     // traite l'appuyage sur le bouton "appliquer"
    @FXML
    private void handleApply(){
        double angle = Double.parseDouble(this.angle.getText());
        if(polygone != null){ polygone.setRotateAngle(Math.toRadians(angle));} //ajouter l'angle de rotation au polygone noyau
        if(rectangle != null){ rectangle.setRotateAngle(Math.toRadians(angle));} //ajouter l'angle de rotation au rectangle noyau
        this.applyClicked = true;
        dialogStage.close();

    }

      // traite l'appuyage sur le bouton "annuler"
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}
