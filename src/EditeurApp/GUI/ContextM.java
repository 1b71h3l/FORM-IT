package EditeurApp.GUI;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


//Cette classe est utilisée pour le menu déroulant
public class ContextM extends ContextMenu {

    private MenuItem Tourner = new MenuItem("Tourner");
    private MenuItem Deplacer = new MenuItem("Deplacer");
    private MenuItem Modifier = new MenuItem("Modifier");
    private MenuItem Supprimer = new MenuItem("Supprimer");
    private MenuItem Copier = new MenuItem("Copier");
    private MenuItem Coller = new MenuItem("Coller");

    //contructeur
    public ContextM()
    {
        //initialiser à nil
        Tourner.setDisable(true);
        Deplacer.setDisable(true);
        Modifier.setDisable(true);
        Supprimer.setDisable(true);
        Copier.setDisable(true);
        Coller.setDisable(true);
        //add items to context menu
        getItems().addAll(Tourner, Deplacer, Modifier,Supprimer, Copier, Coller);
    }

    //get items
    public MenuItem getDeplacer()
    {
        return Deplacer;
    }
    public MenuItem getTourner()
    {
        return Tourner;
    }
    public MenuItem getModifier()
    {
        return Modifier;
    }
    public MenuItem getSupprimer()
    {
        return Supprimer;
    }
    public MenuItem getCopier()
    {
        return Copier;
    }
    public MenuItem getColler()
    {
        return Coller;
    }

    //set items

    public void setDeplacer(MenuItem menuItem)
    {
        Deplacer = menuItem;
    }
    public void setTourner(MenuItem menuItem)
    {
        Tourner = menuItem;
    }
    public void setCopier(MenuItem menuItem)
    {
        Copier = menuItem;
    }
    public void setColler(MenuItem menuItem)
    {
        Coller = menuItem;
    }
    public void setModifier(MenuItem menuItem)
    {
        Modifier = menuItem;
    }
    public void setSupprimer(MenuItem menuItem)
    {
        Supprimer = menuItem;
    }

    public void disableDeplacer(boolean value) { Deplacer.setDisable(value); }
    public void disableTourner(boolean value)
    {
        Tourner.setDisable(value);
    }
    public void disableCopier(boolean value) { Copier.setDisable(value); }
    public void disableColler(boolean value)
    {
        Coller.setDisable(value);
    }
    public void disableModifier(boolean value)
    {
        Modifier.setDisable(value);
    }
    public void disableSupprimer(boolean value)
    {
        Supprimer.setDisable(value);
    }

    //activer les éléments de ce menu
    public void setEnable()
    {
        Deplacer.setDisable(false);
        Tourner.setDisable(false);
        Modifier.setDisable(false);
        Supprimer.setDisable(false);
        Copier.setDisable(false);
        Coller.setDisable(false);
    }

    //désactiver les éléments de ce menu
    public void setDisable()
    {
        Deplacer.setDisable(true);
        Tourner.setDisable(true);
        Modifier.setDisable(true);
        Supprimer.setDisable(true);
        Copier.setDisable(true);
        Coller.setDisable(true);
    }
}
