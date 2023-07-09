package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonRegulier;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreationPolygonController {
    // Ce contrôleur est associé au fichier CreationPolygon.fxml


    static private int nombre = 0; //nombre de polygones crées, utilisé pour nommer les polygones sans nom
    private Stage dialogStage; // le stage de la fenêtre
    private PolygonRegulier polygon;  // le polygone noyau qui contient les informations
    private boolean applyClicked = false;


    @FXML private TextField nSeg;
    @FXML private TextField rayon;
    @FXML private TextField cenX;
    @FXML private TextField cenY;
    @FXML private TextField name;
    @FXML private ColorPicker colorPicker;
    @FXML private ColorPicker colorBorder;


    // setters
    public void setPolygon(PolygonRegulier polygon){
        this.polygon = polygon;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApplyClicked() {
        return applyClicked;
    }

    //donner un nom au polygone
    public void setName(){
        String New = "P" +  String.valueOf(nombre);
        polygon.setName(New);
        nombre++;
    }

    //quand on click apply
    @FXML private void handleApply(){
         //ajouter les informations entrées au polygone de noyau
        polygon.setnSeg(Integer.parseInt(this.nSeg.getText()));
        polygon.setRayon(Double.parseDouble(this.rayon.getText()));
        polygon.setCenterX(Double.parseDouble(this.cenX.getText()));
        polygon.setCenterY(Double.parseDouble(this.cenY.getText()));
        if(this.name.getText().isEmpty())
            this.setName(); //si l'utilisateur ne saisit pas un nom on donne un nombre au polygone.
        else
            polygon.setName(this.name.getText());
        polygon.setColor(this.colorPicker.getValue());
        polygon.setBorder(this.colorBorder.getValue());

        applyClicked = true;
        //fermer la fenêtre
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }


}
