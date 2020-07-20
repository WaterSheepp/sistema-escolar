package org.pedroarmas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.pedroarmas.sistema.Principal;

/**
 *
 * @author programacion
 */
public class GradosController implements Initializable{
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
