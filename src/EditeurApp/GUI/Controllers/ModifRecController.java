package EditeurApp.GUI.Controllers;

import EditeurApp.GUI.GuiRectangle;
import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifRecController
// Ce contrôleur est associé au fenetre de modification des infos d'un rectangle
{
    public static GuiRectangle selected_rectangle;
    private Stage dialogStage;
    private boolean applyClicked = false;
    @FXML private ColorPicker ColorPicker;
    @FXML private ColorPicker ColorBorder;
    @FXML private TextField Hauteur;
    @FXML private TextField Largeur;
    @FXML private TextField name;
    private RectangleShape rectangle;


    public void setRectangle(RectangleShape rectangle){
        this.rectangle = rectangle;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyClicked() {
        return applyClicked;
    }

    @FXML
    private void handleApply() {
        this.applyClicked = true;
        rectangle.setHeight(Double.parseDouble(this.Hauteur.getText()));
        rectangle.setWidth(Double.parseDouble(this.Largeur.getText()));
        rectangle.setName(this.name.getText());
        rectangle.setColor(this.ColorPicker.getValue());
        rectangle.setBorder(this.ColorBorder.getValue());
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    public void ouvrir()
    {
        name.setText(rectangle.getName());
        Hauteur.setText(String.valueOf(rectangle.getHeight()));
        Largeur.setText(String.valueOf(rectangle.getWidth()));
        ColorPicker.setValue(rectangle.getColor());
        ColorBorder.setValue(rectangle.getBorder());
    }
}
