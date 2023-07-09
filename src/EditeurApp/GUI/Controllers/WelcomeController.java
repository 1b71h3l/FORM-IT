package EditeurApp.GUI.Controllers;

import EditeurApp.MainApp;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class WelcomeController {
    // Ce contrôleur est associé a la fenetre de bienvenue

    private Stage dialogStage;
    private MainApp mainApp;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setMainApp(MainApp mainApp)
    {this.mainApp = mainApp;}

    public void OpenRoot() {
        dialogStage.close();
    }

    public void Aide() {
        mainApp.showAide();
    }
}
