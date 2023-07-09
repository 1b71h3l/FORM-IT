package EditeurApp.GUI.Controllers;

import javafx.stage.Stage;

public class SelectionWarningController {
    // Ce contrôleur est associé a la fenetre d'erreurs qui concerne la selection
    private Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void handleOk() {
        dialogStage.close();
    }
}
