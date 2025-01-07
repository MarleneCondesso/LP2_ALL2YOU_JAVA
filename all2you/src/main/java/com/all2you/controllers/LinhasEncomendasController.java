/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;

import com.all2you.App;
import com.all2you.models.encomendas.Encomenda;
import com.all2you.models.encomendas.LinhaEncomenda;
import com.all2you.services.EncomendasService;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

/**
 *
 * @author Marlene Lima
 */
public class LinhasEncomendasController implements Initializable {

    @FXML
    public TableView<LinhaEncomenda> tvLinhaEncomenda;

    @FXML
    private TableColumn<LinhaEncomenda, String> idEncCol;

    @FXML
    private TableColumn<LinhaEncomenda, String> refProdCol;

    @FXML
    private TableColumn<LinhaEncomenda, String> versProdCol;

    @FXML
    private TableColumn<LinhaEncomenda, String> descCol;

    @FXML
    private TableColumn<LinhaEncomenda, Integer> quantidadeCol;

    @FXML
    private TableColumn<LinhaEncomenda, Double> precoCol;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private EncomendasService service = new EncomendasService();

    Stage stage;
    Scene scene;

    Integer position;

    List<Encomenda> lista;
    ObservableList obsList;

    EncomendasController encomendasController;
    LinhaEncomendaController linhaEncController;
    LinhasEncomendasController linhaEncsController;
    Encomenda encomenda;
    LinhaEncomenda linhaEncomenda;

    public LinhasEncomendasController() {

    }

    /**
     * Método para passar valores de EncomendasController
     *
     * @param enc
     * @param pos
     * @param encomendasController
     */
    public void setLinhasEncomendaView(Encomenda enc, Integer pos, EncomendasController encomendasController) {

        this.encomendasController = encomendasController;

        if (!isNull(enc)) {

            this.encomenda = enc;

            System.out.println(enc.getId());
            this.position = pos;

//            refreshTable(this.encomenda);
        }

    }

    /**
     * Método para adicionar Linha Encomenda
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void adicionarLinhaEncomenda(ActionEvent event) throws IOException {
        showModalView("linhaEncomenda", null, this.encomenda, "Adicionar Linha Encomenda", null);
    }

    /**
     * Método para editar Linha Encomenda
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editarLinhaEncomenda(ActionEvent event) throws IOException {
        LinhaEncomenda linhaEnc = tvLinhaEncomenda.getSelectionModel().getSelectedItem();

        Integer pos = tvLinhaEncomenda.getSelectionModel().getSelectedIndex();

        if (!isNull(linhaEnc)) {

            showModalView("linhaEncomenda", linhaEnc, this.encomenda, "Editar Linha Encomenda", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Linha Encomenda em falta", "Por favor, selecione uma Linha Encomenda.");
        }
    }

    /**
     * Método para eliminar Linha Encomenda
     *
     * @param event
     */
    @FXML
    void eliminarLinhaEncomenda(ActionEvent event) {
        LinhaEncomenda linhaEnc = tvLinhaEncomenda.getSelectionModel().getSelectedItem();

        if (linhaEnc != null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Linha Encomenda?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteOrderLine(linhaEnc);

                if (response) {
                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Linha Encomenda eliminada com SUCESSO!");

                    refreshTable(this.encomenda);
                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Linha Encomenda.");
                }

            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Linha Encomenda em falta", "Por favor, selecione uma Linha Encomenda.");
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

    }

    /**
     * Método para preencher e atualizar dados na tabela Linhas Encomenda.
     *
     * @param encomenda
     */
    public void refreshTable(Encomenda encomenda) {
        obsList = FXCollections.observableArrayList(service.getOrderLine(encomenda.getId()));
        configColumns();
        tvLinhaEncomenda.getItems().setAll(obsList);
    }

    /**
     * Método para configurar as propriedades nas colunas da tabela Linhas
     * Encomenda.
     */
    private void configColumns() {
        idEncCol.setCellValueFactory(id -> new SimpleObjectProperty(id.getValue().getOrder().getId()));
        refProdCol.setCellValueFactory(prod -> new SimpleObjectProperty(prod.getValue().getProduct().getReference()));
        versProdCol.setCellValueFactory(prod -> new SimpleObjectProperty(prod.getValue().getProduct().getVersion()));
        descCol.setCellValueFactory(descricao -> new SimpleObjectProperty(descricao.getValue().getDescription()));
        quantidadeCol.setCellValueFactory(quantidade -> new SimpleObjectProperty(quantidade.getValue().getQuantity()));
        precoCol.setCellValueFactory(preco -> new SimpleObjectProperty(preco.getValue().getUnitPrice()));

    }

    /**
     * Método para abrir a view Linha Encomenda
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalView(String fxml, LinhaEncomenda linhaEnc, Encomenda enc, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        linhaEncController = fxmlLoader.getController();
        linhaEncController.setLinhaEncomendaView(linhaEnc, enc, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
