package EditeurApp.GUI.Controllers;

import EditeurApp.GUI.GuiPolygon;
import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifPolController
// Ce contrôleur est associé au fenetre de modification des infos d'un polygon

{
    public static GuiPolygon selected_polygon;
    private Stage dialogStage;
    private boolean applyClicked = false;
    @FXML private ColorPicker colorPicker;
    @FXML private ColorPicker colorBorder;
    @FXML private TextField rayon;
    @FXML private TextField name;
    private PolygonRegulier polygon;


    public void setPolygone(PolygonRegulier polygon){
        this.polygon = polygon;
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
        polygon.setRayon(Double.parseDouble(this.rayon.getText()));
        polygon.setName(this.name.getText());
        polygon.setColor(this.colorPicker.getValue());
        polygon.setBorder(this.colorBorder.getValue());
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    public void ouvrir()
    {
        name.setText(polygon.getName());
        rayon.setText(String.valueOf(polygon.getRayon()));
        colorPicker.setValue(polygon.getColor());
        colorBorder.setValue(polygon.getBorder());
    }


}

