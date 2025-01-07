package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.artigos.TipoUnidade;
import com.all2you.services.ArtigosService;
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
public class TipoUnidadesController implements Initializable {

    @FXML
    public TableView<TipoUnidade> tblTiposUnidades;
    @FXML
    private TableColumn<TipoUnidade, String> colUnidade;
    @FXML
    private TableColumn<TipoUnidade, String> colDescricao;
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

    private TipoUnidadeController tipoUnidadeController;
    private ArtigosService service = new ArtigosService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableFromDB();

        FilteredList<TipoUnidade> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tpUn -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (tpUn.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tpUn.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<TipoUnidade> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblTiposUnidades.comparatorProperty());
        tblTiposUnidades.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Tipo de Unidade
     */
    @FXML
    private void adicionarTipoUnidade(ActionEvent e) throws IOException {

        showModalView("tipoUnidade", null, "Adicionar Tipo de Unidade", null);

    }

    /**
     * Método para abrir janela de edição de Tipo de Unidade
     */
    @FXML
    private void editarTipoUnidade(ActionEvent e) throws IOException {

        TipoUnidade tpUn = tblTiposUnidades.getSelectionModel().getSelectedItem();
        Integer pos = tblTiposUnidades.getSelectionModel().getSelectedIndex();

        if (!isNull(tpUn)) {

            showModalView("tipoUnidade", tpUn, "Editar Tipo de Unidade", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Unidade em falta", "Por favor, selecione um Tipo de Unidade");
        }

    }

    /**
     * Método para botão Eliminar Tipo de Unidade
     */
    @FXML
    private void eliminarTipoUnidade() {
        TipoUnidade tpUn = tblTiposUnidades.getSelectionModel().getSelectedItem();

        if (tpUn != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Tipo de Unidade?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteUnitType(tpUn);

                if (response) {

//                    tblTiposUnidades.getItems().remove(tpUn);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Unidade eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Tipo de Unidade.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Unidade em falta", "Por favor, selecione um Tipo de Unidade");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param tpUn
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, TipoUnidade tpUn, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        tipoUnidadeController = fxmlLoader.getController();
        tipoUnidadeController.setUnitTypeView(tpUn, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllUnitTypes());

        configColumns();
        tblTiposUnidades.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Tipos de
     * Unidade
     */
    private void configColumns() {
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

}
