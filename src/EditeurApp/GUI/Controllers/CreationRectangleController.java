package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.RectangleShape;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreationRectangleController {
    // Ce contrôleur est associé au fichier CreationRectangle.fxml

    private Stage dialogStage; // le stage de la fenêtre
    private RectangleShape rectangle; // le rectangle noyau qui contient les informations
    private boolean applyClicked = false;
    static private int nombre = 0; //nombre de rectangles crées, utilisé pour nommer les rectangles sans nom


    @FXML private TextField height;
    @FXML private TextField width;
    @FXML private TextField cenX;
    @FXML private TextField cenY;
    @FXML private TextField name;
    @FXML private ColorPicker colorPicker;
    @FXML private ColorPicker colorBorder;

        public void setRectangle(RectangleShape rectangle){
            this.rectangle = rectangle;
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }

        public boolean isApplyClicked() {
            return applyClicked;
        }

        //donner un nom au rectangle
        public void setName(){
            String New = "R" + String.valueOf(nombre);
            rectangle.setName(New);
            nombre++;
        }

        @FXML private void handleApply(){
            //ajouter les informations entrées au rectangle noyau
            rectangle.setHeight(Double.parseDouble(this.height.getText()));
            rectangle.setWidth(Double.parseDouble(this.width.getText()));
            rectangle.setCenterX(Double.parseDouble(this.cenX.getText()));
            rectangle.setCenterY(Double.parseDouble(this.cenY.getText()));
            if(this.name.getText().isEmpty())
            setName(); //si l'utilisateur ne saisit pas un nom on donne un nombre au polygone.
            else
                rectangle.setName(this.name.getText());
            rectangle.setColor(this.colorPicker.getValue());
            rectangle.setBorder(this.colorBorder.getValue());

            applyClicked = true;
            //fermer la fenêtre
            dialogStage.close();
        }

        @FXML private void handleCancel(){
            dialogStage.close();
        }
}
