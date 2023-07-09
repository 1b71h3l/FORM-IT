package EditeurApp.GUI.Controllers;

import javafx.stage.Stage;

public class OperationWarningController {
    // Ce contrôleur est associé au fenetre d'erreurs qui concernent les opérations booleéennes

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void handleOk() {
        dialogStage.close();
    }

}
