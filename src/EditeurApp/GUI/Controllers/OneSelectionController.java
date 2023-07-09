package EditeurApp.GUI.Controllers;

import javafx.stage.Stage;

public class OneSelectionController {
    // Ce contrôleur est associé au fenetre d'erreur qui concerne la sélection de deux formes et l'appuyage sur le bouton de miroir
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void handleOk() {
        dialogStage.close();
    }
}
