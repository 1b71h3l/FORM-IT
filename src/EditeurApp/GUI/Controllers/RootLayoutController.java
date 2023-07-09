package EditeurApp.GUI.Controllers;

import EditeurApp.Kernel.PolygonRegulier;
import EditeurApp.Kernel.RectangleShape;
import EditeurApp.GUI.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import EditeurApp.*;


public class RootLayoutController {
    // Ce contrôleur est associé a la fenetre principale de l'application

    private MainApp mainApp;

    @FXML private Pane drawingPane;

    public RootLayoutController() { }

    @FXML private void initialize() { }

       // setter
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.mainApp.setDrawingPane(this.drawingPane);
    }

       // traite l'appuyage sur le bouton de creation du rectangle
    @FXML private void handleCreateRectangle() {
        RectangleShape rectangle = new RectangleShape();
        boolean applyClicked = mainApp.showCreateRectangleDialog(rectangle);
        if (applyClicked) {
            mainApp.drawRectangle(rectangle);
        }
    }

    // traite l'appuyage sur le bouton de creation du polygon
    @FXML
    private void handleCreatePolygon() {
        PolygonRegulier polygon = new PolygonRegulier();
        boolean applyClicked = mainApp.showCreatePolygonDialog(polygon);
        if (applyClicked) {
            if (polygon.getnSeg() < 3)
            {
                mainApp.showWarning();
            }
            else mainApp.drawPolygon(polygon);
        }
    }

    // traite l'appuyage sur le bouton de crayon
    @FXML
    private void handleDraw() {
        Pen pen = new Pen();
        boolean applyClicked = mainApp.showPopUp(pen);
        if (applyClicked) {
            mainApp.startDrawing(pen);
        }
    }
    @FXML
    ToggleButton penButton;


    // traite l'operation lever/poser le crayon
    @FXML private void handlePen() {
        if (penButton.isSelected()) {
            handleDraw();
            //change the style
        }
        else {
            mainApp.stopDrawing();
            // change the style
        }
    }

       // traite l'appuyage sur le bouton de la gomme
    @FXML private void handleRubber() {
        mainApp.executeRubber();
    }

         // retourne l'etat d'activation du crayon
    public boolean penEnabled() {
        return penButton.isSelected();
    }

       // traite l'appuyage sur le bouton de l'union
    @FXML private void handleUnion()throws MainApp.SelectionException {
            mainApp.executeUnion();
    }


    // traite l'appuyage sur le bouton de l'intersection
    @FXML private void handleIntersection() throws MainApp.SelectionException {
        mainApp.executeIntersection();
    }

    // traite l'appuyage sur le bouton de la soustraction
    @FXML private void handleSoustraction()throws MainApp.SelectionException {
        mainApp.executeSoustraction();
    }

    // traite l'appuyage sur le bouton de l'effet miroir horizontal

    @FXML private void handleHmiroir() throws MainApp.SelectionException {
          mainApp.excuteMiroirH();
    }

    // traite l'appuyage sur le bouton de l'effet miroir vertical
    @FXML private void handleVmiroir() throws MainApp.SelectionException{
          mainApp.excuteMiroirV();
    }

    // traite l'appuyage sur le bouton de "besoin d'aide"
    @FXML private void handleAide()
    {
        mainApp.showAide();
    }

    // traite l'appuyage sur le bouton de " a propos de l'application"
    @FXML private void handleAbout()
    {mainApp.showAbout();}

    // traite l'appuyage sur le bouton de personnalisation de l'arriere plan
    @FXML public void handleperso() {
        boolean applyClicked = mainApp.showPersoDialogue();
    }

    // traite l'appuyage sur le bouton de copiage
    public void COPIER() {
        mainApp.copier();
    }

    // // traite l'appuyage sur le bouton de collage
    public void COLLER() {
        mainApp.coller();
    }

    // traite l'appuyage sur le bouton de duplication
    public void Dupliquer() {
        mainApp.dupliquer();
    }

    // traite l'appuyage sur le bouton de sauvegarde
    public void Save() {
        mainApp.handleSave();
    }

    // traite l'appuyage sur le bouton "fermer"
    public void Close(){
        mainApp.handleClose();
    }

    // traite l'appuyage sur le bouton d'ouverture d'une nouvelle fenetre
    public void OpenNewWindow(){
        mainApp.handleOpen();
    }
}


