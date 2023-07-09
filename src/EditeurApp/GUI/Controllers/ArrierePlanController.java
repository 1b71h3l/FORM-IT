package EditeurApp.GUI.Controllers;

import EditeurApp.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

 // le controlleur de la fenetre de personnalisation de l'arriere plan
public class ArrierePlanController {
    @FXML private ColorPicker DrawingColor;  // une palette pour choisir la couleur
    private MainApp mainApp;
    private Stage dialogStage;
    private boolean applyClicked = false;  // un booleen qui indique si le bouton "appliquer" est appuye
    private ActionEvent event = null;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyClicked() {
        return applyClicked;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    private File file;

      // importer une image du disque
    public void OpenImage(ActionEvent event) {
        if (event != null) {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image","*.png","*.jpg"));
            this.file = fc.showOpenDialog(null);
            this.event = event;
        }
    }

        // personnaliser l'arriere plan
    public void Personaliser() {
        this.applyClicked = true;
        mainApp.getDrawingPane().setBackground(new Background(new BackgroundFill(DrawingColor.getValue(), null, null)));

        if (this.event != null)
        {

            Image image = new Image(file.toURI().toString());
            ImagePattern radialGradient = new ImagePattern(image, 0, 0, 1, 1, true);
            mainApp.getDrawingPane().setBackground(new Background(new BackgroundFill(radialGradient, null, null)));
        }
        dialogStage.close();
    }


     // annuler les modifications et fermer la fenetre
    public void Cancel(ActionEvent event) {
        dialogStage.close();
    }
}
