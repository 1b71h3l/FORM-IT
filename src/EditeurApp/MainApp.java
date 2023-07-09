package EditeurApp;

import EditeurApp.GUI.*;
import EditeurApp.GUI.Controllers.*;
import EditeurApp.Kernel.*;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;
    private AnchorPane rootLayout;
    public static Pane drawingPane;
    private static ContextM contextMenu = new ContextM(); // le menu déroulant

    public static ContextM getContextMenu() {
        return contextMenu;
    }

    private static GuiShape firstSelected = null;      // le premier séléctionné
    private static GuiShape secondSelected = null;     // le deuxieme séléctionné

    private static GuiRectangle selected_rectangle = null;     // le rectangle séléctionée
    private static GuiPolygon selected_polygon = null;         // le polygon séléctionée
    private static GuiComplexPolygon selected_complex = null;  // le polygon complex séléctionné

    private static PolygonRegulier copyPoly = null;      // le polygon copié
    private static RectangleShape copyRect = null;       // le rectangle copié
    private static GuiComplexPolygon copyCmplx = null;   // le polygon complex copié

    private static double pasteX, pasteY;  // les coordonnées de collage

    private Doodle doodle;                // dessin
    public static Doodle selectedDoodle;  // le dessin sléctionné
    public static boolean penEnabled;     // un booléen qui indique si le crayon est activé


       // setter des formes selectionnées
    public static void setSelectedShape(GuiShape shape) {

        if (firstSelected == null && secondSelected == null) {
            firstSelected = shape;
        }
        if (firstSelected != null && secondSelected == null && firstSelected != shape) {
            secondSelected = shape;
        }
        if (firstSelected != null && secondSelected != null && firstSelected != shape && secondSelected != shape) {
            firstSelected.deselect();
            firstSelected = secondSelected;
            secondSelected = shape;
        }
    }

      // prépare la fenetre principale de l'application
    protected void initRootLayout(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/RootLayout.fxml"));
            rootLayout = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            penEnabler(controller);

            this.drawingPane.setOnMouseClicked(mouseEvent -> {
                if (firstSelected != null) {
                    firstSelected.deselect();
                    firstSelected = null;
                }
                if (secondSelected != null) {
                    secondSelected.deselect();
                    secondSelected = null;
                }
                if (selectedDoodle != null) {
                    selectedDoodle.deselect();
                    selectedDoodle = null;
                }
                selected_rectangle = null;
                selected_polygon = null;
                selected_complex = null;
            });

            drawingPane.setOnContextMenuRequested(e -> {
                setMenuContext(drawingPane);
            });

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // setter et getter de l'arriere plan
    public void setDrawingPane(Pane drawingPane) {
        this.drawingPane = drawingPane;
    }
    public Pane getDrawingPane() {
        return this.drawingPane;
    }

    // affiche le dialogue de cration d'un rectangle
    public boolean showCreateRectangleDialog(RectangleShape rectangle) {
        // Load the fxml file and create a new stage for the popup dialog.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/CreateRectangleDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Créer un rectangle");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // instantiating the controller to make it start its work
            CreationRectangleController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRectangle(rectangle);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

     // dessiner un rectangle
    public void drawRectangle(RectangleShape rectangle) {
        // creating a drawable rectangle and setting its color properties
        GuiRectangle guiRectangle = new GuiRectangle(rectangle);
        guiRectangle.setFill(rectangle.getColor());
        guiRectangle.setStrokeType(StrokeType.OUTSIDE);
        guiRectangle.setStrokeWidth(1);
        guiRectangle.setStroke(rectangle.getBorder());

        this.drawingPane.getChildren().add(guiRectangle);
    }

    // affiche le dialogue de création d'un polygon
    public boolean showCreatePolygonDialog(PolygonRegulier polygon) {
        // Load the fxml file and create a new stage for the popup dialog.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/CreatePolygonDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Créer un polygone");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            // creating the scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CreationPolygonController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPolygon(polygon);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

      // dessiner un polygon
    public void drawPolygon(PolygonRegulier polygon) {

        //Drawing the polygone from scrath
        GuiPolygon guiPolygon = new GuiPolygon(polygon);

        guiPolygon.setFill(polygon.getColor());
        guiPolygon.setStrokeType(StrokeType.OUTSIDE);
        guiPolygon.setStrokeWidth(1);
        guiPolygon.setStroke(polygon.getBorder());

        this.drawingPane.getChildren().add(guiPolygon);
    }

      // dessiner un polygon complex
    public void drawComplex(GuiComplexPolygon guiComplex) {
        this.drawingPane.getChildren().add(guiComplex);

        //this is needed to display the info
        //may add the number of segments later //is it necessary?
        PolygonComplex c = new PolygonComplex();
        c.setBorder((Color) guiComplex.getStroke());
        c.setColor((Color) guiComplex.getFill());
        guiComplex.setComplexinfo(c);

    }

       // un setter de sélection
    public static void selectedSetter() {
        if (firstSelected == null) {
            selected_polygon = null;
            selected_rectangle = null;
            selected_complex = null;
        } else if (secondSelected == null) {
            if (firstSelected instanceof GuiPolygon) {
                selected_polygon = (GuiPolygon) firstSelected;
                selected_rectangle = null;
                selected_complex = null;
            } else if (firstSelected instanceof GuiRectangle) {
                selected_rectangle = (GuiRectangle) firstSelected;
                selected_polygon = null;
                selected_complex = null;
            } else {
                selected_complex = (GuiComplexPolygon) firstSelected;
                selected_polygon = null;
                selected_rectangle = null;
            }
        } else {
            if (secondSelected instanceof GuiPolygon) {
                selected_polygon = (GuiPolygon) secondSelected;
                selected_rectangle = null;
                selected_complex = null;
            } else if (secondSelected instanceof GuiRectangle) {
                selected_rectangle = (GuiRectangle) secondSelected;
                selected_polygon = null;
                selected_complex = null;
            } else {
                selected_complex = (GuiComplexPolygon) secondSelected;
                selected_polygon = null;
                selected_rectangle = null;
            }
        }
    }


    // affiche le dialogue de déplacement
    public boolean showDeplacementDialogue() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/Deplacement.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            this.selectedSetter();

            if (selected_polygon != null) {
                dialogStage.setTitle("Deplacer " + selected_polygon.getPolygonInfo().getName());
            } else if (selected_rectangle != null) {
                dialogStage.setTitle("Deplacer " + selected_rectangle.getRectangleInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DeplacementController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_polygon != null) {
                controller.setPolygone(selected_polygon.getPolygonInfo());
                controller.setRectangle(null);
            }
            //sinon si le rectangle est sélectionné
            else if (selected_rectangle != null) {
                controller.setPolygone(null);
                controller.setRectangle(selected_rectangle.getRectangleInfo());
            }

            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // affiche le dialogue de rotation
    public boolean showRotationDialogue() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/Rotation.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            this.selectedSetter();

            if (selected_polygon != null) {
                dialogStage.setTitle("Tourner " + selected_polygon.getPolygonInfo().getName());
            } else if (selected_rectangle != null) {
                dialogStage.setTitle("Tourner " + selected_rectangle.getRectangleInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RotationController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            if (selected_polygon != null) {
                controller.setPolygone(selected_polygon.getPolygonInfo());
                controller.setRectangle(null);
            }
            //sinon si le rectangle est sélectionné
            else if (selected_rectangle != null) {
                controller.setPolygone(null);
                controller.setRectangle(selected_rectangle.getRectangleInfo());
            }

            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // affiche le dialogue de modification d'un polygon
    public boolean showModificationDialogue() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/ModificationPol.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            this.selectedSetter();

            if (selected_polygon != null) {
                dialogStage.setTitle("Modifier " + selected_polygon.getPolygonInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ModifPolController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_polygon != null) {
                controller.setPolygone(selected_polygon.getPolygonInfo());
                controller.ouvrir();
            }
            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

      // affiche le dialogue de modification d'un rectangle
    public boolean showModifRecDialogue() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/ModificationRec.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            this.selectedSetter();

            if (selected_rectangle != null) {
                dialogStage.setTitle("Modifier " + selected_rectangle.getRectangleInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ModifRecController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_rectangle != null) {
                controller.setRectangle(selected_rectangle.getRectangleInfo());
                controller.ouvrir();
            }
            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

      // setter du menu déroulant
    public void setMenuContext(Pane pane) {
        //show context menu when right click on drawing pane
        EventHandler<MouseEvent> clickedR = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(pane, Side.LEFT, mouseEvent.getX(), mouseEvent.getY());
                    pasteX = mouseEvent.getX();
                    pasteY = mouseEvent.getY();
                } else {
                    contextMenu.hide();
                }
            }
        };
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, clickedR);
        contextMenu.setDisable();

        //show updated contetxmenu when right click on polygon or rectangle
        contextPolygon();
        contextRectangle();
        contextComplex();
        contextPane();
    }

    // traite le menu déroulant issu d'un clique droit sur un polygon
    public void contextPolygon() {
        // setting up the selected_polygon & selected_rectangle variables
        this.selectedSetter();

        if (selected_polygon != null) {
            //sets the contextMenu items to selected polygon
            contextMenu.disableDeplacer(false);
            contextMenu.getDeplacer().setOnAction(e -> {
                showDeplacementDialogue();
                selected_polygon.getPolygonInfo().deplacer();
                selected_polygon.getPoints().clear();
                selected_polygon.getPoints().addAll(selected_polygon.getPolygonInfo().getAngles());
            });

            contextMenu.disableTourner(false);
            contextMenu.getTourner().setOnAction(e -> {
                showRotationDialogue();
                selected_polygon.getPolygonInfo().tourner();
                selected_polygon.getPoints().clear();
                selected_polygon.getPoints().addAll(selected_polygon.getPolygonInfo().getAngles());
            });

            contextMenu.disableModifier(false);
            contextMenu.getModifier().setOnAction(e -> {
                ModifPolController MP = new ModifPolController();
                showModificationDialogue();
                drawPolygon(selected_polygon.getPolygonInfo());
                this.drawingPane.getChildren().remove(selected_polygon);
            });

            contextMenu.disableSupprimer(false);
            contextMenu.getSupprimer().setOnAction(e -> {
                this.drawingPane.getChildren().remove(selected_polygon);
                selected_polygon = null;
            });

            contextMenu.disableCopier(false);
            contextMenu.getCopier().setOnAction(e -> {
                if (copyRect != null) copyRect = null;
                if (copyCmplx != null) copyCmplx = null;
                copyPoly = new PolygonRegulier(selected_polygon.getPolygonInfo().getCenterX(), selected_polygon.getPolygonInfo().getCenterY(), selected_polygon.getPolygonInfo().getAngles(), selected_polygon.getPolygonInfo().getRayon(), selected_polygon.getPolygonInfo().getnSeg(), selected_polygon.getPolygonInfo().getName(), selected_polygon.getPolygonInfo().getColor(), selected_polygon.getPolygonInfo().getBorder());
                copyPoly.setColor(selected_polygon.getPolygonInfo().getColor());
                copyPoly.setName(selected_polygon.getPolygonInfo().getName() + " 2");
            });

            if (copyPoly != null || copyRect != null || copyCmplx != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    if (copyPoly != null) {
                        copyPoly.setCenterX(selected_polygon.getPolygonInfo().getCenterX());
                        copyPoly.setCenterY(selected_polygon.getPolygonInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_polygon);
                        selected_polygon = null;
                        drawPolygon(copyPoly);
                    }
                    if (copyRect != null) {
                        copyRect.setCenterX(selected_polygon.getPolygonInfo().getCenterX());
                        copyRect.setCenterY(selected_polygon.getPolygonInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_polygon);
                        selected_polygon = null;
                        drawRectangle(copyRect);
                    }
                    if (copyCmplx != null) {
                        copyCmplx.setLayoutX(selected_polygon.getPolygonInfo().getCenterX());
                        copyCmplx.setLayoutY(selected_polygon.getPolygonInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_polygon);
                        selected_polygon = null;
                        drawComplex(copyCmplx);
                    }
                });
            }
        }
    }

    // traite le menu déroulant issu d'un clique droit sur l'arriere plan
    public void contextPane() {
        this.selectedSetter();
        if (selected_polygon == null && selected_rectangle == null && selected_complex == null) {
            if (copyPoly != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    copyPoly.setCenterX(pasteX);
                    copyPoly.setCenterY(pasteY);
                    drawPolygon(copyPoly);
                });
            }
            if (copyRect != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    copyRect.setCenterX(pasteX);
                    copyRect.setCenterY(pasteY);
                    drawRectangle(copyRect);
                });
            }

            if (copyCmplx != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    copyCmplx.setLayoutX(pasteX);
                    copyCmplx.setLayoutY(pasteY);
                    this.getDrawingPane().getChildren().add(copyCmplx);
                });
            }

        }
    }

      // copier la forme sélectionnée
    public void copier(){
        this.selectedSetter();
        if (selected_polygon != null && selected_rectangle == null && selected_complex == null)
        {
            if (copyRect != null) copyRect = null;
            if (copyCmplx != null) copyCmplx = null;
            copyPoly = new PolygonRegulier(selected_polygon.getPolygonInfo().getCenterX(), selected_polygon.getPolygonInfo().getCenterY(), selected_polygon.getPolygonInfo().getAngles(), selected_polygon.getPolygonInfo().getRayon(), selected_polygon.getPolygonInfo().getnSeg(), selected_polygon.getPolygonInfo().getName(), selected_polygon.getPolygonInfo().getColor(), selected_polygon.getPolygonInfo().getBorder());
            copyPoly.setColor(selected_polygon.getPolygonInfo().getColor());
            copyPoly.setName(selected_polygon.getPolygonInfo().getName() + " 2");
        }
        else if (selected_polygon == null && selected_rectangle != null && selected_complex == null)
            {
                if (copyPoly != null) copyPoly = null;
                if (copyCmplx != null) copyCmplx = null;
                copyRect = new RectangleShape(selected_rectangle.getRectangleInfo().getCenterX(), selected_rectangle.getRectangleInfo().getCenterY(), selected_rectangle.getRectangleInfo().getAngles(), selected_rectangle.getRectangleInfo().getHeight(), selected_rectangle.getRectangleInfo().getWidth(), selected_rectangle.getRectangleInfo().getName(), selected_rectangle.getRectangleInfo().getColor(), selected_rectangle.getRectangleInfo().getBorder());
                copyRect.setName(copyRect.getName() + " 2");
                copyRect.setColor(selected_rectangle.getRectangleInfo().getColor());
                copyRect.setBorder(selected_rectangle.getRectangleInfo().getBorder());
            }
            else if  (selected_polygon == null && selected_rectangle == null && selected_complex != null)
                {
                    if (copyPoly != null) copyPoly = null;

                    if (copyRect != null) copyRect = null;
                    copyCmplx = new GuiComplexPolygon();
                    copyCmplx.getPoints().addAll(selected_complex.getPoints());
                    copyCmplx.setFill(selected_complex.getFill());
                    copyCmplx.setStroke(selected_complex.getStroke());
                }
    }


     // coller la forme copiée
    public void coller(){
        this.selectedSetter();
        if (selected_polygon == null && selected_rectangle == null && selected_complex == null) {
            if (copyPoly != null) {
                    copyPoly.setCenterX(copyPoly.getCenterX()+10);
                    copyPoly.setCenterY(copyPoly.getCenterY()+10);
                    drawPolygon(copyPoly);
            }
            if (copyRect != null) {
                    copyRect.setCenterX(copyRect.getCenterX()+10);
                    copyRect.setCenterY(copyRect.getCenterX()+10);
                    drawRectangle(copyRect);
            }

            if (copyCmplx != null) {
                    copyCmplx.setLayoutX(copyCmplx.getLayoutX()+10);
                    copyCmplx.setLayoutY(copyCmplx.getLayoutY()+10);
                    this.getDrawingPane().getChildren().add(copyCmplx);
            }
        }
    }

    //duplication de la forme séléctionné
    public void dupliquer(){
        this.selectedSetter();
        if (selected_polygon != null && selected_rectangle == null && selected_complex == null)
        {
            if (copyRect != null) copyRect = null;
            if (copyCmplx != null) copyCmplx = null;
            copyPoly = new PolygonRegulier(selected_polygon.getPolygonInfo().getCenterX(), selected_polygon.getPolygonInfo().getCenterY(), selected_polygon.getPolygonInfo().getAngles(), selected_polygon.getPolygonInfo().getRayon(), selected_polygon.getPolygonInfo().getnSeg(), selected_polygon.getPolygonInfo().getName(), selected_polygon.getPolygonInfo().getColor(), selected_polygon.getPolygonInfo().getBorder());
            copyPoly.setColor(selected_polygon.getPolygonInfo().getColor());
            copyPoly.setName(selected_polygon.getPolygonInfo().getName() + " 2");
            copyPoly.setCenterX(copyPoly.getCenterX()+10);
            copyPoly.setCenterY(copyPoly.getCenterY()+10);
            drawPolygon(copyPoly);
        }
        else if (selected_polygon == null && selected_rectangle != null && selected_complex == null)
        {
            if (copyPoly != null) copyPoly = null;
            if (copyCmplx != null) copyCmplx = null;
            copyRect = new RectangleShape(selected_rectangle.getRectangleInfo().getCenterX(), selected_rectangle.getRectangleInfo().getCenterY(), selected_rectangle.getRectangleInfo().getAngles(), selected_rectangle.getRectangleInfo().getHeight(), selected_rectangle.getRectangleInfo().getWidth(), selected_rectangle.getRectangleInfo().getName(), selected_rectangle.getRectangleInfo().getColor(), selected_rectangle.getRectangleInfo().getBorder());
            copyRect.setName(copyRect.getName() + " 2");
            copyRect.setColor(selected_rectangle.getRectangleInfo().getColor());
            copyRect.setBorder(selected_rectangle.getRectangleInfo().getBorder());
            copyRect.setCenterX(copyRect.getCenterX()+10);
            copyRect.setCenterY(copyRect.getCenterX()+10);
            drawRectangle(copyRect);
        }
        else if  (selected_polygon == null && selected_rectangle == null && selected_complex != null)
        {
            if (copyPoly != null) copyPoly = null;

            if (copyRect != null) copyRect = null;
            copyCmplx = new GuiComplexPolygon();
            copyCmplx.getPoints().addAll(selected_complex.getPoints());
            copyCmplx.setFill(selected_complex.getFill());
            copyCmplx.setStroke(selected_complex.getStroke());
            copyCmplx.setLayoutX(copyCmplx.getLayoutX()+10);
            copyCmplx.setLayoutY(copyCmplx.getLayoutY()+10);
            this.getDrawingPane().getChildren().add(copyCmplx);
        }
    }

    // traite le menu déroulant issue d'un clique droit sur un rectangle
    public void contextRectangle() {
        // setting up the selected_polygon & selected_rectangle variables
        this.selectedSetter();

        if (selected_rectangle != null) {
            //sets the contextMenu items to the selected rectangle
            contextMenu.disableDeplacer(false);
            contextMenu.getDeplacer().setOnAction(e -> {
                showDeplacementDialogue();
                selected_rectangle.getRectangleInfo().deplacer();
                selected_rectangle.getPoints().clear();
                selected_rectangle.getPoints().addAll(selected_rectangle.getRectangleInfo().getAngles());
            });

            contextMenu.disableTourner(false);
            contextMenu.getTourner().setOnAction(e -> {
                showRotationDialogue();
                selected_rectangle.getRectangleInfo().tourner();
                selected_rectangle.getPoints().clear();
                selected_rectangle.getPoints().addAll(selected_rectangle.getRectangleInfo().getAngles());
            });
            contextMenu.disableModifier(false);
            contextMenu.getModifier().setOnAction(e -> {
                showModifRecDialogue();
                drawRectangle(selected_rectangle.getRectangleInfo());
                this.drawingPane.getChildren().remove(selected_rectangle);
            });
            contextMenu.disableSupprimer(false);
            contextMenu.getSupprimer().setOnAction(e -> {
                this.drawingPane.getChildren().remove(selected_rectangle);
                selected_rectangle = null;
            });

            contextMenu.disableCopier(false);
            contextMenu.getCopier().setOnAction(e -> {
                if (copyPoly != null) copyPoly = null;
                if (copyCmplx != null) copyCmplx = null;
                copyRect = new RectangleShape(selected_rectangle.getRectangleInfo().getCenterX(), selected_rectangle.getRectangleInfo().getCenterY(), selected_rectangle.getRectangleInfo().getAngles(), selected_rectangle.getRectangleInfo().getHeight(), selected_rectangle.getRectangleInfo().getWidth(), selected_rectangle.getRectangleInfo().getName(), selected_rectangle.getRectangleInfo().getColor(), selected_rectangle.getRectangleInfo().getBorder());
                copyRect.setName(copyRect.getName() + " 2");
                copyRect.setColor(selected_rectangle.getRectangleInfo().getColor());
                copyRect.setBorder(selected_rectangle.getRectangleInfo().getBorder());
            });

            if (copyPoly != null || copyRect != null || copyCmplx != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    if (copyPoly != null) {
                        copyPoly.setCenterX(selected_rectangle.getRectangleInfo().getCenterX());
                        copyPoly.setCenterY(selected_rectangle.getRectangleInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_rectangle);
                        selected_rectangle = null;
                        drawPolygon(copyPoly);
                    }
                    if (copyRect != null) {
                        copyRect.setCenterX(selected_rectangle.getRectangleInfo().getCenterX());
                        copyRect.setCenterY(selected_rectangle.getRectangleInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_rectangle);
                        selected_rectangle = null;
                        drawRectangle(copyRect);
                    }
                    if (copyCmplx != null) {
                        copyCmplx.setLayoutX(selected_rectangle.getRectangleInfo().getCenterX());
                        copyCmplx.setLayoutY(selected_rectangle.getRectangleInfo().getCenterY());
                        this.drawingPane.getChildren().remove(selected_rectangle);
                        selected_rectangle = null;
                        drawComplex(copyCmplx);
                    }
                });
            }
        }
    }

      // traite le menu déroulant issu d'un clique droit sur un polygon complex
    public void contextComplex() {
        this.selectedSetter();
        if (selected_complex != null) {
            contextMenu.disableSupprimer(false);
            contextMenu.getSupprimer().setOnAction(e -> {
                this.drawingPane.getChildren().remove(selected_complex);
                selected_complex = null;
            });

            contextMenu.disableCopier(false);
            contextMenu.getCopier().setOnAction(e -> {
                if (copyPoly != null) {
                    copyPoly = null;
                }
                if (copyRect != null) {
                    copyRect = null;
                }
                copyCmplx = new GuiComplexPolygon();
                copyCmplx.getPoints().addAll(selected_complex.getPoints());
                copyCmplx.setFill(selected_complex.getFill());
                copyCmplx.setStroke(selected_complex.getStroke());
            });

            if (copyPoly != null || copyRect != null || copyCmplx != null) {
                contextMenu.disableColler(false);
                contextMenu.getColler().setOnAction(e -> {
                    if (copyPoly != null) {
                        copyPoly.setCenterX(selected_complex.getLayoutX());
                        copyPoly.setCenterY(selected_complex.getLayoutY());
                        this.drawingPane.getChildren().remove(selected_complex);
                        selected_complex = null;
                        drawPolygon(copyPoly);
                    }
                    if (copyRect != null) {
                        copyRect.setCenterX(selected_complex.getLayoutX());
                        copyRect.setCenterY(selected_complex.getLayoutY());
                        this.drawingPane.getChildren().remove(selected_complex);
                        selected_complex = null;
                        drawRectangle(copyRect);
                    }
                    if (copyCmplx != null) {
                        copyCmplx.setLayoutX(selected_complex.getLayoutX());
                        copyCmplx.setLayoutY(selected_complex.getLayoutY());
                        this.drawingPane.getChildren().remove(selected_complex);
                        selected_complex = null;
                        drawComplex(copyCmplx);
                    }
                });

            }
        }
    }

    // affiche les messages d'avertissements
    public void showWarning() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/Warning.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            WarningController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     // affiche la fenetre de bienvenue
    public void showWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/WelcomePage.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            WelcomeController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // affiche les informations d'un polygon apres un double clique
    public static void showInformationPolygone() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/InformationPolygone.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            selectedSetter();

            if (selected_rectangle != null) {
                dialogStage.setTitle("Information " + selected_polygon.getPolygonInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            InformationPolygoneController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_polygon != null) {
                controller.setPolygone(selected_polygon.getPolygonInfo());
                controller.setInformation();
            }
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // affiche les informations d'un polygon complex apres un double clique
    public static void showInformationComplex() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/InformationComplex.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            // setting up the selected_polygon & selected_rectangle variables
            selectedSetter();

            if (selected_rectangle != null) {
                dialogStage.setTitle("Information " + selected_complex.getComplexinfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            InformationComplexController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_complex != null) {
                controller.setComplex(selected_complex.getComplexinfo());
                controller.setInformation();
            }
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     // affiche les informations d'un rectangle apres un double clique
    public static void showInformationRectangle() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/InformationRectangle.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            selectedSetter();

            if (selected_rectangle != null) {
                dialogStage.setTitle("Information " + selected_rectangle.getRectangleInfo().getName());
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            InformationRectangleController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            //Si le polygone est sélectionné
            if (selected_rectangle != null) {
                controller.setRectangle(selected_rectangle.getRectangleInfo());
                controller.setInformation();
            }
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // affiche un dialogue de personnalisation de l'arriere plan
    public boolean showPersoDialogue() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/ArrièrePlan.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ArrierePlanController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

     // affiche un message d'erreur qui concerne la sélection
    public void showSelectionWarning() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/SelectionWarning.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            SelectionWarningController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      // affiche un message d'erreur quand on effectue une opération booléenne erronée
    public void showOperationWarning() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/OperationWarning.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            OperationWarningController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     // affiche un message d'erreur quand on sélectionne 2 formes et on effectue l'effet miroir
    public void showOneSelection() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/OneSelection.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            OneSelectionController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      // exception de sélection
    public class SelectionException extends Exception {
    }


     // executer l'opération de l'intersection
    public void executeIntersection() throws SelectionException {
        // instanciating an AND operation
        BooleanOperation AND = new BooleanOperation();

        GuiComplexPolygon result;
        try {
            AND.setP1(firstSelected);
            AND.setP2(secondSelected);
            if ((AND.getP1() == null) || (AND.getP2() == null)) throw new SelectionException();
            try {
                result = AND.intersection();
                result.setFill(Color.RED);
                result.setStroke(firstSelected.getStroke());

                // deleting the operating shapes from the pane
                this.drawingPane.getChildren().remove(firstSelected);
                this.drawingPane.getChildren().remove(secondSelected);
                firstSelected = null;
                secondSelected = null;

                // adding the result to the pane
                drawComplex(result);
            } catch (Exception ex) {
                showOperationWarning();
            }
        } catch (Exception exx) {
            showSelectionWarning();
        }
    }

     // executer l'opération de l'union
    public void executeUnion() throws SelectionException {
        // instanciating an or operation
        BooleanOperation OR = new BooleanOperation();

        GuiComplexPolygon result;
        try {
            OR.setP1(firstSelected);
            OR.setP2(secondSelected);
            if ((OR.getP1() == null) || (OR.getP2() == null)) throw new SelectionException();
            try {
                result = OR.Union();
                result.setFill(firstSelected.getFill());
                result.setStroke(firstSelected.getStroke());
                // deleting the operating shapes from the pane
                this.drawingPane.getChildren().remove(firstSelected);
                this.drawingPane.getChildren().remove(secondSelected);
                firstSelected = null;
                secondSelected = null;
                result.setFill(Color.RED);
                drawComplex(result);
                //when moving the result polygon it goes back to the shape of one of the polygons.
            } catch (Exception ex) {
                showOperationWarning();
            }
        } catch (Exception exx) {
            showSelectionWarning();
        }
    }

      // executer l'opération de soustraction
    public void executeSoustraction() throws SelectionException {
        // instanciating an not operation
        BooleanOperation NOT = new BooleanOperation();
        // applying la soustraction and setting up the result's properties
        GuiComplexPolygon result;
        try {
            if ((firstSelected == null) || (secondSelected == null)) throw new SelectionException();
            try {
                result = NOT.soustraction(firstSelected, secondSelected);
                result.setFill(firstSelected.getFill());
                result.setStroke(firstSelected.getStroke());
                // deleting the operating shapes from the pane
                this.drawingPane.getChildren().remove(firstSelected);
                this.drawingPane.getChildren().remove(secondSelected);
                firstSelected = null;
                secondSelected = null;
                // adding the result to the pane

                drawComplex(result);
            } catch (Exception ex) {
                showOperationWarning();
            }
        } catch (Exception exx) {
            showSelectionWarning();
        }
    }

     // execute l'effet miroir horizontal
    public void excuteMiroirH() throws SelectionException {
        BooleanOperation H = new BooleanOperation();
        GuiComplexPolygon result;

        try {
            result = H.MiroirH(firstSelected);
            if (firstSelected == null) throw new SelectionException();
            result.setFill(firstSelected.getFill());
            result.setStroke(firstSelected.getStroke());
            this.drawingPane.getChildren().remove(firstSelected);
            firstSelected = secondSelected;
            secondSelected = null;
            drawComplex(result);
        } catch (Exception ex) {
            showOneSelection();
        }

    }


     // execute l'effet miroir vertical
    public void excuteMiroirV() throws SelectionException {
        BooleanOperation V = new BooleanOperation();
        GuiComplexPolygon result;
        try {
            result = V.MiroirV(firstSelected);
            if (firstSelected == null) throw new SelectionException();
            result.setFill(firstSelected.getFill());
            result.setStroke(firstSelected.getStroke());
            this.drawingPane.getChildren().remove(firstSelected);
            firstSelected = null;
            secondSelected = null;
            drawComplex(result);
        } catch (Exception ex) {
            showOneSelection();
        }

    }

      // afficher une fenetre pour la saisie des infos du crayon
    public boolean showPopUp(Pen pen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((MainApp.class.getResource("GUI/fxmlFiles/PopUpPen.fxml")));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Customize your pen !");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // instantiating the controller to make it start its work
            PenController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPen(pen);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApplyClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

     // afficher la fenetre d'aide
    public void showAide() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/AidePage.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            AideController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      // afficher la fenetre de "à propos de l'application"
    public void showAbout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GUI/fxmlFiles/About.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            AboutController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     // commencer à dessiner en utilisant le crayon
    public void startDrawing(Pen pen) {
        doodle = new Doodle(pen);

        drawingPane.setOnMouseClicked(mouseHandler);
        drawingPane.setOnMouseDragged(mouseHandler);
        drawingPane.setOnMouseEntered(mouseHandler);
        drawingPane.setOnMouseExited(mouseHandler);
        drawingPane.setOnMouseMoved(mouseHandler);
        drawingPane.setOnMousePressed(mouseHandler);
        drawingPane.setOnMouseReleased(mouseHandler);

        this.drawingPane.getChildren().add(doodle);

    }

    // arreter le processus de dessin
    public void stopDrawing() {
        drawingPane.setOnMouseDragged(doesNothing);
        drawingPane.setOnMouseEntered(doesNothing);
        drawingPane.setOnMouseExited(doesNothing);
        drawingPane.setOnMouseMoved(doesNothing);
        drawingPane.setOnMousePressed(doesNothing);
        drawingPane.setOnMouseReleased(doesNothing);
        drawingPane.setOnMouseClicked(mouseEvent -> {
            if (firstSelected != null) {
                firstSelected.deselect();
                firstSelected = null;
            }
            if (secondSelected != null) {
                secondSelected.deselect();
                secondSelected = null;
            }
            if (selectedDoodle != null) {
                selectedDoodle.deselect();
                selectedDoodle = null;
            }
            selected_rectangle = null;
            selected_polygon = null;
            selected_complex = null;
        });

    }

     // gestionnaires d'évenements pour le crayon
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                doodle.getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                doodle.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
            }
        }

    };
    EventHandler<MouseEvent> doesNothing = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
        }

    };

       // effacer un dessin (la gomme)
    public void executeRubber() {
        if (selectedDoodle != null) {
            this.getDrawingPane().getChildren().remove(selectedDoodle);
            selectedDoodle = null;
        }
    }

       // activer le crayon
    public void penEnabler(RootLayoutController ctrlr) {
        penEnabled = ctrlr.penEnabled();
    }

      // sauvegarder le projet en format d'image
    public void handleSave() {
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Enregistrer en image");
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Image (*png , *jpeg)", "*.png","*jpeg");
        savefile.getExtensionFilters().add(extension);
        File file = savefile.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(1080, 1000);
                drawingPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "PNG", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

      // fermer le projet
    public void handleClose(){
        primaryStage.close();
    }

      // ouvrir un nouveau projet
    public void handleOpen(){
        handleSave();
        primaryStage.close();
        initRootLayout(primaryStage);
        primaryStage.show();
    }

      // redéfinition de la méthode start
    @Override
    public void start(Stage primaryStage) {
        showWelcome(); // afficher la fenetre de bienvenue

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FORM-IT");
        this.primaryStage.show();

        // appel a la methode qui lance l'application et traite ses cpmposantes
        initRootLayout(primaryStage);
    }

     // le main d'exécution
    public static void main(String[] args) {
        launch(args);
    }
}