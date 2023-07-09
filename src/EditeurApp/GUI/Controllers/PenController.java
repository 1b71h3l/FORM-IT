package EditeurApp.GUI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import EditeurApp.GUI.*;


public class PenController {

    private Stage dialogStage;
    private boolean applyClicked = false;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField width;
    private Pen pen;

    public void setPen(Pen pen) {
        this.pen = pen;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyClicked() {
        return applyClicked;
    }

      // traite l'appuyage sur le bouton "appliquer"
    @FXML
    private void handleApply() {
        this.applyClicked = true;
        pen.setWidth(Double.parseDouble(this.width.getText()));
        pen.setColor(this.colorPicker.getValue());
        dialogStage.close();
    }

      // traite l'appuyage sur le bouton "annuler"
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}

