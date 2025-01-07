package com.all2you.controllers;

import static java.util.Objects.isNull;
import com.all2you.models.operacoes.Operacao;
import com.all2you.services.OperacoesService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import com.all2you.App;

/**
 *
 * @author ruteg
 */
public class OperacoesController implements Initializable {

    @FXML
    public TableView<Operacao> tblOperacoes;
    @FXML
    private TableColumn<Operacao, Integer> colId;
    @FXML
    private TableColumn<Operacao, String> colTipoOperacao;
    @FXML
    private TableColumn<Operacao, String> colRefProduto;
    @FXML
    private TableColumn<Operacao, String> colVersaoProduto;
    @FXML
    private TableColumn<Operacao, Integer> colOrdem;
    @FXML
    private TableColumn<Operacao, String> colDescricao;
    @FXML
    private TableColumn<Operacao, Integer> colMaoObra;
    @FXML
    private TableColumn<Operacao, Integer> colTempo;
    @FXML
    private TableColumn<Operacao, String> colInstrucoes;
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

    private OperacaoController operacaoController;
    private OperacoesService service = new OperacoesService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableFromDB();

        FilteredList<Operacao> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(opc -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (opc.getOperationType().getId().toLowerCase().contains(lowerCaseFilter) || opc.getOperationType().getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (opc.getProduct().getReference().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (opc.getProduct().getVersion().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(opc.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(opc.getOrder()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(opc.getLabor()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(opc.getTime()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } /*else if (!opc.getTechnicalInstructions().isEmpty() && opc.getTechnicalInstructions().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (!opc.getDescription().isEmpty() && opc.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } */ else {
                    return false;
                }
            });
        }
        );

        SortedList<Operacao> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblOperacoes.comparatorProperty());
        tblOperacoes.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Operação
     */
    @FXML
    private void adicionarOperacao(ActionEvent e) throws IOException {

        showModalView("operacao", null, "Adicionar Operação", null);
    }

    /**
     * Método para abrir janela de edição de Operação
     */
    @FXML
    private void editarOperacao(ActionEvent e) throws IOException {

        Operacao opc = tblOperacoes.getSelectionModel().getSelectedItem();
        Integer pos = tblOperacoes.getSelectionModel().getSelectedIndex();

        if (!isNull(opc)) {

            showModalView("operacao", opc, "Editar Operação", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Operação em falta", "Por favor, selecione uma Operação");
        }

    }

    /**
     * Método para botão Eliminar Operação
     *
     * @param event
     */
    @FXML
    private void eliminarOperacao() {
        Operacao opc = tblOperacoes.getSelectionModel().getSelectedItem();

        if (opc != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Operação?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteOperation(opc);

                if (response) {

//                    tblOperacoes.getItems().remove(opc);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Operação eliminada com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Operação.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Operação em falta", "Por favor, selecione uma Operação.");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param opc
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, Operacao opc, String titulo, Integer pos) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        operacaoController = fxmlLoader.getController();
        operacaoController.setOperationView(opc, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllOperations());
        configColumns();
        tblOperacoes.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Operações
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipoOperacao.setCellValueFactory(op -> new SimpleStringProperty(op.getValue().getOperationType().getId()));
        colRefProduto.setCellValueFactory(op -> new SimpleStringProperty(op.getValue().getProduct().getReference()));
        colVersaoProduto.setCellValueFactory(op -> new SimpleStringProperty(op.getValue().getProduct().getVersion()));
        colOrdem.setCellValueFactory(new PropertyValueFactory<>("order"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMaoObra.setCellValueFactory(new PropertyValueFactory<>("labor"));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("time"));
        colInstrucoes.setCellValueFactory(new PropertyValueFactory<>("technicalInstructions"));
    }

}
