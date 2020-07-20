package org.pedroarmas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.pedroarmas.sistema.Principal;
/**
 *
 * @author programacion
 */
public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaAsignaturas(){
        escenarioPrincipal.ventanaAsignaturas();
    }
    
    public void ventanaGrados(){
        escenarioPrincipal.ventanaGrados();
    }
}
