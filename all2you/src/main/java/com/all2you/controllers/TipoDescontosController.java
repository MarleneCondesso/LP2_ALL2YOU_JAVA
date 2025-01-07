package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.encomendas.TipoDesconto;
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
public class TipoDescontosController implements Initializable {

    @FXML
    public TableView<TipoDesconto> tblTiposDescontos;
    @FXML
    private TableColumn<TipoDesconto, Integer> colId;
    @FXML
    private TableColumn<TipoDesconto, String> colDescricao;
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

    private TipoDescontoController tipoDescontoController;
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

        FilteredList<TipoDesconto> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tpDesc -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(tpDesc.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (tpDesc.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<TipoDesconto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblTiposDescontos.comparatorProperty());
        tblTiposDescontos.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Tipo de Desconto
     */
    @FXML
    private void adicionarTipoDesconto(ActionEvent e) throws IOException {

        showModalView("tipoDesconto", null, "Adicionar Tipo de Desconto", null);

    }

    /**
     * Método para abrir janela de edição de Tipo de Desconto
     */
    @FXML
    private void editarTipoDesconto(ActionEvent e) throws IOException {

        TipoDesconto tpDesc = tblTiposDescontos.getSelectionModel().getSelectedItem();
        Integer pos = tblTiposDescontos.getSelectionModel().getSelectedIndex();

        if (!isNull(tpDesc)) {

            showModalView("tipoDesconto", tpDesc, "Editar Tipo de Desconto", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Desconto em falta", "Por favor, selecione um Tipo de Desconto");
        }

    }

    /**
     * Método para botão Eliminar Tipo de Desconto
     */
    @FXML
    private void eliminarTipoDesconto() {
        TipoDesconto tpDesc = tblTiposDescontos.getSelectionModel().getSelectedItem();

        if (tpDesc != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Tipo de Desconto?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteDiscountType(tpDesc);

                if (response) {

//                    tblTiposDescontos.getItems().remove(tpDesc);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Desconto eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Tipo de Desconto.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Desconto em falta", "Por favor, selecione um Tipo de Desconto");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param tpDesc
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, TipoDesconto tpDesc, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        tipoDescontoController = fxmlLoader.getController();
        tipoDescontoController.setUnitTypeView(tpDesc, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllDiscountTypes());

        configColumns();
        tblTiposDescontos.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Tipos de
     * Desconto
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

}
