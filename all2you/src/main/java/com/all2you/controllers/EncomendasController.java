package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.encomendas.Encomenda;
import com.all2you.services.EncomendasService;
import com.all2you.utils.LocalDateTimeAttributeConverter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Date;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EncomendasController implements Initializable {

    @FXML
    public TableView<Encomenda> tvEncomenda;
    @FXML
    private TableColumn<Encomenda, String> idCol;
    @FXML
    private TableColumn<Encomenda, Integer> nifCol;
    @FXML
    private TableColumn<Encomenda, String> idMoradaCol;
    @FXML
    private TableColumn<Encomenda, Date> dataDocumentoCol;
    @FXML
    private TableColumn<Encomenda, Integer> tpDescCol;
    @FXML
    private TableColumn<Encomenda, Double> valorDescCol;
    @FXML
    private TableColumn<Encomenda, LocalDate> dataCriacaoCol;
    @FXML
    private TableColumn<Encomenda, LocalDate> dataModifCol;

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;

    private Scene scene;
    private Stage stage;

    List<Encomenda> lista;
    ObservableList obsList;

    private EncomendasService service = new EncomendasService();
    EncomendaController encomendaController;
    LinhasEncomendasController linhaEncsController;

    /**
     * Ação de clique na tabela Encomendas para abrir Tabela de Linhas de
     * Encomendas.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void tvEncomendaLinha(MouseEvent event) throws IOException {
        Encomenda enc = tvEncomenda.getSelectionModel().getSelectedItem();

        Integer pos = tvEncomenda.getSelectionModel().getSelectedIndex();
        
        if (event.getClickCount() == 2) {
            if (!isNull(enc)) {

                showModalViewLinha("linhaEncomendas", enc, "Lista de Linha de Encomendas", pos);

            } else {
                AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Encomenda em falta", "Por favor, selecione uma Encomenda.");
            }
        }

    }

    /**
     * Método para o botão Adicionar
     *
     * @param e
     * @throws IOException
     */
    @FXML
    void adicionarEncomenda(ActionEvent e) throws IOException {

        showModalView("encomenda", null, "Adicionar Encomenda", null);
    }

    /**
     * Método para o botão Editar
     *
     * @param e
     * @throws IOException
     */
    @FXML
    void editarEncomenda(ActionEvent e) throws IOException {
        Encomenda enc = tvEncomenda.getSelectionModel().getSelectedItem();

        Integer pos = tvEncomenda.getSelectionModel().getSelectedIndex();

        if (!isNull(enc)) {

            showModalView("encomenda", enc, "Editar Encomenda", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Encomenda em falta", "Por favor, selecione uma Encomenda.");
        }
    }

    /**
     * Método para o botão Eliminar
     */
    @FXML
    void eliminarEncomenda() {
        Encomenda enc = tvEncomenda.getSelectionModel().getSelectedItem();

        if (enc != null) {
            AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Encomenda?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteOrder(enc);

                if (response) {
                    AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Encomenda eliminada com SUCESSO!");

                    refreshTable();
                } else {
                    AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar a Encomenda.");
                }

            }
        }
    }

    /**
     * Método de Inicialização (interface Initializable).
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();

    }

    /**
     * Método para preencher e atualizar dados na tabela.
     */
    public void refreshTable() {
        obsList = FXCollections.observableArrayList(service.getAllOrders());
        configColumns();
        tvEncomenda.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Encomendas.
     */
    private void configColumns() {
        tvEncomenda.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nifCol.setCellValueFactory(nif -> new SimpleObjectProperty(nif.getValue().getClient().getNif()));
        idMoradaCol.setCellValueFactory(morada -> new SimpleObjectProperty(morada.getValue().getMorada().getAddress()));
        tpDescCol.setCellValueFactory(tpDesconto -> new SimpleObjectProperty(tpDesconto.getValue().getDiscountType().getDescription()));
        valorDescCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        dataDocumentoCol.setCellValueFactory(dataDocumento -> new SimpleObjectProperty(dataDocumento.getValue().getDocumentDate()));
        dataCriacaoCol.setCellValueFactory(dataCriacao -> new SimpleObjectProperty(dataCriacao.getValue().getCreationDate()));
        dataModifCol.setCellValueFactory(dataModificacao -> new SimpleObjectProperty(dataModificacao.getValue().getLastModification()));

    }

    /**
     * Método para abrir a view Encomenda
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, Encomenda enc, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        encomendaController = fxmlLoader.getController();
        encomendaController.setEncomendaView(enc, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para abrir a view Linhas Encomenda
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalViewLinha(String fxml, Encomenda enc, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        linhaEncsController = fxmlLoader.getController();
        linhaEncsController.setLinhasEncomendaView(enc, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

}
