package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.clientes.contactos.TipoContacto;
import com.all2you.services.ContactosService;
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
public class TipoContactosController implements Initializable {

    @FXML
    public TableView<TipoContacto> tblTiposContactos;
    @FXML
    private TableColumn<TipoContacto, String> colTipo;
    @FXML
    private TableColumn<TipoContacto, String> colDescricao;
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

    private TipoContactoController tipoContactoController;
    private ContactosService service = new ContactosService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableFromDB();

        FilteredList<TipoContacto> filteredData = new FilteredList<>(obsList, b -> true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tpCont -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(tpCont.getType()).contains(lowerCaseFilter)) {
                    return true;
                } else if (tpCont.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        }
        );

        SortedList<TipoContacto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty()
                .bind(tblTiposContactos.comparatorProperty());
        tblTiposContactos.setItems(sortedData);
    }

    /**
     * Método para abrir janela de criação de Tipo de Contacto
     */
    @FXML
    private void adicionarTipoContacto(ActionEvent e) throws IOException {

        showModalView("tipoContacto", null, "Adicionar Tipo de Contacto", null);

    }

    /**
     * Método para abrir janela de edição de Tipo de Contacto
     */
    @FXML
    private void editarTipoContacto(ActionEvent e) throws IOException {

        TipoContacto tpCont = tblTiposContactos.getSelectionModel().getSelectedItem();
        Integer pos = tblTiposContactos.getSelectionModel().getSelectedIndex();

        if (!isNull(tpCont)) {

            showModalView("tipoContacto", tpCont, "Editar Tipo de Contacto", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Contacto em falta", "Por favor, selecione um Tipo de Contacto");
        }

    }

    /**
     * Método para botão Eliminar Tipo de Contacto
     */
    @FXML
    private void eliminarTipoContacto() {
        TipoContacto tpCont = tblTiposContactos.getSelectionModel().getSelectedItem();

        if (tpCont != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Tipo de Contacto?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteContactType(tpCont);

                if (response) {

//                    tblTiposContactos.getItems().remove(tpCont);
                     refreshTableFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Contacto eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Tipo de Contacto.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Tipo de Contacto em falta", "Por favor, selecione um Tipo de Contacto");
        }

    }

    /**
     * Método para abrir a view
     *
     * @param fxml
     * @param tpCont
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, TipoContacto tpCont, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        tipoContactoController = fxmlLoader.getController();
        tipoContactoController.setContactTypeView(tpCont, titulo, pos, this);

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
        obsList = FXCollections.observableArrayList(service.getAllContactTypes());

        configColumns();
        tblTiposContactos.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Tipos de
     * Contacto
     */
    private void configColumns() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

}
