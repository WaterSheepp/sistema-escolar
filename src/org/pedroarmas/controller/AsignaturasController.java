package org.pedroarmas.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pedroarmas.bean.Asignaturas;
import org.pedroarmas.bean.Grado;
import org.pedroarmas.db.Conexion;
import org.pedroarmas.report.GenerarReporte;
import org.pedroarmas.sistema.Principal;

/**
 *
 * @author programacion
 */
public class AsignaturasController implements Initializable{
    private Principal escenarioPrincipal;
    public enum operaciones{Nuevo, Guardar, Modificar, Editar, Eliminar, Ninguno, Actualizar}
    private operaciones tipoDeOperacion = operaciones.Ninguno;
    private ObservableList<Asignaturas> listaAsignaturas;
    private ObservableList<Grado> listaGrado;
    
    @FXML private ComboBox cmbidAsignatura;
    @FXML private ComboBox cmbIdGrado;
    @FXML private TextField txtNombreAsignatura;
    @FXML private TableView tlbAsignaturas;
    @FXML private TableColumn clmIdCodigo;
    @FXML private TableColumn clmNoAsignatura;
    @FXML private TableColumn clmIdGrado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public ObservableList<Asignaturas> getAsignaturas(){
        ArrayList<Asignaturas> lista = new ArrayList<Asignaturas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_StackAsignaturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Asignaturas(
                        resultado.getInt("codigoAsignatura"),
                        resultado.getString("Asignatura"),
                        resultado.getInt("codigoGrado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAsignaturas = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Grado> getGrado(){
        ArrayList<Grado> lista = new ArrayList<Grado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_StackGrados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Grado(
                        resultado.getInt("codigoGrado"),
                        resultado.getString("grado"),
                        resultado.getString("seccion"),
                        resultado.getString("modalidad"),
                        resultado.getString("jornada") ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaGrado = FXCollections.observableArrayList(lista);
    }
    
    
    public Asignaturas searchAsignatura(int codigoAsignatura){
        Asignaturas resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_SearchAsignatura(?)}");
            procedimiento.setInt(1, codigoAsignatura);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Asignaturas (
                            registro.getInt("codigoAsignatura"),
                            registro.getString("Asignatura"),
                              registro.getInt("codigoGrado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Grado searchGrado(int codigoGrado){
        Grado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_SearchGrado(?)}");
            procedimiento.setInt(1, codigoGrado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Grado(
                        registro.getInt("codigoGrado"),
                        registro.getString("grado"),
                        registro.getString("seccion"),
                        registro.getString("modalidad"),
                        registro.getString("jornada")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void cargarDatos(){
       tlbAsignaturas.setItems(getAsignaturas());
       clmIdCodigo.setCellValueFactory(new PropertyValueFactory<Asignaturas, Integer>("codigoAsignatura"));
       clmNoAsignatura.setCellValueFactory(new PropertyValueFactory<Asignaturas, String>("Asignatura"));
       clmIdGrado.setCellValueFactory(new PropertyValueFactory<Grado, Integer>("codigoGrado"));
    }
    
    public void seleccionarDatos(){
        cmbidAsignatura.getSelectionModel().select(searchAsignatura (((Asignaturas) tlbAsignaturas.getSelectionModel().getSelectedItem()).getCodigoAsignatura()));
        txtNombreAsignatura.setText(( (Asignaturas) tlbAsignaturas.getSelectionModel().getSelectedItem()).getAsignatura());
        cmbIdGrado.getSelectionModel().select(searchAsignatura (((Asignaturas) tlbAsignaturas.getSelectionModel().getSelectedItem()).getCodigoGrado()));
    }
    
    
    public void addAsignaturas(){
        Asignaturas registro = new Asignaturas();
        registro.setAsignatura(txtNombreAsignatura.getText());
        registro.setCodigoGrado(Integer.parseInt(cmbIdGrado.getSelectionModel().getSelectedItem().toString()));
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AddAsignatura(?,?)}");
            procedimiento.setString(1, registro.getAsignatura());
            procedimiento.setInt(2, registro.getCodigoGrado());
            procedimiento.execute();
            listaAsignaturas.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateAsignatura(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_UpdateAsignatura(?,?,?)}");
            Asignaturas registro = (Asignaturas) tlbAsignaturas.getSelectionModel().getSelectedItem();
            registro.setCodigoAsignatura(Integer.parseInt(cmbidAsignatura.getSelectionModel().getSelectedItem().toString()));
            registro.setAsignatura(txtNombreAsignatura.getText());
            registro.setCodigoGrado(Integer.parseInt(cmbIdGrado.getSelectionModel().getSelectedItem().toString()));
                        
            procedimiento.setInt(1, registro.getCodigoAsignatura());
            procedimiento.setString(2, registro.getAsignatura());
            procedimiento.setInt(3, registro.getCodigoGrado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void printReport(){
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea realizar un reporte general de la tabla Asignaturas?", "Reporte de Asignaturas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(respuesta == JOptionPane.YES_OPTION){
                Map parametro = new HashMap();  
                GenerarReporte.mostrarReporte("ReporteAsinatura.jasper", "Reporte de Asignaturas", parametro);
        }
    }
    
     public void enableControls(){
        txtNombreAsignatura.setEditable(false);
    }
    
    public void disableControls(){
        txtNombreAsignatura.setEditable(true);
    }
    
    public void cleanControls(){
        txtNombreAsignatura.setText("");
    } 
    
    public void addAreasCase(){
        switch(tipoDeOperacion){
            case Ninguno:
                cmbIdGrado.setDisable(false);
                disableControls();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                tipoDeOperacion = operaciones.Guardar;
             break;
             
            case Guardar:
                addAsignaturas();
                enableControls();
                cleanControls();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperacion = operaciones.Ninguno;
                cargarDatos();
            break;
        }
    }
        
    public void editAsignatura(){
        switch(tipoDeOperacion){    
            case Ninguno:
                if(tlbAsignaturas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea modificar este resgistro de la base de datos?", "Modificar Asignaturas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        cmbidAsignatura.setDisable(false);
                        cmbIdGrado.setDisable(false);
                        disableControls();
                        btnEditar.setText("Modificar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        tipoDeOperacion = operaciones.Modificar;
                        }
                }else{
             JOptionPane.showMessageDialog(null, "Debe seleccionar antes un registro");
                }
            break;
            
            case Modificar:
                enableControls();
                updateAsignatura();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.Ninguno;
                cleanControls();
                cargarDatos();
            break;
        }
    }
    
    public void removeAsigatura(){
        switch(tipoDeOperacion){
            case Guardar:
                disableControls();
                cleanControls();
                btnNuevo.setText("Nuevo");
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperacion = operaciones.Nuevo;
            break;
            
            default:
                if(tlbAsignaturas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar este resgistro de la base de datos?", "Eliminar Asignaturas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_DeleteAsignatura(?)}");
                        procedimiento.setInt(1, ((Asignaturas) tlbAsignaturas.getSelectionModel().getSelectedItem()).getCodigoAsignatura());
                        procedimiento.execute();
                        listaAsignaturas.remove(tlbAsignaturas.getSelectionModel().getSelectedIndex());
                        cleanControls();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                 }
             }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar antes un registro");
             }
               cargarDatos(); 
            break;
        }
    }
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case Ninguno:
                printReport();
                cleanControls();                
                break;
            case Actualizar:
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.Ninguno;
                break;
        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbidAsignatura.setItems(getAsignaturas());
        cmbIdGrado.setItems(getGrado());
        
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
