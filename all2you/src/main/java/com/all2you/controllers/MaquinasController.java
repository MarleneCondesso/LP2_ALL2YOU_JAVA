/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.Maquina;
import com.all2you.models.Operador;
import com.all2you.services.MaquinasService;
import com.all2you.utils.Estados;
import static com.all2you.utils.Estados.estadoAtivoInativo;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Marlene Lima
 */
public class MaquinasController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    public TableView<Maquina> tvMaquina;
    @FXML
    private TableColumn<Maquina, String> colId;
    @FXML
    public TableColumn<Maquina, String> colIdTypeOp;
    @FXML
    public TableColumn<Maquina, String> colStatus;

    @FXML
    private Button btnEstado;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInativar;
    @FXML
    private Scene scene;
    @FXML
    public Stage stage;
    @FXML
    private TextField txtFiltro;

    ObservableList obsList;

    private MaquinasService service = new MaquinasService();

    MaquinaController maquinaController;

    /**
     * Método de ação do botão Adicionar Máquina
     *
     * @param e
     * @throws IOException
     */
    @FXML
    void adicionarMaquina(ActionEvent e) throws IOException {

        showModalView("maquina", null, "Adicionar Máquina", null);
    }

    /**
     * Método de ação do botão Eliminar Máquina
     *
     * @param e
     */
    @FXML
    void eliminarMaquina(ActionEvent e) {
        Maquina maq = tvMaquina.getSelectionModel().getSelectedItem();
        if (maq != null) {

            if (maq.getStatus() == true) {

                AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar a Máquina. \n\n\n MÁQUINA EM FUNCIONAMENTO.");

            } else {

                AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Máquina?");

                if (alerta.getButton().get() == ButtonType.OK) {
                    Boolean response = service.deleteMachine(maq);

                    if (response) {
                        AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Máquina eliminada com SUCESSO!");

                        refreshTable();

                    } else {

                        AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar a Máquina.");
                    }

                }
                if (alerta.getButton().get() == ButtonType.NO) {
                    alerta.getAlert().close();
                }
            }
        } else {

            AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "Máquina em falta", "Por favor, selecione uma Máquina.");
        }
    }

    /**
     * Método para botão Inativar/Ativar Máquina
     */
    @FXML
    void alterarEstado() {

        Maquina maq = tvMaquina.getSelectionModel().getSelectedItem();

        if (maq != null) {

            AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Inativar", "Tem a certeza que pretende inativar esta Máquina?");

            if (alerta.getButton().get() == ButtonType.OK) {

                maq.setStatus(!maq.getStatus());
                Boolean response = service.updateMachine(maq);

                if (response) {

                    int i = tvMaquina.getSelectionModel().getSelectedIndex();
//                        tvMaquina.getItems().set(i, maq);
                    refreshTable();

                    AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Estado de Máquina alterado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível alterar o estado Máquina.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Máquina em falta", "Por favor, selecione uma Máquina.");
        }

    }

    /**
     * Método de Inicialização (interface Initializable).
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();

        FilteredList<Maquina> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(maq -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (maq.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (maq.getOperationType().getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (estadoAtivoInativo(maq.getStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<Maquina> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tvMaquina.comparatorProperty());
        tvMaquina.setItems(sortedData);

    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        obsList = FXCollections.observableArrayList(service.getAllMachines());

        configColumns();
        tvMaquina.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Máquina.
     */
    private void configColumns() {
        tvMaquina.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdTypeOp.setCellValueFactory(tp -> new SimpleObjectProperty(tp.getValue().getOperationType().getId()));
        colStatus.setCellValueFactory(mq -> new SimpleObjectProperty(Estados.estadoAtivoInativo(mq.getValue().getStatus())));

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, Maquina mq, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        maquinaController = fxmlLoader.getController();
        maquinaController.setMaquinaView(mq, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
