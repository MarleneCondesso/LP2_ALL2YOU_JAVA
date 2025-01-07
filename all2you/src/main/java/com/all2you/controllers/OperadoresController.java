package com.all2you.controllers;

import com.all2you.App;
import com.all2you.utils.Estados;
import com.all2you.models.Operador;
import com.all2you.services.OperadoresService;
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
 * @author ruteg
 */
public class OperadoresController implements Initializable {

    @FXML
    public TableView<Operador> tblOperadores;
    @FXML
    private TableColumn<Operador, Integer> colId;
    @FXML
    private TableColumn<Operador, String> colNome;
    @FXML
    private TableColumn<Operador, String> colEstado;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEstado;
    @FXML
    private Pane pane;
    @FXML
    private Scene scene;
    @FXML
    public Stage stage;
    @FXML
    private TextField txtFiltro;

    ObservableList obsList;

    private OperadorController operadorController;
    private OperadoresService service = new OperadoresService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableFromDB();

        FilteredList<Operador> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(op -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (op.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(op.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (estadoAtivoInativo(op.getStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<Operador> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblOperadores.comparatorProperty());
        tblOperadores.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Operador
     */
    @FXML
    private void adicionarOperador(ActionEvent e) throws IOException {

        showModalView("operador", null, "Adicionar Operador", null);

    }

    /**
     * Método para abrir janela de edição de Operador
     */
    @FXML
    private void editarOperador(ActionEvent e) throws IOException {

        Operador op = tblOperadores.getSelectionModel().getSelectedItem();
        Integer pos = tblOperadores.getSelectionModel().getSelectedIndex();

        if (!isNull(op)) {

            showModalView("operador", op, "Editar Operador", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Operador em falta", "Por favor, selecione um Operador");
        }

    }

    /**
     * Método para botão Alterar Estado do Operador
     */
    @FXML
    private void alterarEstadoOperador() {
        Operador op = tblOperadores.getSelectionModel().getSelectedItem();

        if (op != null) {

            AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Inativar", "Tem a certeza que pretende alterar o Estado a este Operador?");

            if (alerta.getButton().get() == ButtonType.OK) {

                
                op.setStatus(!op.getStatus());
                
                Boolean response = service.updateOperator(op);

                if (response) {

                    int i = tblOperadores.getSelectionModel().getSelectedIndex();
                    
                    System.out.println("Index =" + i);
                    System.out.println(op.getStatus());
                    
                    try {
                        tblOperadores.getItems().set(i, op);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                    System.out.println(tblOperadores.getItems().size());
                    
                    
                    AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Estado do Operador alterado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível alterar o Estado do Operador.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Operador em falta", "Por favor, selecione um Operador");
        }

    }

    /**
     * Método para botão Eliminar Operador
     */
    @FXML
    private void eliminarOperador() {
        Operador op = tblOperadores.getSelectionModel().getSelectedItem();

        if (op != null) {
            AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Operador?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteOperator(op);

                if (response) {

//                    tblOperadores.getItems().remove(op);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Operador eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar o Operador.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Operador em falta", "Por favor, selecione um Operador");
        }

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
    private void showModalView(String fxml, Operador op, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        operadorController = fxmlLoader.getController();
        operadorController.setOperatorView(op, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllOperators());

        configColumns();
        tblOperadores.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Operadores
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEstado.setCellValueFactory(op -> new SimpleStringProperty(Estados.estadoAtivoInativo(op.getValue().getStatus())));
    }

}
