package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.artigos.Produto;
import com.all2you.services.ArtigosService;
import com.all2you.utils.Estados;
import static com.all2you.utils.Estados.estadoAtivoInativo;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
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

/**
 *
 * @author ruteg
 */
public class ProdutosController implements Initializable {

    @FXML
    public TableView<Produto> tblProdutos;
    @FXML
    private TableColumn<Produto, String> colReferencia;
    @FXML
    private TableColumn<Produto, String> colVersao;
    @FXML
    private TableColumn<Produto, String> colDesignacao;
    @FXML
    private TableColumn<Produto, String> colDesignacaoComercial;
    @FXML
    private TableColumn<Produto, Integer> colQtdLote;
    @FXML
    private TableColumn<Produto, String> colUnidade;
    @FXML
    private TableColumn<Produto, String> colEstado;

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

    private ProdutoController produtoController;
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

        FilteredList<Produto> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prod -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (prod.getDesignation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (prod.getCommercialDesignation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (prod.getReference().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (prod.getVersion().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(prod.getBatchQuantity()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (prod.getUnitType().getId().toLowerCase().contains(lowerCaseFilter) || prod.getUnitType().getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (estadoAtivoInativo(prod.getStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<Produto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblProdutos.comparatorProperty());
        tblProdutos.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Produto
     */
    @FXML
    private void adicionarProduto(ActionEvent e) throws IOException {

        showModalView("produto", null, "Adicionar Produto", null);

    }

    /**
     * Método para abrir janela de edição de Produto
     */
    @FXML
    private void editarProduto(ActionEvent e) throws IOException {

        Produto prod = tblProdutos.getSelectionModel().getSelectedItem();
        Integer pos = tblProdutos.getSelectionModel().getSelectedIndex();

        if (!isNull(prod)) {

            showModalView("produto", prod, "Editar Produto", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Produto em falta", "Por favor, selecione um Produto");
        }

    }

    /**
     * Método para botão Inativar Produto
     */
    @FXML
    private void inativarProduto() {
        Produto prod = tblProdutos.getSelectionModel().getSelectedItem();

        if (prod != null) {

            if (prod.getStatus()) {

                AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Inativar", "Tem a certeza que pretende inativar este Produto?");

                if (alerta.getButton().get() == ButtonType.OK) {

                    prod.setStatus(false);
                    Boolean response = service.updateProduct(prod);

                    if (response) {

                        int i = tblProdutos.getSelectionModel().getSelectedIndex();
                        tblProdutos.getItems().set(i, prod);

                        AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Produto inativado com SUCESSO!");

                    } else {
                        AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível inativar o Produto.");
                    }

                }
                if (alerta.getButton().get() == ButtonType.NO) {
                    alerta.getAlert().close();
                }
            } else {
                AlertBoxController alertaInfo = new AlertBoxController(Alert.AlertType.INFORMATION, "", "O Produto já se encontra inativo.");
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Produto em falta", "Por favor, selecione um Produto");
        }

    }

    /**
     * Método para botão Eliminar Produto
     */
    @FXML
    private void eliminarProduto() {
        Produto prod = tblProdutos.getSelectionModel().getSelectedItem();

        if (prod != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Produto?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteProduct(prod);

                if (response) {

//                    tblProdutos.getItems().remove(prod);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Produto eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Produto.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Produto em falta", "Por favor, selecione um Produto");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param prod
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, Produto prod, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        produtoController = fxmlLoader.getController();
        produtoController.setProductView(prod, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllProducts());
        configColumns();
        tblProdutos.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Produtos
     */
    private void configColumns() {
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colVersao.setCellValueFactory(new PropertyValueFactory<>("version"));
        colDesignacao.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colDesignacaoComercial.setCellValueFactory(new PropertyValueFactory<>("commercialDesignation"));
        colQtdLote.setCellValueFactory(new PropertyValueFactory<>("batchQuantity"));
        colUnidade.setCellValueFactory(prod -> new SimpleStringProperty(prod.getValue().getUnitType().getDescription() + " (" + prod.getValue().getUnitType().getId() + ")"));
        colEstado.setCellValueFactory(prod -> new SimpleStringProperty(Estados.estadoAtivoInativo(prod.getValue().getStatus())));
    }

}
