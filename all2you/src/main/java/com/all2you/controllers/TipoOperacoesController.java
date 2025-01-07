package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.operacoes.TipoOperacao;
import com.all2you.services.OperacoesService;
import static com.all2you.utils.Estados.estadoAtivoInativo;
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
 * @author Marlene
 */
public class TipoOperacoesController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    public TableView<TipoOperacao> tvTipoOperacao;

    @FXML
    private TableColumn<TipoOperacao, String> colId;

    @FXML
    private TableColumn<TipoOperacao, String> colDescription;

    @FXML
    public Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Scene scene;
    @FXML
    public Stage stage;
    @FXML
    private TextField txtFiltro;

    ObservableList obsList;

    private OperacoesService service = new OperacoesService();
    TipoOperacaoController tpOperacaoController;

    /**
     * Método de ação do botão Adicionar Tipo de Operação
     *
     * @param e
     * @throws IOException
     */
    @FXML
    void adicionarTipoOperacao(ActionEvent e) throws IOException {

        showModalView("tipoOperacao", null, "Adicionar Tipo Operação", null);

    }

    /**
     * Método de ação do botão Editar Tipo de Operação
     *
     * @param e
     * @throws IOException
     */
    @FXML
    void editarTipoOperacao(ActionEvent e) throws IOException {

        TipoOperacao tpOp = tvTipoOperacao.getSelectionModel().getSelectedItem();
        Integer pos = tvTipoOperacao.getSelectionModel().getSelectedIndex();

        if (!isNull(tpOp)) {

            showModalView("tipoOperacao", tpOp, "Editar Tipo Operação", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Tipo de Operação em falta", "Por favor, selecione um Tipo de Operação.");
        }
    }

    /**
     * Método de ação do botão Eliminar Tipo de Operação
     */
    @FXML
    void eliminarTipoOperacao() {
        TipoOperacao tpOp = tvTipoOperacao.getSelectionModel().getSelectedItem();

        if (tpOp != null) {
            AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Tipo de Operação?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteOperationType(tpOp);

                if (response) {
                    AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Tipo de Operação eliminada com SUCESSO!");

                    refreshTable();
                } else {
                    AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar o Tipo de Operação.");
                }
            }
        } else {
            AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "Tipo de Operação em falta.", "Por favor, selecione um Tipo de Operação.");
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
        
        FilteredList<TipoOperacao> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tpOp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                
                if (tpOp.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tpOp.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<TipoOperacao> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tvTipoOperacao.comparatorProperty());
        tvTipoOperacao.setItems(sortedData);
        
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        obsList = FXCollections.observableArrayList(service.getAllOperationTypes());

        configColumns();
        tvTipoOperacao.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Tipo
     * Operações
     */
    public void configColumns() {
        tvTipoOperacao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

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
    private void showModalView(String fxml, TipoOperacao tpOp, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        tpOperacaoController = fxmlLoader.getController();
        tpOperacaoController.setTipoOperacaoView(tpOp, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
