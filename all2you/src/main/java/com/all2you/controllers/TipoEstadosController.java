package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.encomendas.TipoEstado;
import com.all2you.services.EncomendasService;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * @author ruteg
 */
public class TipoEstadosController implements Initializable {

    @FXML
    public TableView<TipoEstado> tblTiposEstados;
    @FXML
    private TableColumn<TipoEstado, Integer> colId;
    @FXML
    private TableColumn<TipoEstado, String> colDescricao;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInativar;
    @FXML
    private Pane pane;
    @FXML
    private Scene scene;
    @FXML
    public Stage stage;
    @FXML
    private TextField txtFiltro;

    ObservableList obsList;

    private TipoEstadoController tipoEstadoController;
    private EncomendasService service = new EncomendasService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableFromDB();

        FilteredList<TipoEstado> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tpEst -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(tpEst.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (tpEst.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<TipoEstado> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblTiposEstados.comparatorProperty());
        tblTiposEstados.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Tipo de Estado
     */
    @FXML
    private void adicionarTipoEstado(ActionEvent e) throws IOException {

        showModalView("tipoEstado", null, "Adicionar Tipo de Estado", null);

    }

    /**
     * Método para abrir janela de edição de Tipo de Estado
     */
    @FXML
    private void editarTipoEstado(ActionEvent e) throws IOException {

        TipoEstado tpEst = tblTiposEstados.getSelectionModel().getSelectedItem();
        Integer pos = tblTiposEstados.getSelectionModel().getSelectedIndex();

        if (!isNull(tpEst)) {

            showModalView("tipoEstado", tpEst, "Editar Tipo de Estado", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Estado em falta", "Por favor, selecione um Tipo de Estado");
        }

    }

    /**
     * Método para botão Eliminar Tipo de Estado
     */
    @FXML
    private void eliminarTipoEstado() {
        TipoEstado tpEst = tblTiposEstados.getSelectionModel().getSelectedItem();

        if (tpEst != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Tipo de Estado?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteStateType(tpEst);

                if (response) {

//                    tblTiposEstados.getItems().remove(tpEst);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Estado eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Tipo de Estado.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Estado em falta", "Por favor, selecione um Tipo de Estado");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param tpEst
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, TipoEstado tpEst, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        tipoEstadoController = fxmlLoader.getController();
        tipoEstadoController.setStateTypeView(tpEst, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTableFromDB() {
        obsList = FXCollections.observableArrayList(service.getAllStatesTypes());

        configColumns();
        tblTiposEstados.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Tipos de
     * Estados
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

}
