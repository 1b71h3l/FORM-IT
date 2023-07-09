package EditeurApp.GUI.Controllers;

import javafx.stage.Stage;

public class WarningController {
    // Ce contrôleur est associé aux fenetres d'avertissements
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void handleOk() {
        dialogStage.close();
    }
}
