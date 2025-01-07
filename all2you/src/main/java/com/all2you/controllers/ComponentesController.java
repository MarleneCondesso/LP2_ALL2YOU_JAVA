package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.artigos.Componente;
import com.all2you.services.ArtigosService;
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
public class ComponentesController implements Initializable {

    @FXML
    public TableView<Componente> tblComponentes;
    @FXML
    private TableColumn<Componente, String> colReferencia;
    @FXML
    private TableColumn<Componente, String> colVersao;
    @FXML
    private TableColumn<Componente, String> colOperacao;
    @FXML
    private TableColumn<Componente, String> colDesignacaoComercial;
    @FXML
    private TableColumn<Componente, Integer> colQuantidade;
    @FXML
    private TableColumn<Componente, String> colUnidade;
    @FXML
    private TableColumn<Componente, String> colAlternativa;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Pane pane;
    @FXML
    private Scene scene;
    @FXML
    public Stage stage;
    @FXML
    private TextField txtFiltro;

    ObservableList obsList;

    private ComponenteController componenteController;
    private ArtigosService service = new ArtigosService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("teste");
        refreshTableFromDB();

        FilteredList<Componente> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(comp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (comp.getReference().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (comp.getVersion().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (comp.getComercialDesignation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(comp.getQuantity()).contains(lowerCaseFilter)) {
                    return true;
                } else if (comp.getAlternative().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (comp.getUnitType().getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (comp.getOperation().getOperationType().getId().toLowerCase().contains(lowerCaseFilter)
                        || comp.getOperation().getOperationType().getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<Componente> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblComponentes.comparatorProperty());
        tblComponentes.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Componente
     */
    @FXML
    private void adicionarComponente(ActionEvent e) throws IOException {
        showModalView("componente", null, "Adicionar Componente", null);
    }

    /**
     * Método para abrir janela de edição de Componente
     */
    @FXML
    private void editarComponente(ActionEvent e) throws IOException {

        Componente comp = tblComponentes.getSelectionModel().getSelectedItem();
        Integer pos = tblComponentes.getSelectionModel().getSelectedIndex();

        if (!isNull(comp)) {

            showModalView("componente", comp, "Editar Componente", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Componente em falta", "Por favor, selecione um Componente");
        }

    }

    /**
     * Método para botão Eliminar Componente
     *
     * @param event
     */
    @FXML
    private void eliminarComponente() {
        Componente comp = tblComponentes.getSelectionModel().getSelectedItem();

        if (comp != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Componente?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteComponent(comp);

                if (response) {

//                    tblComponentes.getItems().remove(comp);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Componente eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Componente.");
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
     * @param comp
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, Componente comp, String titulo, Integer pos) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        componenteController = fxmlLoader.getController();
        componenteController.setComponentView(comp, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllComponents());
        configColumns();
        tblComponentes.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Componentes
     */
    private void configColumns() {
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colVersao.setCellValueFactory(new PropertyValueFactory<>("version"));
        colOperacao.setCellValueFactory(comp -> new SimpleStringProperty("Tipo: " + comp.getValue().getOperation().getOperationType().getId()));
        colDesignacaoComercial.setCellValueFactory(new PropertyValueFactory<>("comercialDesignation"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnidade.setCellValueFactory(comp -> new SimpleStringProperty(comp.getValue().getUnitType().getDescription() + " (" + comp.getValue().getUnitType().getId() + ")"));
        colAlternativa.setCellValueFactory(new PropertyValueFactory<>("alternative"));
    }

}
